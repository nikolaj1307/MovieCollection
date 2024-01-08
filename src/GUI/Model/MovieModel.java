package GUI.Model;

import BE.Movie;
import BLL.MovieManager;
import BLL.Utility.MovieSearcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {

    // ObservableList to store movies for viewing
    private ObservableList<Movie> moviesToBeViewed;

    // Manager for handling movie-related business logic
    private MovieManager movieManager;
    private MovieSearcher movieSearcher = new MovieSearcher();

    // Constructor initializes the MovieManager and loads movies into the observable list
    public MovieModel() throws Exception {
        // Initialize the MovieManager
        movieManager = new MovieManager();

        // Create an observable list for storing movies
        moviesToBeViewed = FXCollections.observableArrayList();

        // Load all movies from the manager into the observable list
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }

    // Getter for the observable list of movies
    public ObservableList<Movie> getObservableMovies() {

        return moviesToBeViewed;
    }

    // Method to create a new movie and add it to the manager and observable list
    public void createMovie(String name, double rating, String fileLink) throws Exception {
        // Create a new Movie object
        Movie movie = new Movie(name, rating, fileLink);

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

    public void searchMovie(String query) throws Exception {
        List<Movie> searchResult = movieManager.searchMovie(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResult);
    }
}


