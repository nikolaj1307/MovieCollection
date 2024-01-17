package DAL;

import BE.Movie;
import GUI.Util.MovieExceptions;

import java.util.Date;
import java.util.List;

public interface IMovieDataAccess {
    public List<Movie> getAllMovies() throws MovieExceptions;

    public Movie createMovie(Movie movie) throws MovieExceptions;

    public void updatePersonalRating(Movie movie, Double newRating) throws MovieExceptions;

    public void updateLastView(Movie movie, Date newDate) throws MovieExceptions;

    public Movie deleteMovie(Movie movie) throws MovieExceptions;
}
