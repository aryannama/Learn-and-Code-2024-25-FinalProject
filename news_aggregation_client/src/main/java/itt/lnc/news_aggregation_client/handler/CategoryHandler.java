package itt.lnc.news_aggregation_client.handler;

import itt.lnc.news_aggregation_client.dto.ArticleFilterRequest;
import itt.lnc.news_aggregation_client.dto.CategoryDto;
import itt.lnc.news_aggregation_client.service.CategoryService;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import itt.lnc.news_aggregation_client.utils.ListSelector;
import itt.lnc.news_aggregation_client.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryHandler {

    private final CategoryService categoryService;
    private ArticleHandler articleHandler;

    @Autowired
    public void setMenuContext(@Lazy ArticleHandler articleHandler) {
        this.articleHandler = articleHandler;
    }

    public void listCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        ConsoleUtil.println("List of categories:");
        ConsoleUtil.displayList(categories, CategoryDto::getName);
    }

    public void addCategory() {
        String category = ConsoleUtil.readLine("Enter category: ");
        if (!ValidationUtil.isNotBlank(category)) {
            ConsoleUtil.printError("Category cannot be empty.");
            return;
        }
        categoryService.addNewCategory(category);
    }

    public void unhideCategory() {
        List<CategoryDto> categories = categoryService.getAllCategories().stream().filter(CategoryDto::isHidden).toList();
        ListSelector<CategoryDto> selector = new ListSelector<>(categories, CategoryDto::getName);
        CategoryDto selectedCategory = selector.select("Select a category to unhide");
        categoryService.unhideCategory(selectedCategory.getId());
    }

    public void hideCategory() {
        CategoryDto selectedCategory = selectFromVisibleCategories("Select a category to hide");
        categoryService.hideCategory(selectedCategory.getId());
    }

    public void showArticlesByCategory(ArticleFilterRequest filter) {
        CategoryDto selectedCategory = selectFromVisibleCategories("Select a category");
        filter.setCategory(selectedCategory.getName());
        articleHandler.listArticles(filter);
    }

    private CategoryDto selectFromVisibleCategories(String prompt) {
        List<CategoryDto> categories = categoryService.getAllCategories().stream().filter(category -> !category.isHidden()).toList();
        ListSelector<CategoryDto> selector = new ListSelector<>(categories, CategoryDto::getName);
        return selector.select(prompt);
    }
}
