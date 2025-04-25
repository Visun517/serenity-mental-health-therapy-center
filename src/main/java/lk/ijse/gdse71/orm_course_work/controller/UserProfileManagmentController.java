package lk.ijse.gdse71.orm_course_work.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private TextField txtConfirmPass;

    @FXML
    private TextField txtCurrentPass;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNewPass;

    @FXML
    private CheckBox chkShowPassword;

    private UserDto userDetails;

    private UserBo userBo = BoFactory.getInstance().getBo(BoFactory.BOType.USER);
    private final PasswordEncryptBo passwordEncryptBo = BoFactory.getInstance().getBo(BoFactory.BOType.PASSWORD);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDto loggedInUser = UserDetails.getInstance().getLoggedInUser();
        userDetails = loggedInUser;
        txtName.setText(loggedInUser.getUsername());
        chkShowPassword.setVisible(false); // Hide the checkbox since all fields are now TextFields
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (userDetails == null) {
            new Alert(Alert.AlertType.ERROR, "User not found...!").show();
            return;
        }

        String username = txtName.getText().trim();
        String currentPass = txtCurrentPass.getText().trim();
        String newPass = txtNewPass.getText().trim();
        String confirmPass = txtConfirmPass.getText().trim();

        // Regex patterns
        String usernameRegex = "^[a-zA-Z0-9_]{3,20}$";
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        // Validate username
        if (username.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Username cannot be empty!").show();
            return;
        } else if (!username.matches(usernameRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid username! Use 3-20 characters (letters, numbers, underscores only).").show();
            return;
        }

        // Validate current password
        if (currentPass.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Current password cannot be empty!").show();
            return;
        }
        boolean isMatched = passwordEncryptBo.checkedPassword(currentPass, userDetails.getPassword());
        if (!isMatched) {
            new Alert(Alert.AlertType.ERROR, "Current password is not matched...!").show();
            return;
        }

        // Validate new password
        if (newPass.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "New password cannot be empty!").show();
            return;
        } else if (!newPass.matches(passwordRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid new password! Must be at least 8 characters, with 1 uppercase, 1 lowercase, 1 number, and 1 special character (e.g., Passw0rd!).").show();
            return;
        }

        // Validate confirm password
        if (confirmPass.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Confirm password cannot be empty!").show();
            return;
        } else if (!confirmPass.equals(newPass)) {
            new Alert(Alert.AlertType.ERROR, "New Password and Confirm Password do not match...!").show();
            return;
        }

        // Additional validation: Check if new password is the same as the current password
        if (passwordEncryptBo.checkedPassword(newPass, userDetails.getPassword())) {
            new Alert(Alert.AlertType.ERROR, "New password cannot be the same as the current password!").show();
            return;
        }

        try {
            userDetails.setUsername(username);
            userDetails.setPassword(newPass);
            boolean isSaved = userBo.update(userDetails);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Your profile is updated successfully!").show();
                txtCurrentPass.setText("");
                txtNewPass.setText("");
                txtConfirmPass.setText("");
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update your profile!").show();
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

    @FXML
    void chkShowPasswordOnAction(ActionEvent actionEvent) {
        // No need for this method anymore since all fields are TextFields
    }
}