/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthe;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.naming.NamingException;

/**
 *
 * @author watan
 */
public class loginController implements Initializable {

    @FXML
    private Label lblUser;
    @FXML
    private Label lblPass;
    @FXML
    private Label status;
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtPass;
    @FXML
    private Button submit;

    public void Login(ActionEvent event) throws IOException, SQLException, NamingException {
        UserBean userBean = new UserBean();
        String name = txtUser.getText();
        String password = txtPass.getText();

        if (userBean.checkUser(name, password)) {
            System.out.println("Login Successful");
            User user = new User();
            user.setName(name);
            Parent root = FXMLLoader.load(getClass().getResource("sleep.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else {
            status.setText("Login Failed.  Invalid Username/Password");
        }
    }

    // pressing enter key in a textfield, runs the Login method
    public void onEnter() throws NamingException, IOException, SQLException {
        ActionEvent event = null;

        Login(event);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtUser.requestFocus();
            }
        });

    }
}
