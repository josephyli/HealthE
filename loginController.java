/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
    
    
    public void Login(ActionEvent event) throws IOException {
        if(txtUser.getText().equals("hello") && txtPass.getText().equals("world")){
            System.out.println("Login Successful");
            Parent root = FXMLLoader.load(getClass().getResource("sleep.fxml"));        
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        else{
            status.setText("Login Failed.  Invalid Username/Password");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
