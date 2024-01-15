package GUI.Model;


import BE.Category;
import BLL.CategoryManager;
import DAL.DB.CategoryDAO_DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel {

    private CategoryManager categoryManager;
    private CategoryDAO_DB categoryDAO;

    public CategoryModel() throws Exception {
        categoryManager = new CategoryManager();

    }

}
