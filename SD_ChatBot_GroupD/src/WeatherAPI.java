import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

class WeatherAPI {
    public static String getWeather(String location) {
        try {
            double[] coordinates = GeolocationAPI.getCoordinates(location);
            if (coordinates == null) return "Error: Location not found.";

            String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + coordinates[0] + "&longitude=" + coordinates[1] + "&current_weather=true";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != 200) return "Error: Unable to fetch weather data.";

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject data = new JSONObject(inline.toString());
            JSONObject currentWeather = data.getJSONObject("current_weather");
            double temperature = currentWeather.getDouble("temperature");
            String description = currentWeather.getDouble("windspeed") > 20 ? "windy" : "clear";

            return "Temperature: " + temperature + "°C, " + description;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

class GeolocationAPI {
    public static double[] getCoordinates(String location) {
        try {
            String encodedLocation = URLEncoder.encode(location, "UTF-8");
            String urlString = "https://nominatim.openstreetmap.org/search?q=" + encodedLocation + "&format=json&limit=1";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != 200) return null;

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            JSONArray results = new JSONArray(inline.toString());
            if (results.length() == 0) return null;

            JSONObject locationData = results.getJSONObject(0);
            return new double[]{locationData.getDouble("lat"), locationData.getDouble("lon")};
        } catch (Exception e) {
            return null;
        }
    }
}