package GUI.Model;


import BE.Category;
import BLL.CategoryManager;
import DAL.DB.CategoryDAO_DB;
import GUI.Util.MovieExceptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoryModel {


    private CategoryManager categoryManager;

    private ObservableList<Category> categoriesToBeViewed;

    public CategoryModel() throws Exception {
        categoryManager = new CategoryManager();
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

