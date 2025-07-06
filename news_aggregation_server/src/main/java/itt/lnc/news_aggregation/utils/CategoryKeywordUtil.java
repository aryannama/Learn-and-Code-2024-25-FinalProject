package itt.lnc.news_aggregation.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.model.Category;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.*;

@Slf4j
public class CategoryKeywordUtil {

    public static Set<Category> getCategoriesByKeyword(ArticleDto dto, List<Category> categories) {
        log.info("Getting categories by keyword");
        Map<String, List<String>> categoryKeywords = loadCategoryKeywords();
        Set<Category> detectedCategories = new HashSet<>();

        String content = (dto.getTitle() + " " + dto.getDescription()).toLowerCase();

        for (Map.Entry<String, List<String>> entry : categoryKeywords.entrySet()) {
            String categoryName = entry.getKey();
            List<String> keywords = entry.getValue();

            for (String keyword : keywords) {
                if (content.contains(keyword.toLowerCase())) {
                    for (Category category : categories) {
                        if (category.getName().equalsIgnoreCase(categoryName)) {
                            detectedCategories.add(category);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return detectedCategories;
    }

    private static Map<String, List<String>> loadCategoryKeywords() {
        Map<String, List<String>> categoryKeywords = new HashMap<>();
        try (InputStream inputStream = CategoryKeywordUtil.class.getClassLoader()
                .getResourceAsStream("category-keywords.json")) {

            if (inputStream == null) throw new RuntimeException("category-keywords.json not found");

            Map<String, List<String>> loaded = JsonParser.parse(inputStream, new TypeReference<>() {
            });
            loaded.forEach((category, keywords) -> categoryKeywords.put(category.toLowerCase(), keywords));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load category-keywords.json", e);
        }
        return categoryKeywords;
    }
}