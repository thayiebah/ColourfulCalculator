import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ColourfulCalculator {

    public static void main(String[] args) {
        CalculatorModel model = new CalculatorModel(); // Initialize the Model
        CalculatorView view = new CalculatorView(); // Initialize the View
        new CalculatorController(model, view); // Link the Model and View via the Controller
        view.setVisible(true); // Make the calculator visible
    }
}

class CalculatorModel {
    private double result;

    public double add(double num1, double num2) {
        result = num1 + num2; 
        return result; 
    }

    public double subtract(double num1, double num2) {
        result = num1 - num2; 
        return result;
    }

    public double multiply(double num1, double num2) {
        result = num1 * num2; 
        return result; 
    }

    public double divide(double num1, double num2) {
        if (num2 == 0) { 
            System.out.println("Error: Division by zero"); 
            return Double.NaN; 
        }
        result = num1 / num2; 
        return result; 
    }

    public double getResult() {
        return result;
    }
}

class CalculatorView extends JFrame {
    private JTextField calculatorScreen; 
    private JButton[] numberButtons = new JButton[10]; 
    private JButton addButton, subtractButton, multiplyButton, divideButton,
            equalButton, clearButton, backspaceButton, decimalButton, historyButton; 

    public CalculatorView() {
        setTitle("Colourful Calculator"); 
        setSize(360, 500); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        setLayout(new GridBagLayout());
        GridBagConstraints screenConstraints = new GridBagConstraints();
        screenConstraints.fill = GridBagConstraints.BOTH; 
        screenConstraints.weightx = 1.0; 
        screenConstraints.weighty = 0.2; 
        screenConstraints.gridx = 0; 
        screenConstraints.gridy = 0; 
        screenConstraints.gridwidth = 4; 

        calculatorScreen = new JTextField();
        calculatorScreen.setEditable(false); 
        calculatorScreen.setFont(new Font("Cambria", Font.BOLD, 30)); 
        calculatorScreen.setHorizontalAlignment(JTextField.RIGHT); 
        calculatorScreen.setBackground(Color.decode("#F0F4FF")); 
        calculatorScreen.setForeground(Color.decode("#071952"));
        add(calculatorScreen, screenConstraints); 

        JPanel buttonPanel = new JPanel(new GridBagLayout());

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.fill = GridBagConstraints.BOTH; 
        buttonConstraints.weightx = 1.0; 
        buttonConstraints.weighty = 1.0; 

        String[] buttons = { "7", "8", "9", "+", "4", "5", "6", "-",
                "1", "2", "3", "x", "0", ".", "←", "÷" };
        int row = 0;
        int column = 0;

        for (String text : buttons) {
            JButton button = createButton(text, switch (text) {
                case "+", "-", "x", "÷", "←" -> Color.decode("#003366");
                case "." -> getGradientColor(1); 
                case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> getGradientColor(Integer.parseInt(text)); 
                                                                                                                   
                                                                                                                   
                default -> Color.decode("#FFFFFF");
            });

            if (Character.isDigit(text.charAt(0))) {
                numberButtons[Integer.parseInt(text)] = button; 
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

            buttonConstraints.gridx = column;
            buttonConstraints.gridy = row;
            buttonConstraints.gridwidth = 1;
            buttonConstraints.gridheight = 1;
            buttonPanel.add(button, buttonConstraints);

            column++;
            if (column == 4) { 
                column = 0;
                row++;
            }
        }

        GridBagConstraints buttonPanelConstraints = new GridBagConstraints();
        buttonPanelConstraints.fill = GridBagConstraints.BOTH; 
        buttonPanelConstraints.weightx = 1.0; 
        buttonPanelConstraints.weighty = 0.8; 
        buttonPanelConstraints.gridx = 0; 
        buttonPanelConstraints.gridy = 1; 
        buttonPanelConstraints.gridwidth = 4; 

        clearButton = createButton("C", Color.decode("#FF8C00"));
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = row;
        buttonConstraints.gridwidth = 1;
        buttonConstraints.gridheight = 1;
        buttonPanel.add(clearButton, buttonConstraints);

        ImageIcon historyIcon = new ImageIcon("historyIcon.png");
        Image scaledImage = historyIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); 
        historyIcon = new ImageIcon(scaledImage);
        historyButton = new JButton(historyIcon);

        historyButton.setBackground(Color.decode("#FF8C00"));
        historyButton.setBorderPainted(false);
        historyButton.setFocusPainted(false);
        historyButton.setContentAreaFilled(false);
        historyButton.setOpaque(false);

        buttonConstraints.gridx = 0; 
        buttonConstraints.gridy = row;
        buttonConstraints.gridwidth = 1;
        buttonConstraints.gridheight = 1;
        buttonPanel.add(historyButton, buttonConstraints);

        equalButton = createButton("=", Color.decode("#FFD700"));
        equalButton.setForeground(Color.decode("#000000"));
        buttonConstraints.gridx = 2;
        buttonConstraints.gridy = row;
        buttonConstraints.gridwidth = 2;
        buttonConstraints.gridheight = 1;
        buttonPanel.add(equalButton, buttonConstraints);

        add(buttonPanel, buttonPanelConstraints); 

    }

 
    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Cambria", Font.BOLD, 30));
        button.setForeground(Color.WHITE);
        button.setOpaque(true); 
        button.setBorderPainted(false); 
        button.setBackground(backgroundColor); 

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(Color.decode("#87CEFA")); 
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(backgroundColor); 
            }
        });

        return button;
    }

    private Color getGradientColor(int number) {
        int red = 255;
        int green = 215 - (number * 15); 
        int blue = 0;
        return new Color(Math.max(red, 0), Math.max(green, 0), Math.max(blue, 0));
    }

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

