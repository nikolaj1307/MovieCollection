package DAL.DB;

import BE.Category;
import BE.Movie;
import DAL.ICategoryDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO_DB implements ICategoryDataAccess {

    private MyDatabaseConnector databaseConnector;

    public CategoryDAO_DB() throws IOException {
        databaseConnector = new MyDatabaseConnector();
    }


    @Override
    public List<Category> getAllCategories() throws Exception {

        ArrayList<Category> allCategories = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM DBO.Category; ";
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                // Map DB row to Movie object
                int id = rs.getInt("Id");
                String name = rs.getString("Name");

                Category category = new Category(id, name);
                allCategories.add(category);
                System.out.println("Category = " + category.getName());
            }
            return allCategories;
        }
    }

    @Override
    public Category createNewCategory(Category category) throws Exception {
        return null;
    }

    @Override
    public void updateCategory(Category category) throws Exception {

    }

    @Override
    public Category deleteCategory(Category category) throws Exception {
        return null;
    }
}

