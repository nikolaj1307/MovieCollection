package GUI.Model;


import BE.Category;
import BE.Movie;
import BLL.CategoryManager;
import DAL.DB.CategoryDAO_DB;
import GUI.Util.MovieExceptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CategoryModel {


    private CategoryManager categoryManager;
    private CategoryDAO_DB categoryDAO;
    private MovieModel movieModel;

    private ObservableList<Category> categoriesToBeViewed;

    public CategoryModel() throws Exception {
        categoryManager = new CategoryManager();
        movieModel = new MovieModel();
        categoriesToBeViewed = FXCollections.observableArrayList();
        categoriesToBeViewed.addAll(categoryManager.getAllCategories());
    }

    // Getter for the observable list of categories
    public ObservableList<Category> getObservableCategories() {
        return categoriesToBeViewed;
    }


    public void createNewCategory(String catName) throws MovieExceptions {
        Category category = new Category(catName);
        categoryManager.createNewCategory(category);
        categoriesToBeViewed.add(category);
    }


    public void deleteCategory(Category selectedCategory) throws MovieExceptions {
        categoryManager.deleteCategory(selectedCategory);
        categoriesToBeViewed.remove(selectedCategory);
    }


}

