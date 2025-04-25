import javafx.application.Application;
import javafx.application.Platform;
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
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.logging.Logger;

public class ChatbotGUI extends Application {

    // Logger for debugging and tracking application behavior
    private static final Logger logger = Logger.getLogger(ChatbotGUI.class.getName());

    // UI components
    private VBox chatContainer;             // Container for displaying chat messages
    private DatePicker startDatePicker;     // For trip start date input
    private TextField locationInput;        // For location input
    private TextField dayInput;             // For visit day (1-3) input
    private ProgressIndicator progressIndicator; // Loading spinner for data fetching
    private Button actionButton;            // Button to drive sequential input

    // State variables for step-by-step flow
    private int step = 0;                   // 0: date, 1-10: locations/days, 11: fetch, 12: restart prompt
    private LocalDate startDate;
    private String[] locations = new String[5];
    private int[] visitDays = new int[5];
    private int currentIndex = 0;           // Index from 0 to 4

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
        locationInput.setDisable(true);

        dayInput = new TextField();
        dayInput.setPromptText("Visit day (1-3)");
        dayInput.setDisable(true);

        // Button to drive the flow
        actionButton = new Button("Submit");
        actionButton.setOnAction(e -> onAction());

        // Input layout grouping
        HBox inputBox = new HBox(10, startDatePicker, locationInput, dayInput, actionButton);
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

        // Start the conversation
        askDate();
    }

    // Handles the Submit button based on current step
    private void onAction() {
        switch (step) {
            case 0 -> handleDate();
            case 1,3,5,7,9 -> handleLocation();
            case 2,4,6,8,10 -> handleDay();
            default -> {}
        }
    }

    // ask for trip start date
    private void askDate() {
        addBotMessage("Welcome to the Trip Clothing Planner Chatbot!\n");
        addBotMessage("I'm Wadrobot! Packing can be tricky but I'm here to simplify your travel planning!\n");
        addBotMessage("Please enter the start date of your trip (YYYY-MM-DD):");
        step = 0;
        startDatePicker.setDisable(false);
        locationInput.setDisable(true);
        dayInput.setDisable(true);
    }

    private void handleDate() {
        try {
            startDate = startDatePicker.getValue();
            if (startDate == null) throw new DateTimeParseException("null", "", 0);
            addUserMessage(startDate.toString());
            startDatePicker.setDisable(true);
            step = 1;
            askLocation();
        } catch (DateTimeParseException ex) {
            addBotMessage("Invalid date format. Please try again using YYYY-MM-DD:");
            logger.warning("Invalid date input");
        }
    }

    // Ask for the current location
    private void askLocation() {
        addBotMessage("Enter location " + (currentIndex + 1) + ":");
        locationInput.clear();
        locationInput.setDisable(false);
        dayInput.setDisable(true);
    }

    private void handleLocation() {
        String loc = locationInput.getText().trim();
        if (loc.isEmpty()) {
            addBotMessage("Enter location " + (currentIndex + 1) + ":");
            return;
        }
        locations[currentIndex] = loc;
        addUserMessage(loc);
        locationInput.setDisable(true);
        step++;
        askDay();
    }

    // Ask for the visit day for current location
    private void askDay() {
        addBotMessage("Enter visit day (1-3) for " + locations[currentIndex] + ":");
        dayInput.clear();
        dayInput.setDisable(false);
    }

    private void handleDay() {
        String d = dayInput.getText().trim();
        try {
            int day = Integer.parseInt(d);
            if (day < 1 || day > 3) throw new NumberFormatException();
            visitDays[currentIndex] = day;
            addUserMessage(String.valueOf(day));
            dayInput.setDisable(true);
            currentIndex++;
            if (currentIndex < 5) {
                step++;
                askLocation();
            } else {
                step = 11;
                fetchAll();
            }
        } catch (NumberFormatException ex) {
            addBotMessage("Invalid visit day. Please enter a number between 1 and 3.");
            logger.warning("Invalid visit day input");
        }
    }

    // fetch and display all weather & clothing
    private void fetchAll() {
        progressIndicator.setVisible(true);
        new Thread(() -> {
            // Simulate delay and fetch
            for (int i = 0; i < 5; i++) {
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
                LocalDate date = startDate.plusDays(visitDays[i] - 1);
                String weatherInfo = WeatherAPI.getWeather(locations[i], date.toString());
                String clothingSuggestion = ClothingRecommender.getClothingSuggestion(weatherInfo, visitDays[i] - 1);
                int idx = i;
                Platform.runLater(() -> {
                    addBotMessage("Weather in " + locations[idx] + " on " + date + ": " + weatherInfo);
                    addBotMessage("Clothing Suggestion for Day " + visitDays[idx] + ": " + clothingSuggestion);
                });
            }
            Platform.runLater(() -> {
                progressIndicator.setVisible(false);
                promptRestart();
            });
        }).start();
    }

    // Ask user if they want another trip
    private void promptRestart() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Plan Another Trip?");
        alert.setHeaderText(null);
        alert.setContentText("Would you like to plan another trip? (yes/no)");

        ButtonType yes = new ButtonType("yes");
        ButtonType no  = new ButtonType("no");
        alert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> res = alert.showAndWait();
        if (res.isPresent() && res.get() == yes) {
            resetAll();
        } else {
            addBotMessage("\nThanks for sharing your plans!");
            addBotMessage("I hope you have an amazing journey! 👋 Safe travels!");
            disableAll();
        }
    }

    // Reset state for a new trip
    private void resetAll() {
        step = 0;
        currentIndex = 0;
        startDatePicker.setValue(null);
        resetInputs();
        askDate();
    }

    // Disable inputs after no
    private void disableAll() {
        startDatePicker.setDisable(true);
        locationInput.setDisable(true);
        dayInput.setDisable(true);
        actionButton.setDisable(true);
    }

    private void resetInputs() {
        locationInput.clear();
        dayInput.clear();
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
