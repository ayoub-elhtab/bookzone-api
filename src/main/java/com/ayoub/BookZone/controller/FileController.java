package com.ayoub.BookZone.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {
   
    private Path rootUploadDir = Paths.get("uploads");
    
    // Setter for testing
    public void setRootUploadDir(Path rootUploadDir) {
        this.rootUploadDir = rootUploadDir;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFiles( @RequestParam("image") MultipartFile imageFile, @RequestParam("pdf") MultipartFile pdfFile) {
        try {
            // Define directories for images and PDFs
            Path imageDir = rootUploadDir.resolve("images");
            Path pdfDir = rootUploadDir.resolve("pdfs");

            // Create directories if they don't exist
            if (!Files.exists(imageDir)) {
                Files.createDirectories(imageDir);
            }
            if (!Files.exists(pdfDir)) {
                Files.createDirectories(pdfDir);
            }

            // Save the image file
            String imageFileName = imageFile.getOriginalFilename();
            Path imagePath = imageDir.resolve(imageFileName);
            Files.copy(imageFile.getInputStream(), imagePath);

            // Save the PDF file
            String pdfFileName = pdfFile.getOriginalFilename();
            Path pdfPath = pdfDir.resolve(pdfFileName);
            Files.copy(pdfFile.getInputStream(), pdfPath);

            // Create URLs for later download
            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", "/files/download/images/" + imageFileName);
            response.put("pdfUrl", "/files/download/pdfs/" + pdfFileName);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/download/{folder}/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String folder, @PathVariable String filename) throws IOException {
        Path filePath = rootUploadDir.resolve(folder).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
