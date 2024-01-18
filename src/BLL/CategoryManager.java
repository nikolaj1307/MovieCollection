package BLL;

import BE.Category;
import BE.Movie;
import DAL.DB.CategoryDAO_DB;
import DAL.ICategoryDataAccess;
import GUI.Util.MovieExceptions;

import java.io.IOException;
import java.util.List;

public class CategoryManager {

    private ICategoryDataAccess categoryDAO;

    private boolean categoriesLoaded = false;

    private List<Category> allCategories;

    public CategoryManager() throws MovieExceptions {
        try {
            categoryDAO = new CategoryDAO_DB();
        } catch (MovieExceptions e) {
            throw new MovieExceptions(e);
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
    }

