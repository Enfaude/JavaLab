package pwr.java;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    static Locale localeUK = new Locale("en", "GB");
    static Locale localeUS = new Locale("en", "US");
    static Locale localePL = new Locale("pl", "PL");
    public static final ResourceBundle bundleUK = ResourceBundle.getBundle("texts", localeUK);
    public static final ResourceBundle bundleUS = ResourceBundle.getBundle("texts", localeUS);
    public static final ResourceBundle bundlePL = ResourceBundle.getBundle("texts", localePL);
    public static ResourceBundle defaultBundle = ResourceBundle.getBundle("texts", Locale.getDefault());
    static Scene mainScene;
    static Stage secondStage = new Stage();
    public static ObservableList<IElement> contextList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("guiBase.fxml"), defaultBundle);
        primaryStage.setTitle(defaultBundle.getString("title"));
        mainScene = new Scene(root, 600, 400);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public static void refresh() throws IOException {
        defaultBundle = ResourceBundle.getBundle("texts", Locale.getDefault());
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("guiBase.fxml"), defaultBundle);
        Stage primaryStage = (Stage) mainScene.getWindow();
        primaryStage.close();
        mainScene.setRoot(loader.load());
        secondStage.setScene(mainScene);
        secondStage.show();
    }


}
