// Import statements
package GUI.Model;

import BE.Movie;
import BLL.MovieManager;
import BLL.Utility.MovieSearcher;
import GUI.Util.MovieExceptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Model class for managing movies
public class MovieModel {

    // ObservableList to store movies for viewing
    private ObservableList<Movie> moviesToBeViewed;

    // Manager for handling movie-related business logic
    private MovieManager movieManager;
    private MovieSearcher movieSearcher;

    // Constructor initializes the MovieManager and loads movies into the observable list
    public MovieModel() throws Exception {
        // Initialize the MovieManager
        movieManager = new MovieManager();

        // Create an observable list for storing movies
        moviesToBeViewed = FXCollections.observableArrayList();

        // Load all movies from the manager into the observable list
        moviesToBeViewed.addAll(movieManager.getAllMovies());

        // Initialize MovieSearcher for searching movies
        movieSearcher = new MovieSearcher();
    }

    // Getter for the observable list of movies
    public ObservableList<Movie> getObservableMovies() {
        return moviesToBeViewed;
    }

    // Method to create a new movie and add it to the manager and observable list
    public void createMovie(String name, String catName, double rating, String fileLink) throws MovieExceptions {
        // Create a new Movie object
        Movie movie = new Movie(name, catName, rating, fileLink);

        // Add the movie to the manager
        movieManager.createMovie(movie);

        // Add the movie to the observable list for immediate viewing in the UI
        moviesToBeViewed.add(movie);
    }

    // Method to delete a movie
    public void deleteMovie(Movie movie) throws MovieExceptions {
        // Delete the movie in DAL layer
        movieManager.deleteMovie(movie);

        // Remove the movie from the observable list (and UI)
        moviesToBeViewed.remove(movie);
        System.out.println("MovieModel");
    }

    // Method to update personal rating of a movie
    public void updatePersonalRating(Movie movie, Double newRating) throws MovieExceptions {
        movieManager.updatePersonalRating(movie, newRating);
    }

    // Method to update the last view date of a movie
    public void updateLastView(Movie movie, Date newDate) throws MovieExceptions {
        movieManager.updateLastView(movie, newDate);
    }

    // Method to get movies based on rating, categories, and search query
    public List<Movie> getMoviesByRatingAndCategories(Double selectedRating, List<String> selectedCategory, String searchQuery) throws MovieExceptions {
        List<Movie> moviesByRatingAndCategories = new ArrayList<>();
        for (Movie movie : moviesToBeViewed) {

            // Check if the movie matches the selected criteria
            boolean categoryIsMatch = selectedCategory == null || selectedCategory.isEmpty() || selectedCategory.contains(movie.getCatName());
            boolean ratingIsMatch = selectedRating == null || movie.getRating() >= selectedRating;
            boolean titleIsMatch = movieSearcher.compareMovieTitle(searchQuery, movie);

            // If all conditions are met, add the movie to the list
            if (categoryIsMatch && ratingIsMatch && titleIsMatch) {
                moviesByRatingAndCategories.add(movie);
            }
        }
        return moviesByRatingAndCategories;
    }
}
