package itt.lnc.news_aggregation.mapper;

import itt.lnc.news_aggregation.dto.ArticleDTO;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleMapper {

    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public Article toEntity(ArticleDTO dto) {
        return modelMapper.map(dto, Article.class);
    }

    public ArticleDTO toDto(Article entity) {
        return modelMapper.map(entity, ArticleDTO.class);
    }
}
