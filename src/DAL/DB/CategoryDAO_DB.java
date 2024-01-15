package DAL.DB;

import BE.Category;
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

                // Map DB row to Category object
                int id = rs.getInt("Id");
                String catname = rs.getString("CatName");

                Category category = new Category(id, catname);
                allCategories.add(category);
                System.out.println("Category = " + category.getCatName());
            }
            return allCategories;
        }
    }

    public Category insertCatToCatMovie(Category category) throws Exception {

        // SQL command
        String sql = "INSERT INTO CatMovie  VALUES (?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            // Bind parameters
            stmt.setString(1, category.getCatName());

            // Run the specified SQL statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            return new Category(id, category.getCatName() );
        }
        catch (SQLException ex)
        {
            // create entry in log file
            ex.printStackTrace();
            throw new Exception("Could not insert category", ex);
        }
    }

    public int getCatId(String catName) throws Exception {
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT GenreID FROM Genre WHERE GenreType = ?")) {

            // Set parameters for the PreparedStatement
            stmt.setString(1, catName);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return rs.getInt("Id");

            }
            return -1;
        } catch (SQLException e) {
            throw new Exception("Error getting Category ID", e);
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

