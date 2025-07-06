package itt.lnc.news_aggregation_client.api;

import itt.lnc.news_aggregation_client.constants.Urls;
import itt.lnc.news_aggregation_client.dto.CategoryDto;
import itt.lnc.news_aggregation_client.dto.NewCategoryDto;
import itt.lnc.news_aggregation_client.exception.InvalidRequestException;
import itt.lnc.news_aggregation_client.utils.APIClient;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import itt.lnc.news_aggregation_client.utils.JsonParser;
import itt.lnc.news_aggregation_client.utils.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;

@Component
public class CategoryApiClient {
    public void addNewCategory(NewCategoryDto request) {
        String token = SessionManager.getAccessToken();
        HttpResponse<String> response = APIClient.post(Urls.CATEGORIES_URL, JsonParser.toJson(request), token);

        if (response.statusCode() == HttpStatus.CREATED.value()) {
            ConsoleUtil.printMessage("Category added successfully");
        } else {
            ConsoleUtil.printError("Failed to add category. Status code: " + response.statusCode());
        }
    }

    public void hideCategory(Long categoryId) {
        String token = SessionManager.getAccessToken();
        HttpResponse<String> response = APIClient.patch(String.format(Urls.HIDE_CATEGORY_URL, categoryId), token);

        if (response.statusCode() == HttpStatus.NO_CONTENT.value()) {
            ConsoleUtil.printMessage("Category hidden successfully");
        } else {
            ConsoleUtil.printError("Failed to hide category. Status code: " + response.statusCode());
        }
    }

    public void unhideCategory(Long categoryId) {
        String token = SessionManager.getAccessToken();
        HttpResponse<String> response = APIClient.patch(String.format(Urls.UNHIDE_CATEGORY_URL, categoryId), token);

        if (response.statusCode() == HttpStatus.NO_CONTENT.value()) {
            ConsoleUtil.printMessage("Category unhidden successfully");
        } else {
            ConsoleUtil.printError("Failed to unhide category. Status code: " + response.statusCode());
        }
    }

    public List<CategoryDto> getAllCategories() {
        String token = SessionManager.getAccessToken();
        HttpResponse<String> response = APIClient.get(Urls.CATEGORIES_URL, token);

        if (response.statusCode() == HttpStatus.OK.value()) {
            return JsonParser.parseList(response.body(), CategoryDto.class);
        } else {
            throw new InvalidRequestException("Failed to fetch categories. Status code: " + response.statusCode());
        }
    }
}
