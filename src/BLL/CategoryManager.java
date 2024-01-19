package BLL;

import BE.Category;
import DAL.DB.CategoryDAO_DB;
import DAL.ICategoryDataAccess;
import GUI.Util.MovieExceptions;

import java.util.List;

public class CategoryManager {

    private ICategoryDataAccess categoryDAO;

    public CategoryManager() throws MovieExceptions {
        try {
            categoryDAO = new CategoryDAO_DB();

        } catch (MovieExceptions e) {
            throw new MovieExceptions(e);
        } catch (Exception e) {
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

    public void deleteCategory (Category selectedCategory) throws MovieExceptions {
        categoryDAO.deleteCategory(selectedCategory);
    }
}



