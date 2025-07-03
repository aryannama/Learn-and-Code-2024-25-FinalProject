package itt.lnc.news_aggregation.helper;

import itt.lnc.news_aggregation.model.Category;
import itt.lnc.news_aggregation.model.NotificationConfiguration;
import itt.lnc.news_aggregation.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserPreferenceHelper {
    private final UserRepository userRepository;
    private final UserReactionRepository userReactionRepository;
    private final SavedArticleRepository savedArticleRepository;
    private final ReadArticleRepository readArticleRepository;
    private final NotificationConfigurationRepository notificationConfigurationRepository;

    public Set<Long> getPreferredCategories(Long userId) {
        Set<Long> categoryIds = new LinkedHashSet<>();

        categoryIds.addAll(
                userReactionRepository.findByUserIdAndLikedTrue(userId).stream()
                        .flatMap(reaction -> reaction.getArticle().getCategories().stream())
                        .map(Category::getId)
                        .collect(Collectors.toSet())
        );

        categoryIds.addAll(
                savedArticleRepository.findAllByUserId(userId).stream()
                        .flatMap(saved -> saved.getArticle().getCategories().stream())
                        .map(Category::getId)
                        .collect(Collectors.toSet())
        );

        categoryIds.addAll(
                readArticleRepository.findByUserId(userId).stream()
                        .flatMap(read -> read.getArticle().getCategories().stream())
                        .map(Category::getId)
                        .collect(Collectors.toSet())
        );

        categoryIds.addAll(
                notificationConfigurationRepository.findByUserIdAndEnabledTrue(userId).stream()
                        .map(config -> config.getCategory().getId())
                        .collect(Collectors.toSet())
        );

        return categoryIds;
    }

    public Set<String> getPreferredKeywords(Long userId) {
        return notificationConfigurationRepository.findByUserIdAndEnabledTrue(userId).stream()
                .map(NotificationConfiguration::getKeywords)
                .filter(Objects::nonNull)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}
