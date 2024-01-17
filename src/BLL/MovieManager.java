package BLL;

import BE.Category;
import BE.Movie;
import GUI.Util.Alerts;
import BLL.Utility.MovieSearcher;
import DAL.DB.CategoryDAO_DB;
import DAL.DB.MovieDAO_DB;
import DAL.IMovieDataAccess;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MovieManager {

    private Alerts alerts;

    // Data access object for handling movie-related database operations
    private IMovieDataAccess movieDAO;

    private CategoryDAO_DB categoryDAO_db;

    private MovieSearcher movieSearcher = new MovieSearcher();
    // Constructor initializes the movieDAO with a concrete implementation (MovieDAO_DB)
    public MovieManager() throws IOException {
        movieDAO = new MovieDAO_DB();
        alerts = new Alerts();
    }

    // Retrieve a list of all movies from the data access layer
    public List<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    // Create a new movie and add it to the data access layer
    public Movie createMovie(Movie newMovie) throws Exception {
        return movieDAO.createMovie(newMovie);
    }

    public void deleteMovie(Movie movie) throws Exception {
        movieDAO.deleteMovie(movie);
        System.out.println("MovieManager");
    }

    public void updatePersonalRating(Movie movie, Double newRating) throws Exception {
        movieDAO.updatePersonalRating(movie, newRating);
    }

    public void updateLastView(Movie movie, Date newDate) throws Exception {
        movieDAO.updateLastView(movie, newDate);
    }

    public List<Movie> searchMovie (String query) throws Exception {
        List<Movie> allMovies = getAllMovies();
        List<Movie> movieResult = movieSearcher.search(allMovies, query);
        return movieResult;
    }

    public void movieExpiring(List<Movie> movies) {
        LocalDate twoYearsAgo = LocalDate.now().minusYears(2);

        for (Movie movie : movies) {
            if (movie.getLastView() != null) {
                LocalDate lastViewDate = new java.sql.Date(movie.getLastView().getTime()).toLocalDate();

                if (lastViewDate.isBefore(twoYearsAgo) && (movie.getPersonalRating() < 6)) {
                    alerts.showExpiringMovieAlert(movie);
                }
            }
        }
    }


}
