package spending;

import data.SQL;
import data.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
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

public class SpendingScreenController implements Initializable {

    @FXML
    private Label userFullNameLabel;
    @FXML
    ImageView profilePicImageView;

    @FXML
    private TableView<Transaction> transactionTableView;
    @FXML
    private PieChart categoryPieChart;

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

        ArrayList<String> categories = new ArrayList<>();
        ArrayList<Double> amounts = new ArrayList<>();

        // populate table
        ObservableList<Transaction> transactionsRow = FXCollections.observableArrayList();
        try {
            while (transactionInfo.next()) {
                // table
                numTransactions++;
                transactionsRow.add(new Transaction(numTransactions, transactionInfo.getString(3),
                        transactionInfo.getDouble(4), transactionInfo.getString(5)));

                // get transaction category & amounts for pie chart
                categories.add(transactionInfo.getString(7));
                amounts.add(transactionInfo.getDouble(4));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // make categories unique and add respective amounts for pie chart
        ArrayList<String> uniqueCategories = new ArrayList<>();
        ArrayList<Double> uniqueAmounts = new ArrayList<>();
        int i = 0;
        for (Object element : categories) {
            if (!uniqueCategories.contains(element)) {
                uniqueCategories.add(categories.get(i));
                uniqueAmounts.add(amounts.get(i));
            }
            i++;
        }

        // populate pie chart
        int j = 0;
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            while (j < uniqueCategories.size()) {
                // omit Transfer and Payment categories
                if (!uniqueCategories.get(j).contains("Transfer") && !uniqueCategories.get(j).contains("Payment")) {
                    pieChartData.add(new PieChart.Data(uniqueCategories.get(j), uniqueAmounts.get(j)));
                }
                j++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        transactionTableView.setItems(transactionsRow);
        categoryPieChart.setData(pieChartData);
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

    public void budgetStart(ActionEvent event) throws IOException {
        System.out.println("settings button clicked");
        Main.switchScene(event, "/budget/BudgetScreen.fxml");
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
