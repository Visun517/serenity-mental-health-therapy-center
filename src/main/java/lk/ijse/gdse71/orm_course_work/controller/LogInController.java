package lk.ijse.gdse71.orm_course_work.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lk.ijse.gdse71.orm_course_work.HelloApplication;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.PasswordEncryptBo;
import lk.ijse.gdse71.orm_course_work.bo.custom.UserBo;
import lk.ijse.gdse71.orm_course_work.bo.exception.InvalidCredentialException;
import lk.ijse.gdse71.orm_course_work.dto.UserDto;
import lk.ijse.gdse71.orm_course_work.userDetails.UserDetails;

import java.io.IOException;

public class LogInController {

    @FXML
    private VBox ancMain;

    @FXML
    private Button btnLogIn;

    @FXML
    private PasswordField passFeild;

    @FXML
    private TextField txtUserName;

    @FXML
    private Label welcomeText;

    @FXML
    private CheckBox showPasswordCheckBox;

    private TextField textField;

    private final UserBo userBo = BoFactory.getInstance().getBo(BoFactory.BOType.USER);
    private final PasswordEncryptBo passwordEncryptBo = BoFactory.getInstance().getBo(BoFactory.BOType.PASSWORD);

    @FXML
    void btnLogInOnAction(ActionEvent event) throws IOException {
        String userName = txtUserName.getText().trim();
        String password = passFeild.getText().trim();

        String userNameRegex = "^[a-zA-Z0-9_]{3,20}$";
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&*])[A-Za-z\\d@#$%^&*]{8,}$";

        if (userName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Username cannot be empty!").show();
            return;
        } else if (!userName.matches(userNameRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid username! Use 3-20 characters (letters, numbers, underscores).").show();
            return;
        }

        if (password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Password cannot be empty!").show();
            return;
        } else if (!password.matches(passwordRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid password! At least 8 characters, with at least one letter, one number, and one special character (@#$%^&*).").show();
            return;
        }

        UserDto user = null;
        try {
            user = userBo.getUser(userName);
        } catch (InvalidCredentialException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        UserDetails.getInstance().setLoggedInUser(user);

        if (user != null) {

            boolean isMatched = passwordEncryptBo.checkedPassword(password, user.getPassword());

            if (isMatched &&user.getRole().equalsIgnoreCase("admin")) {
                Stage stage = (Stage) ancMain.getScene().getWindow();
                stage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/AddMinDashBorad.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage1 = new Stage();
                stage1.setTitle("Admin Dashboard..!");
                stage1.setScene(scene);
                stage1.show();
            } else if (isMatched && user.getRole().equalsIgnoreCase("receptionist")) {
                Stage stage = (Stage) ancMain.getScene().getWindow();
                stage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/ReceptionnistDashBoard.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage1 = new Stage();
                stage1.setTitle("Receptionist Dashboard..!");
                stage1.setScene(scene);
                stage1.show();
            }
        }
    }

    @FXML
    public void togglePasswordVisibility(ActionEvent actionEvent) {
        if (showPasswordCheckBox.isSelected()) {
            textField = new TextField();
            textField.setText(passFeild.getText());
            textField.setPromptText(passFeild.getPromptText());
            textField.setStyle(passFeild.getStyle());
            textField.setPrefHeight(passFeild.getPrefHeight());

            int index = ancMain.getChildren().indexOf(passFeild.getParent());
            VBox parent = (VBox) passFeild.getParent();
            int fieldIndex = parent.getChildren().indexOf(passFeild);
            parent.getChildren().set(fieldIndex, textField);
        } else {
            passFeild.setText(textField.getText());
            int index = ancMain.getChildren().indexOf(textField.getParent());
            VBox parent = (VBox) textField.getParent();
            int fieldIndex = parent.getChildren().indexOf(textField);
            parent.getChildren().set(fieldIndex, passFeild);
        }
    }
}