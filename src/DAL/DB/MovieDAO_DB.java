package DAL.DB;

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
                String sql = "SELECT * FROM DBO.Movie ";
                ResultSet rs = stmt.executeQuery(sql);

                // Loop through rows from the database result set
                while (rs.next()) {

                    // Map DB row to Movie object
                    int id = rs.getInt("Id");
                    String name = rs.getString("Name");
                    double rating = rs.getDouble("Rating");
                    String fileLink = rs.getString("FileLink");
                    int lastView = rs.getInt("LastView");
                    double personalRating = rs.getDouble("PersonalRating");

                    Movie movie = new Movie(name, rating, fileLink, lastView);
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

            String sql = "INSERT INTO dbo.Movie (Name, Rating, FileLink) VALUES (?,?,?);";

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

                Movie createdMovie = new Movie(movie.getName(), movie.getRating(), movie.getFileLink(), id);

                return createdMovie;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void updateMovie(Movie movie) throws Exception {

        }

        @Override
        public Movie deleteMovie(Movie movie) throws Exception {

            return null;
        }





}



