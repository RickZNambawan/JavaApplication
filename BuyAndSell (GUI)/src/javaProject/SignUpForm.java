package javaProject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUpForm extends AlertBoxMessage {

    private MyDatabase db = new MyDatabase();

    @FXML
    private TextField txtFirstName, txtLastName, txtEmail, txtUserName;
    @FXML
    private PasswordField txtPassword, txtConfirmPassword;

    @FXML
    public void submitButton() throws SQLException {
        if (txtFirstName.getText().equals("")) {
            alertBox("FIRST NAME IS REQUIRED FIELD!", "WARNING: REQUIRED FIELDS", new Alert(Alert.AlertType.WARNING));
        } else if(txtLastName.getText().equals("")) {
            alertBox("LAST NAME IS REQUIRED FIELD!", "WARNING: REQUIRED FIELDS", new Alert(Alert.AlertType.WARNING));
        }  else if(txtEmail.getText().equals("")) {
            alertBox("EMAIL ADDRESS IS REQUIRED FIELD!", "WARNING: REQUIRED FIELDS", new Alert(Alert.AlertType.WARNING));
        }  else if(txtUserName.getText().equals("")) {
            alertBox("USERNAME IS REQUIRED FIELD!", "WARNING: REQUIRED FIELDS", new Alert(Alert.AlertType.WARNING));
        }  else if(txtPassword.getText().equals("")) {
            alertBox("PASSWORD IS REQUIRED FIELD!", "WARNING: REQUIRED FIELDS", new Alert(Alert.AlertType.WARNING));
        }  else if(!txtPassword.getText().equals(txtConfirmPassword.getText())) {
            alertBox("PASSWORD DOES NOT MATCHED!", "WARNING: REQUIRED FIELDS", new Alert(Alert.AlertType.WARNING));
        } else {
            Connection conn = db.connectDatabase();
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery("SELECT username FROM accountDetails WHERE username ='" + txtUserName.getText() + "'");

            if(rs.next()) {
                alertBox("USERNAME ALREADY EXIST! PLEASE TRY A NEW ONE!", "WARNING: USERNAME EXISTED", new Alert(Alert.AlertType.WARNING));
            }

            else if(!rs.next()) {
                int generatedUserId = (int) (Math.random() * 1000) + 1;
                db.addUser(generatedUserId, txtFirstName, txtLastName, txtEmail, txtUserName, txtPassword);
                txtFirstName.setText("");
                txtLastName.setText("");
                txtEmail.setText("");
                txtUserName.setText("");
                txtPassword.setText("");
                txtConfirmPassword.setText("");
                alertBox("USER ADDED SUCCESSFULLY!", "YOU CREATED AN ACCOUNT!", new Alert(Alert.AlertType.CONFIRMATION));
            }
        }
    }
 }
