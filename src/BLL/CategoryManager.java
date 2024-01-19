package BLL;

import BE.Category;
import BE.Movie;
import DAL.DB.CategoryDAO_DB;
import DAL.ICategoryDataAccess;
import DAL.IMovieDataAccess;
import GUI.Model.MovieModel;
import GUI.Util.MovieExceptions;

import java.util.List;

public class CategoryManager {

    private ICategoryDataAccess categoryDAO;
    private IMovieDataAccess movieDAO;
    private MovieModel movieModel;


    private boolean categoriesLoaded = false;

    private List<Category> allCategories;

    public CategoryManager() throws MovieExceptions {
        try {
            categoryDAO = new CategoryDAO_DB();
            movieModel = new MovieModel();
        } catch (MovieExceptions e) {
            throw new MovieExceptions(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> getAllCategories() throws MovieExceptions {
        try {
            return categoryDAO.getAllCategories();
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }

    public Category createNewCategory(Category category) throws MovieExceptions {
        try {
            return categoryDAO.createNewCategory(category);
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }


    public void deleteCategory (Category selectedCategory) throws MovieExceptions {
        categoryDAO.deleteCategory(selectedCategory);
    }


}



