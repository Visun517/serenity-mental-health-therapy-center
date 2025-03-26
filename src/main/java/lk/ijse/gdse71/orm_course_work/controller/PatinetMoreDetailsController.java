package lk.ijse.gdse71.orm_course_work.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.PatientBo;
import lk.ijse.gdse71.orm_course_work.dto.PatientDto;
import lk.ijse.gdse71.orm_course_work.dto.PatientProgramsDetailsDto;
import lk.ijse.gdse71.orm_course_work.dto.ProgramDto;
import lk.ijse.gdse71.orm_course_work.dto.tm.PatientProgramsDetailsTm;
import lk.ijse.gdse71.orm_course_work.dto.tm.PatientTm;
import lk.ijse.gdse71.orm_course_work.dto.tm.ProgramTm;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PatinetMoreDetailsController implements Initializable {

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> cmbPatient;

    @FXML
    private TableColumn<PatientTm, String> colContact;

    @FXML
    private TableColumn<PatientTm, String> colEmail;

    @FXML
    private TableColumn<PatientTm, String> colHistory;

    @FXML
    private TableColumn<PatientTm, String> colName;

    @FXML
    private TableColumn<PatientTm, String> colPatinetId;

    @FXML
    private TableColumn<ProgramTm, Double> colFee;

    @FXML
    private TableColumn<ProgramTm, String> colDescription;

    @FXML
    private TableColumn<ProgramTm, String> colDuration;

    @FXML
    private TableColumn<ProgramTm, String> colNamePro;

    @FXML
    private TableColumn<ProgramTm, String> colProgramId;

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
    private TableView<ProgramTm> tblPatientPrgrams;

    private final PatientBo patientBo = BoFactory.getInstance().getBo(BoFactory.BOType.PATIENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPatinetId.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colHistory.setCellValueFactory(new PropertyValueFactory<>("medical_history"));

        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colNamePro.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("theraphy_pro_id"));


        try {
            fullEnrolledPatients();
            getPatientIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        PatientDto patinetDto = patientBo.getPatinet(cmbPatient.getValue());
        List<PatientProgramsDetailsDto> patientPrograms = patinetDto.getPatientProgramsDetails();

        ObservableList<ProgramTm> programTmObservableList = FXCollections.observableArrayList();

        for (PatientProgramsDetailsDto patientProgramsDetailsDto : patientPrograms ){
            ProgramTm programTm = new ProgramTm();
            programTm.setName(patientProgramsDetailsDto.getTheraphyProgram().getName());
            programTm.setDescription(patientProgramsDetailsDto.getTheraphyProgram().getDescription());
            programTm.setFee(patientProgramsDetailsDto.getTheraphyProgram().getFee());
            programTm.setDuration(patientProgramsDetailsDto.getTheraphyProgram().getDuration());
            programTm.setTheraphy_pro_id(patientProgramsDetailsDto.getTheraphyProgram().getTheraphy_pro_id());

            programTmObservableList.add(programTm);
        }
        tblPatientPrgrams.setItems(programTmObservableList);
    }

    @FXML
    void tblPatinetOnCliked(MouseEvent event) {

    }

    public void fullEnrolledPatients(){
        List<PatientDto> fullEnrolledPatients = patientBo.getFullEnrolledPatients();
        ObservableList<PatientTm> patientTms = FXCollections.observableArrayList();

        for (PatientDto patientDto : fullEnrolledPatients){
            PatientTm patientTm = new PatientTm();
            patientTm.setPatient_id(patientDto.getPatient_id());
            patientTm.setName(patientDto.getName());
            patientTm.setContact(patientDto.getContact());
            patientTm.setEmail(patientDto.getEmail());
            patientTm.setMedical_history(patientDto.getMedical_history());
            patientTm.setDate(patientDto.getDate());
            patientTms.add(patientTm);
        }
        tblFullEnrolled.setItems(patientTms);
    }
    public void getPatientIds() throws SQLException {
        List<PatientDto> all = patientBo.getAll();
        ObservableList<String> ids = FXCollections.observableArrayList();

        for (PatientDto patientDto : all){
            ids.add(patientDto.getPatient_id());
        }
        cmbPatient.setItems(ids);
    }
    @FXML
    void cmbPatientOnAction(ActionEvent event) {
        String id = cmbPatient.getValue();
        PatientDto patient = patientBo.getPatinet(id);
        lblPatientName.setText(patient.getName());
    }
}
