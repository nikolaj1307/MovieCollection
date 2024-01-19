package DAL.DB;

import BE.Category;
import DAL.ICategoryDataAccess;
import GUI.Util.MovieExceptions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Data access object (DAO) for handling category-related operations in the database
public class CategoryDAO_DB implements ICategoryDataAccess {

    // Database connector for establishing a connection to the database
    private MyDatabaseConnector databaseConnector;

    // Constructor initializes the database connector
    public CategoryDAO_DB() throws MovieExceptions {
        databaseConnector = new MyDatabaseConnector();
    }

    // Retrieve all categories from the database
    @Override
    public List<Category> getAllCategories() throws MovieExceptions {

        // List to store all categories retrieved from the database
        ArrayList<Category> allCategories = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            // SQL query to select all categories from the Category table
            String sql = "SELECT * FROM DBO.Category; ";
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                // Map DB row to Category object
                int id = rs.getInt("Id");
                String catname = rs.getString("CatName");

                // Create a Category object and add it to the list
                Category category = new Category(id, catname);
                allCategories.add(category);
                System.out.println("Category = " + category.getCatName());
            }

            // Return the list of categories
            return allCategories;

        } catch (SQLException e) {
            // Handle SQL exceptions by wrapping them in a MovieExceptions and rethrowing
            throw new MovieExceptions(e);
        }
    }

    // Create a new category in the database
    @Override
    public Category createNewCategory(Category category) throws MovieExceptions {
        // SQL statement to insert a new category into the Category table
        String sql = "INSERT INTO dbo.Category (CatName) VALUES (?);";

        try (Connection conn = databaseConnector.getConnection()) {
            // Prepare the SQL statement for execution
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Bind parameters
            stmt.setString(1, category.getCatName());

            // Execute the SQL statement to insert the new category
            stmt.executeUpdate();

            // Get the generated ID from the DB (if applicable)
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            // Create a new Category object with the provided name
            Category createdCategory = new Category(category.getCatName());

            // Return the created category
            return createdCategory;

        } catch (SQLException e) {
            // Handle SQL exceptions by wrapping them in a MovieExceptions and rethrowing
            throw new MovieExceptions(e);
        }
    }

    // Delete a category from the database
    @Override
    public Category deleteCategory(Category category) throws MovieExceptions {
        // SQL statement to delete a category from the Category table
        String sql = "DELETE FROM dbo.Category WHERE CatName = ?;";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Bind parameters
            stmt.setString(1, category.getCatName());

            // Execute the SQL statement to delete the category
            stmt.executeUpdate();

        } catch (SQLException ex) {
            // Handle SQL exceptions by wrapping them in a MovieExceptions and rethrowing
            throw new MovieExceptions("Could not delete movie", ex);
        }

        // Return the deleted category
        return category;
    }
}




