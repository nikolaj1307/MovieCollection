package DAL.Rest;

import BE.TMDBMovie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

// REST connector for interacting with The Movie Database (TMDb) API
public class TMDBConnector {

    // Instance variable to store information about the found movie from TMDb
    private TMDBMovie movieFound;

    // Constructor to initiate a movie search by title
    public TMDBConnector(String title) throws JSONException, IOException, URISyntaxException, InterruptedException {
        searchMovie(title);
    }

    // Method to retrieve the TMDb API key from the configuration file
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

    // Private method to search for a movie on TMDb by title
    private void searchMovie(String title) throws IOException, URISyntaxException, InterruptedException, JSONException {
        // Create a new HttpClient for making requests
        HttpClient client = HttpClient.newHttpClient();

        // Get the TMDb API key
        String apiKey = getApiKey();

        // Encode the title for use in the URL
        String query = URLEncoder.encode(title, "UTF-8");

        // Build the HTTP request for searching movies on TMDb
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.themoviedb.org/3/search/movie?query=" + query))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        // Send the HTTP request and get the response
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the JSON response
        JSONObject jsonResponse = new JSONObject(response.body());
        JSONArray data = jsonResponse.getJSONArray("results");

        // Check if there are results
        if (data.length() > 0) {
            // Extract information about the first movie in the results
            JSONObject movieObject = data.getJSONObject(0);
            String jsonTitle = movieObject.getString("original_title");
            String jsonOverview = movieObject.getString("overview");
            String jsonImage = movieObject.getString("poster_path");

            // Create a new TMDBMovie object with the extracted information
            movieFound = new TMDBMovie(jsonTitle, jsonOverview, jsonImage);
        }
    }

    // Getter method to retrieve the found movie from TMDb
    public TMDBMovie getMovieFound() {
        return movieFound;
    }

    // Main method for testing the TMDBConnector class
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, JSONException {
        // Create a TMDBConnector instance with a movie search query
        TMDBConnector tmdbConnector = new TMDBConnector("Batman");

        // Retrieve the found movie and print its details
        TMDBMovie movie = tmdbConnector.getMovieFound();
        System.out.println(movie.getOriginal_title());
        System.out.println(movie.getOverview());
        System.out.println(movie.getPoster_path());
    }
}
