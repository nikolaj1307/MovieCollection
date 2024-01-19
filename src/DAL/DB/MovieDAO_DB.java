package DAL.DB;

import BE.Movie;
import DAL.IMovieDataAccess;
import GUI.Util.MovieExceptions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

// Data access object (DAO) for handling movie-related operations in the database
public class MovieDAO_DB implements IMovieDataAccess {

    // Database connector for establishing a connection to the database
    private MyDatabaseConnector databaseConnector;

    // Constructor initializes the database connector
    public MovieDAO_DB() throws MovieExceptions {
        databaseConnector = new MyDatabaseConnector();
    }

    // Retrieve all movies from the database
    @Override
    public List<Movie> getAllMovies() throws MovieExceptions {
        // List to store all movies retrieved from the database
        ArrayList<Movie> allMovies = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            // SQL query to select all movies with their categories from the Movie, CatMovie, and Category tables
            String sql = "SELECT " +
                    "m.*, " +
                    "STRING_AGG(c.CatName, ', ') AS Categories " +
                    "from Movie m " +
                    "JOIN CatMovie cm ON m.Id = cm.MovieID " +
                    "JOIN Category c ON cm.CategoryId = c.Id " +
                    "GROUP BY m.Id, m.Name, m.FileLink, m.LastView, m.PersonalRating, m.Rating;";
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {
                // Map DB row to Movie object
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String catName = rs.getString("Categories");
                double rating = rs.getDouble("Rating");
                String fileLink = rs.getString("FileLink");
                Date lastView = rs.getDate("LastView");
                double personalRating = rs.getDouble("PersonalRating");

                // Create a Movie object and add it to the list
                Movie movie = new Movie(id, name, catName, rating, fileLink, lastView, personalRating);
                allMovies.add(movie);
            }

            // Return the list of movies
            return allMovies;

        } catch (SQLException ex) {
            // Handle SQL exceptions by wrapping them in a MovieExceptions and rethrowing
            throw new MovieExceptions("Could not get movies from database", ex);
        }
    }

    // Create a new movie in the database
    @Override
    public Movie createMovie(Movie movie) throws MovieExceptions {
        // SQL statement to insert a new movie into the Movie and CatMovie tables
        String sql =
                "INSERT INTO dbo.Movie (Name, Rating, FileLink) " +
                        "VALUES (?,?,?); " +
                        "DECLARE @Id INT; " +
                        "SET @Id = SCOPE_IDENTITY(); " +
                        "INSERT INTO dbo.CatMovie (CategoryId, MovieId) " +
                        "VALUES ((SELECT Id FROM dbo.Category WHERE CatName = ?), @Id);";

        try (Connection conn = databaseConnector.getConnection()) {
            // Prepare the SQL statement for execution
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Bind parameters
            stmt.setString(1, movie.getName());
            stmt.setDouble(2, movie.getRating());
            stmt.setString(3, movie.getFileLink());
            stmt.setString(4, movie.getCatName());

            // Execute the SQL statement to insert the new movie
            stmt.executeUpdate();

            // Get the generated ID from the DB (if applicable)
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            // Create a new Movie object with the provided details
            Movie createdMovie = new Movie(movie.getName(), movie.getRating(), movie.getFileLink());

            // Return the created movie
            return createdMovie;
        } catch (SQLException e) {
            // Handle SQL exceptions by wrapping them in a MovieExceptions and rethrowing
            throw new MovieExceptions("Could not create movie in database", e);
        }
    }

    // Update the personal rating of a movie in the database
    @Override
    public void updatePersonalRating(Movie movie, Double newRating) throws MovieExceptions {
        String sql = "UPDATE dbo.movie SET PersonalRating = ? WHERE [id] = ?;";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Bind parameters
            stmt.setDouble(1, newRating);
            stmt.setInt(2, movie.getId());

            // Execute the SQL statement to update the personal rating
            stmt.executeUpdate();

            // Update the movie object with the new personal rating
            movie.setPersonalRating(newRating);

        } catch (SQLException e) {
            // Handle SQL exceptions by wrapping them in a MovieExceptions and rethrowing
            throw new MovieExceptions("Could not update personal rating in database", e);
        }
    }

    // Update the last view date of a movie in the database
    @Override
    public void updateLastView(Movie movie, Date newDate) throws MovieExceptions {
        String sql = "UPDATE dbo.movie SET LastView = ? WHERE [id] = ?;";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Converts java.util.Date to java.sql.Date - Because .setDate can't take a java.util.Date
            java.sql.Date sqlDate = new java.sql.Date(newDate.getTime());

            // Bind parameters
            stmt.setDate(1, sqlDate);
            stmt.setInt(2, movie.getId());

            // Execute the SQL statement to update the last view date
            stmt.executeUpdate();

            // Update the movie object with the new last view date
            movie.setLastView(sqlDate);

        } catch (SQLException e) {
            // Handle SQL exceptions by wrapping them in a MovieExceptions and rethrowing
            throw new MovieExceptions("Could not update last view in database", e);
        }
    }

    // Delete a movie from the database
    @Override
    public Movie deleteMovie(Movie movie) throws MovieExceptions {
        // SQL statement to delete a movie from the CatMovie and Movie tables
        String sql =
                "DElETE FROM dbo.CatMovie WHERE MovieId = ? " +
                        "DELETE FROM dbo.Movie WHERE Id = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Bind parameters
            stmt.setInt(1, movie.getId());
            stmt.setInt(2, movie.getId());

            // Execute the SQL statement to delete the movie
            stmt.executeUpdate();

        } catch (SQLException ex) {
            // Handle SQL exceptions by wrapping them in a MovieExceptions and rethrowing
            throw new MovieExceptions("Could not delete movie in database", ex);
        }

        // Return the deleted movie
        return movie;
    }
}




