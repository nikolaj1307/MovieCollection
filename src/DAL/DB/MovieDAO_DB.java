package DAL.DB;

import BE.Category;
import BE.Movie;
import DAL.IMovieDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {

        private MyDatabaseConnector databaseConnector;

        public MovieDAO_DB() throws IOException {
            databaseConnector = new MyDatabaseConnector();
        }


        @Override
        public List<Movie> getAllMovies() throws Exception {
            ArrayList<Movie> allMovies = new ArrayList<>();

            try (Connection conn = databaseConnector.getConnection();
                 Statement stmt = conn.createStatement())
            {
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

                    Movie movie = new Movie(id, name, catName, rating, fileLink, lastView, personalRating);
                    allMovies.add(movie);
                }
                return allMovies;
            }
            catch (SQLException ex){
                ex.printStackTrace();
                throw new Exception("Could not get movies from database", ex);
            }
        }

        @Override
        public Movie createMovie(Movie movie) throws Exception {

        String sql =
                "INSERT INTO dbo.Movie (Name, Rating, FileLink) VALUES (?, ?, ?);";

            try (Connection conn = databaseConnector.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);

                // Bind parameters
                stmt.setString(1, movie.getName());
                stmt.setDouble(2, movie.getRating());
                stmt.setString(3, movie.getFileLink());

                // Run the specified SQL statement
                stmt.executeUpdate();

                // Get the generated ID from the DB
                ResultSet rs = stmt.getGeneratedKeys();
                int id = 0;

                if (rs.next()) {
                    id = rs.getInt(1);
                }

                Movie createdMovie = new Movie(movie.getName(), movie.getRating(), movie.getFileLink());

                return createdMovie;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    @Override
    public void updatePersonalRating(Movie movie, Double newRating) throws Exception {
        String sql = "UPDATE dbo.movie SET PersonalRating = ? WHERE [id] = ?;";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, newRating);
            stmt.setInt(2, movie.getId());

            stmt.executeUpdate();

            movie.setPersonalRating(newRating);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateLastView(Movie movie, java.util.Date newDate) throws Exception {
        String sql = "UPDATE dbo.movie SET LastView = ? WHERE [id] = ?;";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            java.sql.Date sqlDate = new java.sql.Date(newDate.getTime());

            stmt.setDate(1, sqlDate);
            stmt.setInt(2, movie.getId());

            stmt.executeUpdate();

            movie.setLastView(sqlDate);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
        public Movie deleteMovie(Movie movie) throws Exception {

            String sql = "DELETE FROM dbo.Movie WHERE Id = ?;";


            try (Connection conn = databaseConnector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql))
            {
                // Bind parameters
                stmt.setInt(1, movie.getId());

                stmt.executeUpdate();
                // Run the specified SQL statement
                System.out.println("MovieDAO");
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                throw new Exception("Could not delete movie", ex);
            }
            return movie;
        }
}



