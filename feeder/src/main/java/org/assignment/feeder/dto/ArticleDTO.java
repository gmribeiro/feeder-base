package org.assignment.feeder.dto;

public class ArticleDTO {
	
	private String id;
	private String title;
	private String description;
	private String document;
	private String url;
	private String minUrl;
	private String image;
	private Long publicationDate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDocument() {
		return document;
	}
	
	public void setDocument(String document) {
		this.document = document;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getMinUrl() {
		return minUrl;
	}
	
	public void setMinUrl(String minUrl) {
		this.minUrl = minUrl;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public Long getPublicationDate() {
		return publicationDate;
	}
	
	public void setPublicationDate(Long publicationDate) {
		this.publicationDate = publicationDate;
	}
}
