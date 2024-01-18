package DAL.Rest;

import BE.TMDBMovie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class TMDBConnector {

    private TMDBMovie movieFound;

    public TMDBConnector(String title) throws JSONException, IOException, URISyntaxException, InterruptedException {
    searchMovie(title);
    }

    public static String getApiKey() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("config/config.api")) {
            properties.load(fileInputStream);
            return properties.getProperty("API_KEY");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void searchMovie(String title) throws IOException, URISyntaxException, InterruptedException, JSONException {
        HttpClient client =HttpClient.newHttpClient();
        String apiKey = getApiKey();

        String query = URLEncoder.encode(title, "UTF-8");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.themoviedb.org/3/search/movie?query=" + query))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonResponse = new JSONObject(response.body());
        JSONArray data = jsonResponse.getJSONArray("results");
        if(data.length() > 0) {
            JSONObject movieObject = data.getJSONObject(0);
            String jsonTitle = movieObject.getString("original_title");
            String jsonOverview = movieObject.getString("overview");
            String jsonImage = movieObject.getString("poster_path");
            movieFound = new TMDBMovie(jsonTitle, jsonOverview, jsonImage);
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
        System.out.println(movie.getPoster_path());

    }
}
