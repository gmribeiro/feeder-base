package org.assignment.feeder.controller;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.assignment.feeder.data.ArticleDAO;
import org.assignment.feeder.data.entity.Article;
import org.assignment.feeder.dto.ArticleDTO;
import org.assignment.feeder.dto.FeedEntryDTO;
import org.assignment.feeder.exception.ArticleNotFoundException;
import org.assignment.feeder.mapper.FeedMapper;
import org.assignment.feeder.reader.FeedReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.syndication.io.FeedException;

@RestController
@RequestMapping("/feed.me")
@EnableScheduling
public class FeederController {

	private static Logger LOGGER = LoggerFactory.getLogger(FeederController.class);
	
	@Autowired
	private FeedReader reader;

	@Autowired
	private ArticleDAO articleDAO;

	@PostConstruct
	public void onStart() throws IllegalArgumentException, FeedException, IOException {
		read();
	}

	// Not safe to be called after a few readings due to the size of the result set
	// Feeds' items are stored and never deleted
	@GetMapping("/all")
	public List<ArticleDTO> getAll() {
		return FeedMapper.INSTANCE.toArticleDTOList(articleDAO.findAll());
	}

	@GetMapping({"/latest", "/latest/{count}"})
	public List<ArticleDTO> getLatest(@PathVariable(value="count", required=false) Integer count) {
		count = count == null ? reader.getConfig().getLimit() : count;
		Pageable top = PageRequest.of(0, count);

		return FeedMapper.INSTANCE.toArticleDTOList(articleDAO.findByOrderByPubDateDesc(top));
	}

	@GetMapping("/article/{id}")
	public ArticleDTO get(@PathVariable("id") int id) throws ArticleNotFoundException {
		Article article = articleDAO.findById(id).orElseThrow(() -> new ArticleNotFoundException("It was not possible to retrieve any articles for the given id " + id));

		return FeedMapper.INSTANCE.toArticleDTO(article);
	}

	@Scheduled(cron = "${scheduler.cron}")
	private void read() {
		LOGGER.info("[SCHEDULER] started scheduled activity");

		List<FeedEntryDTO> entries = reader.read().stream().limit(reader.getConfig().getLimit()).collect(Collectors.toList());

		entries.forEach(entry -> {
			Optional<Article> opt = Optional.ofNullable(articleDAO.findByTitle(entry.getTitle()));

			//don't insert existent entries
			if(opt.isEmpty())
				articleDAO.save(FeedMapper.INSTANCE.toArticle(entry));
		});

		LOGGER.info("[SCHEDULER] finished scheduled activity");
	}
}