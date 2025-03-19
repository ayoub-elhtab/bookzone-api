package com.ayoub.BookZone.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
public class BookEntity {

	@Id
	private Long id;
	private String title;
	private String author;
	private String category;
	private String language;
	private String description;
	private int pages;
	private String imageUrl;
	private String pdfUrl;
	
	
	public BookEntity() {
	}

	public BookEntity(Builder builder) {
		
		this.id = builder.id;
		this.title = builder.title;
		this.author = builder.author;
		this.category = builder.category;
		this.language = builder.language;
		this.description = builder.description;
		this.pages = builder.pages;
		this.imageUrl = builder.imageUrl;
		this.pdfUrl = builder.pdfUrl;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder{
		private Long id;
		private String title;
		private String author;
		private String category;
		private String language;
		private String description;
		private int pages;
		private String imageUrl;
		private String pdfUrl;
		
		public Builder id(Long id) {
			this.id = id;
			return this;
		}
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		public Builder author(String author) {
			this.author = author;
			return this;
		}
		public Builder category(String category) {
			this.category = category;
			return this;
		}
		public Builder language(String language) {
			this.language = language;
			return this;
		}
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		public Builder pages(int pages) {
			this.pages = pages;
			return this;
		}
		public Builder imageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
			return this;
		}
		public Builder pdfUrl(String pdfUrl) {
			this.pdfUrl = pdfUrl;
			return this;
		}
		
		
		public BookEntity build() {
            return new BookEntity(this);
        }
	}
	
	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}
	
	
	
	
}
