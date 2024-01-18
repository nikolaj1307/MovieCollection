package DAL.DB;

import BE.Category;
import BE.Movie;
import DAL.ICategoryDataAccess;
import GUI.Util.MovieExceptions;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO_DB implements ICategoryDataAccess {

    private MyDatabaseConnector databaseConnector;

    public CategoryDAO_DB() throws MovieExceptions {
        databaseConnector = new MyDatabaseConnector();
    }


    @Override
    public List<Category> getAllCategories() throws MovieExceptions {

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
        catch (SQLException e){
            throw new MovieExceptions(e);
        }
    }

    public Category insertCatToCatMovie(Category category) throws MovieExceptions {

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
            throw new MovieExceptions("Could not insert category", ex);
        }
    }

    public int getCatId(String catName) throws MovieExceptions {
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
            throw new MovieExceptions("Error getting Category ID", e);
        }
    }

    @Override
    public Category createNewCategory(Category category) throws MovieExceptions {
        String sql = "INSERT INTO dbo.Category (CatName) VALUES (?);";

        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Bind parameters
            stmt.setString(1, category.getCatName());

            // Run the specified SQL statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            Category createdCategory = new Category(category.getCatName());

            return createdCategory;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updateCategory(Category category) throws MovieExceptions {

    }


    @Override
    public void deleteCategoryFromMovie(Category category, Movie movie) throws MovieExceptions {
        String sql =
                "DElETE FROM dbo.CatMovie WHERE CategoryId = ? " +
                        "DELETE FROM dbo.Movie WHERE Id = ?" +
                        "DELETE FROM dbo.Category WHERE Id = ?";


        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Bind parameters
            stmt.setInt(1, category.getCatId());
            stmt.setInt(2, movie.getId());
            stmt.setInt(3, category.getCatId());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new MovieExceptions("Could not delete category", ex);
        }
    }

    @Override
    public Category deleteCategory(Category category) throws MovieExceptions {
        return null;
    }
}


