import java.util.Scanner; 


public class Chatbot {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); // Create scanner object for user input

        // Welcome message
        System.out.println("Welcome to the Trip Clothing Planner Chatbot!");

        // Ask for the number of locations
        System.out.println("Enter the number of locations you will visit: ");
        int numLocations = scanner.nextInt(); // Get the number of locations
        scanner.nextLine(); // Clear the buffer

        // Loop through each location
        for (int i = 1; i <= numLocations; i++) {

            // Ask for location name
            System.out.println("Enter location " + i + ":");
            String location = scanner.nextLine();

            // Fetch weather information for the location
            String weatherInfo = WeatherAPI.getWeather(location); // Fetch weather data from the API

            // Display the weather information
            System.out.println("Weather in " + location + ": " + weatherInfo);

            // Suggest clothing based on weather data
            System.out.println("Clothing Suggestion: " + ClothingRecommender.getClothingSuggestion(weatherInfo));

        }

        scanner.close(); // Close the scanner to free up resources
    }
}