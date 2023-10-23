package com.example.mygymmanager;

import com.example.mygymmanager.models.Member;
import com.example.mygymmanager.project.ConnectionProvider;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListMembers extends Application {
    public Button refreshButton;
    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> idColumn, nameColumn, mobileColumn, emailColumn, genderColumn, parent1Column, parent2Column, sinColumn, ageColumn, gymTimeColumn, monthlyFeeColumn;
    @FXML
    private AnchorPane listMembersPane;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("XMLs/list-members-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 679);
        primaryStage.setX(175);
        primaryStage.setY(100);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    protected void initialize() {
        // Initialize the columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        parent1Column.setCellValueFactory(new PropertyValueFactory<>("parent1"));
        parent2Column.setCellValueFactory(new PropertyValueFactory<>("parent2"));
        sinColumn.setCellValueFactory(new PropertyValueFactory<>("sin"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        gymTimeColumn.setCellValueFactory(new PropertyValueFactory<>("gymTime"));
        monthlyFeeColumn.setCellValueFactory(new PropertyValueFactory<>("monthlyFee"));

        // Set the items of the table from the database
        try {
            Connection con = ConnectionProvider.getCon();
            assert con != null;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from member");

            ObservableList<Member> members = FXCollections.observableArrayList();
            while (rs.next()) {
                members.add(new Member(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)));
            }
            tableView.setItems(members);
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, "Connection Error!\n" + throwables, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    protected void refresh() {
        initialize();
    }

    @FXML
    protected void close() {
        listMembersPane.setVisible(false);
    }
}
