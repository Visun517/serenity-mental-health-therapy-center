package lk.ijse.gdse71.orm_course_work.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.PasswordEncryptBo;
import lk.ijse.gdse71.orm_course_work.bo.custom.UserBo;
import lk.ijse.gdse71.orm_course_work.dto.UserDto;
import lk.ijse.gdse71.orm_course_work.userDetails.UserDetails;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserProfileManagmentController implements Initializable {

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

    private  UserDto userDetails;

    private UserBo userBo = BoFactory.getInstance().getBo(BoFactory.BOType.USER);
    private final PasswordEncryptBo passwordEncryptBo = BoFactory.getInstance().getBo(BoFactory.BOType.PASSWORD);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDto loggedInUser = UserDetails.getInstance().getLoggedInUser();
        userDetails = loggedInUser;
        txtName.setText(loggedInUser.getUsername());
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        if (userDetails == null){
            System.out.println(userDetails.getUsername());
            new Alert(Alert.AlertType.ERROR, "User not found...!").show();
            return;
        }
        boolean isMatched =passwordEncryptBo.checkedPassword(txtCurrentPass.getText(),userDetails.getPassword());
        if (!isMatched){
            new Alert(Alert.AlertType.ERROR, "Password is not matched...!").show();
            return;
        }
        String newPass = txtNewPass.getText();
        String reEnterPass = txtConfirmPass.getText();
        if (!newPass.equals(reEnterPass)){
            new Alert(Alert.AlertType.ERROR, "New Password and Confirm Password is not matched...!").show();
            return;
        }
        try {
            userDetails.setPassword(txtConfirmPass.getText());
            boolean isSaved = userBo.update(userDetails);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Your new Password is saved").show();
            }else {
                new Alert(Alert.AlertType.INFORMATION, "Your new Password is not saved").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnCancelOnAction(ActionEvent actionEvent) {
        txtName.setText(userDetails.getUsername());
        txtConfirmPass.setText("");
        txtCurrentPass.setText("");
        txtNewPass.setText("");
    }

    public void chkShowPasswordOnAction(ActionEvent actionEvent) {

        if (chkShowPassword.isSelected()) {
            // Show passwords
            TextField tempCurrent = new TextField();
            tempCurrent.setText(txtCurrentPass.getText());
            tempCurrent.setLayoutX(txtCurrentPass.getLayoutX());
            tempCurrent.setLayoutY(txtCurrentPass.getLayoutY());
            tempCurrent.setPrefHeight(txtCurrentPass.getPrefHeight());
            tempCurrent.setPrefWidth(txtCurrentPass.getPrefWidth());
            tempCurrent.setStyle(txtCurrentPass.getStyle());

            TextField tempNew = new TextField();
            tempNew.setText(txtNewPass.getText());
            tempNew.setLayoutX(txtNewPass.getLayoutX());
            tempNew.setLayoutY(txtNewPass.getLayoutY());
            tempNew.setPrefHeight(txtNewPass.getPrefHeight());
            tempNew.setPrefWidth(txtNewPass.getPrefWidth());
            tempNew.setStyle(txtNewPass.getStyle());

            TextField tempConfirm = new TextField();
            tempConfirm.setText(txtConfirmPass.getText());
            tempConfirm.setLayoutX(txtConfirmPass.getLayoutX());
            tempConfirm.setLayoutY(txtConfirmPass.getLayoutY());
            tempConfirm.setPrefHeight(txtConfirmPass.getPrefHeight());
            tempConfirm.setPrefWidth(txtConfirmPass.getPrefWidth());
            tempConfirm.setStyle(txtConfirmPass.getStyle());

            AnchorPane parent = (AnchorPane) txtCurrentPass.getParent();
            parent.getChildren().removeAll(txtCurrentPass, txtNewPass, txtConfirmPass);
            parent.getChildren().addAll(tempCurrent, tempNew, tempConfirm);

            txtCurrentPass = (PasswordField) tempCurrent;
            txtNewPass = (PasswordField) tempNew;
            txtConfirmPass = (PasswordField) tempConfirm;
        } else {
            // Hide passwords
            PasswordField tempCurrent = new PasswordField();
            tempCurrent.setText(txtCurrentPass.getText());
            tempCurrent.setLayoutX(txtCurrentPass.getLayoutX());
            tempCurrent.setLayoutY(txtCurrentPass.getLayoutY());
            tempCurrent.setPrefHeight(txtCurrentPass.getPrefHeight());
            tempCurrent.setPrefWidth(txtCurrentPass.getPrefWidth());
            tempCurrent.setStyle(txtCurrentPass.getStyle());

            PasswordField tempNew = new PasswordField();
            tempNew.setText(txtNewPass.getText());
            tempNew.setLayoutX(txtNewPass.getLayoutX());
            tempNew.setLayoutY(txtNewPass.getLayoutY());
            tempNew.setPrefHeight(txtNewPass.getPrefHeight());
            tempNew.setPrefWidth(txtNewPass.getPrefWidth());
            tempNew.setStyle(txtNewPass.getStyle());

            PasswordField tempConfirm = new PasswordField();
            tempConfirm.setText(txtConfirmPass.getText());
            tempConfirm.setLayoutX(txtConfirmPass.getLayoutX());
            tempConfirm.setLayoutY(txtConfirmPass.getLayoutY());
            tempConfirm.setPrefHeight(txtConfirmPass.getPrefHeight());
            tempConfirm.setPrefWidth(txtConfirmPass.getPrefWidth());
            tempConfirm.setStyle(txtConfirmPass.getStyle());

            AnchorPane parent = (AnchorPane) txtCurrentPass.getParent();
            parent.getChildren().removeAll(txtCurrentPass, txtNewPass, txtConfirmPass);
            parent.getChildren().addAll(tempCurrent, tempNew, tempConfirm);

            txtCurrentPass = tempCurrent;
            txtNewPass = tempNew;
            txtConfirmPass = tempConfirm;
        }
    }
}