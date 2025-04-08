import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ChatbotGUI extends Application {
    private VBox chatContainer;
    private DatePicker startDatePicker;   // Trip start date
    private TextField locationInput;      // Trip location
    private TextField dayInput;           // Visit day (1-3)

    public static void main(String[] args) {
        launch(args); // Start JavaFX app
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Trip Clothing Planner Chatbot");

        // Chat message area
        chatContainer = new VBox(10);
        chatContainer.setPadding(new Insets(20));
        chatContainer.setPrefWidth(450);

        ScrollPane scrollPane = new ScrollPane(chatContainer);
        scrollPane.setFitToWidth(true);

        // Input fields
        startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Trip Start Date (YYYY-MM-DD)");
        locationInput = new TextField();
        locationInput.setPromptText("Enter location...");
        dayInput = new TextField();
        dayInput.setPromptText("Visit day (1-3)");

        // Button to get weather and clothing advice
        Button fetchWeatherButton = new Button("Get Weather & Suggestion");
        fetchWeatherButton.setOnAction(e -> fetchWeather());

        // Input layout
        HBox inputBox = new HBox(10, startDatePicker, locationInput, dayInput, fetchWeatherButton);
        inputBox.setPadding(new Insets(10));
        inputBox.setAlignment(Pos.CENTER);

        // Main layout
        VBox layout = new VBox(scrollPane, inputBox);
        Scene scene = new Scene(layout, 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

        // Welcome messages
        addBotMessage("Hi! I'm Wardrobot your trip clothing assistant.");
        addBotMessage("Enter your trip start date, a location, and visit day (1-3) to get suggestions.");
    }

    // Handle button click: fetch weather and clothing suggestions
    private void fetchWeather() {
        LocalDate startDate = startDatePicker.getValue();
        String location = locationInput.getText();
        String dayStr = dayInput.getText();

        // Validate inputs
        if (startDate == null || location.isEmpty() || dayStr.isEmpty()) {
            addBotMessage("Please enter the trip start date, location, and visit day.");
            return;
        }

        int day;
        try {
            day = Integer.parseInt(dayStr);
        } catch (NumberFormatException ex) {
            addBotMessage("Visit day must be a number between 1 and 3.");
            return;
        }

        if (day < 1 || day > 3) {
            addBotMessage("Visit day must be between 1 and 3.");
            return;
        }

        // Calculate visit date
        LocalDate visitDate = startDate.plusDays(day - 1);
        addUserMessage("Location: " + location + ", Visit Date: " + visitDate);

        // Fetch weather and clothing info
        String weatherInfo = WeatherAPI.getWeather(location, visitDate.toString());
        String clothingSuggestion = ClothingRecommender.getClothingSuggestion(weatherInfo, day - 1);

        // Display results
        addBotMessage("Weather in " + location + " on " + visitDate + ":\n" + weatherInfo);
        addBotMessage("Clothing Suggestion for Day " + day + ":\n" + clothingSuggestion);
    }

    // Show bot message in chat
    private void addBotMessage(String text) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.TOP_LEFT);
        ImageView avatar = new ImageView(new Image(getClass().getResourceAsStream("/images/robot.face.png")));
        avatar.setFitWidth(45);
        avatar.setFitHeight(45);
        Label bubble = new Label(text);
        bubble.setWrapText(true);
        bubble.getStyleClass().add("bot-bubble");
        bubble.setFont(Font.font(14));
        row.getChildren().addAll(avatar, bubble);
        chatContainer.getChildren().add(row);
    }

    // Show user message in chat
    private void addUserMessage(String text) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.TOP_RIGHT);
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
