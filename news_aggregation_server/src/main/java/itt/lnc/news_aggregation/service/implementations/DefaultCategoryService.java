package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.dto.ArticleDTO;
import itt.lnc.news_aggregation.dto.CategoryRequest;
import itt.lnc.news_aggregation.model.Category;
import itt.lnc.news_aggregation.repository.CategoryRepository;
import itt.lnc.news_aggregation.service.CategoryService;
import itt.lnc.news_aggregation.utils.CategoryKeywordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    public Category addCategory(CategoryRequest categoryRequest) {
        categoryRepository.findByNameIgnoreCase(categoryRequest.getName()).ifPresent(exist -> {
            throw new RuntimeException("Category: " + categoryRequest.getName() + "already exists!");
        });

        Category category = new Category();
        category.setName(category.getName());

        return categoryRepository.save(category);
    }

    public Set<Category> findMatchingCategories(ArticleDTO dto) {
        List<Category> categories = categoryRepository.findAll();
        Set<Category> matchedCategories = new HashSet<>();

        if (dto.getCategories() == null || dto.getCategories().isEmpty()) {
            matchedCategories = CategoryKeywordUtil.getCategoriesByKeyword(dto, categories);
        } else {
            for (String name : dto.getCategories()) {
                for (Category category : categories) {
                    if (category.getName().equalsIgnoreCase(name.trim())) {
                        matchedCategories.add(category);
                        break;
                    }
                }
            }
        }

        if (matchedCategories.isEmpty()) {
            for (Category category : categories) {
                if (category.getName().equalsIgnoreCase("all")) {
                    matchedCategories.add(category);
                    break;
                }
            }
        }
        return matchedCategories;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
