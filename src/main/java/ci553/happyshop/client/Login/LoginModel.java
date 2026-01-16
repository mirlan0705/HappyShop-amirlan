package ci553.happyshop.client.Login;

import ci553.happyshop.client.customer.CustomerController;
import ci553.happyshop.client.customer.CustomerModel;
import ci553.happyshop.client.customer.CustomerView;
import ci553.happyshop.storageAccess.DatabaseRW;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.SQLException;

public class LoginModel {
    public DatabaseRW databaseRW;
    public LoginView loginView;
    public CustomerModel customerModel;



    public LoginModel(DatabaseRW databaseRW) {
        this.databaseRW = databaseRW;
    }

    public void signIn(String email, String password) throws SQLException {

        boolean valid = databaseRW.checkUser(email, password);

        if (!valid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email or password");
            alert.showAndWait();
            return;
        }

        if (customerModel != null) {
            // popup login
            customerModel.setUsername(email);   // updates label immediately
            loginView.closeWindow();
        } else {
            // startup login
            loginView.closeWindow();
            launchCustomerClient(email);
        }
    }


    public void signUp() throws SQLException {

        TextInputDialog emailDialog = new TextInputDialog();
        emailDialog.setTitle("Create account");
        emailDialog.setHeaderText(null);
        emailDialog.setContentText("Email:");

        var emailResult = emailDialog.showAndWait();
        if (emailResult.isEmpty()) return;

        String email = emailResult.get().trim();
        if (email.isEmpty()) return;

        TextInputDialog passDialog = new TextInputDialog();
        passDialog.setTitle("Create account");
        passDialog.setHeaderText(null);
        passDialog.setContentText("Password:");

        var passResult = passDialog.showAndWait();
        if (passResult.isEmpty()) return;

        String password = passResult.get().trim();
        if (password.isEmpty()) return;

        boolean created = databaseRW.createUser(email, password);

        Alert alert;
        if (created) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Account created. You can now sign in.");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Email already exists.");
        }
        alert.showAndWait();
    }


    public void continueAsGuest() {
        // close login window
        loginView.closeWindow();

        // open customer window at fixed position
        launchCustomerClient("Guest");

    }

    private void launchCustomerClient(String username) {
        CustomerView cusView = new CustomerView();
        CustomerController cusController = new CustomerController();
        CustomerModel cusModel = new CustomerModel();
        cusModel.setUsername(username);


        cusView.cusController = cusController;
        cusController.cusModel = cusModel;
        cusModel.cusView = cusView;

        // reusing same database connection used by login
        cusModel.databaseRW = databaseRW;

        cusView.start(new Stage());
    }
}
