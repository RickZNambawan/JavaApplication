package javaProject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class SplashScreen implements Initializable{

    @FXML
    private AnchorPane splashScreenPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Sleeper().start();
    }

    class Sleeper extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        AnchorPane root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("LoginFormFXML.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Scene mainScene = new Scene(root);
                        Stage mainWindow = new Stage();
                        mainWindow.setTitle("LOG IN");
                        mainWindow.setScene(mainScene);
                        mainWindow.show();

                        splashScreenPane.getScene().getWindow().hide();
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
