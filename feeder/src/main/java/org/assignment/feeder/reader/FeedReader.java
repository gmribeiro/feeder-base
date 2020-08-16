package org.assignment.feeder.reader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assignment.feeder.dto.FeedEntryDTO;
import org.assignment.feeder.dto.FeedEntryDTO.FeedEntryDTOBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.syndication.feed.module.DCModuleImpl;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

@Component
public class FeedReader {
	
	private static Logger LOGGER = LoggerFactory.getLogger(FeedReader.class);
	
	@Autowired
	private FeedReaderConfig config;
	
	@SuppressWarnings("unchecked")
	public List<FeedEntryDTO> read() {
		List<FeedEntryDTO> entries = new ArrayList<>();
		
		Arrays.asList(config.getUrls()).stream().forEach(u -> {
			try {
				URL url = new URL(u);
		    	SyndFeedInput input = new SyndFeedInput();
		    	SyndFeed feed = input.build(new XmlReader(url));

		    	LOGGER.info("[READER] Feed obtained successfully");
		    	
		    	List<SyndEntry> synds = feed.getEntries();
		    	synds.forEach(entry -> {
		    		
		    		DCModuleImpl module = (DCModuleImpl) entry.getModules().get(0);
		    		SyndEnclosure enclosure = (SyndEnclosure) entry.getEnclosures().get(0);
		    		String document = entry.getDescription().getValue();
		    		
		    		FeedEntryDTOBuilder builder = new FeedEntryDTOBuilder()
		    				.withTitle(entry.getTitle())
		    				.withDescription(htmlParse(document))
		    				.withDocument(document)
		    				.withTimestamp(module.getDate().toInstant().toEpochMilli())
		    				.withURL(entry.getLink())
		    				.withMinimizedURL(entry.getUri())
		    				.withImageURL(enclosure.getUrl());
		    		
		    		entries.add(builder.build());
		    		
		    		LOGGER.info("[READER] Article inserted successfully into the DB");
		    	});
		    	
			} catch (Exception e) {
				LOGGER.error("Error while reading a feed", e);
				e.printStackTrace();
			}
		});
		
		return entries;
	}
	
	private String htmlParse(String html) {
		OutputSettings settings = new Document.OutputSettings().prettyPrint(false);
		return Jsoup.clean(html, "", Whitelist.none(), settings);
	}

	public FeedReaderConfig getConfig() {
		return config;
	}
}
