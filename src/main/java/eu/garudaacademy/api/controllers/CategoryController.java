package eu.garudaacademy.api.controllers;

import eu.garudaacademy.api.models.constants.ApiPaths;
import eu.garudaacademy.api.models.entity.Category;
import eu.garudaacademy.api.models.responses.CategoryWithPurchasesResponse;
import eu.garudaacademy.api.models.entity.Purchase;
import eu.garudaacademy.api.models.responses.factory.CategoryWithPurchasesResponseFactory;
import eu.garudaacademy.api.models.exception.ResourceNotFoundException;
import eu.garudaacademy.api.repository.CategoryRepository;
import eu.garudaacademy.api.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(ApiPaths.CATEGORIES_BASE)
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CategoryWithPurchasesResponseFactory categoryWithPurchasesResponseFactory;

    @GetMapping(ApiPaths.GET_ALL)
    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    @GetMapping(ApiPaths.GET_BY_ID)
    public Category getById(@PathVariable(value = "id") final long categoryId) {
        return this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found: " + categoryId));
    }

    @GetMapping(ApiPaths.CATEGORIES_GET_WITH_PURCHASES)
    public List<CategoryWithPurchasesResponse> getByUser(@PathVariable(value = "userId") final long userId) {
        List<Category> categories = this.categoryRepository.findAll();
        List<Purchase> purchases = this.purchaseRepository.findByUserId(userId);

        return categoryWithPurchasesResponseFactory.build(categories, purchases);
    }

    @RequestMapping(
        value = ApiPaths.CREATE,
        produces = "application/json",
        method = {RequestMethod.POST})
    public Category create(@RequestBody final Category category) {
        return this.categoryRepository.save(category);
    }

    @PutMapping(ApiPaths.UPDATE)
    public Category updateUser(
            @RequestBody final Category category,
            @PathVariable(value = "id") final long categoryId) {

        final Category current = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + category));
        current.setName(category.getName());
        current.setUrl(category.getUrl());

        return this.categoryRepository.save(current);
    }

    @DeleteMapping(ApiPaths.DELETE)
    public ResponseEntity<Category> delete(@PathVariable(value = "id") final long categoryId) {

        final Category current = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + categoryId));
        this.categoryRepository.delete(current);

        return ResponseEntity.ok().build();
    }
}
