package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane root;
    public TextField txtUserName;
    public TextField txtPassword;
    public static String enteredUserName;
    public static String enteredUserId;
    public void lblCreateNewAccountOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/CreateNewAccountForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Create New Account");
        primaryStage.centerOnScreen();
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * FROM user where password = ? and user_name=?");
            preparedStatement.setObject(2,userName);
            preparedStatement.setObject(1,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean isExist = resultSet.next();
            if(isExist){
                 enteredUserId = resultSet.getString(1);
                 enteredUserName = resultSet.getString(2);
                Parent parent = FXMLLoader.load(this.getClass().getResource("../view/StudentDetailsForm.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.setTitle("Student Details Form");
                primaryStage.centerOnScreen();
            }else{
                new Alert(Alert.AlertType.ERROR,"Invalid User Name or Password").showAndWait();
                txtUserName.clear();
                txtPassword.clear();
                txtUserName.requestFocus();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
