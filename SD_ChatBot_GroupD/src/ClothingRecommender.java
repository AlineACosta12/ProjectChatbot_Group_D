public class ClothingRecommender {


    public static String getClothingSuggestion(String weatherInfo) {

        if (weatherInfo.contains("Error")) return "Unable to determine clothing suggestion.";


        if (weatherInfo.contains("rain")) return "Carry an umbrella and wear waterproof clothing.";

        if (weatherInfo.contains("snow")) return "Wear warm clothes, a coat, gloves, and a scarf.";

        if (weatherInfo.contains("clear")) return "Wear light clothes and sunglasses.";

        if (weatherInfo.contains("cold")) return "Wear a jacket and warm clothing.";

        if (weatherInfo.contains("hot")) return "Wear breathable fabrics like cotton and drink plenty of water.";

        if (weatherInfo.contains("windy")) return "Wear a windbreaker or a sturdy jacket.";

        if (weatherInfo.contains("humid")) return "Wear loose, moisture-wicking clothes to stay comfortable.";


        return "Wear comfortable clothing suitable for mild weather.";

    }

}
