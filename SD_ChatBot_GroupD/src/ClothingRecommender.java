

import java.util.List;

// ClothingRecommender provides clothing suggestions based on weather information.
public class ClothingRecommender {

    // Returns a suggestion based on weather info and visit day.
    public static String getClothingSuggestion(String weather, int dayIndex) {
        String suggestion = "";
        if (weather.contains("Sunny")) {
            suggestion = "Wear light clothing, sunglasses, and don't forget the sunscreen.";
        } else if (weather.contains("Rain")) {
            suggestion = "Bring a raincoat and waterproof shoes and an beautiful umbrella.";
        } else if (weather.contains("Cold")) {
            suggestion = "Dress warmly with a jacket,gloves and hat and try get some hot chocolate.";
        } else {
            suggestion = "Dress appropriately for moderate weather with your own style.";
        }
        if (dayIndex == 0) {
            suggestion += " It's morning, consider a light jacket.";
        } else if (dayIndex == 1) {
            suggestion += " Afternoon, light clothing is fine.";
        } else {
            suggestion += " Evening, perhaps a sweater or jacket.";
        }
        return suggestion;
    }

    // Overloaded method: returns suggestion based solely on weather description.
    public static String getClothingSuggestion(String weatherDescription) {
        weatherDescription = weatherDescription.toLowerCase();
        if (weatherDescription.contains("rain")) {
            return "Carry an umbrella and wear waterproof clothing.";
        } else if (weatherDescription.contains("snow")) {
            return "Wear warm clothes, a coat, gloves, and a scarf.";
        } else if (weatherDescription.contains("clear")) {
            return "Wear light clothes and sunglasses.";
        } else if (weatherDescription.contains("cold") || weatherDescription.contains("5°c")) {
            return "Wear a good jacket and warm clothing.";
        } else if (weatherDescription.contains("hot") || weatherDescription.contains("35°c")) {
            return "Wear breathable fabrics like cotton and drink plenty of water.";
        } else {
            return "Check the weather forecast and dress accordingly.";
        }
    }

    // Returns personalized suggestions based on user's wardrobe.
    public static String getPersonalizedClothingSuggestion(String weather, List<String> wardrobeItems) {
        String suggestion = "Based on your wardrobe:\n";
        if (wardrobeItems.contains("Raincoat") && weather.contains("Rain")) {
            suggestion += "You can wear your raincoat today.";
        }
        return suggestion;
    }
}