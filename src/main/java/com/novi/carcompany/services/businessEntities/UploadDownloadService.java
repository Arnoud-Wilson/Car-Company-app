package com.novi.carcompany.services.businessEntities;

import com.novi.carcompany.models.businessEntities.FileDocument;
import com.novi.carcompany.repositories.DocFileRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

        return document;
    }
}
