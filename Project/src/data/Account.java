package data;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class Account {
    @FXML
    private SimpleStringProperty accountType;
    @FXML
    private SimpleStringProperty issuer;
    @FXML
    private SimpleDoubleProperty balance;
    @FXML
    private SimpleIntegerProperty accountNum;

    public Account(int accountNum, String accountType, String issuer, double balance) {
        this.accountType = new SimpleStringProperty(accountType);
        this.issuer = new SimpleStringProperty(issuer);
        this.balance = new SimpleDoubleProperty(balance);
        this.accountNum = new SimpleIntegerProperty(accountNum);
    }

    public String getAccountType() {
        return accountType.get();
    }

    public SimpleStringProperty accountTypeProperty() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType.set(accountType);
    }

    public String getIssuer() {
        return issuer.get();
    }

    public SimpleStringProperty issuerProperty() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer.set(issuer);
    }

    public double getBalance() {
        return balance.get();
    }

    public SimpleDoubleProperty balanceProperty() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    public int getAccountNum() {
        return accountNum.get();
    }

    public SimpleIntegerProperty accountNumProperty() {
        return accountNum;
    }

    public void setAccountNum(int accountNum) {
        this.accountNum.set(accountNum);
    }
}
