package login;

import data.SQL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {
    static Connection sqlConnection;

    public static void main(String[] args) {
        sqlConnection = SQL.getConnection();
        System.out.println(sqlConnection);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Login to Program");
        stage.setScene(scene);
        stage.show();
    }

    public static void switchScene(ActionEvent event, String fxml) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource(fxml));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
