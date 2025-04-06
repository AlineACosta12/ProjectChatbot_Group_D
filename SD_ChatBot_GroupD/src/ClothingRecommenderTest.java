import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClothingRecommenderTest {

    @Test
    public void testSunnyMorningSuggestion() {
        String result = ClothingRecommender.getClothingSuggestion("Sunny", 0);
        assertTrue(result.contains("light clothing"));
        assertTrue(result.contains("sunglasses"));
        assertTrue(result.contains("morning"));
    }

    @Test
    public void testRainyAfternoonSuggestion() {
        String result = ClothingRecommender.getClothingSuggestion("Rain", 1);
        assertTrue(result.contains("raincoat"));
        assertTrue(result.contains("Afternoon"));
    }

    @Test
    public void testColdEveningSuggestion() {
        String result = ClothingRecommender.getClothingSuggestion("Cold", 2);
        assertTrue(result.contains("Dress warmly"));
        assertTrue(result.contains("Evening"));
    }

    @Test
    public void testPersonalizedSuggestionWithRaincoat() {
        List<String> wardrobe = Arrays.asList("Raincoat", "Boots", "Hat");
        String result = ClothingRecommender.getPersonalizedClothingSuggestion("Rain", wardrobe);
        assertTrue(result.contains("You can wear your raincoat"));
    }

    @Test
    public void testPersonalizedSuggestionWithoutRainItems() {
        List<String> wardrobe = Arrays.asList("T-shirt", "Jeans");
        String result = ClothingRecommender.getPersonalizedClothingSuggestion("Rain", wardrobe);
        assertTrue(result.contains("Based on your wardrobe"));
        assertTrue(!result.contains("raincoat"));
    }
}
