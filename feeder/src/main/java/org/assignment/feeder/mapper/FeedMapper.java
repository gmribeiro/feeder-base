package org.assignment.feeder.mapper;

import java.util.ArrayList;
import java.util.List;

import org.assignment.feeder.data.entity.Article;
import org.assignment.feeder.dto.ArticleDTO;
import org.assignment.feeder.dto.FeedEntryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface FeedMapper {
	public static FeedMapper INSTANCE = Mappers.getMapper(FeedMapper.class);

	@Mapping(target = "min_url", source = "minimizedURL")
	@Mapping(target = "image_url", source = "imageURL")
	@Mapping(target = "pubDate", source = "timestamp")
	Article toArticle(FeedEntryDTO entry);
	
	
	@Mapping(target = "minUrl", source = "min_url")
	@Mapping(target = "image", source = "image_url")
	@Mapping(target = "publicationDate", source = "pubDate")
	ArticleDTO toArticleDTO(Article article);
	
	default List<ArticleDTO> toArticleDTOList(List<Article> articles) {
		List<ArticleDTO> dtos = new ArrayList<>();
		
		
		if(articles != null && !articles.isEmpty()) {
			articles.stream().forEach(a -> {
				dtos.add(toArticleDTO(a));
			});
		}
		
		return dtos;
	}
}
