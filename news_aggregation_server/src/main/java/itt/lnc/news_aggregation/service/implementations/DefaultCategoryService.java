package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.dto.CategoryRequest;
import itt.lnc.news_aggregation.exception.DuplicateResourceException;
import itt.lnc.news_aggregation.exception.ResourceNotFoundException;
import itt.lnc.news_aggregation.model.Category;
import itt.lnc.news_aggregation.repository.CategoryRepository;
import itt.lnc.news_aggregation.service.CategoryService;
import itt.lnc.news_aggregation.utils.CategoryKeywordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    public Category addCategory(CategoryRequest categoryRequest) {
        log.info("Adding new category");
        categoryRepository.findByNameIgnoreCase(categoryRequest.getName()).ifPresent(exist -> {
            log.error("Category already exists: {}", categoryRequest.getName());
            throw new DuplicateResourceException("Category: " + categoryRequest.getName() + "already exists!");
        });

        Category category = new Category();
        category.setName(categoryRequest.getName());

        log.info("New Category: {} added successfully", category.getName());
        return categoryRepository.save(category);
    }

    public Set<Category> findMatchingCategories(ArticleDto dto) {
        log.info("Finding matching categories");
        List<Category> categories = categoryRepository.findAll();
        Set<Category> matchedCategories = new HashSet<>();

        if (dto.getCategories() == null || dto.getCategories().isEmpty()) {
            log.info("No categories found in article, using keyword matching");
            matchedCategories = CategoryKeywordUtil.getCategoriesByKeyword(dto, categories);
        } else {
            log.info("Matching categories");
            for (String name : dto.getCategories()) {
                for (Category category : categories) {
                    if (category.getName() != null && category.getName().equalsIgnoreCase(name.trim())) {
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

    @Override
    public void hideCategory(Long categoryId) {
        log.info("Hiding category with id: {}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        category.setHidden(true);
        log.info("Category hided successfully");
        categoryRepository.save(category);
    }

    @Override
    public void unhideCategory(Long categoryId) {
        log.info("Unhiding category with id: {}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        category.setHidden(false);
        log.info("Category unhidden successfully");
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getHiddenCategories() {
        log.info("Getting hidden categories");
        return getAllCategories().stream()
                .filter(Category::isHidden)
                .toList();
    }
}
