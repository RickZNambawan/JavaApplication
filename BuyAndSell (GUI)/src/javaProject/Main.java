package javaProject;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

    @Override
    public void start(Stage mainWindow) throws Exception{
        Parent mainScene = FXMLLoader.load(getClass().getResource("SplashScreenFXML.fxml"));
        mainWindow.initStyle(StageStyle.UNDECORATED);
        mainWindow.setScene(new Scene(mainScene));
        mainWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
