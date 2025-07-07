package itt.lnc.news_aggregation.service;


import itt.lnc.news_aggregation.dto.ArticleDto;
import itt.lnc.news_aggregation.dto.CategoryRequest;
import itt.lnc.news_aggregation.exception.DuplicateResourceException;
import itt.lnc.news_aggregation.exception.ResourceNotFoundException;
import itt.lnc.news_aggregation.model.Category;
import itt.lnc.news_aggregation.repository.CategoryRepository;
import itt.lnc.news_aggregation.service.implementations.DefaultCategoryService;
import itt.lnc.news_aggregation.utils.CategoryKeywordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultCategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private DefaultCategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new DefaultCategoryService(categoryRepository);
    }

    @Test
    void testAddCategory_Success() {
        CategoryRequest request = new CategoryRequest();
        request.setName("Tech");

        when(categoryRepository.findByNameIgnoreCase("Tech")).thenReturn(Optional.empty());

        Category saved = new Category();
        saved.setId(1L);
        saved.setName("Tech");

        when(categoryRepository.save(any(Category.class))).thenReturn(saved);

        Category result = categoryService.addCategory(request);

        assertEquals("Tech", result.getName());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void testAddCategory_Duplicate_ThrowsException() {
        CategoryRequest request = new CategoryRequest();
        request.setName("Tech");

        when(categoryRepository.findByNameIgnoreCase("Tech")).thenReturn(Optional.of(new Category()));

        assertThrows(DuplicateResourceException.class, () -> categoryService.addCategory(request));
    }

    @Test
    void testGetAllCategories() {
        Category category1 = new Category();
        category1.setName("Tech");
        Category category2 = new Category();
        category2.setName("Health");

        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        List<Category> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
    }

    @Test
    void testHideCategory_Success() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Tech");
        category.setHidden(false);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any())).thenReturn(category);

        categoryService.hideCategory(1L);

        assertTrue(category.isHidden());
        verify(categoryRepository).save(category);
    }

    @Test
    void testHideCategory_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.hideCategory(1L));
    }

    @Test
    void testUnhideCategory_Success() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Tech");
        category.setHidden(true);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any())).thenReturn(category);

        categoryService.unhideCategory(1L);

        assertFalse(category.isHidden());
        verify(categoryRepository).save(category);
    }

    @Test
    void testUnhideCategory_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.unhideCategory(1L));
    }

    @Test
    void testGetHiddenCategories() {
        Category hidden = new Category();
        hidden.setName("Blocked");
        hidden.setHidden(true);

        Category visible = new Category();
        visible.setName("Visible");
        visible.setHidden(false);

        when(categoryRepository.findAll()).thenReturn(List.of(hidden, visible));

        List<Category> hiddenCategories = categoryService.getHiddenCategories();

        assertEquals(1, hiddenCategories.size());
        assertTrue(hiddenCategories.get(0).isHidden());
    }

    @Test
    void testFindMatchingCategories_WithNamesInDto() {
        Category c1 = new Category();
        c1.setName("Tech");

        Category c2 = new Category();
        c2.setName("Health");

        ArticleDto dto = new ArticleDto();
        dto.setCategories(Set.of("Tech", "Unknown"));

        when(categoryRepository.findAll()).thenReturn(List.of(c1, c2));

        Set<Category> result = categoryService.findMatchingCategories(dto);

        assertEquals(1, result.size());
        assertTrue(result.contains(c1));
    }

    @Test
    void testFindMatchingCategories_WithNoCategoryNames_UsesKeywordUtil() {
        ArticleDto dto = new ArticleDto();
        dto.setTitle("Tech innovation");

        Category tech = new Category();
        tech.setName("Tech");

        List<Category> allCategories = List.of(tech);

        when(categoryRepository.findAll()).thenReturn(allCategories);

        mockStaticCategoryKeywordUtil(dto, allCategories, Set.of(tech));

        Set<Category> result = categoryService.findMatchingCategories(dto);

        assertEquals(1, result.size());
        assertTrue(result.contains(tech));
    }

    private void mockStaticCategoryKeywordUtil(ArticleDto dto, List<Category> allCategories, Set<Category> matchedCategories) {
        CategoryKeywordUtil utilMock = mock(CategoryKeywordUtil.class);
        mockStatic(CategoryKeywordUtil.class, invocation -> {
            if (invocation.getMethod().getName().equals("getCategoriesByKeyword")) {
                return matchedCategories;
            }
            return invocation.callRealMethod();
        });
    }
}
