import java.util.List;

public class ClothingRecommender {


    public static String getClothingSuggestion(String weather, int dayIndex) {
        String suggestion = "";

        // Basic suggestions based on weather.
        if (weather.contains("Sunny")) {
            suggestion = "Wear light clothing, sunglasses, and sunscreen.";
        } else if (weather.contains("Rain")) {
            suggestion = "Bring a raincoat and waterproof shoes.";
        } else if (weather.contains("Cold")) {
            suggestion = "Dress warmly with a jacket and gloves.";
        }

        // Add-on suggestions based on time of day.
        if (dayIndex == 0) {
            suggestion += " It's morning, consider a light jacket.";
        } else if (dayIndex == 1) {
            suggestion += " Afternoon, light clothing is fine.";
        } else {
            suggestion += " Evening, perhaps a sweater or jacket.";
        }

        return suggestion;
    }

    // Normalize input for easier keyword matching.
    public static String getClothingSuggestion(String weatherDescription) {
        weatherDescription = weatherDescription.toLowerCase();

        // Check for key weather conditions and return appropriate suggestions.
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


    public static String getPersonalizedClothingSuggestion(String weather, List<String> wardrobeItems) {
        String suggestion = "Based on your wardrobe:\n";

        // Suggest the raincoat if it's raining and the user owns one.
        if (wardrobeItems.contains("Raincoat") && weather.contains("Rain")) {
            suggestion += "You can wear your raincoat today.";
        }

        // If no specific item matches the weather, return only the intro line.
        return suggestion;
    }
}
