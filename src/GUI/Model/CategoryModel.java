package GUI.Model;


import BE.Category;
import BE.Movie;
import BLL.CategoryManager;
import DAL.DB.CategoryDAO_DB;
import GUI.Util.MovieExceptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel {



    private CategoryManager categoryManager;
    private CategoryDAO_DB categoryDAO;

    private ObservableList<Category> categoriesToBeViewed;

    public CategoryModel() throws Exception {
        categoryManager = new CategoryManager();

        categoriesToBeViewed = FXCollections.observableArrayList();
        categoriesToBeViewed.addAll(categoryManager.getAllCategories());

    }

    public void createNewCategory(String catName) throws MovieExceptions {
        Category category = new Category(catName);
        categoryManager.createNewCategory(category);
        categoriesToBeViewed.add(category);
    }

}
