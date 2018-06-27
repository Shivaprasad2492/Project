package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Category;
import org.upgrad.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void savecategory(String title, String description) {
        categoryRepository.save(title,description);
    }
}
