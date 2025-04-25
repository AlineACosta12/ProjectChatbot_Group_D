import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Chatbot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner for reading user input.

        // Outer loop to allow repeated searches.
        boolean keepRunning = true;

        while (keepRunning) {
            // Friendly greeting (Output).
            System.out.println("Welcome to the Trip Clothing Planner Chatbot!\n");
            System.out.println("I'm Wadrobot! Packing can be tricky but I'm here to simplify your travel planning!\n");

            // Ask user to enter trip start date (Output).
            System.out.println("Please enter the start date of your trip (YYYY-MM-DD):");

            String startDateStr;
            LocalDate startDate = null;

            // Loop until a valid date is entered.
            while (true) {
                startDateStr = scanner.nextLine(); // Read input.
                try {
                    startDate = LocalDate.parse(startDateStr); // Try to parse it.
                    break; // If successful, exit loop.
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please try again using YYYY-MM-DD:");
                }
            }

            // Collect 5 trip locations and the visit day for each.
            String[] locations = new String[5];
            int[] visitDays = new int[5];

            for (int i = 0; i < 5; i++) {
                System.out.println("Enter location " + (i + 1) + ":");
                locations[i] = scanner.nextLine();

                System.out.println("Enter visit day (1-3) for " + locations[i] + ":");
                try {
                    visitDays[i] = Integer.parseInt(scanner.nextLine());
                    if (visitDays[i] < 1 || visitDays[i] > 3) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.out.println("Invalid visit day. Please enter a number between 1 and 3.");
                    i--; // Stay on the current iteration and ask again.
                    continue;
                }
            }

            // Loop through each location and provide weather + clothing advice.
            for (int i = 0; i < 5; i++) {
                LocalDate visitDate = startDate.plusDays(visitDays[i] - 1);
                String weatherInfo = WeatherAPI.getWeather(locations[i], visitDate.toString());

                System.out.println("Weather in " + locations[i] + " on " + visitDate + ": " + weatherInfo);
                System.out.println("Clothing Suggestion for Day " + visitDays[i] + ": " +
                        ClothingRecommender.getClothingSuggestion(weatherInfo, visitDays[i] - 1));
            }

            // Ask user if they'd like to try again.
            System.out.println("\nWould you like to plan another trip? (yes/no):");
            String response = scanner.nextLine().trim().toLowerCase();

            if (!response.equals("yes") && !response.equals("y")) {
                keepRunning = false;
                System.out.println("\nThanks for sharing your plans!");
                System.out.println("I hope you have an amazing journey! 👋 Safe travels!");
            }
        }

        scanner.close(); // Close the scanner to free resources.
    }
}
