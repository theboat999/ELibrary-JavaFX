import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class register {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField usernameField;

    @FXML
    private DatePicker birthdateTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label firstNameErrorLabel;

    @FXML
    private Label lastNameErrorLabel;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private Label usernameErrorLabel;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    private Label birthdateErrorLabel;

    @FXML
    void createAccountButton(ActionEvent event) {
        String username = usernameField.getText().toUpperCase(); // Convert username to uppercase
        String password = passwordField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        LocalDate birthdate = birthdateTextField.getValue();

        firstNameErrorLabel.setText(""); // Clear previous error message
        lastNameErrorLabel.setText(""); // Clear previous error message
        emailErrorLabel.setText(""); // Clear previous error message
        usernameErrorLabel.setText(""); // Clear previous error message
        passwordErrorLabel.setText(""); // Clear previous error message
        birthdateErrorLabel.setText("");

        if (firstName.isBlank()) {
            firstNameErrorLabel.setText("What's your name?");
        }

        if (lastName.isBlank()) {
            lastNameErrorLabel.setText("What's your name?");
        }

        if (!(isValidPhoneNumber(email) || isValidEmail(email))) {
            emailErrorLabel.setText("You need this for your account verification.");
        }

        if (username.length() < 5) {
            usernameErrorLabel.setText("Combination of at least 5 letters and numbers");
        }

        if (password.length() < 8 || !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            passwordErrorLabel.setText("Combination of at least 6 numbers, letters, and special characters");
        }

        if (birthdate == null) {
            birthdateErrorLabel.setText("Birthdate cannot be blank");
            return;
        }

        // Proceed with database insertion if all fields are valid

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB != null) {
            String insertQuery = "INSERT INTO users (username, password, first_name, last_name, email, birthdate) VALUES (?, ?, ?, ?, ?, ?)";

            try {
                PreparedStatement preparedStatement = connectDB.prepareStatement(insertQuery);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, firstName);
                preparedStatement.setString(4, lastName);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, birthdate.toString());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User data inserted successfully.");
                } else {
                    System.out.println("Failed to insert user data.");
                }

                preparedStatement.close();
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // PARA SA EMAIL OR PHONE NUMBER
    private boolean isValidPhoneNumber(String input) {
        return input.matches("^(09|63)\\d{9}$");
    }

    // PANG CHECK IF VALID YUNG EMAIL DOMAIN
    private boolean isValidEmail(String email) {
        // Simple email validation using regex (you can improve this regex as needed)
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
}