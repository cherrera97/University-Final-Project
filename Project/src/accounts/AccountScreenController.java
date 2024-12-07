package accounts;

import com.mysql.cj.log.Log;
import com.plaid.client.response.Account;
import com.plaid.client.response.LiabilitiesGetResponse;
import com.plaid.client.response.TransactionsGetResponse;
import data.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import login.LoginScreenController;
import login.Main;
import data.Plaid;
import settings.SettingsScreenController;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

public class AccountScreenController implements Initializable {

    @FXML
    Label userFullNameLabel;
    @FXML
    ImageView profilePicImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userFullNameLabel.setText(LoginScreenController.userEmail);
        SettingsScreenController.setProfilePic(profilePicImageView);
    }

    public void plaidImportStart(ActionEvent event) throws IOException, ParseException {
        System.out.println("plaid button clicked");

        // populate database with plaid accounts
        List<Account> accounts = Plaid.getPlaidAccounts();
        ;
        for (int i = 0; i < accounts.size(); i++) {
            String email = LoginScreenController.userEmail;
            String accountID = accounts.get(i).getAccountId();
            String accountType = accounts.get(i).getType();
            String issuer = accounts.get(i).getName();
            String dueDate = null;
            Account.Balances accountBalance = accounts.get(i).getBalances();
            double balance = accountBalance.getCurrent();
            // adjust balance based on accountType for proper displaying
            if (accountType.equals("credit") || accountType.equals("loan"))
                balance = -balance;
            SQL.insertUserAccount(email, accountID, accountType, issuer, balance, dueDate);
        }

        // add due dates to liability accounts in database
        List<LiabilitiesGetResponse.CreditCardLiability> liabilities = Plaid.getPlaidLiabilities();
        for (int i = 0; i < liabilities.size(); i++) {
            String accountID = liabilities.get(i).getAccountId();
            String dueDate = liabilities.get(i).getNextPaymentDueDate();
            SQL.updateAccountDueDate(accountID, dueDate);
        }

        // populate database with plaid transactions
        List<TransactionsGetResponse.Transaction> transactions = Plaid.getPlaidTransactions();
        ;
        for (int i = 0; i < transactions.size(); i++) {
            String email = LoginScreenController.userEmail;
            String accountID = transactions.get(i).getAccountId();
            String transactionID = transactions.get(i).getTransactionId();
            String name = transactions.get(i).getName();
            String category = transactions.get(i).getCategory().get(0); // take only first category, not sub-categories
            double amount = transactions.get(i).getAmount();
            String date = transactions.get(i).getDate();
            SQL.insertAccountTransaction(email, accountID, transactionID, name, amount, date, category);
        }
    }

    public void plaidLoginStart(ActionEvent event) throws IOException {
        System.out.println("plaid login button clicked");
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI("http://localhost:8000");
            desktop.browse(oURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void plaidHyperLinkStart(ActionEvent event) throws IOException {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI("https://plaid.com/how-it-works-for-consumers/");
            desktop.browse(oURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAccountsStart(ActionEvent event) throws IOException {
        System.out.println("delete accounts button clicked");
        SQL.deleteUserAccounts(LoginScreenController.userEmail);
    }

    public void overviewStart(ActionEvent event) throws IOException {
        System.out.println("overview button clicked");
        Main.switchScene(event, "/home/MainScreen.fxml");
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
