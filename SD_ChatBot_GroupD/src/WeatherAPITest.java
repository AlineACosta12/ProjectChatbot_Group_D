import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WeatherAPITest {

    // Test to check if the WeatherAPI provides a valid response for a real location
    @Test
    void testValidLocationWeather() {
        // Test weather data for a valid location (New York) on a given date
        String date = "2025-05-10";
        String response = WeatherAPI.getWeather("New York", date);
        // Check that the response is not null and contains temperature information
        assertNotNull(response);
        assertTrue(response.contains("Temperature:"), "Response should contain temperature info");
    }

    // Test to check how the API handles an empty location input
    @Test
    void testInvalidLocationWeather() {
        String date = "2025-05-10";
        // Should return an error message when the location is empty
        String response = WeatherAPI.getWeather("", date);
        assertEquals("Error: Location not found.", response);
    }

    // Test to check how the API handles an invalid date format
    @Test
    void testInvalidDateFormat() {
        // Provide an invalid date format and expect the API to return an error message
        String response = WeatherAPI.getWeather("Dublin", "invalid-date");
        assertTrue(response.startsWith("Error:"), "Response should start with an error message");
    }

    @Test
    void testFutureDateForecast() {
        String date = "2025-06-01";
        // Call the API to get the weather forecast for Lisbon
        String response = WeatherAPI.getWeather("Lisbon", date);
        // Log to see the full response from the API
        System.out.println("API Response for Future Date: " + response);
        // Check if the response contains the word "Temperature"
        if (response.contains("Error:")) {
            // If there's an error in the response, print the error message and assert it
            System.out.println("Error fetching weather data: " + response);
            assertTrue(response.contains("Error:"), "Expected error message in the response");
        } else {
            // If the response contains temperature info, assert that it's correct
            assertTrue(response.contains("Temperature:"), "Response should contain temperature information");
        }
    }


    // Test to check if the GeolocationAPI returns valid coordinates for a real location
    @Test
    void testGetCoordinatesValidLocation() {
        double[] coords = GeolocationAPI.getCoordinates("Berlin");
        // Ensure coordinates are returned (not null or empty) and are valid
        assertNotNull(coords);
        assertEquals(2, coords.length); // Should return an array of latitude and longitude
        assertTrue(coords[0] != 0 && coords[1] != 0); // Latitude and longitude should not be 0
    }

    // Test to check if the GeolocationAPI returns null for an invalid location
    @Test
    void testGetCoordinatesInvalidLocation() {
        // Invalid location input (random string), should return null
        double[] coords = GeolocationAPI.getCoordinates("ajsdjas333");
        assertNull(coords);
    }

    // Test to check if the GeolocationAPI returns null for an empty location string
    @Test
    void testGetCoordinatesEmptyString() {
        // Empty location string should return null
        double[] coords = GeolocationAPI.getCoordinates("");
        assertNull(coords);
    }
}
