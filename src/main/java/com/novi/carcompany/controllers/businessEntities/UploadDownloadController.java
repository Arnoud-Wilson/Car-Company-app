package com.novi.carcompany.controllers.businessEntities;

import com.novi.carcompany.dtos.businessEntities.FileUploadResponseDto;
import com.novi.carcompany.models.businessEntities.FileDocument;
import com.novi.carcompany.services.businessEntities.UploadDownloadService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@CrossOrigin
@RestController
public class UploadDownloadController {

    private final UploadDownloadService uploadDownloadService;

    public UploadDownloadController(UploadDownloadService uploadDownloadService) {
        this.uploadDownloadService = uploadDownloadService;
    }


    @PostMapping("single/uploadDb")
    public FileUploadResponseDto singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {


        // next line makes url. example "http://localhost:8080/download/naam.jpg"
        FileDocument fileDocument = uploadDownloadService.uploadFileDocument(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        String contentType = file.getContentType();

        return new FileUploadResponseDto(fileDocument.getFileName(), url, contentType );
    }

    //    get for single download
    @GetMapping("/downloadFromDB/{fileName}")
    ResponseEntity<byte[]> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        FileDocument document = uploadDownloadService.singleFileDownload(fileName, request);


        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + document.getFileName()).body(document.getDocFile());
    }

    //    post for multiple uploads to database
    @PostMapping("/multiple/upload/db")
    List<FileUploadResponseDto> multipleUpload(@RequestParam("files") MultipartFile [] files) {

        if(files.length > 7) {
            throw new RuntimeException("to many files selected");
        }

        return uploadDownloadService.createMultipleUpload(files);

    }

    @GetMapping("zipDownload/db")
    public void zipDownload(@RequestBody String[] files, HttpServletResponse response) throws IOException {

        uploadDownloadService.getZipDownload(files, response);

    }

    @GetMapping("/getAll/db")
    public Collection<FileDocument> getAllFromDB(){
        return uploadDownloadService.getALlFromDB();
    }
}
