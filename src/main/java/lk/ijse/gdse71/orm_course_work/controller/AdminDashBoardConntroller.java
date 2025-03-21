package lk.ijse.gdse71.orm_course_work.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AdminDashBoardConntroller {

    @FXML
    private AnchorPane ancMain;

    @FXML
    private AnchorPane ancSecondMain;

    @FXML
    private VBox ancSecondMainChild;

    @FXML
    private VBox ancSide;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnPatientMangment;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnReporting;

    @FXML
    private Button btnSessionScheduling;

    @FXML
    private Button btnTheraphistsManagment;

    @FXML
    private Button btnTheraphyProgram;

    @FXML
    private Button btnUserRoleMangment;

    @FXML
    private Button btnViewHistory;

    @FXML
    void btnLogOutOnAction(ActionEvent event) {

    }

    @FXML
    void btnPatientMangmentOnAction(ActionEvent event) throws IOException {

    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {

    }

    @FXML
    void btnReportingOnAction(ActionEvent event) {

    }

    @FXML
    void btnSessionSchedulingOnAction(ActionEvent event) {

    }

    @FXML
    void btnTheraphistsManagmentOnAction(ActionEvent event) {

    }

    @FXML
    void btnTheraphyProgramOnAction(ActionEvent event) {

    }

    @FXML
    void btnUserRoleMangmentOnAcion(ActionEvent event) throws IOException {
        ancSecondMainChild.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/UserRoleManageBoard.fxml"));
        ancSecondMainChild.getChildren().add(parent);
    }

    @FXML
    void btnViewHistoryOnAction(ActionEvent event) {

    }

}
