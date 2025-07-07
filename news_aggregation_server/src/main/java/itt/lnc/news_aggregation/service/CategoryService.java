package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.dto.CategoryRequest;
import itt.lnc.news_aggregation.model.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    Category addCategory(CategoryRequest request);
    Set<Category> findMatchingCategories(ArticleDto dto);
    List<Category> getAllCategories();
    void hideCategory(Long categoryId);
    void unhideCategory(Long categoryId);
    List<Category> getHiddenCategories();
}
