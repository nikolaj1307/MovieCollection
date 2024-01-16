package BLL;

import BE.Category;
import BE.Movie;
import DAL.DB.CategoryDAO_DB;
import DAL.ICategoryDataAccess;

import java.io.IOException;
import java.util.List;

public class CategoryManager {

    private ICategoryDataAccess categoryDAO;

    private boolean categoriesLoaded = false;

    private List<Category> allCategories;

    public CategoryManager() throws IOException {
        categoryDAO = new CategoryDAO_DB();
    }

    public List<Category> getAllCategories() throws Exception {
        return categoryDAO.getAllCategories();
    }
}
