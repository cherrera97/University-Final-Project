// Class that holds all the methods for interaction with database.
package data;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

public class SQL {

    public static Connection sqlConnection;
    public static PreparedStatement sqlStatement;

    static boolean accountCreationFailed = false;

    public static Connection getConnection() {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String database = "mydb";
            String url = "jdbc:mysql://localhost:3306/" + database;
            String username = "root";
            String password = "";

            Class.forName(driver);
            sqlConnection = DriverManager.getConnection(url, username, password);
            System.out.println("Connecting to " + url + " with " + driver);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Connected.");
        }
        return sqlConnection;
    }

    public static boolean verifyUser(String email, String pass) {
        System.out.println("Retrieving user file " + email + " for verification.");
        boolean verified = false;
        try {
            String query = "SELECT pass FROM user_table WHERE email = ?";
            sqlStatement = sqlConnection.prepareStatement(query);
            sqlStatement.setString(1, email);

            ResultSet result = sqlStatement.executeQuery();
            result.next();
            String userInfo = result.getString(1);

            if (userInfo.equals(pass)) {
                System.out.println("pass verified.");
                verified = true;
            } else {
                System.out.println("pass not verified.");
                verified = false;
            }
        } catch (Exception e) {
            System.out.println("user not found.");
        }
        return verified;
    }

    public static void insertUser(String email, String pass) {
        System.out.println("Inserting user file by email: " + email);
        try {
            String query = "INSERT INTO user_table (email, pass) VALUES (?, ?);";
            sqlStatement = sqlConnection.prepareStatement(query);
            sqlStatement.setString(1, email);
            sqlStatement.setString(2, pass);
            sqlStatement.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException exception) {
            System.out.println("User " + email + " already exists.");
            accountCreationFailed = true;

            // alert user account already exists
            Stage popupwindow = new Stage();
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("Sign Up Error!");

            Label label1 = new Label("Account with email '" + email + "' already exists.");
            Button button1 = new Button("Okay");
            button1.setOnAction(e -> popupwindow.close());

            VBox layout = new VBox(10);
            layout.getChildren().addAll(label1, button1);
            layout.setAlignment(Pos.CENTER);

            Scene scene1 = new Scene(layout, 500, 200);
            popupwindow.setScene(scene1);
            popupwindow.showAndWait();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            System.out.println("Inserting done.");
            // check if account now exists, then alert user account created successfully
            if (!accountCreationFailed) {
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("New Account");

                Label label1 = new Label("Account created successfully!\nYou can now log in.");
                Button button1 = new Button("Okay");
                button1.setOnAction(e -> popupwindow.close());

                VBox layout = new VBox(10);
                layout.getChildren().addAll(label1, button1);
                layout.setAlignment(Pos.CENTER);

                Scene scene1 = new Scene(layout, 250, 200);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            }
            accountCreationFailed = false;
        }
    }

    public static void updateUser(String email, String pass, String newEmail) {
        System.out.println("Updating user by email: " + email);
        try {
            String query = "UPDATE user_table SET email = ?, pass = ? WHERE email = ?";
            sqlStatement = sqlConnection.prepareStatement(query);
            sqlStatement.setString(1, newEmail);
            sqlStatement.setString(2, pass);
            sqlStatement.setString(3, email);
            sqlStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            System.out.println("Updating done.");

            Stage popupwindow = new Stage();
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("Account");

            Label label1 = new Label("Account password changed!");
            Button button1 = new Button("Okay");
            button1.setOnAction(e -> popupwindow.close());

            VBox layout = new VBox(10);
            layout.getChildren().addAll(label1, button1);
            layout.setAlignment(Pos.CENTER);

            Scene scene1 = new Scene(layout, 250, 200);
            popupwindow.setScene(scene1);
            popupwindow.showAndWait();
        }
    }


    public static void insertUserAccount(String email, String accountID, String accountType,
                                         String issuer, double balance, String dueDate) {
        System.out.println("Inserting user account by email and account type: " + email + " + " + accountType);
        try {
            String query = "INSERT INTO user_accounts (email, accountID, accountType, issuer, balance, dueDate)" +
                    "values (?, ?, ?, ?, ?, ?)";
            sqlStatement = sqlConnection.prepareStatement(query);
            sqlStatement.setString(1, email);
            sqlStatement.setString(2, accountID);
            sqlStatement.setString(3, accountType);
            sqlStatement.setString(4, issuer);
            sqlStatement.setDouble(5, balance);
            sqlStatement.setString(6, dueDate);
            sqlStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("User account " + accountType + " already exists.");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            System.out.println("Inserting done.");
        }
    }

    public static void updateAccountDueDate(String accountID, String dueDate) {
        System.out.println("Updating user account by accountID for due date: " + accountID + " + " + dueDate);
        try {
            String query = "UPDATE user_accounts SET dueDate = ? WHERE accountID = ?";
            sqlStatement = sqlConnection.prepareStatement(query);
            sqlStatement.setString(1, dueDate);
            sqlStatement.setString(2, accountID);
            sqlStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            System.out.println("Updating done.");
        }
    }

    public static void insertAccountTransaction(String email, String accountID, String transactionID,
                                                String name, double amount, String date, String category) {
        System.out.println("Inserting user account transaction by email and name: " + email + " + " + name);
        try {
            String query = "INSERT INTO account_transactions (accountID, transactionID, name, amount, date, email, category)" +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            sqlStatement = sqlConnection.prepareStatement(query);
            sqlStatement.setString(1, accountID);
            sqlStatement.setString(2, transactionID);
            sqlStatement.setString(3, name);
            sqlStatement.setDouble(4, amount);
            sqlStatement.setString(5, date);
            sqlStatement.setString(6, email);
            sqlStatement.setString(7, category);
            sqlStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("User transaction " + name + " already exists.");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            System.out.println("Inserting done.");
        }
    }
    //    public static void updateUserAccount(String email, String accountType, String issuer, double balance) {
