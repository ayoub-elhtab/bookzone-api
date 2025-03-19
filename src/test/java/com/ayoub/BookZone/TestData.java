package com.ayoub.BookZone;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.ayoub.BookZone.model.Book;
import com.ayoub.BookZone.model.BookEntity;

public class TestData {

    private TestData() {}

    public static Book testBook(){
        return Book.builder()
                .id(6L)
                .title("The Prince")
                .author("Niccolò Machiavelli")
                .category("Political Philosophy")
                .build();
    }

    public static BookEntity testBookEntity(){
        return BookEntity.builder()
                .id(6L)
                .title("The Prince")
                .author("Niccolò Machiavelli")
                .category("Political Philosophy")
                .build();
    }

    public static MockMultipartFile testImageFile() {
        return new MockMultipartFile(
                "image", "test-image.jpg", MediaType.IMAGE_JPEG_VALUE, "test image content".getBytes()
        );
    }

    public static MockMultipartFile testPdfFile() {
        return new MockMultipartFile(
                "pdf", "test-document.pdf", MediaType.APPLICATION_PDF_VALUE, "test pdf content".getBytes()
        );
    }
}
