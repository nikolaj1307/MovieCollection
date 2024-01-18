package DAL;

import BE.Category;
import BE.Movie;
import GUI.Util.MovieExceptions;


import java.util.List;

public interface ICategoryDataAccess {
    public List<Category> getAllCategories() throws MovieExceptions;

    public Category createNewCategory(Category category) throws MovieExceptions;

    public void updateCategory(Category category) throws MovieExceptions;

    public Category deleteCategory(Category category) throws MovieExceptions;

    public void deleteCategoryFromMovie(Category category, Movie movie) throws MovieExceptions;
}

