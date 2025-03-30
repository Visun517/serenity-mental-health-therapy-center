package lk.ijse.gdse71.orm_course_work.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lk.ijse.gdse71.orm_course_work.HelloApplication;

import java.io.IOException;

public class ReceptionistDashBoardController {

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
    private Button btnUserRoleMangment;

    @FXML
    private Button btnViewHistory;

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) ancMain.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogIn.fxml"));
        Parent root = loader.load();
        Stage login = new Stage();
        login.setScene(new Scene(root));
        login.show();
    }

    @FXML
    void btnPatientMangmentOnAction(ActionEvent event) throws IOException {
        ancSecondMainChild.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/PatientMangmentBoard.fxml"));
        ancSecondMainChild.getChildren().add(parent);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {

    }

    @FXML
    void btnReportingOnAction(ActionEvent event) {

    }

    @FXML
    void btnSessionSchedulingOnAction(ActionEvent event) throws IOException {
        ancSecondMainChild.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/SessionSchedulingBoard.fxml"));
        ancSecondMainChild.getChildren().add(parent);
    }

    @FXML
    void btnUserRoleMangmentOnAcion(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/UserProfileManagment.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage1 = new Stage();
        stage1.setTitle("User profile management..!");
        stage1.setScene(scene);
        stage1.show();

    }

    @FXML
    void btnViewHistoryOnAction(ActionEvent event) {

    }

}
