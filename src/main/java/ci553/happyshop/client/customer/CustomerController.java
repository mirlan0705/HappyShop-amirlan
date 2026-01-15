package ci553.happyshop.client.customer;
import ci553.happyshop.client.Login.LoginController;
import ci553.happyshop.client.Login.LoginModel;
import ci553.happyshop.client.Login.LoginView;
import ci553.happyshop.utility.WindowBounds;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerController {
    public CustomerModel cusModel;

    public void doAction(String action) throws SQLException, IOException {
        switch (action) {
            case "Search":
                cusModel.search();
                break;
            case "Add to Trolley":
                cusModel.addToTrolley();
                break;
            case "Cancel":
                cusModel.cancel();
                break;
            case "Sign In":
                openLoginPopup();
                break;
            case "Checkout":
                cusModel.checkOut();
                break;
            case "OK & Close":
                cusModel.closeReceipt();
                break;
        }

    }
    private void openLoginPopup() {
        // popup Stage
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL); // simple modal popup

        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController();
        LoginModel loginModel = new LoginModel(cusModel.databaseRW); // reuse same DatabaseRW

        loginView.loginController = loginController;
        loginController.loginModel = loginModel;
        loginModel.loginView = loginView;
        loginModel.databaseRW = cusModel.databaseRW;

        loginView.start(popup); // LoginView calls popup.show() internally
    }


}
