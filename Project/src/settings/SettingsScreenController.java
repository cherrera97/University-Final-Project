package settings;

import data.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import login.LoginScreenController;
import login.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingsScreenController implements Initializable {

    @FXML
    Label userFullNameLabel;
    @FXML
    TextField emailTextField;
    @FXML
    PasswordField passwordTextField;
    @FXML
    ImageView profilePicImageView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userFullNameLabel.setText(LoginScreenController.userEmail);
        emailTextField.setText(LoginScreenController.userEmail);
        setProfilePic(profilePicImageView);
    }

    public static void setProfilePic(ImageView profilePicImageView) {
        // if user has already set a profile pic, use that instead of default jimmy fallon pic
        if (new File("src\\images\\profile-pic.jpg").exists()) {
            Image image = new Image(new File("src\\images\\profile-pic.jpg").toURI().toString());
            profilePicImageView.setImage(image);
        } else {
            Image image = new Image(new File("src\\images\\jimmy-fallon.png").toURI().toString());
            profilePicImageView.setImage(image);
        }
    }

    public void submitPassStart(ActionEvent event) throws IOException {
        System.out.println("submit new pass button clicked");
        SQL.updateUser(LoginScreenController.userEmail, passwordTextField.getText(), emailTextField.getText());
    }

    public void chooseFileStart(ActionEvent event) throws IOException {
        System.out.println("choose profile pic button clicked");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter
                ("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);
        Path target = Paths.get("src\\images\\profile-pic.jpg");
        Files.copy(Paths.get(file.getAbsolutePath()), target, StandardCopyOption.REPLACE_EXISTING);

        setProfilePic(profilePicImageView);
    }

    public void overviewStart(ActionEvent event) throws IOException {
        System.out.println("overview button clicked");
        Main.switchScene(event, "/home/MainScreen.fxml");
    }

    public void accountsStart(ActionEvent event) throws IOException {
        System.out.println("accounts button clicked");
        Main.switchScene(event, "/accounts/AccountScreen.fxml");
    }

    public void budgetStart(ActionEvent event) throws IOException {
        System.out.println("settings button clicked");
        Main.switchScene(event, "/budget/BudgetScreen.fxml");
    }

    public void spendingStart(ActionEvent event) throws IOException {
        System.out.println("spending button clicked");
        Main.switchScene(event, "/spending/SpendingScreen.fxml");
    }

    public void transactionsStart(ActionEvent event) throws IOException {
        System.out.println("transactions button clicked");
        Main.switchScene(event, "/transactions/TransactionsScreen.fxml");
    }

    public void signOut(ActionEvent event) throws IOException, SQLException {
        System.out.println("signout button clicked");
        SQL.sqlConnection.close();
        Main.switchScene(event, "/login/LoginScreen.fxml");
        SQL.getConnection();
    }

}
