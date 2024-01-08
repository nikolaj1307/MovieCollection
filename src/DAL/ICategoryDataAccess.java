package DAL;

import BE.Category;
import BE.Movie;


import java.util.List;

public interface ICategoryDataAccess {
    public List<Category> getAllCategories() throws Exception;

    public Category createNewCategory(Category category) throws Exception;

    public void updateCategory(Category category) throws Exception;

    public Category deleteCategory(Category category) throws Exception;
}

