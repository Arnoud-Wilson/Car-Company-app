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
import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/load")
public class UploadDownloadController {

    private final UploadDownloadService uploadDownloadService;

    public UploadDownloadController(UploadDownloadService uploadDownloadService) {
        this.uploadDownloadService = uploadDownloadService;
    }


    @PostMapping("single/upload")
    public FileUploadResponseDto singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {


        // next line makes url. example "http://localhost:8080/download/naam.jpg"
        FileDocument fileDocument = uploadDownloadService.uploadFileDocument(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("load//download/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        String contentType = file.getContentType();

        return new FileUploadResponseDto(fileDocument.getFileName(), contentType, url);
    }

    @GetMapping("/download/{fileName}")
    ResponseEntity<byte[]> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        FileDocument document = uploadDownloadService.singleFileDownload(fileName, request);


        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + document.getFileName()).body(document.getDocFile());
    }

    @GetMapping("/getAll")
    public Collection<FileDocument> getAllFromDB(){
        return uploadDownloadService.getALlFromDB();
    }
}
