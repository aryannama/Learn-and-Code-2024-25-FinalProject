package itt.lnc.news_aggregation_client.service;

import itt.lnc.news_aggregation_client.api.CategoryApiClient;
import itt.lnc.news_aggregation_client.dto.CategoryDto;
import itt.lnc.news_aggregation_client.dto.NewCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryApiClient categoryApiClient;

    public void addNewCategory(String categoryName) {
        NewCategoryDto newCategoryDto = new NewCategoryDto(categoryName);
        categoryApiClient.addNewCategory(newCategoryDto);
    }

    public void hideCategory(Long categoryId) {
        categoryApiClient.hideCategory(categoryId);
    }

    public void unhideCategory(Long categoryId) {
        categoryApiClient.unhideCategory(categoryId);
    }

    public List<CategoryDto> getAllCategories() {
        return categoryApiClient.getAllCategories();
    }
}
