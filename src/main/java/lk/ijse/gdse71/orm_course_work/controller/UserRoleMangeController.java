package lk.ijse.gdse71.orm_course_work.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.UserBo;
import lk.ijse.gdse71.orm_course_work.dto.UserDto;
import lk.ijse.gdse71.orm_course_work.dto.tm.UserTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
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
    private TableView<UserTm> tblUser;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    private final UserBo userBo = BoFactory.getInstance().getBo(BoFactory.BOType.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colUserRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        comboInit();
        refresh();


    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String userId = lblUserIdShow.getText();
        try{
            boolean isDelete = userBo.delete(userId);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION, "User is deleted...!").show();
                refresh();
            }else {
                new Alert(Alert.AlertType.ERROR, "User is not deleted...!").show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    UserDto userDto = new UserDto();
    userDto.setUser_id(lblUserIdShow.getText());
    userDto.setUsername(txtUserName.getText());
    userDto.setPassword(txtPassword.getText());
    userDto.setRole(comboBoxRole.getValue());

        try {

            if (userBo.getUser(userDto.getUsername()) != null){
                new Alert(Alert.AlertType.ERROR, "User is already exist...!").show();
                refresh();
                return;
            }
            boolean isSaved = userBo.save(userDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "User is saved...!").show();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR, "User is not saved...!").show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @FXML
    void btnUpadateOnAction(ActionEvent event) {

        UserDto userDto = new UserDto();
        userDto.setUser_id(lblUserIdShow.getText());
        userDto.setUsername(txtUserName.getText());
        userDto.setPassword(txtPassword.getText());
        userDto.setRole(comboBoxRole.getValue());

        try{
            boolean isUpdated = userBo.update(userDto);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION, "User is updated...!").show();
                refresh();
            }else {
                new Alert(Alert.AlertType.ERROR, "User is not updated...!").show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    void comboBoxRoleOnAction(ActionEvent event) {

    }

    @FXML
    void tblUserOncliked(MouseEvent event) {

        UserTm selectedItem = tblUser.getSelectionModel().getSelectedItem();

        if (selectedItem != null){
            lblUserIdShow.setText(selectedItem.getUser_id());
            txtUserName.setText(selectedItem.getUsername());
            comboBoxRole.setValue(selectedItem.getRole());
        }


    }
    public void comboInit(){
        String [] roles = {"Admin" , "Receptionist"};
        comboBoxRole.getItems().addAll(roles);
    }

    public void refresh(){

        txtPassword.setText("");
        txtUserName.setText("");
        comboBoxRole.setValue("Admin");

        try {
            lblUserIdShow.setText(userBo.getNextId());
            getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getAll(){
        try {
            List<UserDto> users = userBo.getAll();
            ObservableList<UserTm> userTms = FXCollections.observableArrayList();

            for (UserDto user : users) {
                UserTm userTm = new UserTm();
                userTm.setUser_id(user.getUser_id());
                userTm.setUsername(user.getUsername());
                userTm.setRole(user.getRole());

                userTms.add(userTm);
            }
            tblUser.setItems(userTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
