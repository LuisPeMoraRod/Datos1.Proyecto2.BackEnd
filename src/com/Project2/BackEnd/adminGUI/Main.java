package admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    Stage window;
    Scene loginPage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;
        window.initStyle(StageStyle.TRANSPARENT);
        window.centerOnScreen();

        Parent loginGUI = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        loginPage = new Scene(loginGUI, 1280, 776);

        window.setTitle("CookTime - Administrator");
        window.setScene(loginPage);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
