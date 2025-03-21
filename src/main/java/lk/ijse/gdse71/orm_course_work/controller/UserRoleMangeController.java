package lk.ijse.gdse71.orm_course_work.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class UserRoleMangeController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpadate;

    @FXML
    private TableColumn<String, String> colUserId;

    @FXML
    private TableColumn<String, String> colUserName;

    @FXML
    private TableColumn<String, String> colUserPassword;

    @FXML
    private TableColumn<String, String> colUserRole;

    @FXML
    private ComboBox<String> comboBoxRole;

    @FXML
    private Label lblHeader;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblRole;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserIdShow;

    @FXML
    private Label lblUserName;

    @FXML
    private TableView<?> tblUser;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpadateOnAction(ActionEvent event) {

    }

    @FXML
    void comboBoxRoleOnAction(ActionEvent event) {

    }

    @FXML
    void tblUserOncliked(MouseEvent event) {

    }
}
