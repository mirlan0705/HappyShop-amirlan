package ci553.happyshop.client.Login;

import ci553.happyshop.utility.UIStyle;
import ci553.happyshop.utility.WinPosManager;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginView {
    public LoginController loginController;
    private final int WIDTH = UIStyle.loginWinWidth;
    private final int HEIGHT = UIStyle.loginWinHeight;
    private VBox root;
    private TextField emailField;
    private PasswordField passwordField;


    public void start(Stage window) {

        root = new VBox(12);
        root.setAlignment(Pos.CENTER);
        root.setStyle(UIStyle.loginRootStyle);

        VBox card = new VBox(10);
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(UIStyle.loginCardStyle);

        // Make the card fill most of the window width nicely
        double cardW = UIStyle.loginWinWidth - 36; // root padding ~18*2
        card.setPrefWidth(cardW);

        Label titleLabel = new Label("Sign in");
        titleLabel.setStyle(UIStyle.loginTitleStyle);

        Label subTitle = new Label("Sign in for faster checkout and order tracking.");
        subTitle.setStyle(UIStyle.loginSubTitleStyle);
        subTitle.setWrapText(true);
        subTitle.setMaxWidth(cardW - 10);

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle(UIStyle.loginInputStyle);
        emailField.setPrefHeight(34);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle(UIStyle.loginInputStyle);
        passwordField.setPrefHeight(34);

        Button btnSignIn = new Button("Sign In");
        btnSignIn.setStyle(UIStyle.loginPrimaryBtnStyle);
        btnSignIn.setPrefHeight(34);
        btnSignIn.setPrefWidth((cardW - 10) * 0.55);
        btnSignIn.setOnAction(this::buttonClicked);

        Button btnGuest = new Button("Continue as Guest");
        btnGuest.setStyle(UIStyle.loginSecondaryBtnStyle);
        btnGuest.setPrefHeight(34);
        btnGuest.setPrefWidth((cardW - 10) * 0.45);
        btnGuest.setOnAction(this::buttonClicked);

        HBox actionsRow = new HBox(10, btnSignIn, btnGuest);
        actionsRow.setAlignment(Pos.CENTER_LEFT);

        Button btnSignUp = new Button("Create an account");
        btnSignUp.setStyle(UIStyle.loginLinkBtnStyle);
        btnSignUp.setOnAction(this::buttonClicked);

        // spacing before link so it doesn't stick to buttons
        VBox linkBox = new VBox(2, btnSignUp);
        linkBox.setAlignment(Pos.CENTER_LEFT);

        card.getChildren().addAll(titleLabel, subTitle, emailField, passwordField, actionsRow, linkBox);

        root.getChildren().add(card);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        window.setScene(scene);
        window.setTitle("🛒 HappyShop — Sign in");
        WinPosManager.registerWindow(window, WIDTH, HEIGHT);
        window.setResizable(false);
        window.show();
    }


    private void buttonClicked(ActionEvent event) {
        sounds.SoundPlayer.playClick();

        Button btn = (Button) event.getSource();
        String action = btn.getText();

        try {
            handleAction(action);
        } catch (Exception e) {
            e.printStackTrace(); // so you SEE the real error in console
        }
    }

    private void handleAction(String action) throws SQLException {
        switch (action) {
            case "Sign In":
                loginController.loginModel.signIn(emailField.getText(),
                        passwordField.getText());
                break;

            case "Continue as Guest":
                loginController.loginModel.continueAsGuest();
                break;

            case "Create an account":
                loginController.loginModel.signUp();
                break;
        }
    }



    public void closeWindow() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

}

