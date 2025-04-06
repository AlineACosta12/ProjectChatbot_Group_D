import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test class for the chatbot's clothing recommendation functionality
class ChatbotTest {

    // Test clothing suggestion for sunny weather
    @Test
    void testSunnyWeatherClothing() {
        String suggestion = ClothingRecommender.getClothingSuggestion("Sunny, 25°C", 0);
        assertTrue(suggestion.contains("Wear light clothing")); // Check for light clothing suggestion
    }

    // Test clothing suggestion for rainy weather
    @Test
    void testRainyWeatherClothing() {
        String suggestion = ClothingRecommender.getClothingSuggestion("Rain, 18°C", 1);
        assertTrue(suggestion.contains("Bring a raincoat")); // Check for raincoat suggestion
    }
}
