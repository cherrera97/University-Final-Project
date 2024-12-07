package home;

import data.Account;
import data.Alert;
import data.SQL;
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
import java.math.RoundingMode;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML
    private Label userFullNameLabel, totalBalanceLabel, numAccountsLabel, totalDebtLabel;
    @FXML
    ImageView profilePicImageView;

    @FXML
    private TableView<Account> accountTableView;
    @FXML
    private TableView<Alert> alertsTableView;

    @FXML
    private TableColumn<Account, String> accountTypeCol;
    @FXML
    private TableColumn<Account, String> issuerCol;
    @FXML
    private TableColumn<Account, Double> balanceCol;
    @FXML
    private TableColumn<Account, Integer> numAccountsCol;

    @FXML
    private TableColumn<Alert, String> alertTypeCol;
    @FXML
    private TableColumn<Alert, String> alertIssuerCol;
    @FXML
    private TableColumn<Alert, Double> alertBalanceCol;
    @FXML
    private TableColumn<Alert, String> alertDueDateCol;

    ResultSet userInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SettingsScreenController.setProfilePic(profilePicImageView);

        int numAccounts = 0;
        double totalBalance = 0;
        double totalDebt = 0;
        userInfo = SQL.getAllUserAccounts(LoginScreenController.userEmail);

        // accounts table columns
        accountTypeCol.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        issuerCol.setCellValueFactory(new PropertyValueFactory<>("issuer"));
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        numAccountsCol.setCellValueFactory(new PropertyValueFactory<>("accountNum"));
        // alerts table columns
        alertTypeCol.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        alertIssuerCol.setCellValueFactory(new PropertyValueFactory<>("issuer"));
        alertBalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        alertDueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        // populate both tables
        ObservableList<Account> accountsRow = FXCollections.observableArrayList();
        ObservableList<Alert> alertsRow = FXCollections.observableArrayList();
        try {
            while (userInfo.next()) {
                numAccounts++;
                // add accounts to accounts table
                accountsRow.add(new Account(numAccounts, userInfo.getString(3),
                        userInfo.getString(4), userInfo.getDouble(5)));
                // make debt balances show as negative number
                if (userInfo.getString(3).equals("credit") || userInfo.getString(3).equals("loan")) {
                    totalDebt = totalDebt - userInfo.getDouble(5);
                    // add account with due date to alerts table
                    if (userInfo.getString(6) != null)
                        alertsRow.add(new Alert(userInfo.getString(3), userInfo.getString(4),
                                userInfo.getDouble(5), userInfo.getString(6)));
                } else
                    totalBalance = totalBalance + userInfo.getDouble(5);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        accountTableView.setItems(accountsRow);
        alertsTableView.setItems(alertsRow);

        // set values
        userFullNameLabel.setText(LoginScreenController.userEmail);
        numAccountsLabel.setText(String.valueOf(numAccounts));
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);
        totalBalance = Double.parseDouble(df.format(totalBalance));
        totalDebt = Double.parseDouble(df.format(totalDebt));
        totalBalanceLabel.setText("$ " + totalBalance);
        totalDebtLabel.setText("$ -" + totalDebt);
    }

    public void accountsStart(ActionEvent event) throws IOException {
        System.out.println("accounts button clicked");
        Main.switchScene(event, "/accounts/AccountScreen.fxml");
    }

    public void budgetStart(ActionEvent event) throws IOException {
        System.out.println("accounts button clicked");
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
