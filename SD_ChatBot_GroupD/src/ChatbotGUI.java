import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatbotGUI extends Application {

    // Logger for debugging and tracking application behavior
    private static final Logger logger = Logger.getLogger(ChatbotGUI.class.getName());

    // UI components
    private VBox chatContainer;             // Container for displaying chat messages
    private DatePicker startDatePicker;  // For trip start date input
    private TextField locationInput;       // For location input
    private TextField dayInput;            // For visit day (1-3) input
    private ProgressIndicator progressIndicator; // Loading spinner for data fetching

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Trip Clothing Planner Chatbot");

        // Initialize chat container
        chatContainer = new VBox(10);
        chatContainer.setPadding(new Insets(20));
        chatContainer.setPrefWidth(450);

        // Scrollable container for chat messages
        ScrollPane scrollPane = new ScrollPane(chatContainer);
        scrollPane.setFitToWidth(true);

        // Initialize input fields
        startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Trip Start Date (YYYY-MM-DD)");

        locationInput = new TextField();
        locationInput.setPromptText("Enter location...");

        dayInput = new TextField();
        dayInput.setPromptText("Visit day (1-3)");

        // Button to trigger weather and clothing suggestion fetch
        Button fetchWeatherButton = new Button("Get Weather & Suggestion");
        fetchWeatherButton.setOnAction(e -> fetchWeather());

        // Input layout grouping
        HBox inputBox = new HBox(10, startDatePicker, locationInput, dayInput, fetchWeatherButton);
        inputBox.setPadding(new Insets(10));
        inputBox.setAlignment(Pos.CENTER);

        // Configure loading indicator
        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false); // Hidden by default
        progressIndicator.setProgress(-1);   // Indeterminate loading animation

        // Assemble the main layout
        VBox layout = new VBox(scrollPane, inputBox, progressIndicator);
        layout.setSpacing(20); // Space between elements

        Scene scene = new Scene(layout, 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

        // Initial welcome messages from the chatbot
        addBotMessage("Hi! I'm Wardrobot, your trip clothing assistant.");
        addBotMessage("Enter your trip start date, a location, and visit day (1-3) to get suggestions.");
    }

    // Handles the logic for fetching weather and clothing suggestions
    private void fetchWeather() {
        // Get input values
        LocalDate startDate = startDatePicker.getValue();
        String location = locationInput.getText();
        String dayStr = dayInput.getText();

        // Input validation
        if (startDate == null || location.isEmpty() || dayStr.isEmpty()) {
            addBotMessage("Please enter the trip start date, location, and visit day.");
            logger.warning("Missing required fields: start date, location, or day.");
            return;
        }

        int day;
        try {
            day = Integer.parseInt(dayStr);
        } catch (NumberFormatException ex) {
            addBotMessage("Visit day must be a number between 1 and 3.");
            logger.warning("Invalid visit day input: " + dayStr);
            return;
        }

        if (day < 1 || day > 3) {
            addBotMessage("Visit day must be between 1 and 3.");
            logger.warning("Invalid visit day: " + day);
            return;
        }

        logger.info("Fetching weather data for location: " + location + " on day: " + day);

        // Show progress indicator while fetching data
        progressIndicator.setVisible(true);

        // Calculate visit date
        LocalDate visitDate = startDate.plusDays(day - 1);
        addUserMessage("Location: " + location + ", Visit Date: " + visitDate);

        // Run the data fetching in a background thread
        new Thread(() -> {
            try {
                // Simulate delay (e.g., API request)
                Thread.sleep(2000);

                // Retrieve weather and clothing info from backend classes
                String weatherInfo = WeatherAPI.getWeather(location, visitDate.toString());
                String clothingSuggestion = ClothingRecommender.getClothingSuggestion(weatherInfo, day - 1);

                // Update UI on JavaFX thread
                javafx.application.Platform.runLater(() -> {
                    progressIndicator.setVisible(false); // Hide loader
                    addBotMessage("Weather in " + location + " on " + visitDate + ":\n" + weatherInfo);
                    addBotMessage("Clothing Suggestion for Day " + day + ":\n" + clothingSuggestion);
                    logger.info("Weather fetched for " + location + ": " + weatherInfo);
                    logger.info("Clothing suggestion: " + clothingSuggestion);
                });

            } catch (InterruptedException e) {
                logger.severe("Error fetching weather data: " + e.getMessage());
            }
        }).start();
    }

    // Add a bot message to the chat container
    private void addBotMessage(String text) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.TOP_LEFT);
        ImageView avatar = new ImageView(new Image(getClass().getResourceAsStream("/images/robot.face.png")));
        avatar.setFitWidth(45);
        avatar.setFitHeight(45);
        // Styled message bubble
        Label bubble = new Label(text);
        bubble.setWrapText(true);
        bubble.getStyleClass().add("bot-bubble");
        bubble.setFont(Font.font(14));
        row.getChildren().addAll(avatar, bubble);
        chatContainer.getChildren().add(row);
    }

    // Add a user message to the chat container
    private void addUserMessage(String text) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.TOP_RIGHT);
        // Styled user message bubble
        Label bubble = new Label(text);
        bubble.setWrapText(true);
        bubble.getStyleClass().add("user-bubble");
        bubble.setFont(Font.font(14));
        ImageView avatar = new ImageView(new Image(getClass().getResourceAsStream("/images/Avatar.png")));
        avatar.setFitWidth(45);
        avatar.setFitHeight(45);
        row.getChildren().addAll(bubble, avatar);
        chatContainer.getChildren().add(row);
    }
}
