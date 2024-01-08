package GUI.Model;


import BE.Category;
import DAL.DB.CategoryDAO_DB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel {

    private CategoryDAO_DB categoryDAO;

    public CategoryModel() throws IOException {
        categoryDAO = new CategoryDAO_DB();
    }

}
