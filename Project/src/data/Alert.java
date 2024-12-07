package data;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class Alert {
    @FXML
    private SimpleStringProperty accountType;
    @FXML
    private SimpleDoubleProperty balance;
    @FXML
    private SimpleStringProperty dueDate;
    @FXML
    private SimpleStringProperty issuer;

    public Alert(String accountType, String issuer, double balance, String dueDate) {
        this.accountType = new SimpleStringProperty(accountType);
        this.issuer = new SimpleStringProperty(issuer);
        this.balance = new SimpleDoubleProperty(balance);
        this.dueDate = new SimpleStringProperty(dueDate);
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

    public String getDueDate() {
        return dueDate.get();
    }

    public SimpleStringProperty dueDateProperty() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate.set(dueDate);
    }
}
