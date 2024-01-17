package BLL.Utility;

import BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSearcher {

    // søge efter film i en given liste ved at matche en forespørgsel.
    public List<Movie> search(List<Movie> searchBase, String query){
        List<Movie> searchResult = new ArrayList<>();

        for (Movie movie: searchBase){
            if (compareMovieTitle(query, movie)) {
                searchResult.add(movie);
            }
        }
        return searchResult;
    }

    private boolean compareMovieTitle(String query, Movie movie){
        return movie.getName().toLowerCase().contains(query.toLowerCase());
    }


}
