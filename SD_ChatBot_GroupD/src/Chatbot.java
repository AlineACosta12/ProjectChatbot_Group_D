

import java.util.Scanner;
import java.time.LocalDate;

public class Chatbot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Trip Clothing Planner Chatbot!");
        System.out.println("Im Wadrobot!");

        // Ask for trip start date.
        System.out.println("Please enter the start date of your trip (YYYY-MM-DD):");
        String startDateStr = scanner.nextLine();
        LocalDate startDate = LocalDate.parse(startDateStr);

        // Collect 5 locations and visit day (1-3) for each location.
        String[] locations = new String[5];
        int[] visitDays = new int[5];
        for (int i = 0; i < 5; i++) {
            System.out.println("Enter location " + (i + 1) + ":");
            locations[i] = scanner.nextLine();
            System.out.println("Enter visit day (1-3) for " + locations[i] + ":");
            visitDays[i] = scanner.nextInt();
            scanner.nextLine(); // Clear buffer.
        }

        // Process each location: compute visit date, fetch weather, and suggest clothing.
        for (int i = 0; i < 5; i++) {
            LocalDate visitDate = startDate.plusDays(visitDays[i] - 1);
            String weatherInfo = WeatherAPI.getWeather(locations[i], visitDate.toString());
            System.out.println("Weather in " + locations[i] + " on " + visitDate + ": " + weatherInfo);
            System.out.println("Clothing Suggestion for Day " + visitDays[i] + ": " +
                    ClothingRecommender.getClothingSuggestion(weatherInfo, visitDays[i] - 1));
            System.out.println("I hope you enjoy your travel! I'm glad to help you, Bye, see you soon!!!git ");
        }
        scanner.close();
    }
}