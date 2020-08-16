package org.assignment.feeder;

import java.util.ArrayList;
import java.util.List;

import org.assignment.feeder.data.ArticleDAO;
import org.assignment.feeder.data.entity.Article;
import org.assignment.feeder.dto.ArticleDTO;
import org.assignment.feeder.dto.FeedEntryDTO;
import org.assignment.feeder.dto.FeedEntryDTO.FeedEntryDTOBuilder;
import org.assignment.feeder.mapper.FeedMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Could've used spring beans, configuration test class and annotations to mock articles and/or articleDTOs
 **/
@RunWith(JUnit4.class)
@org.junit.Ignore public class FeederGenericTests {
	
	private static final String DESCRIPTION = "TEST TITLE!!!";
	private static final String DOCUMENT = "TEST TITLE!!!";
	private static final int ID = 1;
	private static final String IMAGE = "TEST TITLE!!!";
	private static final String MINURL = "TEST TITLE!!!";
	private static final Long PUBDATE = 0l;
	private static final String TITLE = "TEST TITLE!!!";
	private static final String URL = "TEST TITLE!!!";

	@Autowired
	ArticleDAO articleDAO;

	private FeedEntryDTO entry;

	@BeforeClass
	void init() {
		this.entry = new FeedEntryDTOBuilder()
				.withTitle(TITLE)
				.withDescription(DESCRIPTION)
				.withDocument(DOCUMENT)
				.withImageURL(IMAGE)
				.withMinimizedURL(MINURL)
				.withTimestamp(PUBDATE)
				.withURL(URL)
				.build();
	}

	/**
	 * Make sure DB is running before running this test case
	 **/
	@Test
	void mappingsWithDbConnectionTest() {
		// insert article in db
		articleDAO.save(FeedMapper.INSTANCE.toArticle(entry));

		// get single articles, map and assert
		Article article = articleDAO.findByTitle(TITLE);
		ArticleDTO dto = FeedMapper.INSTANCE.toArticleDTO(article);
		Assert.assertNotNull(dto);
		assertDTOValues(dto);

		// get multiple articles, map and assert
		List<ArticleDTO> dtos = FeedMapper.INSTANCE.toArticleDTOList(articleDAO.findAll());
		Assert.assertTrue(dtos.size() > 0);
		assertDTOListValues(dtos);

		//delete test!!
		articleDAO.deleteAll();
	}

	@Test
	void mappingsTest() {

		Article article = FeedMapper.INSTANCE.toArticle(entry);
		Assert.assertNotNull(article);

		ArticleDTO dto = FeedMapper.INSTANCE.toArticleDTO(article);
		Assert.assertNotNull(dto);
		assertDTOValues(dto);

		List<Article> articles = new ArrayList<>();
		articles.add(article);

		List<ArticleDTO> dtos = FeedMapper.INSTANCE.toArticleDTOList(articles);
		Assert.assertTrue(dtos.size() > 0);
		assertDTOListValues(dtos);
	}

	void assertDTOListValues(List<ArticleDTO> dtos) {
		dtos.forEach(d -> {
			assertDTOValues(d);
		});
	}

	void assertDTOValues(ArticleDTO dto) {
		Assert.assertEquals(DESCRIPTION, dto.getDescription());
		Assert.assertEquals(DOCUMENT, dto.getDocument());
		Assert.assertEquals(ID, dto.getId());
		Assert.assertEquals(IMAGE, dto.getImage());
		Assert.assertEquals(MINURL, dto.getMinUrl());
		Assert.assertEquals(PUBDATE, dto.getPublicationDate());
		Assert.assertEquals(TITLE, dto.getTitle());
		Assert.assertEquals(URL, dto.getUrl());
	}
}
