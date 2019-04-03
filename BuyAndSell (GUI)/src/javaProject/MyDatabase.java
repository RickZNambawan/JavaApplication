package javaProject;

import java.sql.*;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MyDatabase {

    private Connection conn = null;

    public Connection connectDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:myDatabase.db");
        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return conn;
    }

    public void addUser(int generatedUserId, TextField txtFirstName, TextField txtLastName, TextField txtEmail, TextField txtUserName, PasswordField txtPassword) throws SQLException {
        Connection conn = connectDatabase();
        conn.setAutoCommit(false);
        Statement state = conn.createStatement();
        state.executeUpdate("INSERT INTO accountDetails (userId, firstName, lastName, emailAdd, username, password) VALUES ( "
                + generatedUserId + ", '" + txtFirstName.getText() + "', '" + txtLastName.getText() + "', '"
                + txtEmail.getText() + "', '" + txtUserName.getText() + "', '" + txtPassword.getText() + "')");
        state.close();
        conn.commit();
        conn.close();
        System.out.println("User Added!");
    }

}
