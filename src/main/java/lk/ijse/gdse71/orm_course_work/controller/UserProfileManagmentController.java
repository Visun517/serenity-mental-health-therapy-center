package lk.ijse.gdse71.orm_course_work.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserProfileManagmentController {

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblConfirmPass;

    @FXML
    private Label lblCurrentPass;

    @FXML
    private Label lblHeader;

    @FXML
    private Label lblNewPass;

    @FXML
    private Label lblUserName;

    @FXML
    private PasswordField txtConfirmPass;

    @FXML
    private PasswordField txtCurrentPass;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtNewPass;

    @FXML
    private CheckBox chkShowPassword;

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
    }

    @FXML
    void btnCancelOnAction(ActionEvent actionEvent) {
    }

    public void chkShowPasswordOnAction(ActionEvent actionEvent) {
    }
}