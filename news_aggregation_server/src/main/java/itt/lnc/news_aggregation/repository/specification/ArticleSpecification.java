package itt.lnc.news_aggregation.repository.specification;

import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.BlockedKeyword;
import itt.lnc.news_aggregation.model.Category;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleSpecification {

    public static Specification<Article> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) return null;
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.join("categories").get("name")),
                    category.toLowerCase()
            );
        };
    }

    public static Specification<Article> searchQuery(String searchQuery) {
        return (root, query, criteriaBuilder) -> {
            if (searchQuery == null) return null;
            String pattern = "%" + searchQuery.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), pattern)
            );
        };
    }

    public static Specification<Article> dateBetween(LocalDate from, LocalDate to) {
        return (root, query, criteriaBuilder) -> {
            if (from != null && to != null) {
                return criteriaBuilder.between(root.get("publishedAt"), from, to);
            }
            return null;
        };
    }

    public static Specification<Article> isNotHidden() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("hidden"));
    }

    public static Specification<Article> excludeHiddenCategories(List<Category> hiddenCategories) {
        return (root, query, criteriaBuilder) -> {
            if (hiddenCategories == null || hiddenCategories.isEmpty()) return null;
            Join<Object, Object> categories = root.join("categories");
            return criteriaBuilder.not(categories.get("id").in(hiddenCategories.stream().map(Category::getId).collect(Collectors.toSet())));
        };
    }

    public static Specification<Article> excludeBlockedKeywords(List<BlockedKeyword> blockedKeywords) {
        return (root, query, criteriaBuilder) -> {
            if (blockedKeywords == null || blockedKeywords.isEmpty()) return null;
            return blockedKeywords.stream()
                    .map(keyword -> criteriaBuilder.and(
                            criteriaBuilder.notLike(criteriaBuilder.lower(root.get("title")), "%" + keyword.getKeyword().toLowerCase() + "%"),
                            criteriaBuilder.notLike(criteriaBuilder.lower(root.get("description")), "%" + keyword.getKeyword().toLowerCase() + "%")
                    ))
                    .reduce(criteriaBuilder::and)
                    .orElse(null);
        };
    }
}