//        System.out.println("Updating user account by email and account type: " + email + " + " + accountType);
//        try {
//            String query = "UPDATE user_accounts SET accountType = ?, issuer = ?, balance = ? WHERE email = ?";
//            sqlStatement = sqlConnection.prepareStatement(query);
//            sqlStatement.setString(1, accountType);
//            sqlStatement.setString(2, issuer);
//            sqlStatement.setDouble(3, balance);
//            sqlStatement.setString(4, email);
//            sqlStatement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e);
//        } finally {
//            System.out.println("Updating done.");
//        }
//    }

    public static void deleteUserAccounts(String email) {
        System.out.println("Deleting user accounts and associated transactions by email: " + email);
        try {
            String query = "DELETE FROM account_transactions WHERE email = ?";
            sqlStatement = sqlConnection.prepareStatement(query);
            sqlStatement.setString(1, email);
            sqlStatement.executeUpdate();

            query = "DELETE FROM user_accounts WHERE email = ?";
            sqlStatement = sqlConnection.prepareStatement(query);
            sqlStatement.setString(1, email);
            sqlStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            System.out.println("Deleting done.");
        }
    }

    public static ResultSet getAllUserAccounts(String email) {
        ResultSet resultSet = null;
        try {
            String query = "select email, accountID, accountType, issuer, balance, dueDate from user_accounts where email = ?";
            sqlStatement = sqlConnection.prepareStatement(query);
            sqlStatement.setString(1, email);

            resultSet = sqlStatement.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return resultSet;
    }

    public static ResultSet getAllUserTransactions(String email) {
        ResultSet resultSet = null;
        try {
            String query = "select accountID, transactionID, name, amount, date, email, category from account_transactions" +
                    " where email = ?";
            PreparedStatement statement = sqlConnection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, email);

            resultSet = statement.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return resultSet;
    }

}
