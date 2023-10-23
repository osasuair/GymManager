package com.example.mygymmanager;

import com.example.mygymmanager.project.ConnectionProvider;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;

public class Home extends Application {
    @FXML
    private Label homeLabel;
    @FXML
    private Menu newMemberMenu, updateDeleteMenu, listMembersMenu, paymentMenu, logoutMenu;
    @FXML
    private AnchorPane newMemberPane, updateDeletePane, listMembersPane, paymentPane;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("XMLs/home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 768);
        stage.setTitle("MyGymManager");
        stage.getIcons().add(new Image("file:src/main/resources/com/example/mygymmanager/Images/icon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    @FXML
    protected void initialize() {
        Connection con = ConnectionProvider.getCon();
        if (con == null) {
            JOptionPane.showMessageDialog(null, "Connection not established with database.");
            System.exit(0);
        }
    }

    @FXML
    protected void newMemberOption() {
        newMemberMenu.hide();
        hideAllPanes();
        newMemberPane.setVisible(true);
    }

    @FXML
    protected void updateDeleteOption() {
        updateDeleteMenu.hide();
        hideAllPanes();
        updateDeletePane.setVisible(true);
    }

    @FXML
    protected void listMembersOption() {
        listMembersMenu.hide();
        hideAllPanes();
        Button refreshButton = (Button) listMembersPane.lookup("#refreshButton");
        refreshButton.fire();
        listMembersPane.setVisible(true);
    }

    @FXML
    protected void paymentOption() {
        paymentMenu.hide();
        hideAllPanes();
        paymentPane.setVisible(true);
    }

    private void hideAllPanes() {
        newMemberPane.setVisible(false);
        updateDeletePane.setVisible(false);
        listMembersPane.setVisible(false);
        paymentPane.setVisible(false);
    }

    @FXML
    protected void logout() {
        int choice = JOptionPane.showConfirmDialog(null, "Do you really want to logout?", "Logout", JOptionPane.YES_NO_OPTION);

        logoutMenu.hide();
        if (choice == 0) {
            try {
                // Delete current pane and open login pane
                ((Stage) newMemberPane.getScene().getWindow()).close();
                new Login().start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((Stage) homeLabel.getScene().getWindow()).close();
        }
    }

    @FXML
    protected void exit() {
        System.exit(0);
    }
}
