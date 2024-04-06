import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Blob;
import java.io.ByteArrayInputStream;

public class searchedBook {

    @FXML
    private FlowPane resultsField;

    public void searchBooks(String searchQuery) {
        // Query the database for books based on the search query
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB != null) {
            String searchBooksQuery = "SELECT * FROM books WHERE " +
                    "UPPER(author) LIKE ? OR " +
                    "UPPER(year) LIKE ? OR " +
                    "UPPER(title) LIKE ? OR " +
                    "UPPER(genre) LIKE ?";
            
            try {
                PreparedStatement preparedStatement = connectDB.prepareStatement(searchBooksQuery);
                preparedStatement.setString(1, "%" + searchQuery.toUpperCase() + "%");
                preparedStatement.setString(2, "%" + searchQuery.toUpperCase() + "%");
                preparedStatement.setString(3, "%" + searchQuery.toUpperCase() + "%");
                preparedStatement.setString(4, "%" + searchQuery.toUpperCase() + "%");

                ResultSet resultSet = preparedStatement.executeQuery();

                // Clear previous results
                resultsField.getChildren().clear();

                while (resultSet.next()) {
                    // Get book details
                    String author = resultSet.getString("author");
                    String year = resultSet.getString("year");
                    String title = resultSet.getString("title");
                    String genre = resultSet.getString("genre");

                    // Get BLOB data for the image
                    Blob blob = resultSet.getBlob("image_path");
                    byte[] imageData = blob.getBytes(1, (int) blob.length());

                    // Convert the byte array to a JavaFX Image
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
                    Image image = new Image(inputStream);
                    ImageView imageView = new ImageView(image);
                    
                    // Set the width and height of the image view
                    imageView.setFitWidth(150); // Set the width (adjust as needed)
                    imageView.setFitHeight(200); // Set the height (adjust as needed)

                    // Create a label to display book details
                    Label bookDetails = new Label("Title: " + title + "\nAuthor: " + author + "\nYear: " + year + "\nGenre: " + genre);
                    bookDetails.setWrapText(true); // Wrap text in case it's too long
                    
                    // Set font properties for the label
                    bookDetails.setFont(Font.font("Arial", FontWeight.MEDIUM, 16)); // Set font, weight, and size
                    bookDetails.setTextFill(Color.BLACK); // Set font color

                    // Create an HBox to hold the image and details
                    HBox bookContainer = new HBox(imageView, bookDetails);
                    bookContainer.setSpacing(20); // Set spacing between nodes

                    // Add the book container to the resultsField FlowPane
                    resultsField.getChildren().add(bookContainer);
                }

                // Close resources
                resultSet.close();
                preparedStatement.close();
                connectDB.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
