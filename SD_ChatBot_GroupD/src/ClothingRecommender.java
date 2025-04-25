// Main class definition
public class ClothingRecommender {

    // This method returns a clothing suggestion based on the weather description and time of day (dayIndex).
    public static String getClothingSuggestion(String weather, int dayIndex) {
        StringBuilder suggestion = new StringBuilder(); // Used to build the final suggestion string.
        weather = weather.toLowerCase(); // Normalize weather string to lowercase for easier matching.

        double temperature = 20.0; // Default temperature if parsing fails.

        // Try to extract a temperature value from the weather string.
        try {
            int index = weather.indexOf("°c");
            if (index > 0) {
                int start = index;

                // Walk backwards to find the beginning of the temperature number (can include negative sign and decimal).
                while (start > 0 && (Character.isDigit(weather.charAt(start - 1)) ||
                        weather.charAt(start - 1) == '.' ||
                        weather.charAt(start - 1) == '-')) {
                    start--;
                }

                // Parse the number between start and "°C".
                temperature = Double.parseDouble(weather.substring(start, index));
            }
        } catch (Exception e) {
            // If anything goes wrong, stick with the default temperature of 20.0.
        }

        //Main clothing suggestion based on weather conditions and parsed temperature.
        if (weather.contains("rain")) {
            // If "rain" is mentioned in the forecast.
            suggestion.append("☔ Rain alert! Grab your cutest raincoat and waterproof boots — bonus points if your umbrella matches!");
        } else if (weather.contains("snow") || temperature <= 0) {
            // Cold or snowy weather.
            suggestion.append("❄️ Snowy vibes today! Bundle up with a coat, scarf, gloves — and maybe do a snow angel or two?");
        } else if (temperature > 0 && temperature <= 10) {
            // Cold but not freezing.
            suggestion.append("🥶 It's chilly out! Rock that puffer jacket, gloves, and a hat — hot chocolate highly recommended!");
        } else if (temperature > 10 && temperature <= 20) {
            // Mild weather.
            suggestion.append("🍂 Mild weather ahead! Think layers — maybe a hoodie or a stylish light jacket.");
        } else if (temperature > 20 && temperature <= 30) {
            // Warm and sunny.
            suggestion.append("🌞 Sun’s out, style’s out! Go for light clothing, sunglasses, and slap on some SPF like a responsible explorer.");
        } else if (temperature > 30) {
            // Very hot.
            suggestion.append("🔥 Hot hot hot! Stick to breathable fabrics (hello, cotton!), stay hydrated, and maybe skip the black outfit.");
        } else {
            // If temperature parsing was weird or doesn't match any condition.
            suggestion.append("🤔 Hmm, it’s a bit unpredictable. Go with your gut — layers might be a smart call.");
        }

        // Add extra tips based on the time of day.
        switch (dayIndex) {
            case 0: // Morning (condition).
                if (temperature <= 10) {
                    suggestion.append(" Since it’s a chilly morning, a warm drink and an extra layer will go a long way.");
                } else if (temperature > 25) {
                    suggestion.append(" Morning heat is real — dress light and start early if you're going out.");
                } else {
                    suggestion.append(" Since it’s the morning, pack a light jacket just in case.");
                }
                break;

            case 1: // Afternoon (condition).
                if (weather.contains("rain")) {
                    suggestion.append(" Rainy afternoons call for waterproof gear and cozy indoor plans.");
                } else if (temperature > 30) {
                    suggestion.append(" Blazing afternoon! Avoid peak sun hours and keep water close.");
                } else {
                    suggestion.append(" Afternoon adventures? Keep it comfy and airy.");
                }
                break;

            case 2: // Evening (condition).
                if (temperature <= 10) {
                    suggestion.append(" Evening chill incoming — grab a warm coat and maybe some gloves.");
                } else if (weather.contains("rain")) {
                    suggestion.append(" Evening rain is no joke — stay dry and maybe carry a flashlight too.");
                } else {
                    suggestion.append(" Evening stroll? A cozy sweater wouldn't hurt.");
                }
                break;
        }

        // Return the final composed suggestion.
        return suggestion.toString();
    }
}
