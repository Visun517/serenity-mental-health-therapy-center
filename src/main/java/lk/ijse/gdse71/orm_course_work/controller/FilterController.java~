package lk.ijse.gdse71.orm_course_work.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.PatientBo;
import lk.ijse.gdse71.orm_course_work.bo.custom.ProgramsBo;
import lk.ijse.gdse71.orm_course_work.dto.FilterDto;
import lk.ijse.gdse71.orm_course_work.dto.ProgramDto;
import lk.ijse.gdse71.orm_course_work.dto.tm.FilterTm;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FilterController implements Initializable {

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> cmbProgrmaId;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableColumn<FilterTm, String> colContact;

    @FXML
    private TableColumn<FilterTm, Date> colDate;

    @FXML
    private TableColumn<FilterTm, String> colEmail;

    @FXML
    private TableColumn<FilterTm, String> colName;

    @FXML
    private TableColumn<FilterTm, String> colPrograms;

    @FXML
    private TableColumn<FilterTm, String> colSessionId;

    @FXML
    private TableColumn<FilterTm, String> colPatientId;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblHeader;

    @FXML
    private Label lblProgramId;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnClear;

    @FXML
    private TableView<FilterTm> tblFilter;

    private final ProgramsBo programsBo = BoFactory.getInstance().getBo(BoFactory.BOType.PROGRAMS);
    private final PatientBo patientBo =  BoFactory.getInstance().getBo(BoFactory.BOType.PATIENT);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrograms.setCellValueFactory(new PropertyValueFactory<>("program_id"));
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("session_id"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patient_id"));

        try {
            setStatus();
            setProgramIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void setStatus(){
        ObservableList<String> values = FXCollections.observableArrayList("Cancelled", "Scheduled", "Completed");
        cmbStatus.setItems(values);
    }
    public void setProgramIds() throws SQLException {
        List<ProgramDto> all = programsBo.getAll();
        ObservableList<String> ids = FXCollections.observableArrayList();
        for (ProgramDto programDto : all) {
            ids.add(programDto.getTheraphy_pro_id());
        }
        cmbProgrmaId.setItems(ids);
    }
    public void filterByStatus(String status){
        List<FilterDto> filterDtos = patientBo.filterByStatus(status);
        ObservableList<FilterTm> observableList = FXCollections.observableArrayList();

        for (FilterDto filterDto : filterDtos) {
            FilterTm filterTm = new FilterTm();
            filterTm.setPatient_id(filterDto.getPatient_id());
            filterTm.setName(filterDto.getName());
            filterTm.setContact(filterDto.getContact());
            filterTm.setEmail(filterDto.getEmail());
            filterTm.setSession_id(filterDto.getSession_id());
            filterTm.setDate(filterDto.getDate());
            filterTm.setProgram_id(filterDto.getProgram_id());
            observableList.add(filterTm);
        }
        tblFilter.setItems(observableList);
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String status = cmbStatus.getValue();
        filterByStatus(status);
    }

    @FXML
    void btnSearchOnProgramOnAction(ActionEvent event) {
        String programId = cmbProgrmaId.getValue();
        filterByProgram(programId);

    }

    public void refresh(){
        cmbProgrmaId.getSelectionModel().select("");
        cmbStatus.getSelectionModel().select("");
        tblFilter.getItems().clear();
    }

    public void filterByProgram(String programId){
        List<FilterDto> filterDtos = patientBo.filterByProgramId(programId);
        ObservableList<FilterTm> observableList = FXCollections.observableArrayList();

        for (FilterDto filterDto : filterDtos) {
            FilterTm filterTm = new FilterTm();
            filterTm.setPatient_id(filterDto.getPatient_id());
            filterTm.setName(filterDto.getName());
            filterTm.setContact(filterDto.getContact());
            filterTm.setEmail(filterDto.getEmail());
            filterTm.setSession_id(filterDto.getSession_id());
            filterTm.setDate(filterDto.getDate());
            filterTm.setProgram_id(filterDto.getProgram_id());
            observableList.add(filterTm);
        }
        tblFilter.setItems(observableList);
    }

    @FXML
    void btnClearOnActionn(ActionEvent event) {
        refresh();
    }


}
