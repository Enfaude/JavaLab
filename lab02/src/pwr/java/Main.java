package pwr.java;

import com.sun.org.apache.bcel.internal.util.ClassPath;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Enumeration;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    Locale localeUK = new Locale("en", "GB");
    Locale localeUS = new Locale("en", "US");
    Locale localePL = new Locale("pl", "PL");
    ResourceBundle bundleUK = ResourceBundle.getBundle("texts", localeUK);
    ResourceBundle bundleUS = ResourceBundle.getBundle("texts", localeUS);
    ResourceBundle bundlePL = ResourceBundle.getBundle("texts", localePL);

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("guiBase.fxml"), bundlePL);
        primaryStage.setTitle("Java lab sorting app");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
