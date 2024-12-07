package data;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class Transaction {
    @FXML
    private SimpleStringProperty name;
    @FXML
    private SimpleDoubleProperty amount;
    @FXML
    private SimpleStringProperty date;
    @FXML
    private SimpleIntegerProperty transactionNum;

    public Transaction(int transactionNum, String name, double amount, String date) {
        this.transactionNum = new SimpleIntegerProperty(transactionNum);
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleDoubleProperty(amount);
        this.date = new SimpleStringProperty(date);
    }

    public int getTransactionNum() {
        return transactionNum.get();
    }

    public SimpleIntegerProperty transactionNumProperty() {
        return transactionNum;
    }

    public void setTransactionNum(int transactionNum) {
        this.transactionNum.set(transactionNum);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

}
