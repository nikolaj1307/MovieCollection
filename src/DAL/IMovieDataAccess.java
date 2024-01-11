package DAL;

import BE.Movie;

import java.util.List;

public interface IMovieDataAccess {
    public List<Movie> getAllMovies() throws Exception;

    public Movie createMovie(Movie movie) throws Exception;

    public void updatePersonalRating(Movie movie, Double newRating) throws Exception;

    public Movie deleteMovie(Movie movie) throws Exception;
}
