package itt.lnc.news_aggregation.controller;

import itt.lnc.news_aggregation.dto.CategoryRequest;
import itt.lnc.news_aggregation.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.addCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{categoryId}/hide")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hideCategory(@PathVariable Long categoryId) {
        categoryService.hideCategory(categoryId);
    }

    @PatchMapping("/{categoryId}/unhide")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unhideCategory(@PathVariable Long categoryId) {
        categoryService.unhideCategory(categoryId);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
