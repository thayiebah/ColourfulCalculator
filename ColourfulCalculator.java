
/**
 * NUR THAYIEBAH BINTI HAMDAN
 * 1221305552
 * TUTORIAL SECTION: T13L
 * LECTURE SECTION: TC4L
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry point of the Colourful Calculator application.
 * 
 * Demonstrates the implementation of the MVC (Model-View-Controller) design
 * pattern:
 * - The Model (CalculatorModel) handles all calculation logic.
 * - The View (CalculatorView) is responsible for the user interface (UI).
 * - The Controller (CalculatorController) manages user interactions, updating
 * the Model and View accordingly.
 * 
 * This method initializes the components, links them, and makes the application
 * visible to the user.
 */
public class ColourfulCalculator {

    public static void main(String[] args) {
        CalculatorModel model = new CalculatorModel(); // Initialize the Model
        CalculatorView view = new CalculatorView(); // Initialize the View
        new CalculatorController(model, view); // Link the Model and View via the Controller
        view.setVisible(true); // Make the calculator visible
    }
}

/**
 * MVC Design Pattern: Model
 * This class represents the Model in the MVC pattern.
 * 
 * The Model is responsible for handling the calculations (logic)
 * and storing the result. It does not handle user input or output
 * (that is the job of the View and Controller).
 */
class CalculatorModel {
    // The variable stores the result of the last calculation.
    private double result;

    public double add(double num1, double num2) {
        result = num1 + num2; // Perform addition.
        return result; // Return the result.
    }

    public double subtract(double num1, double num2) {
        result = num1 - num2; // Perform subtraction.
        return result; // Return the result.
    }

    public double multiply(double num1, double num2) {
        result = num1 * num2; // Perform multiplication
        return result; // Return the result.
    }

    public double divide(double num1, double num2) {
        if (num2 == 0) { // Check for division by zero.
            System.out.println("Error: Division by zero"); // Print error message.
            return Double.NaN; // Return NaN to avoid errors.
        }
        result = num1 / num2; // Perform division.
        return result; // Return the result.
    }

    public double getResult() {
        return result; // Return the result variable.
    }
}

/**
 * MVC Design Pattern: View
 * The View class for the Calculator UI.
 * 
 * This class is responsible for displaying the UI elements of the calculator,
 * such as the screen, buttons, and layout. It does not handle any logic or user
 * input
 * processing:
 * those are handled by the Model and Controller.
 */
class CalculatorView extends JFrame {
    private JTextField calculatorScreen; // Display field for showing input/output
    private JButton[] numberButtons = new JButton[10]; // Buttons for digits 0-9
    private JButton addButton, subtractButton, multiplyButton, divideButton,
            equalButton, clearButton, backspaceButton, decimalButton, historyButton; // Operation buttons

