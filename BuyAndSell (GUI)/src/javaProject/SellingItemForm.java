package javaProject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SellingItemForm extends AlertBoxMessage implements Initializable {

    private MyDatabase db = new MyDatabase();

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField txtUsername, txtItemName, txtItemPrice, txtQuantity;
    @FXML
    private ChoiceBox<String> txtItemCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            choiceBoxes();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void addImageButton() {
        Image image;
        ImageView imageView;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\RickZ\\Pictures"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpeg", "*.jpg", "*.JPG", "*.JPEG"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null) {
            image = new Image(selectedFile.toURI().toString());
            imageView = new ImageView(image);
            imageView.setFitWidth(220);
            imageView.setFitHeight(220);
            imageView.setPreserveRatio(true);
            borderPane.setCenter(imageView);
        }
    }

    @FXML
    public void addProductButton() throws SQLException {
        Connection conn = db.connectDatabase();
        Statement state = conn.createStatement();

        if (txtUsername.getText().equals("")) {
            alertBox("USERNAME IS REQUIRED FIELD!", "WARNING: REQUIRED FIELDS", new Alert(Alert.AlertType.WARNING));
        } else if (txtItemName.getText().equals("")) {
           alertBox("ITEM NAME IS REQUIRED FIELD!", "WARNING: REQUIRED FIELDS", new Alert(Alert.AlertType.WARNING));
        } else if (txtItemPrice.getText().equals("")) {
            alertBox("PRICE IS REQUIRED FIELD!", "WARNING: REQUIRED FIELDS", new Alert(Alert.AlertType.WARNING));
        } else if (txtQuantity.getText().equals("")) {
            alertBox("QUANTITY IS REQUIRED FIELD!", "WARNING: REQUIRED FIELDS", new Alert(Alert.AlertType.WARNING));
        } else {
            ResultSet rs = state.executeQuery("SELECT username FROM accountDetails WHERE username ='" + txtUsername.getText() + "'");
            if (rs.next()) {
                alertBox("PRODUCT SUCCESSFULLY ADDED!", "PRODUCT ADDED", new Alert(Alert.AlertType.CONFIRMATION));
                String currentUsername = txtUsername.getText();
                String userFirstName = "";
                int userItemNumber = (int) (Math.random() * 1000000000 + 1);
                ResultSet rs2 = state.executeQuery("SELECT firstName FROM accountDetails WHERE username=" + "'" + currentUsername + "'");

                while (rs2.next()) {
                    userFirstName = rs.getString("firstName");
                }

                state.executeUpdate("INSERT INTO itemsToSell (itemCategory, itemName, itemNumber, itemPrice, quantity, firstName) VALUES" +
                        "('" + txtItemCategory.getValue() + "'," + "'" + txtItemName.getText() + "', "  + userItemNumber + ", '" + txtItemPrice.getText()
                        + "'," + "'" + txtQuantity.getText() + "'," + "'" + userFirstName + "'" + ")");

                System.out.println("Product Added!");
                System.out.println(txtItemCategory.getValue());
                System.out.println(txtItemName.getText());
                System.out.println(userItemNumber);
                System.out.println(txtItemPrice.getText());
                System.out.println(txtQuantity.getText());
                System.out.println(userFirstName);

                txtQuantity.setText("");
                txtItemPrice.setText("");
                txtItemName.setText("");
                txtUsername.setText("");
            } else {
                alertBox("USERNAME IS NOT EXISTING ACCOUNT! PLEASE INPUT YOUR VALID USERNAME!", "WARNING: USERNAME UNKNOWN", new Alert(Alert.AlertType.WARNING));
            }
        }
    }

    @FXML
    private void choiceBoxes() throws SQLException {
        Connection conn = db.connectDatabase();
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM categories");
        txtItemCategory.setValue("computer");
        while (rs.next()) {
            String itemCategory = rs.getString("itemCategory");
            txtItemCategory.getItems().addAll(itemCategory);
        }
    }
}
