import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Blob;
import java.io.ByteArrayInputStream;

public class borrowBook {

    @FXML
    private TextField borrowBookTextField;

    @FXML
    private ImageView borrowedBook;

    @FXML
    void searchButton(ActionEvent event) {
        String searchQuery = borrowBookTextField.getText(); // Get the search query from the TextField

        // Query the database for the searched book
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB != null) {
            String searchBookQuery = "SELECT * FROM books WHERE UPPER(title) LIKE ? LIMIT 1";

            try {
                PreparedStatement preparedStatement = connectDB.prepareStatement(searchBookQuery);
                preparedStatement.setString(1, "%" + searchQuery.toUpperCase() + "%");

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Get BLOB data for the image
                    Blob blob = resultSet.getBlob("image_path");
                    byte[] imageData = blob.getBytes(1, (int) blob.length());

                    // Convert the byte array to a JavaFX Image
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
                    Image image = new Image(inputStream);

                    // Set the image to the borrowedBook ImageView
                    borrowedBook.setImage(image);
                } else {
                    // Book not found, clear the borrowedBook ImageView
                    borrowedBook.setImage(null);
                }

                resultSet.close();
                preparedStatement.close();
                connectDB.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }   

    @FXML
void borrowBookButton(ActionEvent event) {
    String bookTitle = borrowBookTextField.getText(); // Get the book title from the TextField
    String loggedInUsername = Main.loggedInUsername; // Get the currently logged-in username

    // Get the user details from the database based on the username
    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    if (connectDB != null) {
        String getUserDetailsQuery = "SELECT first_name, last_name, email FROM users WHERE username = ?";
        String getBookDetailsQuery = "SELECT image_path FROM books WHERE title = ?";

        try {
            // Get user details
            PreparedStatement userStatement = connectDB.prepareStatement(getUserDetailsQuery);
            userStatement.setString(1, loggedInUsername);
            ResultSet userResult = userStatement.executeQuery();

            if (userResult.next()) {
                String firstName = userResult.getString("first_name");
                String lastName = userResult.getString("last_name");
                String email = userResult.getString("email");

                // Get book image path
                PreparedStatement bookStatement = connectDB.prepareStatement(getBookDetailsQuery);
                bookStatement.setString(1, bookTitle);
                ResultSet bookResult = bookStatement.executeQuery();

                if (bookResult.next()) {
                    String bookImagePath = bookResult.getString("image_path");

                    // Insert the borrowed book details into the BorrowedBooks table
                    String insertBorrowedBookQuery = "INSERT INTO BorrowedBooks (first_name, last_name, username, email, BookBorrowed, image_path) VALUES (?, ?, ?, ?, ?, ?)";

                    PreparedStatement preparedStatement = connectDB.prepareStatement(insertBorrowedBookQuery);
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setString(3, loggedInUsername);
                    preparedStatement.setString(4, email);
                    preparedStatement.setString(5, bookTitle);
                    preparedStatement.setString(6, bookImagePath); // Copy book image path

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Book borrowed successfully.");
                        // You can add further logic here if needed
                    } else {
                        System.out.println("Failed to borrow the book.");
                    }
                } else {
                    System.out.println("Book details not found.");
                }

                bookResult.close();
                bookStatement.close();
            } else {
                System.out.println("User details not found.");
            }

            userResult.close();
            userStatement.close();
            connectDB.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



}
