package com.example.gymmanagementsystem;

import com.example.gymmanagementsystem.project.ConnectionProvider;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class UpdateDeleteMember extends Application {


    @FXML
    private AnchorPane updateDeletePane;
    @FXML
    private TextField idTextField, nameTextField, mobileTextField, emailTextField, parent1TextField, parent2TextField, sinTextField, ageTextField, monthlyFeeTextField;
    @FXML
    private ComboBox<String> genderComboBox, gymTimeComboBox;
    @FXML
    private Button deleteButton, updateButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("XMLs/update-delete-view."));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 679);
        primaryStage.setX(175);
        primaryStage.setY(100);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    protected void initialize() {
        deleteButton.setDisable(true);
        updateButton.setDisable(true);
        genderComboBox.setDisable(true);
        gymTimeComboBox.setDisable(true);
        ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female", "Others");
        ObservableList<String> gymTimeList = FXCollections.observableArrayList("Morning", "Evening");
        genderComboBox.setItems(genderList);
        gymTimeComboBox.setItems(gymTimeList);
    }

    @FXML
    protected void close() {
        updateDeletePane.setVisible(false);
    }

    @FXML
    protected void reset() {
        idTextField.clear();
        idTextField.setEditable(true);
        nameTextField.clear();
        mobileTextField.clear();
        emailTextField.clear();
        parent1TextField.clear();
        parent2TextField.clear();
        sinTextField.clear();
        ageTextField.clear();
        monthlyFeeTextField.clear();
        gymTimeComboBox.setValue("");
        genderComboBox.setValue("");
        initialize();
    }

    @FXML
    protected void search() {
        boolean flag = false;
        String id = idTextField.getText();
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from member where id = '" + id + "'");
            while (rs.next()) {
                flag = true;
                idTextField.setEditable(false);
                nameTextField.setText(rs.getString("name"));
                mobileTextField.setText(rs.getString("mobileNumber"));
                emailTextField.setText(rs.getString("email"));
                genderComboBox.setValue(rs.getString("gender"));
                parent1TextField.setText(rs.getString("parent1"));
                parent2TextField.setText(rs.getString("parent2"));
                gymTimeComboBox.setValue(rs.getString("gymTime"));
                sinTextField.setText(rs.getString("sinNumber"));
                sinTextField.setEditable(false);
                ageTextField.setText(rs.getString("age"));
                monthlyFeeTextField.setText(rs.getString("monthlyFee"));
            }
            if (!flag) {
                JOptionPane.showMessageDialog(null, "Member ID does not exist!");
                reset();
            } else {
                deleteButton.setDisable(false);
                updateButton.setDisable(false);
                genderComboBox.setDisable(false);
                gymTimeComboBox.setDisable(false);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void delete() {
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this member?", "Delete", JOptionPane.YES_NO_OPTION) == 1) {
            return;
        }

        String id = idTextField.getText();
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            st.executeUpdate("delete from member where id = '" + id + "'");
            JOptionPane.showMessageDialog(null, "Member deleted successfully!");
            reset();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Member deletion failed! Something went wrong!\n\n" + e);
        }
    }

    @FXML
    protected void update() {
        String id = idTextField.getText();
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
            PreparedStatement ps = con.prepareStatement("update member set name = ?, mobileNumber = ?, email = ?, gender = ?, parent1 = ?, parent2 = ?, gymTime = ?, sinNumber = ?, age = ?, monthlyFee = ? where id = ?");
            ps.setString(1, name);
            ps.setString(2, mobile);
            ps.setString(3, email);
            ps.setString(4, gender);
            ps.setString(5, parent1);
            ps.setString(6, parent2);
            ps.setString(7, gymTime);
            ps.setString(8, sin);
            ps.setString(9, age);
            ps.setString(10, monthlyFee);
            ps.setString(11, id);
            int i = ps.executeUpdate();
            if (i > 0) {
                JOptionPane.showMessageDialog(null, "Member updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Member update failed! Something went wrong!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Member update failed! Something went wrong!\n\n" + e);
        }
    }
}
