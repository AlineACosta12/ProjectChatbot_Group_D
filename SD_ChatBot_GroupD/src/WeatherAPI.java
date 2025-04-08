import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

// WeatherAPI fetches live weather data based on location and visit date.
class WeatherAPI {
    public static String getWeather(String location, String date) {
        try {
            double[] coordinates = GeolocationAPI.getCoordinates(location);
            if (coordinates == null) return "Error: Location not found.";

            // Construct API URL to get daily forecast for the specific date
            String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + coordinates[0]
                    + "&longitude=" + coordinates[1]
                    + "&daily=temperature_2m_max,temperature_2m_min,weathercode&timezone=auto"
                    + "&start_date=" + date + "&end_date=" + date;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();// Connect to the API server

            // Check if the API response is successful (status code 200)
            if (conn.getResponseCode() != 200) return "Error: Unable to fetch weather data.";

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();
            // Convert the API response from String to a JSON object
            JSONObject data = new JSONObject(inline.toString());
            JSONObject daily = data.getJSONObject("daily");
            // Get the maximum temperature for the day as an example
            JSONArray tempMaxArray = daily.getJSONArray("temperature_2m_max");
            double temperature = tempMaxArray.getDouble(0);
            // Determine basic description based on temperature
            String description = temperature > 25 ? "clear" : (temperature < 10 ? "cold" : "moderate");
            return "Temperature: " + temperature + "°C, " + description + " on " + date;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

// GeolocationAPI gets the latitude and longitude for a given location using Nominatim API.
class GeolocationAPI {
    public static double[] getCoordinates(String location) {
        try {
            // Encode the location name to ensure it is compatible with the URL
            String encodedLocation = URLEncoder.encode(location, "UTF-8");
            String urlString = "https://nominatim.openstreetmap.org/search?q=" + encodedLocation + "&format=json&limit=1";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();// Connect to the server

            // If the response code is not successful, return null
            if (conn.getResponseCode() != 200) return null;
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();
            // Convert the API response from String to a JSON array
            JSONArray results = new JSONArray(inline.toString());
            if (results.length() == 0) return null;
            JSONObject locationData = results.getJSONObject(0);
            // Return the latitude and longitude as a double array
            return new double[]{locationData.getDouble("lat"), locationData.getDouble("lon")};
        } catch (Exception e) {
            return null;
        }
    }
}