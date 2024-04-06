import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class mainMenu {

    @FXML
    private TextField searchBookTextField;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView image4;

    @FXML 
    private ImageView image5;

    @FXML
    private ImageView image6;

    @FXML
    private ImageView image7;

    @FXML 
    private ImageView image8;

    @FXML
    private ImageView image9;

    @FXML
    private ImageView image10;

    @FXML
    private ImageView image11;

    @FXML
    private ImageView image12;

    @FXML 
    private ImageView image13;

    @FXML
    private ImageView image14;

    @FXML
    private ImageView image15;

    @FXML 
    private ImageView image16;



    // Assuming this method is called during initialization or when you want to display the image
    public void displayImage() {
        try {
            // Load the image from the resources folder
            Image img1 = new Image(getClass().getResourceAsStream("/1.jpg"));
            Image img2 = new Image(getClass().getResourceAsStream("/2.jpg"));
            Image img3 = new Image(getClass().getResourceAsStream("/3.jpg"));
            Image img4 = new Image(getClass().getResourceAsStream("/4.jpg"));
            Image img5 = new Image(getClass().getResourceAsStream("/5.jpg"));
            Image img6 = new Image(getClass().getResourceAsStream("/6.jpg"));
            Image img7 = new Image(getClass().getResourceAsStream("/7.jpg"));
            Image img8 = new Image(getClass().getResourceAsStream("/8.jpg"));
            Image img9 = new Image(getClass().getResourceAsStream("/9.jpg"));
            Image img10 = new Image(getClass().getResourceAsStream("/10.jpg"));
            Image img11 = new Image(getClass().getResourceAsStream("/11.jpg"));
            Image img12 = new Image(getClass().getResourceAsStream("/12.jpg"));
            Image img13 = new Image(getClass().getResourceAsStream("/13.jpg"));
            Image img14 = new Image(getClass().getResourceAsStream("/14.jpg"));
            Image img15 = new Image(getClass().getResourceAsStream("/15.jpg"));
            Image img16 = new Image(getClass().getResourceAsStream("/16.jpg"));

            // Set the loaded image to the ImageView
            image1.setImage(img1);
            image2.setImage(img2);
            image3.setImage(img3);
            image4.setImage(img4);
            image5.setImage(img5);
            image6.setImage(img6);
            image7.setImage(img7);
            image8.setImage(img8);
            image9.setImage(img9);
            image10.setImage(img10);
            image11.setImage(img11);
            image12.setImage(img12);
            image13.setImage(img13);
            image14.setImage(img14);
            image15.setImage(img15);
            image16.setImage(img16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    void borrowBookBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("borrowBook.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Borrow book");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void browseBookBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("browseBook.fxml"));
            Parent root = loader.load();

            // Get the controller associated with the loaded FXML
            browseBook controller = loader.getController();

            // Call the displayAllBooks() method to fetch and display all books
            controller.displayAllBooks();

            Stage stage = new Stage();
            stage.setTitle("Browse Books");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void returnBookBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("returnBook.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Return Book");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    void searchBooksButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("searchedBook.fxml"));
            Parent root = loader.load();

            // Get the controller associated with the loaded FXML
            searchedBook controller = loader.getController();

            // Pass the search query to the controller
            controller.searchBooks(searchBookTextField.getText());

            Stage stage = new Stage();
            stage.setTitle("Search Results");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void viewAccountBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userInfo.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("User Information");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}