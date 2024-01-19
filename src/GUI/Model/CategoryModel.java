// Import statements
package GUI.Model;

import BE.Category;
import BLL.CategoryManager;
import GUI.Util.MovieExceptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Model class for managing categories
public class CategoryModel {

    // Instance of CategoryManager to handle category-related business logic
    private CategoryManager categoryManager;

    // ObservableList to store categories for UI updates
    private ObservableList<Category> categoriesToBeViewed;

    // Constructor for initializing the model
    public CategoryModel() throws Exception {
        // Initialize CategoryManager and ObservableList
        categoryManager = new CategoryManager();
        categoriesToBeViewed = FXCollections.observableArrayList();
        // Add all categories from the data layer to the ObservableList
        categoriesToBeViewed.addAll(categoryManager.getAllCategories());
    }

    // Getter for the observable list of categories
    public ObservableList<Category> getObservableCategories() {
        return categoriesToBeViewed;
    }

    // Method to create a new category
    public void createNewCategory(String catName) throws MovieExceptions {
        // Create a new Category instance
        Category category = new Category(catName);
        // Call CategoryManager to create the category in the data layer
        categoryManager.createNewCategory(category);
        // Add the new category to the ObservableList for UI updates
        categoriesToBeViewed.add(category);
    }

    // Method to delete a category
    public void deleteCategory(Category selectedCategory) throws MovieExceptions {
        // Call CategoryManager to delete the category from the data layer
        categoryManager.deleteCategory(selectedCategory);
        // Remove the deleted category from the ObservableList for UI updates
        categoriesToBeViewed.remove(selectedCategory);
    }
}
