
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClothingRecommenderTest {

    @Test
    void testGetClothingSuggestion() {

        // Testing for rain condition.
        assertEquals("Carry an umbrella and wear waterproof clothing.",
                ClothingRecommender.getClothingSuggestion("Temperature: 15°C, rain"));

        // Testing for snow condition.
        assertEquals("Wear warm clothes, a coat, gloves, and a scarf.",
                ClothingRecommender.getClothingSuggestion("Temperature: -5°C, snow"));

        // Testing for clear weather condition.
        assertEquals("Wear light clothes and sunglasses.",
                ClothingRecommender.getClothingSuggestion("Temperature: 25°C, clear"));

        // Testing for cold weather condition.
        assertEquals("Wear a jacket and warm clothing.",
                ClothingRecommender.getClothingSuggestion("Temperature: 5°C, cold"));

        // Testing for hot weather condition.
        assertEquals("Wear breathable fabrics like cotton and drink plenty of water.",
                ClothingRecommender.getClothingSuggestion("Temperature: 35°C, hot"));
    }
}