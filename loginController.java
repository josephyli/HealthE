/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthe;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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

    public void Login(ActionEvent event) throws IOException, SQLException, NamingException, NoSuchAlgorithmException, InvalidKeySpecException {
        UserBean userBean = new UserBean();
        String name = txtUser.getText();
        String originalPassword = txtPass.getText();
        String securedPassword = generateStorngPasswordHash(originalPassword);
        boolean matched = validatePassword(originalPassword, userBean.getHashedPassword(name));

        if (matched) {
            System.out.println("Login Successful");
            User user = new User();
            user.setName(name);
            showSleepTimeDialog(user);
        } else {
            status.setText("Login Failed.  Invalid Username/Password");
        }
    }

    public Stage showSleepTimeDialog(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "sleep.fxml"
                )
        );

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );

        SleepController controller
                = loader.<SleepController>getController();
        controller.initData(user);

        stage.show();

        return stage;
    }

    public void Register(ActionEvent event) throws IOException, SQLException, NamingException, NoSuchAlgorithmException, InvalidKeySpecException {
        UserBean userBean = new UserBean();
        String name = txtUser.getText();
        String originalPassword = txtPass.getText();
        String securedPassword = generateStorngPasswordHash(originalPassword);

        if (userBean.userExistsAlready(name)) {
            status.setText("Register Failed.  Username already exists.");
        } else if (originalPassword.equalsIgnoreCase("")) {
            status.setText("Register Failed.  Password cannot be blank.");
        } else {

            userBean.createNewUser(name, securedPassword);
            System.out.println("Registration Successful");
            User user = new User();
            user.setName(name);
            Parent root = FXMLLoader.load(getClass().getResource("sleep.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }

    // pressing enter key in a textfield, runs the Login method
    public void onEnter() throws NamingException, IOException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
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
        status.setText("Welcome! Please ensure your Postgres server is running");
    }

    ////////////////////////////////////////////////////////////////////////////
    // Hash/salt Code from Lokesh Gupta 
    // from http://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    private static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    ////////////////////////////////////////////////////////////////////////////
}
