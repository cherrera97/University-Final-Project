package budget;

import data.SQL;
import data.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BudgetScreenController implements Initializable {

    @FXML
    private Label userFullNameLabel;
    @FXML
    ImageView profilePicImageView;

    @FXML
    private PieChart categoryPieChart;

    @FXML
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;

    ResultSet transactionInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SettingsScreenController.setProfilePic(profilePicImageView);

        transactionInfo = SQL.getAllUserTransactions(LoginScreenController.userEmail);

        // get categories
        ArrayList<String> categories = new ArrayList<>();
        try {
            while (transactionInfo.next()) {
                // omit Payment/Transfer categories
                if (!transactionInfo.getString(7).equals("Payment") &&
                        !transactionInfo.getString(7).equals("Transfer"))
                    categories.add(transactionInfo.getString(7));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // make categories unique
        ArrayList<String> uniqueCategories = new ArrayList<>();
        int i = 0;
        for (Object element : categories) {
            if (!uniqueCategories.contains(element)) {
                uniqueCategories.add(categories.get(i));
            }
            i++;
        }

        // set checkboxes to unique categories
        if (!uniqueCategories.isEmpty())
            checkBox1.setText(uniqueCategories.remove(0));
        if (!uniqueCategories.isEmpty())
            checkBox2.setText(uniqueCategories.remove(0));
        if (!uniqueCategories.isEmpty())
            checkBox3.setText(uniqueCategories.remove(0));
        if (!uniqueCategories.isEmpty())
            checkBox4.setText(uniqueCategories.remove(0));
        if (!uniqueCategories.isEmpty())
            checkBox5.setText(uniqueCategories.remove(0));

        userFullNameLabel.setText(LoginScreenController.userEmail);

    }

    public void populateChart() {
        ArrayList<String> essentialCategories = new ArrayList<>();

        // get essential/non-essential category choices from checkboxes
        if (checkBox1.isSelected() && !checkBox1.getText().isEmpty())
            essentialCategories.add(checkBox1.getText());
        if (checkBox2.isSelected() && !checkBox2.getText().isEmpty())
            essentialCategories.add(checkBox2.getText());
        if (checkBox3.isSelected() && !checkBox3.getText().isEmpty())
            essentialCategories.add(checkBox3.getText());
        if (checkBox4.isSelected() && !checkBox4.getText().isEmpty())
            essentialCategories.add(checkBox4.getText());
        if (checkBox5.isSelected() && !checkBox5.getText().isEmpty())
            essentialCategories.add(checkBox5.getText());

        // get total spend to calculate non essential spend later
        double totalSpend = 0;
        try {
            transactionInfo.first();
            while (transactionInfo.next()) {
                // have to omit Transfer/Payment categories again here
                if (!transactionInfo.getString(7).equals("Payment") &&
                        !transactionInfo.getString(7).equals("Transfer"))
                    totalSpend = totalSpend + transactionInfo.getDouble(4);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // get essential spend
        double essentialSpend = 0;
        double nonEssentialSpend = 0;
        try {
            for (String essentialCategory : essentialCategories) {
                transactionInfo.first();
                while (transactionInfo.next()) {
                    if (transactionInfo.getString(7).contains(essentialCategory)) {
                        essentialSpend = essentialSpend + (transactionInfo.getDouble(4));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        nonEssentialSpend = totalSpend - essentialSpend;

        // populate chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.add(new PieChart.Data("Essential Spend", essentialSpend));
        pieChartData.add(new PieChart.Data("Non-Essential Spend", nonEssentialSpend));

        categoryPieChart.setData(pieChartData);
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
        System.out.println("refresh button clicked");
        populateChart();
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
