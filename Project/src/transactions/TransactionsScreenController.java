package transactions;

import data.SQL;
import data.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import login.LoginScreenController;
import login.Main;
import settings.SettingsScreenController;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TransactionsScreenController implements Initializable {

    @FXML
    private Label userFullNameLabel;
    @FXML
    ImageView profilePicImageView;

    @FXML
    private TableView<Transaction> transactionTableView;

    @FXML
    private TableColumn<Transaction, Integer> numTransactionsCol;
    @FXML
    private TableColumn<Transaction, String> transactionNameCol;
    @FXML
    private TableColumn<Transaction, Double> amountCol;
    @FXML
    private TableColumn<Transaction, String> dateCol;

    ResultSet transactionInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SettingsScreenController.setProfilePic(profilePicImageView);

        int numTransactions = 0;
        transactionInfo = SQL.getAllUserTransactions(LoginScreenController.userEmail);

        numTransactionsCol.setCellValueFactory(new PropertyValueFactory<>("transactionNum"));
        transactionNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        // populate table
        ObservableList<Transaction> transactionsRow = FXCollections.observableArrayList();
        try {
            while (transactionInfo.next()) {
                // table
                numTransactions++;
                transactionsRow.add(new Transaction(numTransactions, transactionInfo.getString(3),
                        transactionInfo.getDouble(4), transactionInfo.getString(5)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        transactionTableView.setItems(transactionsRow);
        userFullNameLabel.setText(LoginScreenController.userEmail);
    }

    public void overviewStart(ActionEvent event) throws IOException {
        System.out.println("overview button clicked");
        Main.switchScene(event, "/home/MainScreen.fxml");
    }

    public void accountsStart(ActionEvent event) throws IOException {
        System.out.println("accounts button clicked");
        Main.switchScene(event, "/accounts/AccountScreen.fxml");
    }

    public void spendingStart(ActionEvent event) throws IOException {
        System.out.println("spending button clicked");
        Main.switchScene(event, "/spending/SpendingScreen.fxml");
    }

    public void budgetStart(ActionEvent event) throws IOException {
        System.out.println("settings button clicked");
        Main.switchScene(event, "/budget/BudgetScreen.fxml");
    }

    public void settingsStart(ActionEvent event) throws IOException {
        System.out.println("settings button clicked");
        Main.switchScene(event, "/settings/SettingsScreen.fxml");
    }

    public void signOut(ActionEvent event) throws IOException, SQLException {
        System.out.println("signout button clicked");
        SQL.sqlConnection.close();
        Main.switchScene(event, "/login/LoginScreen.fxml");
        SQL.getConnection();
    }

}
