package DAL.DB;

import BE.Category;
import BE.Movie;
import DAL.IMovieDataAccess;
import GUI.Util.MovieExceptions;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class MovieDAO_DB implements IMovieDataAccess {

        private MyDatabaseConnector databaseConnector;

        public MovieDAO_DB() throws MovieExceptions {
            databaseConnector = new MyDatabaseConnector();
        }


        @Override
        public List<Movie> getAllMovies() throws MovieExceptions {
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
                throw new MovieExceptions("Could not get movies from database", ex);
            }
        }


        @Override
        public Movie createMovie(Movie movie) throws MovieExceptions {

            String sql =
                    "INSERT INTO dbo.Movie (Name, Rating, FileLink) " +
                            "VALUES (?,?,?); " +
                            "DECLARE @Id INT; " +
                            "SET @Id = SCOPE_IDENTITY(); " +
                            "INSERT INTO dbo.CatMovie (CategoryId, MovieId) " +
                            "VALUES ((SELECT Id FROM dbo.Category WHERE CatName = ?), @Id);";

            try (Connection conn = databaseConnector.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);

                // Bind parameters
                stmt.setString(1, movie.getName());
                stmt.setDouble(2, movie.getRating());
                stmt.setString(3, movie.getFileLink());
                stmt.setString(4, movie.getCatName());

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
                throw new MovieExceptions(e);
            }
        }

    @Override
    public void updatePersonalRating(Movie movie, Double newRating) throws MovieExceptions {
        String sql = "UPDATE dbo.movie SET PersonalRating = ? WHERE [id] = ?;";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, newRating);
            stmt.setInt(2, movie.getId());

            stmt.executeUpdate();

            movie.setPersonalRating(newRating);

        } catch (SQLException e) {
            throw new MovieExceptions(e);
        }

    }

    @Override
    public void updateLastView(Movie movie, Date newDate) throws MovieExceptions {
        String sql = "UPDATE dbo.movie SET LastView = ? WHERE [id] = ?;";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            //Converts java.util.Date to java.sql.Date - Because .setDate can't take a java.util.Date
            java.sql.Date sqlDate = new java.sql.Date(newDate.getTime());

            stmt.setDate(1, sqlDate);
            stmt.setInt(2, movie.getId());

            stmt.executeUpdate();

            movie.setLastView(sqlDate);

        } catch (SQLException e) {
            throw new MovieExceptions(e);
        }
    }

    @Override
        public Movie deleteMovie(Movie movie) throws MovieExceptions {

        String sql =
                "DElETE FROM dbo.CatMovie WHERE MovieId = ? " +
                        "DELETE FROM dbo.Movie WHERE Id = ?";


            try (Connection conn = databaseConnector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql))
            {
                // Bind parameters
                stmt.setInt(1, movie.getId());
                stmt.setInt(2, movie.getId());

                stmt.executeUpdate();
                // Run the specified SQL statement
                System.out.println("MovieDAO");
            }
            catch (SQLException ex)
            {
                throw new MovieExceptions("Could not delete movie", ex);
            }
            return movie;
        }
}



