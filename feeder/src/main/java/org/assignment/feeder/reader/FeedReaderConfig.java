package org.assignment.feeder.reader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeedReaderConfig {
	
	@Value("${reader.urls}")
	private String[] urls;
	
	@Value("${reader.feedLimit: 10}")
	private int limit;

	public String[] getUrls() {
		return urls;
	}

	public void setUrls(String[] urls) {
		this.urls = urls;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}