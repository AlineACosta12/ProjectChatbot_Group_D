import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatbotGUI extends Application {
    private TextArea outputArea;
    private TextField locationInput;

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Trip Clothing Planner Chatbot"); // Set window title

        Label locationLabel = new Label("Enter Location:"); // Label for input
        locationInput = new TextField(); // Text field for user input
        Button fetchWeatherButton = new Button("Get Weather & Clothing Suggestion"); // Button to trigger fetch
        outputArea = new TextArea(); // Text area for output
        outputArea.setEditable(false); // Make output read-only

        fetchWeatherButton.setOnAction(e -> fetchWeather()); // Set button action

        VBox layout = new VBox(10); // Vertical layout with spacing
        layout.setPadding(new Insets(10)); // Set padding
        layout.getChildren().addAll(locationLabel, locationInput, fetchWeatherButton, outputArea); // Add elements

        Scene scene = new Scene(layout, 500, 400); // Create scene
        primaryStage.setScene(scene); // Set scene on stage
        primaryStage.show(); // Show the GUI
    }

    private void fetchWeather() {
        String location = locationInput.getText(); // Get location from input
        if (location.isEmpty()) {
            outputArea.setText("Please enter a location."); // Show error if input is empty
            return;
        }

        String weatherInfo = WeatherAPI.getWeather(location); // Fetch weather data

        // Build clothing suggestions for 3 days
        StringBuilder suggestions = new StringBuilder("Clothing Suggestions:\n");
        for (int i = 0; i < 3; i++) {
            suggestions.append("Day " + (i + 1) + ": " + ClothingRecommender.getClothingSuggestion(weatherInfo, i) + "\n");
        }

        // Display weather info and suggestions
        outputArea.setText("Weather in " + location + ":\n" + weatherInfo + "\n\n" + suggestions);
    }
}
