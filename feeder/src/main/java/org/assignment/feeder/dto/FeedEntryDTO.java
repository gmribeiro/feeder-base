package org.assignment.feeder.dto;

public class FeedEntryDTO {

	private String title;
	private String description;
	private String document;
	private String url;
	private String minimizedURL;
	private String imageURL;
	private Long timestamp;
	
	private FeedEntryDTO(FeedEntryDTOBuilder builder) {
		this.title = builder.title;
		this.description = builder.description;
		this.document = builder.document;
		this.url = builder.url;
		this.minimizedURL = builder.minimizedURL;
		this.imageURL = builder.imageURL;
		this.timestamp = builder.timestamp;
	}
	
	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
	
	public String getDocument() {
		return document;
	}

	public String getUrl() {
		return url;
	}

	public String getMinimizedURL() {
		return minimizedURL;
	}

	public String getImageURL() {
		return imageURL;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public static class FeedEntryDTOBuilder {
		
		private String title;
		private String description;
		private String document;
		private String url;
		private String minimizedURL;
		private String imageURL;
		private Long timestamp;
		
		public FeedEntryDTOBuilder withTitle(String title) {
			this.title = title;
			return this;
		}
		
		public FeedEntryDTOBuilder withDescription(String description) {
			this.description = description;
			return this;
		}
		
		public FeedEntryDTOBuilder withDocument(String document) {
			this.document = document;
			return this;
		}
		
		public FeedEntryDTOBuilder withURL(String url) {
			this.url = url;
			return this;
		}
		
		public FeedEntryDTOBuilder withMinimizedURL(String minimizedURL) {
			this.minimizedURL = minimizedURL;
			return this;
		}
		
		public FeedEntryDTOBuilder withImageURL(String imageURL) {
			this.imageURL = imageURL;
			return this;
		}
		
		public FeedEntryDTOBuilder withTimestamp(Long timestamp) {
			this.timestamp = timestamp;
			return this;
		}
		
		public FeedEntryDTO build() {
			return new FeedEntryDTO(this);
		}
	}
}