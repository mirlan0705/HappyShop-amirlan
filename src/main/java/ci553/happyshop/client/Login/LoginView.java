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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle(UIStyle.loginInputStyle);
        emailField.setPrefHeight(40);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle(UIStyle.loginInputStyle);
        passwordField.setPrefHeight(40);


        root = new VBox(14);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 30px;");

        VBox card = new VBox(12);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(UIStyle.loginCardStyle);
        card.setPrefWidth(360);

        Label titleLabel = new Label("Sign in");
        titleLabel.setStyle(UIStyle.loginTitleStyle);

        Button btnGuest = new Button("Continue as Guest");
        btnGuest.setStyle(UIStyle.loginLinkBtnStyle);
        btnGuest.setOnAction(this::buttonClicked);

        Label subTitle = new Label("Sign in for faster checkout and order tracking.");
        subTitle.setStyle(UIStyle.loginSubTitleStyle);
        subTitle.setWrapText(true);

        Button btnSignIn = new Button("Sign In");
        btnSignIn.setStyle(UIStyle.loginPrimaryBtnStyle);
        btnSignIn.setPrefWidth(360);
        btnSignIn.setPrefHeight(42);
        btnSignIn.setOnAction(this::buttonClicked);

        Button btnSignUp = new Button("Create an account");
        btnSignUp.setStyle(UIStyle.loginLinkBtnStyle);
        btnSignUp.setOnAction(this::buttonClicked);

        card.getChildren().addAll(titleLabel, subTitle, emailField, passwordField, btnSignIn, btnGuest, btnSignUp);
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
            handleAction(action); // controller logic moved into its own method
        } catch (SQLException e) {
            e.printStackTrace();
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

