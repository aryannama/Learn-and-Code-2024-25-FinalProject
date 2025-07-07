package itt.lnc.news_aggregation.service;

import itt.lnc.news_aggregation.dto.NotificationConfigurationDto;
import itt.lnc.news_aggregation.exception.ResourceNotFoundException;
import itt.lnc.news_aggregation.mapper.NotificationConfigurationMapper;
import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.Category;
import itt.lnc.news_aggregation.model.NotificationConfiguration;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.repository.NotificationConfigurationRepository;
import itt.lnc.news_aggregation.service.implementations.DefaultNotificationConfigurationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultNotificationConfigurationServiceTest {

    @Mock
    private NotificationConfigurationRepository configRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private NotificationConfigurationMapper configMapper;

    private DefaultNotificationConfigurationService service;

    @BeforeEach
    void setUp() {
        service = new DefaultNotificationConfigurationService(configRepository, categoryService, configMapper);
    }

    @Test
    void testGetMatchingArticles_ReturnsMatching() {
        Long userId = 1L;
        Article article = new Article();
        article.setTitle("Java AI");
        article.setDescription("New features");
        Category tech = new Category();
        tech.setName("Tech");
        article.setCategories(Set.of(tech));

        NotificationConfiguration config = NotificationConfiguration.builder()
                .category(tech)
                .keywords(Set.of("java"))
                .enabled(true)
                .build();

        when(configRepository.findByUserIdAndEnabledTrue(userId)).thenReturn(List.of(config));

        List<Article> result = service.getMatchingArticles(userId, List.of(article));

        assertEquals(1, result.size());
        assertEquals(article, result.get(0));
    }

    @Test
    void testGetMatchingArticles_NoKeywordMatch_ReturnsEmpty() {
        Long userId = 1L;
        Article article = new Article();
        article.setTitle("Python News");
        article.setDescription("Updates");
        Category tech = new Category();
        tech.setName("Tech");
        article.setCategories(Set.of(tech));

        NotificationConfiguration config = NotificationConfiguration.builder()
                .category(tech)
                .keywords(Set.of("java"))
                .enabled(true)
                .build();

        when(configRepository.findByUserIdAndEnabledTrue(userId)).thenReturn(List.of(config));

        List<Article> result = service.getMatchingArticles(userId, List.of(article));
        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateDefaultNotificationConfigurations() {
        User user = new User();
        user.setId(1L);
        Category c1 = new Category();
        c1.setName("Tech");
        Category c2 = new Category();
        c2.setName("Health");

        when(categoryService.getAllCategories()).thenReturn(List.of(c1, c2));

        service.createDefaultNotificationConfigurations(user);

        ArgumentCaptor<List<NotificationConfiguration>> captor = ArgumentCaptor.forClass(List.class);
        verify(configRepository).saveAll(captor.capture());
        List<NotificationConfiguration> saved = captor.getValue();

        assertEquals(2, saved.size());
        assertFalse(saved.get(0).isEnabled());
        assertEquals(user, saved.get(0).getUser());
    }

    @Test
    void testGetUserNotificationConfiguration() {
        NotificationConfiguration config = new NotificationConfiguration();
        NotificationConfigurationDto dto = new NotificationConfigurationDto();

        when(configRepository.findByUserId(1L)).thenReturn(List.of(config));
        when(configMapper.toDto(config)).thenReturn(dto);

        List<NotificationConfigurationDto> result = service.getUserNotificationConfiguration(1L);

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
    }

    @Test
    void testToggleCategory_EnabledTrue() {
        NotificationConfiguration config = NotificationConfiguration.builder()
                .enabled(false)
                .build();

        when(configRepository.findByUserIdAndCategoryId(1L, 2L)).thenReturn(Optional.of(config));

        service.toggleCategory(1L, 2L, true);

        assertTrue(config.isEnabled());
        verify(configRepository).save(config);
    }

    @Test
    void testToggleCategory_NotFound_Throws() {
        when(configRepository.findByUserIdAndCategoryId(1L, 2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.toggleCategory(1L, 2L, true));
    }

    @Test
    void testAddKeyword() {
        NotificationConfiguration config = NotificationConfiguration.builder()
                .keywords(new HashSet<>())
                .build();

        when(configRepository.findByUserIdAndCategoryId(1L, 2L)).thenReturn(Optional.of(config));

        service.addKeyword(1L, 2L, "ai");

        assertTrue(config.getKeywords().contains("ai"));
        verify(configRepository).save(config);
    }

    @Test
    void testRemoveKeyword() {
        Set<String> keywords = new HashSet<>(Set.of("ai", "java"));
        NotificationConfiguration config = NotificationConfiguration.builder()
                .keywords(keywords)
                .build();

        when(configRepository.findByUserIdAndCategoryId(1L, 2L)).thenReturn(Optional.of(config));

        service.removeKeyword(1L, 2L, "java");

        assertFalse(config.getKeywords().contains("java"));
        verify(configRepository).save(config);
    }
}