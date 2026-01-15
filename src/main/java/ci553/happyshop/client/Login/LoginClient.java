package ci553.happyshop.client.Login;
import ci553.happyshop.storageAccess.DatabaseRW;
import ci553.happyshop.storageAccess.DatabaseRWFactory;
import javafx.application.Application;
import javafx.stage.Stage;


public class LoginClient extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage window) {
        LoginView loginView = new LoginView();

        DatabaseRW databaseRW = DatabaseRWFactory.createDatabaseRW();

        LoginController loginController = new LoginController();
        LoginModel loginModel = new LoginModel(databaseRW);

        loginView.loginController = loginController;
        loginController.loginModel = loginModel;
        loginModel.loginView = loginView;
        loginModel.databaseRW = databaseRW;
        loginView.start(window);

    }
}
