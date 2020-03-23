package pwr.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    static Locale localeUK = new Locale("en", "GB");
    static Locale localeUS = new Locale("en", "US");
    static Locale localePL = new Locale("pl", "PL");
    public static ResourceBundle bundleUK = ResourceBundle.getBundle("texts", localeUK);
    public static ResourceBundle bundleUS = ResourceBundle.getBundle("texts", localeUS);
    public static ResourceBundle bundlePL = ResourceBundle.getBundle("texts", localePL);

    public static ResourceBundle activeBundle = bundleUK;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("guiBase.fxml"), activeBundle);
        primaryStage.setTitle(activeBundle.getString("title"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

}
