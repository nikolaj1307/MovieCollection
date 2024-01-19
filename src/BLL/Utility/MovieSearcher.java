package BLL.Utility;

import BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSearcher {

    // søge efter film i en given liste ved at matche en forespørgsel.
    public List<Movie> search(List<Movie> searchBase, String query){
        List<Movie> searchResult = new ArrayList<>();

        for (Movie movie: searchBase){
            if (matchesFiltersAndSearch(query, movie)) {
                searchResult.add(movie);
            }
        }
        return searchResult;
    }

    public boolean compareMovieTitle(String query, Movie movie){
        return movie.getName().toLowerCase().contains(query.toLowerCase());
    }

    private boolean matchesFiltersAndSearch(String query, Movie movie) {
        boolean matchesTitle = compareMovieTitle(query, movie);

        return matchesTitle;
    }
}
