package login;

import data.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {
    public TextField emailTextField;
    public TextField passTextField;
    public static String userEmail;

    public void loginButton(ActionEvent event) throws IOException {
        System.out.println("login clicked");
        String userEmail = emailTextField.getText();
        String userPass = passTextField.getText();

        boolean accountVerified = SQL.verifyUser(userEmail, userPass);
        if (accountVerified) {
            System.out.println("account verified. move to main scene");
            this.userEmail = userEmail;
            Main.switchScene(event, "/home/MainScreen.fxml");
        } else {
            Stage popupwindow = new Stage();
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("Login Error!");

            Label label1 = new Label("Wrong username or password.");
            Button button1 = new Button("Okay");
            button1.setOnAction(e -> popupwindow.close());

            VBox layout = new VBox(10);
            layout.getChildren().addAll(label1, button1);
            layout.setAlignment(Pos.CENTER);

            Scene scene1 = new Scene(layout, 250, 200);
            popupwindow.setScene(scene1);
            popupwindow.showAndWait();
        }
    }

    public void signupStart(ActionEvent event) throws IOException {
        System.out.println("signupbutton page button clicked");
        Main.switchScene(event, "SignupScreen.fxml");
    }

    public void clearButton() {
        System.out.println("clear button clicked");
        emailTextField.clear();
        passTextField.clear();
    }

    public void forgotpassButton(ActionEvent actionEvent) {
        System.out.println("forgot password button clicked");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}