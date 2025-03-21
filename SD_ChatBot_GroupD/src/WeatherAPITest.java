import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

class WeatherAPITest {

   @Test
    void testGetWeather() {
        // Test if weather information is returned correctly for a location
        String weather = WeatherAPI.getWeather("New York");
        assertTrue(weather.contains("Temperature"));
        assertTrue(weather.contains("°C"));
    }

    @Test
    void testErrorHandling() {
        // Test if the API returns an error message for an invalid location
        String weather = WeatherAPI.getWeather("InvalidLocation");
        assertTrue(weather.contains("Error"));
    }
} 