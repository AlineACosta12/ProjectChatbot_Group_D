import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WeatherAPITest {
    @Test
    void testValidLocationWeather() {
        // This test checks if we get a response for a valid location
        String response = WeatherAPI.getWeather("New York");
        assertNotNull(response);
    }
    @Test
    void testInvalidLocationWeather() {
        // This test checks if the app handles an invalid location
        String response = WeatherAPI.getWeather("");
        assertEquals("Error: Invalid location.", response);
    }
}