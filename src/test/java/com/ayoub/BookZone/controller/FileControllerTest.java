package com.ayoub.BookZone.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.ayoub.BookZone.TestData.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.servlet.multipart.max-file-size=10MB", "spring.servlet.multipart.max-request-size=10MB"})
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Create a temporary directory that is automatically cleaned up after tests
    @TempDir
    static Path tempDir;

    @TestConfiguration
    static class FileControllerTestConfig {
        @Bean
        public FileController fileController() {
            FileController controller = new FileController();
            // Override the upload directory to use the temporary directory
            controller.setRootUploadDir(tempDir);
            return controller;
        }
    }

    @BeforeEach
    void setUp() throws IOException {
        // Create the directories
        Files.createDirectories(tempDir.resolve("images"));
        Files.createDirectories(tempDir.resolve("pdfs"));
    }

    @Test
    public void testUploadFilesReturnsHTTP200AndCorrectURLsWhenFilesAreValid() throws Exception {
        MockMultipartFile imageFile = testImageFile();
        MockMultipartFile pdfFile = testPdfFile();

        mockMvc.perform(multipart("/api/files/upload")
                .file(imageFile)
                .file(pdfFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imageUrl").value("/files/download/images/test-image.jpg"))
                .andExpect(jsonPath("$.pdfUrl").value("/files/download/pdfs/test-document.pdf"));

        // Verify files were saved correctly
        Path imagePath = tempDir.resolve("images").resolve("test-image.jpg");
        Path pdfPath = tempDir.resolve("pdfs").resolve("test-document.pdf");
        
        assertTrue(Files.exists(imagePath));
        assertTrue(Files.exists(pdfPath));
        assertEquals("test image content", new String(Files.readAllBytes(imagePath)));
        assertEquals("test pdf content", new String(Files.readAllBytes(pdfPath)));
    }

    @Test
    public void testUploadFilesReturnsHTTP500WhenFileOperationFails() throws Exception {
        // Create a faulty image file that simulates an IOException
        MockMultipartFile faultyImage = new MockMultipartFile(
                "image",
                "faulty-image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        ) 
        {
            @Override
            public java.io.InputStream getInputStream() throws IOException {
                throw new IOException("Simulated failure");
            }
        };

        MockMultipartFile pdfFile = testPdfFile();

        mockMvc.perform(multipart("/api/files/upload")
                .file(faultyImage)
                .file(pdfFile))
            .andExpect(status().isInternalServerError());
    }


    @Test
    public void testDownloadFileReturnsHTTP200AndFileWhenFileExists() throws Exception {
        // Create a test file to download
        String testContent = "This is test content";
        Path testFile = tempDir.resolve("images").resolve("existing-image.jpg");
        Files.write(testFile, testContent.getBytes());

        mockMvc.perform(get("/api/files/download/images/existing-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(testContent.getBytes()));
    }

    @Test
    public void testDownloadFileReturnsHTTP404WhenFileDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/files/download/images/non-existent-file.jpg"))
                .andExpect(status().isNotFound());
    }
}