class CalculatorController {
    private CalculatorModel model; 
    private CalculatorView view; 
    private List<String> historyList = new ArrayList<>();

    private double firstNumber = 0; 
    private double secondNumber = 0; 
    private String operator = ""; 
    private boolean decimalUsed = false; 
    private boolean resetOnNextInput = false; 

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;

        for (int i = 0; i < view.getNumberButtons().length; i++) {
            int number = i; 
            view.getNumberButtons()[i].addActionListener(new NumberButtonListener(number));
        }

        view.getAddButton().addActionListener(new OperatorButtonListener("+"));
        view.getSubtractButton().addActionListener(new OperatorButtonListener("-"));
        view.getMultiplyButton().addActionListener(new OperatorButtonListener("*"));
        view.getDivideButton().addActionListener(new OperatorButtonListener("÷"));
        view.getEqualButton().addActionListener(new EqualsButtonListener());
        view.getClearButton().addActionListener(new ClearButtonListener());
        view.getDecimalButton().addActionListener(new DecimalButtonListener());
        view.getBackspaceButton().addActionListener(new BackspaceButtonListener());
        view.getHistoryButton().addActionListener(new HistoryButtonListener());
    }

    private class NumberButtonListener implements ActionListener {
        private final int number;

        public NumberButtonListener(int number) {
            this.number = number; 
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = view.getDisplayField().getText();

            if (resetOnNextInput || currentText.equals("Error") || currentText.contains("Cannot")) {
                view.getDisplayField().setText(""); 
                resetOnNextInput = false; 
            }
            view.getDisplayField().setText(view.getDisplayField().getText() + number);
        }
    }


    private class OperatorButtonListener implements ActionListener {
        private final String operator;

        public OperatorButtonListener(String operator) {
            this.operator = operator;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = view.getDisplayField().getText();

            if (currentText.equals("Error") || currentText.contains("Cannot")) { 
                view.getDisplayField().setText("");
                resetOnNextInput = false;
            }
            handleOperator(operator); 
        }
    }

    private class EqualsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleEquals();
            decimalUsed = false;
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getDisplayField().setText(""); 
            firstNumber = 0;
            secondNumber = 0;
            operator = "";
            decimalUsed = false;
            resetOnNextInput = false;
        }
    }

    private class DecimalButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = view.getDisplayField().getText();

            if (resetOnNextInput || currentText.equals("Error") || currentText.contains("Cannot")) {
                view.getDisplayField().setText("");
                resetOnNextInput = false;
            }

            if (!decimalUsed) {
                if (view.getDisplayField().getText().isEmpty()) {
                    view.getDisplayField().setText("0."); 
                } else {
                    view.getDisplayField().setText(view.getDisplayField().getText() + ".");
                }
                decimalUsed = true; 
            }
        }
    }

    private class BackspaceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = view.getDisplayField().getText();
            if (currentText.equals("Error") || currentText.contains("Cannot")) {
                view.getDisplayField().setText(""); 
                resetOnNextInput = false;
                return;
            }
            if (!currentText.isEmpty()) {
                if (currentText.endsWith(".")) {
                    decimalUsed = false; 
                }
                view.getDisplayField().setText(currentText.substring(0, currentText.length() - 1));
            }
        }
    }

    private class HistoryButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame historyFrame = new JFrame("Calculation History");
            historyFrame.setSize(300, 400); 
            historyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

            JTextArea historyTextArea = new JTextArea();
            historyTextArea.setEditable(false); 
            historyTextArea.setFont(new Font("Cambria", Font.PLAIN, 16));

            StringBuilder historyContent = new StringBuilder();
            for (String record : historyList) { 
                historyContent.append(record).append("\n");
            }
            historyTextArea.setText(historyContent.toString());

            JScrollPane scrollPane = new JScrollPane(historyTextArea);
            historyFrame.add(scrollPane);

            historyFrame.setVisible(true);
        }
    }

    private void handleOperator(String op) {
        String displayText = view.getDisplayField().getText();
        if (displayText.isEmpty() || displayText.equals("Error") || displayText.contains("Cannot")) {
            view.getDisplayField().setText("Error");
            return;
        }
        try {
            firstNumber = Double.parseDouble(view.getDisplayField().getText());
            operator = op; 
            view.getDisplayField().setText(""); 
            decimalUsed = false;
        } catch (NumberFormatException ex) {
            view.getDisplayField().setText("Error"); 
        }
    }

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
