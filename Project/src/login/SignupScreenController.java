package login;

import data.SQL;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignupScreenController {
    public TextField emailTextField;
    public TextField passTextField;

    public void signupButton(ActionEvent event) {
        System.out.println("signup button clicked");
        String userEmail = emailTextField.getText();
        String userPass = passTextField.getText();
        SQL.insertUser(userEmail, userPass);
    }

    public void loginStart(ActionEvent event) throws IOException {
        System.out.println("login page button clicked");
        Main.switchScene(event, "LoginScreen.fxml");
    }

    public void clearButton() {
        System.out.println("clear button clicked");
        emailTextField.clear();
        passTextField.clear();
    }

    public void forgotpassButton(ActionEvent actionEvent) {
        System.out.println("forgot password button clicked");
    }

}