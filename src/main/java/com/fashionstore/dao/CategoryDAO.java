package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Category;

public interface CategoryDAO {

	boolean addCategory(Category category);

	Category getCategoryById(int categoryId);

	Category getCategoryByName(String categoryName);

	List<Category> getAllCategories();

	boolean updateCategory(Category category);

	boolean deleteCategory(int categoryId);
}