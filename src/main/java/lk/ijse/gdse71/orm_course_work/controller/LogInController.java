package lk.ijse.gdse71.orm_course_work.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lk.ijse.gdse71.orm_course_work.HelloApplication;
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

    private final UserDao userDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.USER);

    @FXML
    void btnLogInOnAction(ActionEvent event) throws IOException {

        String userName = txtUserName.getText();
        String password = passFeild.getText();

        //data base ekene userta adala karana objetc eka ganna oni.

        User user = userDao.getUser(userName);
        if (user != null){
            if (password.equals(user.getPassword())){
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

}