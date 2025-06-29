package itt.lnc.news_aggregation.mapper;

import itt.lnc.news_aggregation.dto.ArticleDTO;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.Category;
import itt.lnc.news_aggregation.service.CategoryService;
import itt.lnc.news_aggregation.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArticleMapper {

    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    public Article toEntity(ArticleDTO dto) {
        Article article = modelMapper.map(dto, Article.class);
        article.setCategories(categoryService.findMatchingCategories(dto));
        article.setPublishedAt(DateUtil.parseDate(dto.getPublishedAt()));
        return article;
    }

    public ArticleDTO toDto(Article entity) {
        ArticleDTO article = modelMapper.map(entity, ArticleDTO.class);
        article.setPublishedAt(DateUtil.formatDate(entity.getPublishedAt()));
        article.setCategories(entity.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toSet()));
        return article;
    }
}
