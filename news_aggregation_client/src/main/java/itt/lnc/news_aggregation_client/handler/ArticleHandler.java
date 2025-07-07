package itt.lnc.news_aggregation_client.handler;

import itt.lnc.news_aggregation_client.constants.MenuType;
import itt.lnc.news_aggregation_client.dto.ArticleDto;
import itt.lnc.news_aggregation_client.dto.ArticleFilterRequest;
import itt.lnc.news_aggregation_client.dto.UserReaction;
import itt.lnc.news_aggregation_client.menu.MenuContext;
import itt.lnc.news_aggregation_client.service.ArticleService;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import itt.lnc.news_aggregation_client.utils.PaginatedListSelector;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static itt.lnc.news_aggregation_client.constants.AppConstants.PAGE_SIZE;

@Component
@RequiredArgsConstructor
public class ArticleHandler {

    private final ArticleService articleService;

    private MenuContext menuContext;
    private CategoryHandler categoryHandler;

    @Autowired
    public void setMenuContext(@Lazy MenuContext menuContext, @Lazy CategoryHandler categoryHandler) {
        this.menuContext = menuContext;
        this.categoryHandler = categoryHandler;
    }

    public void listArticles(ArticleFilterRequest filter) {
        PaginatedListSelector<ArticleDto> selector = new PaginatedListSelector<>(
                page -> articleService.getArticles(filter, page, PAGE_SIZE),
                article -> String.format(" [ID: %s] %s\n   Categories: %s   👍 %d 👎 %d",
                        article.getId(),
                        article.getTitle(),
                        String.join(", ", article.getCategories()),
                        article.getLikeCount(),
                        article.getDislikeCount())
        );

        ArticleDto selectedArticle = selector.select("Choose an article to view details or perform actions", true);
        viewArticleDetails(selectedArticle.getId());
    }

    public void viewArticleDetails(Long articleId) {
        ArticleDto article = articleService.getArticleById(articleId);
        articleService.markArticleAsRead(articleId);
        UserReaction reaction = articleService.getUserReaction(articleId);

        ConsoleUtil.printHeader("Article Details");
        ConsoleUtil.println("ID          : " + article.getId());
        ConsoleUtil.println("Title       : " + article.getTitle());
        ConsoleUtil.println("Published At: " + article.getPublishedAt());
        ConsoleUtil.println("Categories  : " + String.join(", ", article.getCategories()));
        ConsoleUtil.println("Likes       : " + article.getLikeCount() + "   👎 Dislikes : " + article.getDislikeCount());

        ConsoleUtil.println("Description : " + article.getDescription());
        ConsoleUtil.println("");
        ConsoleUtil.printSeparatorLine();

        String likedOption = reaction.isLiked() ? "Liked" : "Like";
        String dislikedOption = reaction.isDisliked() ? "Disliked" : "Dislike";
        String reportOption = reaction.isReported() ? "Reported" : "Report";

        int choice = ConsoleUtil.promptMenu(List.of(
                likedOption, dislikedOption, reportOption, "Save", "Back"
        ));

        switch (choice) {
            case 1:
                articleService.likeArticle(articleId);
                return;
            case 2:
                articleService.dislikeArticle(articleId);
                return;
            case 3:
                articleService.reportArticle(articleId);
                return;
            case 4:
                articleService.saveArticle(articleId);
                ConsoleUtil.printMessage("Article saved successfully.");
                return;
            case 5:
                menuContext.navigateTo(MenuType.HEADLINES);
                return;
            default:
                ConsoleUtil.printError("Invalid choice. Please try again.");
        }
    }

    public void handleDateRangeSelection() {
        LocalDate startDate = ConsoleUtil.readDate("Enter start date");
        LocalDate endDate = ConsoleUtil.readDate("Enter end date");

        try {
            ArticleFilterRequest filter = new ArticleFilterRequest();
            filter.setStartDate(startDate);
            filter.setEndDate(endDate);
            categoryHandler.showArticlesByCategory(filter);
        } catch (Exception exception) {
            ConsoleUtil.printError(exception.getMessage());
        }
    }

    public void showTodayArticles() {
        LocalDate today = LocalDate.now();
        ArticleFilterRequest filter = new ArticleFilterRequest();
        filter.setStartDate(today);
        filter.setEndDate(today);

        try {
            listArticles(filter);
        } catch (Exception exception) {
            ConsoleUtil.printError(exception.getMessage());
        }
    }

    public void showSavedArticles() {
        ConsoleUtil.displayList(articleService.getSavedArticles(),
                article -> String.format(" [ID: %s] %s\n   Categories: %s   👍 %d 👎 %d",
                        article.getId(),
                        article.getTitle(),
                        String.join(", ", article.getCategories()),
                        article.getLikeCount(),
                        article.getDislikeCount()));
    }

    public void searchArticles() {
        String query = ConsoleUtil.readLine("Enter search term: ");
        ArticleFilterRequest filter = new ArticleFilterRequest();
        filter.setQuery(query);
        listArticles(filter);
    }
}
