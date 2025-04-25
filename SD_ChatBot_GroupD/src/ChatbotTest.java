import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test class for ClothingRecommender.
class ChatbotTest {

    // Test for sunny weather in the morning (Day 0).
    @Test
    void testSunnyWeatherMorningClothing() {
        // Simulate a weather input for a sunny 25°C morning.
        String suggestion = ClothingRecommender.getClothingSuggestion("Sunny, 25°C", 0);

        // Check if the main sunny suggestion is present.
        assertTrue(suggestion.contains("🌞 Sun’s out, style’s out!"));

        // Since it's morning, either of these should be included.
        assertTrue(suggestion.contains("pack a light jacket") ||
                suggestion.contains("Morning heat is real"));
    }

    // Test for rainy weather in the afternoon (Day 1).
    @Test
    void testRainyWeatherAfternoonClothing() {
        // Simulate a rainy 18°C afternoon.
        String suggestion = ClothingRecommender.getClothingSuggestion("Rain, 18°C", 1);

        // Check for general rain alert message.
        assertTrue(suggestion.contains("☔ Rain alert!"));

        // Check for tailored afternoon rain advice.
        assertTrue(suggestion.contains("Rainy afternoons call for waterproof gear"));
    }

    // Test for snowy weather in the evening (Day 2).
    @Test
    void testSnowyEveningClothing() {
        // Simulate snowy weather at -2°C in the evening.
        String suggestion = ClothingRecommender.getClothingSuggestion("Snow, -2°C", 2);

        // Check for snow-related advice.
        assertTrue(suggestion.contains("❄️ Snowy vibes today"));

        // For evening, either of these may appear.
        assertTrue(suggestion.contains("Evening chill incoming") ||
                suggestion.contains("Evening rain is no joke")); // Note: rain text can show up if code logic matches both.
    }

    // Test for very hot weather in the afternoon (Day 1).
    @Test
    void testHotWeatherAfternoonClothing() {
        // Simulate a clear, hot day at 34°C.
        String suggestion = ClothingRecommender.getClothingSuggestion("Clear, 34°C", 1);

        // Check for hot weather suggestion.
        assertTrue(suggestion.contains("🔥 Hot hot hot!"));

        // Check if specific afternoon heat warning is present.
        assertTrue(suggestion.contains("Blazing afternoon"));
    }

    // Test for cold weather in the morning (Day 0).
    @Test
    void testColdMorningClothing() {
        // Simulate a foggy 5°C morning.
        String suggestion = ClothingRecommender.getClothingSuggestion("Fog, 5°C", 0);

        // Check if the chatbot suggests warm clothes.
        assertTrue(suggestion.contains("🥶 It's chilly out!"));

        // Check for morning-specific cold advice.
        assertTrue(suggestion.contains("chilly morning") ||
                suggestion.contains("extra layer"));
    }
}
