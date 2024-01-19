package GUI.Model;

import BE.Movie;
import BLL.MovieManager;
import BLL.Utility.MovieSearcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        movieSearcher = new MovieSearcher();
    }

    // Getter for the observable list of movies
    public ObservableList<Movie> getObservableMovies() {
        return moviesToBeViewed;
    }

    // Method to create a new movie and add it to the manager and observable list
    public void createMovie(String name, String catName, double rating, String fileLink) throws Exception {
        // Create a new Movie object
        Movie movie = new Movie(name, catName, rating, fileLink);

        // Add the movie to the manager
        movieManager.createMovie(movie);

        // Add the movie to the observable list for immediate viewing in the UI
        moviesToBeViewed.add(movie);
    }

    public void deleteMovie(Movie movie) throws Exception {
        // delete movie in DAL layer (through the layers)
        movieManager.deleteMovie(movie);
        // remove from observable list (and UI)
        moviesToBeViewed.remove(movie);
        System.out.println("MovieModel");
    }

    public void updatePersonalRating(Movie movie, Double newRating) throws Exception {
        movieManager.updatePersonalRating(movie, newRating);
    }

    public void updateLastView(Movie movie, Date newDate) throws Exception {
        movieManager.updateLastView(movie, newDate);
    }

    public List<Movie> getMoviesByRatingAndCategories(Double selectedRating, List<String> selectedCategory, String searchQuery) {
        List<Movie> moviesByRatingAndCategories = new ArrayList<>();
        for (Movie movie : moviesToBeViewed) {

            boolean categoryIsMatch = selectedCategory == null || selectedCategory.isEmpty() || selectedCategory.contains(movie.getCatName());
            boolean ratingIsMatch = selectedRating == null || movie.getRating() >= selectedRating;
            boolean titleIsMatch = movieSearcher.compareMovieTitle(searchQuery, movie);

            if (categoryIsMatch && ratingIsMatch && titleIsMatch) {
                moviesByRatingAndCategories.add(movie);
            }
        }
        return moviesByRatingAndCategories;
    }
}


