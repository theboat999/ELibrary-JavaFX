import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class libraryGUI {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label loginMessageLabel;

    @FXML
    void loginBtnClicked(ActionEvent event) {
        if (!usernameTextField.getText().isBlank() && !passwordTextField.getText().isBlank()) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter your username or password");

        }
    }

    @FXML
    void registerBtnClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("REGISTER ACCOUNT");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // VALIDATE LOGIN
    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB != null) {
            String verifyLogin = "SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1";

            try {
                PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
                preparedStatement.setString(1, usernameTextField.getText());
                preparedStatement.setString(2, passwordTextField.getText());
                ResultSet queryResult = preparedStatement.executeQuery();

                if (queryResult.next()) {
                    Main.loggedInUsername = usernameTextField.getText(); // Store the logged-in username

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
                        Parent root = loader.load();

                        Stage stage = new Stage();
                        stage.setTitle("");
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    loginMessageLabel.setText("Incorrect username or password");
                }

                preparedStatement.close();
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}