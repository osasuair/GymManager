package com.example.gymmanagementsystem;

import com.example.gymmanagementsystem.project.ConnectionProvider;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class NewMember extends Application {

    @FXML
    private Label idLabel;
    @FXML
    private ComboBox<String> genderComboBox, gymTimeComboBox;
    @FXML
    private TextField nameTextField, mobileTextField, emailTextField, parent1TextField, parent2TextField, sinTextField, ageTextField, monthlyFeeTextField;
    @FXML
    private ObservableList<String> genderList, gymTimeList;
    @FXML
    private AnchorPane newMemberPane;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("XMLs/new-member-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 679);
        primaryStage.setX(175);
        primaryStage.setY(100);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    protected void initialize() {
        genderList = FXCollections.observableArrayList("Male", "Female", "Others");
        gymTimeList = FXCollections.observableArrayList("Morning", "Evening");

        genderComboBox.setValue("Male");
        genderComboBox.setItems(genderList);
        gymTimeComboBox.setValue("Morning");
        gymTimeComboBox.setItems(gymTimeList);

        // Set id to the next available id
        try {
            int id = 1;
            String str1 = String.valueOf(id);
            idLabel.setText(str1);
            Connection con = ConnectionProvider.getCon();
            if(con == null) {
                JOptionPane.showMessageDialog(null, "Connection is Invalid");
                System.exit(0);
            }
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from member");
            while (rs.next()) {
                id = rs.getInt(1) + 1;
            }
            String str = String.valueOf(id);
            idLabel.setText(str);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    protected void close() {
        newMemberPane.setVisible(false);
    }

    @FXML
    protected void clear() {
        nameTextField.clear();
        mobileTextField.clear();
        emailTextField.clear();
        parent1TextField.clear();
        parent2TextField.clear();
        sinTextField.clear();
        ageTextField.clear();
        monthlyFeeTextField.clear();
        initialize();
    }

    @FXML
    protected void save() {
        String id = idLabel.getText();
        String name = nameTextField.getText();
        String mobile = mobileTextField.getText();
        String email = emailTextField.getText();
        String gender = genderComboBox.getValue();
        String parent1 = parent1TextField.getText();
        String parent2 = parent2TextField.getText();
        String gymTime = gymTimeComboBox.getValue();
        String sin = sinTextField.getText();
        String age = ageTextField.getText();
        String monthlyFee = monthlyFeeTextField.getText();

        try {
            Connection con = ConnectionProvider.getCon();
            assert con != null;
            PreparedStatement ps = con.prepareStatement("insert into member values(?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, mobile);
            ps.setString(4, email);
            ps.setString(5, gender);
            ps.setString(6, parent1);
            ps.setString(7, parent2);
            ps.setString(8, gymTime);
            ps.setString(9, sin);
            ps.setString(10, age);
            ps.setString(11, monthlyFee);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Successfully Saved");
            clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
