package worth.client.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import worth.client.Const;
import worth.client.tcp.Commands;
import worth.client.tcp.Connection;

public class GUI extends Application {

    Scene scene;

    public static void show() {
        launch("");
    }

    public void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            scene.setRoot((Parent) loader.load());
            LoginController controller = loader.<LoginController>getController();
            controller.init(this);
        } catch (IOException e) {
            if (Const.DEBUG)
                e.printStackTrace();
        }
    }

    public void showMenu(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            scene.setRoot((Parent) loader.load());
            MenuController controller = loader.<MenuController>getController();
            controller.init(scene, username);
        } catch (IOException e) {
            if (Const.DEBUG)
                e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(new VBox());
        showLogin();
        stage.setTitle("WORTH Client");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        if(Connection.isConnected()) Commands.logout();
        System.exit(0);
    }
}