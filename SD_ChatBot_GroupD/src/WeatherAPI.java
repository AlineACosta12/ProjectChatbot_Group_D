import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class WeatherAPI {

    // Method to get latitude and longitude using a geolocation API
    private static double[] getCoordinates(String location) {
        String apiKey = "YOUR_API_KEY";  // Replace with actual API key
        String urlString = "https://api.opencagedata.com/geocode/v1/json?q=" + location + "&key=" + apiKey;
        try {
            // Create URL and open connection
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // Read the response from the API
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();
            //Convert the response to JSON and extract coordinates
            JSONObject data = new JSONObject(inline.toString());
            JSONObject locationData = data.getJSONArray("results").getJSONObject(0).getJSONObject("geometry");
            double latitude = locationData.getDouble("lat");
            double longitude = locationData.getDouble("lng");
            // Return coordinates as an array
            return new double[]{latitude, longitude};
        } catch (Exception e) {
            return null;  // Handle geolocation error
        }
    }

    // Method to fetch weather data for a given location
    public static String getWeather(String location) {
        // Get coordinates from location
        double[] coordinates = getCoordinates(location);
        if (coordinates == null) {
            return "Error: Invalid location.";
        }

        double latitude = coordinates[0];
        double longitude = coordinates[1];

        // Build weather API URL using the coordinates
        try {
            String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current_weather=true&daily=temperature_2m_max,temperature_2m_min,precipitation_sum,wind_speed_10m_max,humidity_2m_max&timezone=auto";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            // Check if the response code is OK (200)
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                return "Error: Unable to fetch weather data.";
            }
            // Read the response from the API
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject data = new JSONObject(inline.toString());
            JSONObject currentWeather = data.getJSONObject("current_weather");
            double temperature = currentWeather.getDouble("temperature");
            double windSpeed = currentWeather.getDouble("windspeed");
            // Determine weather description based on wind speed
            String description = windSpeed > 20 ? "Windy" : "Clear";

            // Include humidity, temperature, and wind speed
            return "Temperature: " + temperature + "°C, " + description + ", Wind: " + windSpeed + " km/h";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}