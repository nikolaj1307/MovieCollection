package BLL;

import BE.Movie;
import GUI.Util.Alerts;
import BLL.Utility.MovieSearcher;
import DAL.DB.MovieDAO_DB;
import DAL.IMovieDataAccess;
import GUI.Util.MovieExceptions;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MovieManager {

    private Alerts alerts;

    // Data access object for handling movie-related database operations
    private IMovieDataAccess movieDAO;

    private MovieSearcher movieSearcher = new MovieSearcher();

    // Constructor initializes the movieDAO with a concrete implementation (MovieDAO_DB)
    public MovieManager() throws MovieExceptions {
        try {
            movieDAO = new MovieDAO_DB();
            alerts = new Alerts();
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }
    // Retrieve a list of all movies from the data access layer
    public List<Movie> getAllMovies() throws MovieExceptions {
        try {
            return movieDAO.getAllMovies();
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }

    // Create a new movie and add it to the data access layer
    public Movie createMovie(Movie newMovie) throws MovieExceptions {
        try {
            return movieDAO.createMovie(newMovie);
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }

    public void deleteMovie(Movie movie) throws MovieExceptions {
        try {
            movieDAO.deleteMovie(movie);
            System.out.println("MovieManager");
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }
    public void updatePersonalRating(Movie movie, Double newRating) throws MovieExceptions {
        try {
            movieDAO.updatePersonalRating(movie, newRating);
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
    }

    public void updateLastView(Movie movie, Date newDate) throws MovieExceptions {
        try {
            movieDAO.updateLastView(movie, newDate);
        } catch (Exception e) {
            throw new MovieExceptions(e);
        }
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
