package lk.ijse.gdse71.orm_course_work.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.PatientBo;
import lk.ijse.gdse71.orm_course_work.bo.custom.ProgramsBo;
import lk.ijse.gdse71.orm_course_work.bo.custom.SessionBo;
import lk.ijse.gdse71.orm_course_work.dto.HistoryDto;
import lk.ijse.gdse71.orm_course_work.dto.PatientDto;
import lk.ijse.gdse71.orm_course_work.dto.PatientProgramsDetailsDto;
import lk.ijse.gdse71.orm_course_work.dto.ProgramDto;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TheraphySessionHistoryController implements Initializable {

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<HistoryDto, Double> colPaidAmount;

    @FXML
    private TableColumn<HistoryDto, Double> colPendingAmount;

    @FXML
    private TableColumn<HistoryDto, String> colProgramId;

    @FXML
    private TableColumn<HistoryDto, String> colSessionId;

    @FXML
    private TableColumn<HistoryDto, String> colStatus;

    @FXML
    private TableColumn<HistoryDto, Double> colTotalFee;

    @FXML
    private TableView<HistoryDto> tblTherapyHistory;

    @FXML
    private TextField txtPatientId;

    @FXML
    private ComboBox<String> cmbPatinetIds;

    @FXML
    private Button btnClear;

    private final PatientBo patientBo = BoFactory.getInstance().getBo(BoFactory.BOType.PATIENT);
    private final SessionBo sessionBo = BoFactory.getInstance().getBo(BoFactory.BOType.SESSION);
    private final ProgramsBo programsBo = BoFactory.getInstance().getBo(BoFactory.BOType.PROGRAMS);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaidAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        colPendingAmount.setCellValueFactory(new PropertyValueFactory<>("pendingAmount"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("session_id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTotalFee.setCellValueFactory(new PropertyValueFactory<>("totalFee"));
        setComboBox();
    }

    @FXML
    void searchPatientHistory(ActionEvent event) {
        String patientId = cmbPatinetIds.getValue();
        try {
            List<PatientProgramsDetailsDto> patientProgramsDetailsDtos = patientBo.getTheraphyHistoryById(patientId);
            ObservableList<HistoryDto> historyDtos = FXCollections.observableArrayList();

            for (PatientProgramsDetailsDto patientProgramsDetailsDto : patientProgramsDetailsDtos){
                HistoryDto historyDto = new HistoryDto();
                historyDto.setProgramId(patientProgramsDetailsDto.getProgram_id());

                String sessionId = sessionBo.getPatinetProgramSession(patientProgramsDetailsDto.getPatient_id(),patientProgramsDetailsDto.getProgram_id());
                if (sessionId != null){
                    historyDto.setSession_id(sessionId);
                }else{
                    historyDto.setSession_id("Still not assigned");
                }


                ProgramDto program = programsBo.getProgram(patientProgramsDetailsDto.getProgram_id());
                historyDto.setTotalFee(String.valueOf(program.getFee()));

                historyDto.setPaidAmount(String.valueOf(program.getFee() - patientProgramsDetailsDto.getAmount()));
                historyDto.setPendingAmount(String.valueOf(patientProgramsDetailsDto.getAmount()));
                historyDto.setStatus(patientProgramsDetailsDto.getStatus());

                historyDtos.add(historyDto);
            }
            tblTherapyHistory.setItems(historyDtos);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void setComboBox(){
        try {
            List<PatientDto> all = patientBo.getAll();
            for (PatientDto patientDto : all) {
                cmbPatinetIds.getItems().add(patientDto.getPatient_id());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        cmbPatinetIds.setValue("");
        tblTherapyHistory.getItems().clear();

    }


}
