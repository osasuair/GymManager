package com.example.gymmanagementsystem;

import com.example.gymmanagementsystem.models.MonthPayment;
import com.example.gymmanagementsystem.project.ConnectionProvider;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Payment extends Application {
    @FXML
    private AnchorPane paymentPane;
    @FXML
    private Label dateLabel;
    @FXML
    private TextField idTextField, nameTextField, mobileTextField, emailTextField, feeTextField;
    @FXML
    private TableColumn<MonthPayment, String> monthColumn, feeColumn;
    @FXML
    private TableView<MonthPayment> table;
    @FXML
    private Button saveButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("XMLs/payment-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 679);
        primaryStage.setX(175);
        primaryStage.setY(100);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    protected void close() {
        paymentPane.setVisible(false);
    }

    @FXML
    protected void initialize() {
        date();
    }

    public void tableDetails() {
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("fee"));

        ObservableList<MonthPayment> list = FXCollections.observableArrayList();
        String id = idTextField.getText();
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from payment where id = '" + id + "'");
            while (rs.next()) {
                list.add(new MonthPayment(rs.getString(2), rs.getString(3)));
            }
            table.setItems(list);
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, "Connection Error!\n" + throwables, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearTable() {
        ObservableList<MonthPayment> list = FXCollections.observableArrayList();
        table.setItems(list);
    }

    public void date() {
        dateLabel.setText(new SimpleDateFormat("MM-yyyy").format(new Date()));
    }

    @FXML
    public void search() {
        tableDetails();
        boolean flag = false;
        String id = idTextField.getText();
        String month = dateLabel.getText();
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from member where id = '" + id + "'");
            while (rs.next()) {
                flag = true;
                nameTextField.setText(rs.getString(2));
                mobileTextField.setText(rs.getString(3));
                emailTextField.setText(rs.getString(4));
                feeTextField.setText(rs.getString(11));
                saveButton.setDisable(false);
            }
            if (!flag) {
                JOptionPane.showMessageDialog(null, "Member ID does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                reset();
            }
            ResultSet rs1 = st.executeQuery("select * from payment where id = '" + id + "' and month = '" + month + "'");
            while (rs1.next()) {
                JOptionPane.showMessageDialog(null, "Payment is already done for this month!", "Error", JOptionPane.ERROR_MESSAGE);
                saveButton.setDisable(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    protected void save() {
        String id = idTextField.getText();
        String month = dateLabel.getText();
        String fee = feeTextField.getText();
        try {
            Connection con = ConnectionProvider.getCon();
            PreparedStatement ps = con.prepareStatement("insert into payment values(?, ?, ?)");
            ps.setString(1, id);
            ps.setString(2, month);
            ps.setString(3, fee);
            ps.executeUpdate();
            tableDetails();
            JOptionPane.showMessageDialog(null, "Successfully Updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, "Connection Error!\n" + throwables, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    protected void reset() {
        idTextField.setText("");
        nameTextField.setText("");
        mobileTextField.setText("");
        emailTextField.setText("");
        feeTextField.setText("");
        saveButton.setDisable(true);
        clearTable();
    }
}
