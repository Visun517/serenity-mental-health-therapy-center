package lk.ijse.gdse71.orm_course_work.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.PatientBo;
import lk.ijse.gdse71.orm_course_work.dto.tm.PatientTm;

import java.net.URL;
import java.util.ResourceBundle;

public class PatinetMoreDetailsController implements Initializable {

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> cmbPatient;

    @FXML
    private TableColumn<PatientTm, String> colContact;

    @FXML
    private TableColumn<PatientTm, String> colContact1;

    @FXML
    private TableColumn<PatientTm, String> colEmail;

    @FXML
    private TableColumn<PatientTm, String> colEmail1;

    @FXML
    private TableColumn<PatientTm, String> colHistory;

    @FXML
    private TableColumn<PatientTm, String> colHistory1;

    @FXML
    private TableColumn<PatientTm, String> colName;

    @FXML
    private TableColumn<PatientTm, String> colName1;

    @FXML
    private TableColumn<PatientTm, String> colPatinetId;

    @FXML
    private TableColumn<PatientTm, String> colPatinetId1;

    @FXML
    private Label lblChoosing;

    @FXML
    private Label lblFullyEnrolledPatients;

    @FXML
    private Label lblHeader;

    @FXML
    private Label lblPatientName;

    @FXML
    private Label lblPatientProgram;

    @FXML
    private TableView<PatientTm> tblFullEnrolled;

    @FXML
    private TableView<PatientTm> tblPatientPrgrams;

    private final PatientBo patientBo = BoFactory.getInstance().getBo(BoFactory.BOType.PATIENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fullEnrolledPatients();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void tblPatinetOnCliked(MouseEvent event) {

    }

    public void fullEnrolledPatients(){
        patientBo.getFullEnrolledPatients();

    }


}
