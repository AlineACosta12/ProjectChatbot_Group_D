import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class WeatherAPI {

    // Method to fetch the weather for a given location
    public static String getWeather(String location) {
        try {
            // Hardcoded for simplicity (Example: New York)
            double latitude = 40.7128; //
            double longitude = -74.0060;

            // Construct the API URL for fetching weather data
            String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current_weather=true";
            URL url = new URL(urlString); // Create a URL object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Open a connection to the API URL
            conn.setRequestMethod("GET"); // Set the request method to GET
            conn.connect(); // Establish the connection to the API

            // Check the response code to ensure the request was successful
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                return "Error: Unable to fetch weather data.";
            }


            // Create a Scanner to read the response from the API
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            // Parse JSON response using the JSONObject class
            JSONObject data = new JSONObject(inline.toString()); // Convert the string response to a JSONObject
            JSONObject currentWeather = data.getJSONObject("current_weather");
            double temperature = currentWeather.getDouble("temperature");
            String description = currentWeather.getDouble("windspeed") > 20 ? "windy" : "clear";

            // Return the formatted weather information as a string
            return "Temperature: " + temperature + "°C, " + description;

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
