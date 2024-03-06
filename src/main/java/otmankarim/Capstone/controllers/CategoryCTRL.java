package otmankarim.Capstone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import otmankarim.Capstone.entities.Category;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.payloads.CategoryDTO;
import otmankarim.Capstone.services.CategorySRV;

import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryCTRL {
    @Autowired
    private CategorySRV categorySRV;

    @GetMapping
    public Page<Category> getCategories(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String orderBy) {
        return this.categorySRV.getCategories(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable UUID id) {
        return this.categorySRV.getCategoryById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Category saveCategory(@RequestBody @Validated CategoryDTO newCategory, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.categorySRV.save(newCategory);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Category updateCategoryById(@RequestBody @Validated CategoryDTO updatedCategory, @PathVariable UUID id, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.categorySRV.updateCategoryById(updatedCategory, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable UUID id) {
        this.categorySRV.delete(id);
    }

}
