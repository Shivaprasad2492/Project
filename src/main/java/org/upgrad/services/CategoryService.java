package org.upgrad.services;

import org.upgrad.models.Category;

public interface CategoryService {

    Iterable<Category> getAllCategories();

    void savecategory(String title, String description);
}
