package GUI.Controller;

import BE.Category;
import GUI.Model.CategoryModel;
import GUI.Util.MovieExceptions;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoryAddRemoveViewController {

    private CategoryModel categoryModel;

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


    @FXML
    private MFXButton categoryCancelBtn;

    @FXML
    private TextField categoryTextField;

    public void onClickCategoryCancelBtn(ActionEvent event) {
        // Close the current stage
        Stage stage = (Stage) categoryCancelBtn.getScene().getWindow();
        stage.close();

    }

    public void onClickCategorySaveBtn(ActionEvent event) throws MovieExceptions {
        try {
            categoryModel.createNewCategory(categoryTextField.getText());

            String category = categoryTextField.getText();

            Category newCategory = new Category(category);

            addMovieController.addCategoriesToBox(newCategory);

            addMovieController.loadCategories();

            // Close the current stage
            Stage stage = (Stage) categoryCancelBtn.getScene().getWindow();
            stage.close();

        } catch (MovieExceptions e) {
            throw new MovieExceptions(e);
        }
    }
}
