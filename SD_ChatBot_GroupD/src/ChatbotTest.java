
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ChatbotTest {



    @Test

    void testGetClothingSuggestion() {

        assertEquals("Carry an umbrella and wear waterproof clothing.", ClothingRecommender.getClothingSuggestion("Temperature: 15°C, rain"));

        assertEquals("Wear warm clothes, a coat, gloves, and a scarf.", ClothingRecommender.getClothingSuggestion("Temperature: -5°C, snow"));

        assertEquals("Wear light clothes and sunglasses.", ClothingRecommender.getClothingSuggestion("Temperature: 25°C, clear"));

        assertEquals("Wear a good jacket and warm clothing.", ClothingRecommender.getClothingSuggestion("Temperature: 5°C, cold"));

        assertEquals("Wear breathable fabrics like cotton and drink plenty of water.", ClothingRecommender.getClothingSuggestion("Temperature: 35°C, hot"));

    }

}