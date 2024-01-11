package DAL.Rest;

import BE.TMDBMovie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TMDBConnector {

    private TMDBMovie movieFound;

    public TMDBConnector(String title) throws JSONException, IOException, URISyntaxException, InterruptedException {
    searchMovie(title);
    }

    private void searchMovie(String title) throws IOException, URISyntaxException, InterruptedException, JSONException {
        HttpClient client =HttpClient.newHttpClient();

        String query = URLEncoder.encode(title, "UTF-8");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.themoviedb.org/3/search/movie?query=" + query))
                .header("accept", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2M2U0ZWMzY2IxOGQ5ZGQ3ODFkZTgxYTY5NTRmNTZkYSIsInN1YiI6IjY1OWRkYjM1NmQ5N2U2MDBmMDc4ZDMyNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.8beju07le3y6Pp4-QMttgFeFUlP6wqRaHEfWvW5mqRo")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonResponse = new JSONObject(response.body());
        JSONArray data = jsonResponse.getJSONArray("results");
        if(data.length() > 0) {
            JSONObject movieObject = data.getJSONObject(0);
            String jsonTitle = movieObject.getString("original_title");
            String jsonOverview = movieObject.getString("overview");
            movieFound = new TMDBMovie(jsonTitle, jsonOverview);
        }
    }
    public TMDBMovie getMovieFound() {
        return movieFound;
    }

    public static void main (String[]args) throws URISyntaxException, IOException, InterruptedException, JSONException {
        TMDBConnector tmdbConnector = new TMDBConnector("Batman");

        TMDBMovie movie = tmdbConnector.getMovieFound();
        System.out.println(movie.getOriginal_title());
        System.out.println(movie.getOverview());

    }
}
