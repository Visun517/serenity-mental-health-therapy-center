package lk.ijse.gdse71.orm_course_work.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import lk.ijse.gdse71.orm_course_work.dto.UserDto;
import lk.ijse.gdse71.orm_course_work.entity.User;
import lk.ijse.gdse71.orm_course_work.userDetails.UserDetails;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LogInController  {

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

        UserDto user = userBo.getUser(userName);
        UserDetails.getInstance().setLoggedInUser(user);
        boolean isMatched = passwordEncryptBo.checkedPassword(password,user.getPassword());
        System.out.println(password);
        System.out.println(isMatched);

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
                }else if (user.getRole().equalsIgnoreCase("receptionist")){
                    Stage stage = (Stage) ancMain.getScene().getWindow();
                    stage.close();
//
//                    FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("/view/UserProfileManagment.fxml"));
//                    Parent parent = fxmlLoader1.load();
//                    UserProfileManagmentController userProfileManagmentController = fxmlLoader1.getController();
//                    userProfileManagmentController.setUserDetails(user);


                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/ReceptionnistDashBoard.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage1 = new Stage();
                    stage1.setTitle("Receptionist Dashboard..!");
                    stage1.setScene(scene);
                    stage1.show();
                }
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"User not found ..!");
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