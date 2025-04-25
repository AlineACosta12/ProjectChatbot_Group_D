import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class to validate ClothingRecommender logic across different weather scenarios.
public class ClothingRecommenderTest {

    // Test rainy morning suggestion.
    @Test
    public void testRainyMorning() {
        String result = ClothingRecommender.getClothingSuggestion("Rain, 15°C", 0);
        assertTrue(result.contains("☔ Rain alert!")); // Checks for main rain message.
        assertTrue(result.contains("morning")); // Ensures the time-specific advice is included.
    }

    // Test snowy evening suggestion.
    @Test
    public void testSnowyEvening() {
        String result = ClothingRecommender.getClothingSuggestion("Snow, -3°C", 2);
        assertTrue(result.contains("❄️ Snowy vibes today")); // Main snowy suggestion.
        assertFalse(result.contains("Evening stroll")); // Ensure casual stroll advice is not shown in freezing.
    }

    // Test freezing temperature when only temperature triggers snowy logic.
    @Test
    public void testFreezingTemperatureOnly() {
        String result = ClothingRecommender.getClothingSuggestion("Cloudy, -1°C", 1);
        assertTrue(result.contains("❄️ Snowy vibes today")); // Triggered by temperature <= 0
        assertTrue(result.contains("Afternoon adventures")); // Ensure afternoon-specific logic still runs.
    }

    // Test cold temperature in the 0–10°C range.
    @Test
    public void testColdTemperatureRange() {
        String result = ClothingRecommender.getClothingSuggestion("Overcast, 7°C", 0);
        assertTrue(result.contains("🥶 It's chilly out!")); // Cold-weather dressing suggestion.
    }

    // Test mild temperature in the 10–20°C range.
    @Test
    public void testMildTemperatureRange() {
        String result = ClothingRecommender.getClothingSuggestion("Cloudy, 15°C", 1);
        assertTrue(result.contains("🍂 Mild weather ahead")); // Suggest layers for mid temperature.
    }

    // Test warm, sunny day.
    @Test
    public void testWarmSunny() {
        String result = ClothingRecommender.getClothingSuggestion("Sunny, 25°C", 2);
        assertTrue(result.contains("🌞 Sun’s out, style’s out!")); // Light clothing advice.
    }

    // Test very hot weather.
    @Test
    public void testHotWeather() {
        String result = ClothingRecommender.getClothingSuggestion("Clear, 35°C", 1);
        assertTrue(result.contains("🔥 Hot hot hot!")); // Advice for extreme heat.
    }

    @Test
    public void testDefaultTemperatureOnInvalidInput() {
        // Given an invalid temperature input.
        String result = ClothingRecommender.getClothingSuggestion("Uncertain, ???", 0);

        // The system should fall back to the 20°C suggestion range.
        assertTrue(result.contains("🍂 Mild weather ahead")); // Based on default 20°C.
    }


    // Test temperature parsing with a negative sign.
    @Test
    public void testTemperatureParsingWithNegativeSign() {
        String result = ClothingRecommender.getClothingSuggestion("Snow, -5°C", 2); // Evening
        assertTrue(result.contains("❄️ Snowy vibes today")); // Should work with negative values.
    }

    // Test clothing advice at exact temperature thresholds.
    @Test
    public void testExactTemperatureThresholds() {
        // Temperature = 10°C → Chilly threshold.
        String result10 = ClothingRecommender.getClothingSuggestion("Windy, 10°C", 0);
        assertTrue(result10.contains("🥶")); // Should be considered cold.

        // Temperature = 20°C → Mild threshold.
        String result20 = ClothingRecommender.getClothingSuggestion("Breezy, 20°C", 1);
        assertTrue(result20.contains("🍂")); // Suggest layers.

        // Temperature = 30°C → Warm threshold.
        String result30 = ClothingRecommender.getClothingSuggestion("Sunny, 30°C", 2);
        assertTrue(result30.contains("🌞")); // Suggest sun protection.
    }
}
