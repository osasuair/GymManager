package com.example.gymmanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class Login extends javafx.application.Application {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label incorrectLabel;
    @FXML
    private Button loginButton;
    @FXML
    private CheckBox showPasswordCheckBox;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("XMLs/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 768);
        stage.setTitle("Gym Management System");
        stage.getIcons().add(new javafx.scene.image.Image("file:src/main/resources/com/example/gymmanagementsystem/Images/icon_2.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    protected void usernameClicked() {
        incorrectLabel.setVisible(false);
    }

    @FXML
    protected void passwordClicked() {
        incorrectLabel.setVisible(false);
    }

    @FXML
    protected void exit() {
        int choice = JOptionPane.showConfirmDialog(null, "Do you really want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if (choice == 0) {
            System.exit(0);
        }
    }

    @FXML
    protected void showPassword() {
        if (showPasswordCheckBox.isSelected()) {
            passwordTextField.setPromptText(passwordTextField.getText());
            passwordTextField.setText("");
            passwordTextField.setDisable(true);
        } else {
            passwordTextField.setText(passwordTextField.getPromptText());
            passwordTextField.setPromptText("Password");
            passwordTextField.setDisable(false);
        }
    }

    @FXML
    protected void login() throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText().equals("") ? passwordTextField.getPromptText() : passwordTextField.getText();
        if (username.equals("admin") && password.equals("admin")) {
            // set current frame to invisible and open new frame
            loginButton.getScene().getWindow().hide();
            new Home().start((Stage) loginButton.getScene().getWindow());
        } else {
            incorrectLabel.setVisible(true);
        }
    }

    @FXML
    protected void onEnter() throws IOException {
        login();
    }
}