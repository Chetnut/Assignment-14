import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MetricConverter extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create GUI components
        Label instructions = new Label("Enter conversion query (e.g., 1 km = mi):");
        TextField inputField = new TextField();
        Button convertButton = new Button("Convert");
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);

        // Event handling for convert button
        convertButton.setOnAction(e -> {
            String input = inputField.getText().trim();
            String result = handleConversion(input);
            outputArea.setText(result);
        });

        // Layout setup
        VBox layout = new VBox(10, instructions, inputField, convertButton, outputArea);
        layout.setStyle("-fx-padding: 20; -fx-font-size: 14;");
        Scene scene = new Scene(layout, 400, 250);

        // Set up stage
        primaryStage.setTitle("Metric Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Core logic for parsing and handling input
    private String handleConversion(String input) {
        if (input.equalsIgnoreCase("exit") || input.equals("-1")) {
            System.exit(0);
        }

        try {
            // Expecting format like: "1 km = mi"
            String[] parts = input.split(" ");
            if (parts.length != 4 || !parts[2].equals("=")) {
                return "Invalid input format. Use format: '1 km = mi'";
            }

            double value = Double.parseDouble(parts[0]);
            String fromUnit = parts[1].toLowerCase();
            String toUnit = parts[3].toLowerCase();

            double converted = convert(value, fromUnit, toUnit);
            return value + " " + fromUnit + " = " + converted + " " + toUnit;

        } catch (NumberFormatException e) {
            return "Invalid number. Please enter a valid query.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // Conversion logic
    private double convert(double value, String from, String to) {
        if (from.equals("kg") && to.equals("lb")) return value * 2.20462;
        if (from.equals("g") && to.equals("oz")) return value * 0.035274;
        if (from.equals("km") && to.equals("mi")) return value * 0.621371;
        if (from.equals("mm") && to.equals("in")) return value * 0.0393701;

        throw new IllegalArgumentException("Conversion from " + from + " to " + to + " is not supported.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
