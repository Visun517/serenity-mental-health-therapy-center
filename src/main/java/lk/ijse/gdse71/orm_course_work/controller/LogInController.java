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
import lk.ijse.gdse71.orm_course_work.dao.DaoFactory;
import lk.ijse.gdse71.orm_course_work.dao.custom.UserDao;
import lk.ijse.gdse71.orm_course_work.entity.User;

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

        String userName = txtUserName.getText();
        String password = passFeild.getText();

        //data base ekene userta adala karana objetc eka ganna oni.

        User user = userBo.getUser(userName);

        if (user != null){
            if (passwordEncryptBo.checkedPassword(password,user.getPassword())){
                if (user.getRole().equalsIgnoreCase("admin")){
                    Stage stage = (Stage) ancMain.getScene().getWindow();
                    stage.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/AddMinDashBorad.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage1 = new Stage();
                    stage1.setTitle("Admin Dashboard..!");
                    stage1.setScene(scene);
                    stage1.show();
                }else{
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
    }

    public void togglePasswordVisibility(ActionEvent actionEvent) {
        if (showPasswordCheckBox.isSelected()) {
            // Show password as plain text
            textField = new TextField();
            textField.setText(passFeild.getText());
            textField.setPromptText(passFeild.getPromptText());
            textField.setStyle(passFeild.getStyle());
            textField.setPrefHeight(passFeild.getPrefHeight());

            // Replace PasswordField with TextField
            int index = ancMain.getChildren().indexOf(passFeild.getParent());
            VBox parent = (VBox) passFeild.getParent();
            int fieldIndex = parent.getChildren().indexOf(passFeild);
            parent.getChildren().set(fieldIndex, textField);
        } else {
            // Hide password (switch back to PasswordField)
            passFeild.setText(textField.getText());
            int index = ancMain.getChildren().indexOf(textField.getParent());
            VBox parent = (VBox) textField.getParent();
            int fieldIndex = parent.getChildren().indexOf(textField);
            parent.getChildren().set(fieldIndex, passFeild);
        }
    }

}