import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class returnBook {

    @FXML
    private TextField returnSearchTextField;

    @FXML
    void searchReturnBookButton(ActionEvent event) {
        String bookTitle = returnSearchTextField.getText(); // Get the book title to be returned

        // Get the currently logged-in username
        String loggedInUsername = Main.loggedInUsername;

        // Query to delete the borrowed book based on title and username
        String deleteBookQuery = "DELETE FROM BorrowedBooks WHERE BookBorrowed = ? AND username = ?";

        // Connect to the database
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB != null) {
            try {
                // Prepare the delete statement
                PreparedStatement preparedStatement = connectDB.prepareStatement(deleteBookQuery);
                preparedStatement.setString(1, bookTitle);
                preparedStatement.setString(2, loggedInUsername);

                // Execute the delete statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Book returned successfully
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Book Returned");
                    alert.setHeaderText(null);
                    alert.setContentText("The book has been returned successfully.");
                    alert.showAndWait();
                } else {
                    // Book not found in the BorrowedBooks table
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Book Not Found");
                    alert.setHeaderText(null);
                    alert.setContentText("The book was not found in your borrowed books.");
                    alert.showAndWait();
                }

                preparedStatement.close();
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
