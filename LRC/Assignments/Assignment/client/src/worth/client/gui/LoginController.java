package worth.client.gui;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import worth.client.utils.Utils;
import worth.client.utils.Const;
import worth.client.tcp.Commands;
import worth.client.tcp.Connection;
import worth.client.tcp.Response;

public class LoginController {
    @FXML
    protected VBox container = new VBox();

    GUI parent;

    @FXML
    protected TextField tfIp = new TextField();
    @FXML
    protected TextField tfUsr = new TextField();
    @FXML
    protected PasswordField tfPwd = new PasswordField();
    @FXML
    protected Text respText = new Text();

    public void init(GUI parent) {
        if(Const.DEBUG) {
            tfIp.setText(Const.IP);
            tfIp.setDisable(true);
        }
        this.parent = parent;
    }

    @FXML
    public void initialize() {}

    @FXML
    protected void loginAction(Event e) {
        Const.IP = tfIp.getText();
        if (!Connection.isConnected()) {
            if (!Connection.connect()) {
                Utils.showErrorAndExit("Cannot connect to the server");
            }
        }
        Response r = Commands.login(tfUsr.getText(), tfPwd.getText());
        if (r.getStatus()) {
            respText.setFill(Paint.valueOf("LIME"));
            respText.setText("OK");
            try {
                Utils.registerCallbackService(tfUsr.getText());
            } catch (RemoteException | NotBoundException e1) {
                if(Const.DEBUG) e1.printStackTrace();
                return;
            }
            parent.showMenu(tfUsr.getText());
        } else {
            respText.setFill(Paint.valueOf("RED"));
            respText.setText("Wrong username / password");
        }
    }
    @FXML
    protected void registerAction(Event e) {
        Const.IP = tfIp.getText();
        Response r = Utils.register(tfUsr.getText(), tfPwd.getText());
        if (r.getStatus()) {
            respText.setFill(Paint.valueOf("LIME"));
            respText.setText("Registration successful");
        } else {
            respText.setFill(Paint.valueOf("RED"));
            respText.setText(r.getMessage());
            return;
        }
        Utils.InfoAlert("Registration successful").showAndWait();
        loginAction(e);
    }
}
