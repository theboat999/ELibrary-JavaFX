import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class userInfo {

    @FXML
    private Label userFirstName;

    @FXML
    private Label userUsername;

    @FXML
    private Label userEmail;

    @FXML
    private Label userBirthdate;

    @FXML
    private Label userPassword;

    @FXML
    public void initialize() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB != null) {
            String query = "SELECT first_name, last_name, username, email, birthdate, password FROM users WHERE username = ?";

            try {
                PreparedStatement preparedStatement = connectDB.prepareStatement(query);
                preparedStatement.setString(1, Main.loggedInUsername); // Use the stored username

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String birthdate = resultSet.getString("birthdate");
                    String password = resultSet.getString("password");

                    // Concatenate first name and last name
                    String fullName = firstName + " " + lastName;

                    userFirstName.setText(fullName); // Set the full name on the label
                    userUsername.setText(username);
                    userEmail.setText(email);
                    userBirthdate.setText(birthdate);
                    userPassword.setText(password);
                }

                resultSet.close();
                preparedStatement.close();
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}