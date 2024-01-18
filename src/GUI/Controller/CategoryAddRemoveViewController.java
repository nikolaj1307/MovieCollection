package GUI.Controller;

import BE.Category;
import BLL.CategoryManager;
import BLL.MovieManager;
import GUI.Model.CategoryModel;
import GUI.Model.MovieModel;
import GUI.Util.Alerts;
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
    public CategoryAddRemoveViewController() {
        try {
           categoryModel = new CategoryModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Setter for AddMovieController
    public void setAddMovieController(AddMovieController addMovieController) {
        this.addMovieController = addMovieController;
    }


    @FXML
    private MFXButton categoryCancelBtn;

    @FXML
    private MFXButton categorySaveBtn;

    @FXML
    private TextField categoryTextField;

    public void onClickCategoryCancelBtn(ActionEvent event) throws MovieExceptions {
        // Close the current stage
        Stage stage = (Stage) categoryCancelBtn.getScene().getWindow();
        stage.close();

    }

    public void onClickCategorySaveBtn(ActionEvent event) {
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
            throw new RuntimeException(e);
        }
    }
}