    /**
     * Constructor to initialize and set up the calculator UI
     */
    public CalculatorView() {
        // Set up the main window (JFrame) properties.
        setTitle("Colourful Calculator"); // Title of the window
        setSize(360, 500); // Set the window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the program exits when closed

        setLayout(new GridBagLayout());
        GridBagConstraints screenConstraints = new GridBagConstraints();
        screenConstraints.fill = GridBagConstraints.BOTH; // Allow both horizontal and vertical resizing
        screenConstraints.weightx = 1.0; // Give priority to horizontal resizing
        screenConstraints.weighty = 0.2; // Give less priority to vertical resizing
        screenConstraints.gridx = 0; // Column 0
        screenConstraints.gridy = 0; // Row 0
        screenConstraints.gridwidth = 4; // Span across 4 columns

        // Add the calculator screen
        calculatorScreen = new JTextField();
        calculatorScreen.setEditable(false); // Prevent user from typing directly
        calculatorScreen.setFont(new Font("Cambria", Font.BOLD, 30)); // Font settings for readability
        calculatorScreen.setHorizontalAlignment(JTextField.RIGHT); // Align text to the right
        calculatorScreen.setBackground(Color.decode("#F0F4FF")); // Set background color
        calculatorScreen.setForeground(Color.decode("#071952"));
        add(calculatorScreen, screenConstraints); // Add the screen with constraints

        // Create the panel for buttons and configure its layout
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.fill = GridBagConstraints.BOTH; // Button fill the grid cell
        buttonConstraints.weightx = 1.0; // Allow buttons to stretch horizontally
        buttonConstraints.weighty = 1.0; // Allow buttons to stretch vertically

        // Define button labels and set up buttons
        String[] buttons = { "7", "8", "9", "+", "4", "5", "6", "-",
                "1", "2", "3", "x", "0", ".", "←", "÷" };
        int row = 0;
        int column = 0;

        for (String text : buttons) {
            // Create a button with a specific label and color based on its purpose
            JButton button = createButton(text, switch (text) {
                case "+", "-", "x", "÷", "←" -> Color.decode("#003366");
                case "." -> getGradientColor(1); // Use the first gradient value for the decimal button
                case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> getGradientColor(Integer.parseInt(text)); // Gradient
                                                                                                                   // for
                                                                                                                   // numbers
                default -> Color.decode("#FFFFFF");
            });

            // Map buttons to their respective variables for later use
            if (Character.isDigit(text.charAt(0))) {
                numberButtons[Integer.parseInt(text)] = button; // Assign to numberButtons array
            } else {
                switch (text) {
                    case "+" -> addButton = button;
                    case "-" -> subtractButton = button;
                    case "x" -> multiplyButton = button;
                    case "÷" -> divideButton = button;
                    case "←" -> backspaceButton = button;
                    case "." -> decimalButton = button;
                }
            }

            // Add the button to the grid
            buttonConstraints.gridx = column;
            buttonConstraints.gridy = row;
            buttonConstraints.gridwidth = 1;
            buttonConstraints.gridheight = 1;
            buttonPanel.add(button, buttonConstraints);

            column++;
            if (column == 4) { // Move to the next row after 4 buttons
                column = 0;
                row++;
            }
        }

        // Set up the button panel constraints
        GridBagConstraints buttonPanelConstraints = new GridBagConstraints();
        buttonPanelConstraints.fill = GridBagConstraints.BOTH; // Allow the panel to stretch
        buttonPanelConstraints.weightx = 1.0; // Allow horizontal resizing
        buttonPanelConstraints.weighty = 0.8; // Allow vertical resizing
        buttonPanelConstraints.gridx = 0; // Column 0
        buttonPanelConstraints.gridy = 1; // Row 1 (below the calculator screen)
        buttonPanelConstraints.gridwidth = 4; // Span across 4 columns

        // Add Clear button
        clearButton = createButton("C", Color.decode("#FF8C00"));
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = row;
        buttonConstraints.gridwidth = 1;
        buttonConstraints.gridheight = 1;
        buttonPanel.add(clearButton, buttonConstraints);

        // Create the history button with an image
        ImageIcon historyIcon = new ImageIcon("historyIcon.png");
        Image scaledImage = historyIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Resize image
        historyIcon = new ImageIcon(scaledImage);
        historyButton = new JButton(historyIcon);

        // Button appearance settings
        historyButton.setBackground(Color.decode("#FF8C00"));
        historyButton.setBorderPainted(false);
        historyButton.setFocusPainted(false);
        historyButton.setContentAreaFilled(false);
        historyButton.setOpaque(false);

        // Add history button
        buttonConstraints.gridx = 0; // Set position
        buttonConstraints.gridy = row;
        buttonConstraints.gridwidth = 1;
        buttonConstraints.gridheight = 1;
        buttonPanel.add(historyButton, buttonConstraints);

        // Add Equals button
        equalButton = createButton("=", Color.decode("#FFD700"));
        equalButton.setForeground(Color.decode("#000000"));
        buttonConstraints.gridx = 2;
        buttonConstraints.gridy = row;
        buttonConstraints.gridwidth = 2;
        buttonConstraints.gridheight = 1;
        buttonPanel.add(equalButton, buttonConstraints);

        add(buttonPanel, buttonPanelConstraints); // Add button panel to the center of the frame

    }

    /**
     * Helper method to create a button with custom properties.
     */
    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Cambria", Font.BOLD, 30));
        button.setForeground(Color.WHITE);
        button.setOpaque(true); // Make button background visible
        button.setBorderPainted(false); // Remove default border
        button.setBackground(backgroundColor); // Set button background color

