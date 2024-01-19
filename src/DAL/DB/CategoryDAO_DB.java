package DAL.DB;

import BE.Category;
import DAL.ICategoryDataAccess;
import GUI.Util.MovieExceptions;

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
            throw new MovieExceptions(e);
        }
    }

        @Override
        public Category deleteCategory(Category category) throws MovieExceptions {
            String sql = "DELETE FROM dbo.Category WHERE CatName = ?;";


            try (Connection conn = databaseConnector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                // Bind parameters
                stmt.setString(1, category.getCatName());

                stmt.executeUpdate();
                // Run the specified SQL statement

            } catch (SQLException ex) {
                throw new MovieExceptions("Could not delete movie", ex);
            }

            return category;
        }
    }



