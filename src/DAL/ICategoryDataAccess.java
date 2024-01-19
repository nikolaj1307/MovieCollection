package DAL;

import BE.Category;
import GUI.Util.MovieExceptions;


import java.util.List;

public interface ICategoryDataAccess {
    public List<Category> getAllCategories() throws MovieExceptions;

    public Category createNewCategory(Category category) throws MovieExceptions;

    public Category deleteCategory(Category category) throws MovieExceptions;
}