        // Add hover effect to change button color when the mouse is over it
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(Color.decode("#87CEFA")); // Light blue hover color
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(backgroundColor); // Restore original color when mouse exits
            }
        });

        return button;
    }

    /**
     * Helper method to provide gradient colors for numbers
     */
    private Color getGradientColor(int number) {
        // Create a gradient from yellow to orange for numbers 0-9
        int red = 255;
        int green = 215 - (number * 15); // Gradual decrease in green for darker orange
        int blue = 0;
        return new Color(Math.max(red, 0), Math.max(green, 0), Math.max(blue, 0));
    }

    /**
     * Getter methods to allow the Controller to interact with the View elements.
     */
    public JTextField getDisplayField() {
        return calculatorScreen;
    }

    public JButton[] getNumberButtons() {
        return numberButtons;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getSubtractButton() {
        return subtractButton;
    }

    public JButton getMultiplyButton() {
        return multiplyButton;
    }

    public JButton getDivideButton() {
        return divideButton;
    }

    public JButton getEqualButton() {
        return equalButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getDecimalButton() {
        return decimalButton;
    }

    public JButton getBackspaceButton() {
        return backspaceButton;
    }

    public JButton getHistoryButton() {
        return historyButton;
    }
}

/**
 * MVC Design Pattern: Controller
 * 
 * The Controller class connnects the Model and the View.
 * It listens to user actions (e.g., button clicks) on the View and updates
 * the Model and/or View accordingly. This class handles all the logic behind
 * user interactions with the calculator.
 */
class CalculatorController {
    private CalculatorModel model; // Reference to the Model (business logic)
    private CalculatorView view; // Reference to the View (UI)
    private List<String> historyList = new ArrayList<>();

    private double firstNumber = 0; // Stores the first operand
    private double secondNumber = 0; // Stores the second operand
    private String operator = ""; // Stores the selected operator
    private boolean decimalUsed = false; // Tracks if a decimal point is used
    private boolean resetOnNextInput = false; // Resets display after certain operations

    /**
     * Constructor for the Controller.
     * Links the Model and View and sets up event listeners for user interactions.
     * 
     * @param model The CalculatorModel (business logic)
     * @param view  The CalculatorView (user interface)
     */
    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;

        // Add listeners for number buttons (0-9)
        for (int i = 0; i < view.getNumberButtons().length; i++) {
            int number = i; // Capture the button's number
            view.getNumberButtons()[i].addActionListener(new NumberButtonListener(number));
        }

        // Add listeners for operator buttons (+, -, *, ÷)
        view.getAddButton().addActionListener(new OperatorButtonListener("+"));
        view.getSubtractButton().addActionListener(new OperatorButtonListener("-"));
        view.getMultiplyButton().addActionListener(new OperatorButtonListener("*"));
        view.getDivideButton().addActionListener(new OperatorButtonListener("÷"));

        // Add listener for the equals (=) button
        view.getEqualButton().addActionListener(new EqualsButtonListener());

        // Add listener for the clear button
        view.getClearButton().addActionListener(new ClearButtonListener());

        // Add listener for the decimal button
        view.getDecimalButton().addActionListener(new DecimalButtonListener());

        // Add listener for the backspace button
        view.getBackspaceButton().addActionListener(new BackspaceButtonListener());

        // Add listener for the history button
        view.getHistoryButton().addActionListener(new HistoryButtonListener());
    }

    /**
     * Handles number button clicks. Update the display to append the selected
     * number.
     */
    private class NumberButtonListener implements ActionListener {
        private final int number;

        public NumberButtonListener(int number) {
            this.number = number; // The number this button represents
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = view.getDisplayField().getText();

            // Clear the display if it's in an error state or ready for new input
            if (resetOnNextInput || currentText.equals("Error") || currentText.contains("Cannot")) {
                view.getDisplayField().setText(""); // clear the display
                resetOnNextInput = false; // reset the flag
            }
            // Append the number to the display
            view.getDisplayField().setText(view.getDisplayField().getText() + number);
        }
    }

    /**
     * Handles operator button clicks (e.g., +, -, *, ÷).
     */
    private class OperatorButtonListener implements ActionListener {
        private final String operator;

        public OperatorButtonListener(String operator) {
            this.operator = operator;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = view.getDisplayField().getText();

            if (currentText.equals("Error") || currentText.contains("Cannot")) { /////////////
                view.getDisplayField().setText("");
                resetOnNextInput = false;
            }
            handleOperator(operator); // Delegate to handleOperator method
        }
    }

    /**
     * Handles the equals (=) button click. Performs the calculation.
     */
    private class EqualsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleEquals();
            decimalUsed = false; // Reset decimal usage after calculation
        }
    }

    /**
     * Handles the clear button click. Resets the calculator state.
     */
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getDisplayField().setText(""); // Clear the display
            firstNumber = 0;
            secondNumber = 0;
            operator = "";
            decimalUsed = false;
            resetOnNextInput = false;
        }
    }

    /**
     * Handles the decimal (.) button click.
     */
    private class DecimalButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = view.getDisplayField().getText();

            if (resetOnNextInput || currentText.equals("Error") || currentText.contains("Cannot")) {
                view.getDisplayField().setText("");
                resetOnNextInput = false;
            }

            // Add decimal point only if it hasn't been used yet
            if (!decimalUsed) {
                if (view.getDisplayField().getText().isEmpty()) {
                    view.getDisplayField().setText("0."); // Start with "0." if empty
                } else {
                    view.getDisplayField().setText(view.getDisplayField().getText() + ".");
                }
                decimalUsed = true; // Mark decimal as used
            }
        }
    }

    /**
     * Handles the backspace (←) button click. Removes the last character from the
     * display.
     */
    private class BackspaceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = view.getDisplayField().getText();
            if (currentText.equals("Error") || currentText.contains("Cannot")) {
                view.getDisplayField().setText(""); // Clear the entire message
                resetOnNextInput = false;
                return;
            }
            if (!currentText.isEmpty()) {
                if (currentText.endsWith(".")) {
                    decimalUsed = false; // Reset decimal if it was removed
                }
                view.getDisplayField().setText(currentText.substring(0, currentText.length() - 1));
            }
        }
    }

    /**
     * Handles the history button click.
     */
    private class HistoryButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a new JFrame for displaying history
            JFrame historyFrame = new JFrame("Calculation History");
            historyFrame.setSize(300, 400); // Set size of the history window
            historyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the history window

            // Create a JTextArea to display history
            JTextArea historyTextArea = new JTextArea();
            historyTextArea.setEditable(false); // Make it read-only
            historyTextArea.setFont(new Font("Cambria", Font.PLAIN, 16)); // Set font for readability

            // Get the history from the model or a stored history variable
            StringBuilder historyContent = new StringBuilder();
            for (String record : historyList) { // Assume `historyList` stores the calculation history
                historyContent.append(record).append("\n");
            }
            historyTextArea.setText(historyContent.toString());

            // Add the JTextArea to a JScrollPane for scrolling
            JScrollPane scrollPane = new JScrollPane(historyTextArea);
            historyFrame.add(scrollPane);

            // Make the history window visible
            historyFrame.setVisible(true);
        }
    }

    /**
     * Processes the operator button click. Stores the current number and operator.
     */
    private void handleOperator(String op) {
        String displayText = view.getDisplayField().getText();
        if (displayText.isEmpty() || displayText.equals("Error") || displayText.contains("Cannot")) {
            view.getDisplayField().setText("Error");
            return;
        }
        try {
            firstNumber = Double.parseDouble(view.getDisplayField().getText());
            operator = op; // Save the selected operator
            view.getDisplayField().setText(""); // Clear the display for the next number
            decimalUsed = false;
        } catch (NumberFormatException ex) {
            view.getDisplayField().setText("Error"); // Show error if input is invalid
        }
    }

    /**
     * Processes the equals (=) button click. Performs the calculation.
     */
    private void handleEquals() {
        try {
            secondNumber = Double.parseDouble(view.getDisplayField().getText());
            double result;
            switch (operator) {
                case "+" -> result = model.add(firstNumber, secondNumber);
                case "-" -> result = model.subtract(firstNumber, secondNumber);
                case "*" -> result = model.multiply(firstNumber, secondNumber);
                case "÷" -> {
                    if (secondNumber == 0) {
                        view.getDisplayField().setText("Cannot divide by zero");
                        resetOnNextInput = true;
                        return;
                    }
                    result = model.divide(firstNumber, secondNumber);
                }
                default -> {
                    view.getDisplayField().setText("Error");
                    resetOnNextInput = true;
                    return;
                }
            }

            // Format numbers for display
            String formattedFirstNumber = (firstNumber == (long) firstNumber) ? String.valueOf((long) firstNumber)
                    : String.valueOf(firstNumber);
            String formattedSecondNumber = (secondNumber == (long) secondNumber) ? String.valueOf((long) secondNumber)
                    : String.valueOf(secondNumber);
            String formattedResult = (result == (long) result) ? String.valueOf((long) result) : String.valueOf(result);

            String calculation = formattedFirstNumber + " " + operator + " " + formattedSecondNumber + " = "
                    + formattedResult;
            historyList.add(calculation);

            // check if the result is whole number
            if (result == (long) result) {
                // display as an integer
                view.getDisplayField().setText(String.valueOf((long) result));
                resetOnNextInput = true;
            } else {
                // display as decimal
                view.getDisplayField().setText(String.valueOf(result));
                resetOnNextInput = true;
            }
        } catch (NumberFormatException ex) {
            view.getDisplayField().setText("Error");
            resetOnNextInput = true;
        }

    }
}
