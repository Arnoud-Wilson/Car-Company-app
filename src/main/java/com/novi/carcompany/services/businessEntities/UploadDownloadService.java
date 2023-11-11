package com.novi.carcompany.services.businessEntities;

import com.novi.carcompany.dtos.businessEntities.FileUploadResponseDto;
import com.novi.carcompany.models.businessEntities.FileDocument;
import com.novi.carcompany.models.businessEntities.Part;
import com.novi.carcompany.repositories.DocFileRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
public class UploadDownloadService {
    private final DocFileRepository docFileRepository;

    public UploadDownloadService(DocFileRepository docFileRepository){
        this.docFileRepository = docFileRepository;
    }

    public Collection<FileDocument> getALlFromDB() {
        return docFileRepository.findAll();
    }

    public FileDocument uploadFileDocument(MultipartFile file) throws IOException {
        String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileDocument fileDocument = new FileDocument();
        fileDocument.setFileName(name);
        fileDocument.setDocFile(file.getBytes());

        docFileRepository.save(fileDocument);

        return fileDocument;

    }

    public FileDocument singleFileDownload(String fileName, HttpServletRequest request){

        FileDocument document = docFileRepository.findByFileName(fileName);

//        this mediaType decides witch type you accept if you only accept 1 type
//        MediaType contentType = MediaType.IMAGE_JPEG;
//        this is going to accept multiple types

        String mimeType = request.getServletContext().getMimeType(document.getFileName());

//        for download attachment use next line
//        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + resource.getFilename()).body(resource);
//        for showing image in browser
        return document;

    }

    public List<FileUploadResponseDto> createMultipleUpload(MultipartFile[] files){
        List<FileUploadResponseDto> uploadResponseList = new ArrayList<>();
        Arrays.stream(files).forEach(file -> {

            String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            FileDocument fileDocument = new FileDocument();
            fileDocument.setFileName(name);
            try {
                fileDocument.setDocFile(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            docFileRepository.save(fileDocument);

//            next line makes url. example "http://localhost:8080/download/naam.jpg"
            String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(name).toUriString();

            String contentType = file.getContentType();

            FileUploadResponseDto response = new FileUploadResponseDto(name, contentType, url);

            uploadResponseList.add(response);
        });
        return uploadResponseList;
    }

    public void getZipDownload(String[] files, HttpServletResponse response) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            Arrays.stream(files).forEach(file -> {
                try {
                    createZipEntry(file, zos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            zos.finish();

            response.setStatus(200);
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=zipfile");
        }
    }

    public Resource downLoadFileDatabase(String fileName) {

        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(fileName).toUriString();

        Resource resource;

        try {
            resource = new UrlResource(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }

        if(resource.exists()&& resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or not readable");
        }
    }

    public void createZipEntry(String file, ZipOutputStream zos) throws IOException {

        Resource resource = downLoadFileDatabase(file);
        ZipEntry zipEntry = new ZipEntry(Objects.requireNonNull(resource.getFilename()));
        try {
            zipEntry.setSize(resource.contentLength());
            zos.putNextEntry(zipEntry);

            StreamUtils.copy(resource.getInputStream(), zos);

            zos.closeEntry();
        } catch (IOException e) {
            System.out.println("some exception while zipping");
        }
    }
}
