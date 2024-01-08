package BLL;

import BE.Movie;
import BLL.Utility.MovieSearcher;
import DAL.DB.MovieDAO_DB;
import DAL.IMovieDataAccess;

import java.io.IOException;
import java.util.List;

public class MovieManager {

    // Data access object for handling movie-related database operations
    private IMovieDataAccess movieDAO;

    // Constructor initializes the movieDAO with a concrete implementation (MovieDAO_DB)
    public MovieManager() throws IOException {
        movieDAO = new MovieDAO_DB();
    }

    // Retrieve a list of all movies from the data access layer
    public List<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    // Create a new movie and add it to the data access layer
    public void createMovie(Movie movie) throws Exception {
        movieDAO.createMovie(movie);
    }

    public void deleteMovie(Movie movie) throws Exception {
        movieDAO.deleteMovie(movie);
    }

}
