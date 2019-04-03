package javaProject;

import javafx.scene.control.Alert;

public class AlertBoxMessage {
    protected void alertBox(String message, String title, Alert alertType) {
        Alert alertMessageBox = alertType;
        alertMessageBox.setTitle(title);
        alertMessageBox.setContentText(message);
        alertMessageBox.setHeaderText(null);
        alertMessageBox.showAndWait();
    }
}
