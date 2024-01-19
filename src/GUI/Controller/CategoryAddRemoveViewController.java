// Import statements
package GUI.Controller;

import BE.Category;
import GUI.Model.CategoryModel;
import GUI.Util.MovieExceptions;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// Controller class for the CategoryAddRemoveView
public class CategoryAddRemoveViewController {

    // Model instance for accessing data
    private CategoryModel categoryModel;

    // Reference to the AddMovieController for communication between controllers
    private AddMovieController addMovieController;

    // Constructor for initializing model instances
    public CategoryAddRemoveViewController() throws MovieExceptions {
        try {
            categoryModel = new CategoryModel();
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }

    // Setter for AddMovieController
    public void setAddMovieController(AddMovieController addMovieController) {
        this.addMovieController = addMovieController;
    }

    // FXML elements for UI controls
    @FXML
    private MFXButton categoryCancelBtn;

    @FXML
    private TextField categoryTextField;

    // Event handler for the Cancel button
    public void onClickCategoryCancelBtn(ActionEvent event) {
        // Close the current stage
        Stage stage = (Stage) categoryCancelBtn.getScene().getWindow();
        stage.close();
    }

    // Event handler for the Save button
    public void onClickCategorySaveBtn(ActionEvent event) throws MovieExceptions {
        try {
            // Create a new category in the database
            categoryModel.createNewCategory(categoryTextField.getText());

            // Retrieve the entered category name
            String category = categoryTextField.getText();

            // Create a Category object with the new category name
            Category newCategory = new Category(category);

            // Add the new category to the ComboBox in the AddMovieController
            addMovieController.addCategoriesToBox(newCategory);

            // Reload categories in the ComboBox of AddMovieController
            addMovieController.loadCategories();

            // Close the current stage
            Stage stage = (Stage) categoryCancelBtn.getScene().getWindow();
            stage.close();
        } catch (MovieExceptions e) {
            throw new MovieExceptions(e);
        }
    }
}
