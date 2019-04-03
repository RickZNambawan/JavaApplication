package javaProject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;

public class LoginForm extends AlertBoxMessage {

    private MyDatabase db = new MyDatabase();

    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;

    @FXML
    public void submitButton() throws IOException, SQLException {
        Connection conn = db.connectDatabase();
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM accountDetails WHERE username= " + "'" + txtUserName.getText() + "'" +
                "AND password= " + "'" + txtPassword.getText() + "'");

        do {
            if(!rs.next()) {
                txtUserName.setText("");
                txtPassword.setText("");
                alertBox("INVALID USERNAME OR PASSWORD! PLEASE TRY AGAIN", "INCORRECT CREDENTIALS", new Alert(Alert.AlertType.ERROR));
            } else {
                String nameOfUser = "";
                rs = state.executeQuery("SELECT firstName FROM accountDetails where username=" + "'" + txtUserName.getText() + "'");
                if(rs.next()){
                    nameOfUser = rs.getString("firstName");
                }

                alertBox("Welcome, " + nameOfUser + "!", "LOGIN SUCCESSFULLY!!", new Alert(Alert.AlertType.CONFIRMATION));
                Stage mainAppWindow = new Stage();
                mainAppWindow.setTitle("BANGBANG SHOP");
                mainAppWindow.setScene(new Scene(FXMLLoader.load(getClass().getResource("MainAppWindowFXML.fxml"))));
                mainAppWindow.show();
            }
        } while (rs.next());
    }

    @FXML
    public void signUpButton() throws IOException {
        Stage signUpFormWindow = new Stage();
        signUpFormWindow.initModality(Modality.APPLICATION_MODAL);
        signUpFormWindow.setTitle("Sign Up");
        signUpFormWindow.setScene(new Scene(FXMLLoader.load(getClass().getResource("SignUpFormFXML.fxml"))));
        signUpFormWindow.showAndWait();
    }

}
