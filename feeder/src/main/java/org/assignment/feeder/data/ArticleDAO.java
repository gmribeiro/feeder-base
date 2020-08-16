package org.assignment.feeder.data;

import java.util.List;

import org.assignment.feeder.data.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleDAO extends JpaRepository<Article, Integer>{

	Article findByTitle(String title);
	
	List<Article> findByOrderByPubDateDesc(Pageable p);
	
	Article findTop1ByOrderByPubDateDesc();
}
