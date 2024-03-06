package otmankarim.Capstone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import otmankarim.Capstone.entities.Category;
import otmankarim.Capstone.exceptions.BadRequestException;
import otmankarim.Capstone.exceptions.NotFoundException;
import otmankarim.Capstone.payloads.CategoryDTO;
import otmankarim.Capstone.repositories.CategoryDAO;

import java.util.UUID;

@Service
public class CategorySRV {
    @Autowired
    private CategoryDAO categoryDAO;

    public Page<Category> getCategories(int pageNum, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(orderBy));
        return categoryDAO.findAll(pageable);
    }

    public Category save(CategoryDTO newCategory) {
        categoryDAO.findByName(newCategory.name()).ifPresent(role -> {
            throw new BadRequestException("Category " + newCategory.name() + " already exist!");
        });
        Category category = new Category(newCategory.name());
        return categoryDAO.save(category);
    }

    public Category getCategoryById(UUID id) {
        return categoryDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Category updateCategoryById(CategoryDTO updatedCategory, UUID id) {
        Category found = getCategoryById(id);
        found.setName(updatedCategory.name());
        return categoryDAO.save(found);
    }

    public void delete(UUID id) {
        Category found = getCategoryById(id);
        categoryDAO.delete(found);
    }

}
