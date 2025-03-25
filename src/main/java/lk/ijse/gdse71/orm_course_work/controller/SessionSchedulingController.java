package lk.ijse.gdse71.orm_course_work.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.PatientBo;
import lk.ijse.gdse71.orm_course_work.bo.custom.ProgramsBo;
import lk.ijse.gdse71.orm_course_work.bo.custom.SessionBo;
import lk.ijse.gdse71.orm_course_work.bo.custom.TheraphistsBo;
import lk.ijse.gdse71.orm_course_work.dto.PatientDto;
import lk.ijse.gdse71.orm_course_work.dto.ProgramDto;
import lk.ijse.gdse71.orm_course_work.dto.SessionDto;
import lk.ijse.gdse71.orm_course_work.dto.TherapistDto;
import lk.ijse.gdse71.orm_course_work.dto.tm.SessionTm;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;
import lk.ijse.gdse71.orm_course_work.entity.TheraphySession;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;

public class SessionSchedulingController implements Initializable {

//    @FXML
    //private AnchorPane DatePicker;

    @FXML
    private Button btnBook;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnRechedule;

    @FXML
    private ComboBox<String> cmbPatientIds;

    @FXML
    private ComboBox<String> cmbPrograms;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private ComboBox<String> cmbThearapistId;

    @FXML
    private TableColumn<TheraphySession, Date> colDate;

    @FXML
    private TableColumn<TheraphySession, String> colPatinetiId;

    @FXML
    private TableColumn<TheraphySession, String> colPrograms;

    @FXML
    private TableColumn<TheraphySession, String> colSessionId;

    @FXML
    private TableColumn<TheraphySession, String> colStatus;

    @FXML
    private TableColumn<TheraphySession, String> colTherapistId;

    @FXML
    private TableColumn<TheraphySession, Time> colTime;

    @FXML
    private Label lblDatePicker;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblHeader;

    @FXML
    private Label lblPatientId;

    @FXML
    private Label lblProgrmas;

    @FXML
    private Label lblSessionId;

    @FXML
    private Label lblSessionIdShow;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblTheraphistId;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<SessionTm> tblSessions;

    @FXML
    private Label lblProName;

    @FXML
    private TextField txtTime;

    private final SessionBo sessionBo = BoFactory.getInstance().getBo(BoFactory.BOType.SESSION);
    private final PatientBo patientBo = BoFactory.getInstance().getBo(BoFactory.BOType.PATIENT);
    private final ProgramsBo programsBo = BoFactory.getInstance().getBo(BoFactory.BOType.PROGRAMS);
    private final TheraphistsBo theraphistsBo = BoFactory.getInstance().getBo(BoFactory.BOType.THERAPIST);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPrograms.setCellValueFactory(new PropertyValueFactory<>("program_id"));
        colPatinetiId.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("session_id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapist_id"));
        refresh();
    }

    @FXML
    void btnBookOnAction(ActionEvent event) {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setSession_id(lblSessionIdShow.getText());
        sessionDto.setPatient_id(cmbPatientIds.getValue());
        sessionDto.setProgram_id(cmbPrograms.getValue());
        sessionDto.setTherapist_id(cmbThearapistId.getValue());
        sessionDto.setTime(Time.valueOf(txtTime.getText() + ":00"));
        sessionDto.setDate(Date.valueOf(datePicker.getValue()));
        sessionDto.setStatus(cmbStatus.getValue());

        try {
            boolean isBooked = sessionBo.book(sessionDto);
            if (isBooked) {
                new Alert(Alert.AlertType.INFORMATION, "User is booked...!").show();
                refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "User is not booked...!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setSession_id(lblSessionIdShow.getText());
        sessionDto.setPatient_id(cmbPatientIds.getValue());
        sessionDto.setProgram_id(cmbPrograms.getValue());
        sessionDto.setTherapist_id(cmbThearapistId.getValue());
        sessionDto.setTime(Time.valueOf(txtTime.getText() + ":00"));
        sessionDto.setDate(Date.valueOf(datePicker.getValue()));
        sessionDto.setStatus("Cancelled");

        try {
            boolean isCanceled = sessionBo.cancel(sessionDto);
            if (isCanceled) {
                new Alert(Alert.AlertType.INFORMATION, "User is canceled...!").show();
                refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "User is not canceled...!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnRecheduleOnAction(ActionEvent event) {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setSession_id(lblSessionIdShow.getText());
        sessionDto.setPatient_id(cmbPatientIds.getValue());
        sessionDto.setProgram_id(cmbPrograms.getValue());
        sessionDto.setTherapist_id(cmbThearapistId.getValue());
        sessionDto.setTime(Time.valueOf(txtTime.getText() + ":00"));

        try {
            getAvailableTherapist(cmbPrograms.getValue(), Time.valueOf(txtTime.getText() + ":00"), Date.valueOf(datePicker.getValue()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sessionDto.setDate(Date.valueOf(datePicker.getValue()));
        sessionDto.setStatus(cmbStatus.getValue());

        try {
            boolean isRescheduled = sessionBo.reschedule(sessionDto);
            if (isRescheduled) {
                new Alert(Alert.AlertType.INFORMATION, "User is rescheduled...!").show();
                refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "User is not rescheduled...!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void cmbProgramsOnAction(ActionEvent event) {
        String programId = cmbPrograms.getSelectionModel().getSelectedItem();
        Time time = Time.valueOf(txtTime.getText() + ":00");

        if (programId != null && txtTime.getText() != null) {
            try {
                getAvailableTherapist(programId, time, Date.valueOf(datePicker.getValue()));
                if (programsBo.getProgramName(programId) == null) {
                    cmbPrograms.setValue("Select Program");
                }
                lblProName.setText(programsBo.getProgramName(programId));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void refresh() {
        try {
            lblSessionIdShow.setText(sessionBo.getNextId());
            setStatusCmb();
            setPatientIdsCmb();
            getPrograms();
            getAll();

            cmbPatientIds.setValue("Select patient");
            cmbStatus.setValue("Select status");
            cmbThearapistId.setValue("Select Therapist");
            lblProName.setText("");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStatusCmb() {
        ObservableList<String> values = FXCollections.observableArrayList("Cancelled", "Scheduled", "Completed");
        cmbStatus.setItems(values);
    }

    public void setPatientIdsCmb() throws SQLException {
        List<PatientDto> all = patientBo.getAll();
        ObservableList<String> patientIds = FXCollections.observableArrayList();
        for (PatientDto patientDto : all) {
            patientIds.add(patientDto.getPatient_id());
        }
        cmbPatientIds.setItems(patientIds);
    }

    public void getPrograms() throws SQLException {
        List<ProgramDto> allPrograms = programsBo.getAll();
        ObservableList<String> ids = FXCollections.observableArrayList();
        for (ProgramDto programDto : allPrograms) {
            ids.add(programDto.getTheraphy_pro_id());
        }
        cmbPrograms.setItems(ids);
    }

    public void getAvailableTherapist(String programId, Time time, Date date) throws SQLException {
        List<TherapistDto> availableTherapist = theraphistsBo.getAvailableTherapist(programId, time, date);

        if (availableTherapist.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Not available therapists..!").show();
        }

        ObservableList<String> therapistIds = FXCollections.observableArrayList();

        for (TherapistDto therapistDto : availableTherapist) {
            therapistIds.add(therapistDto.getTheraphists_id());
        }
        cmbThearapistId.setItems(therapistIds);
    }

    public void getAll() throws SQLException {
        List<SessionDto> sessionDtoList = sessionBo.getAll();
        ObservableList<SessionTm> sessions = FXCollections.observableArrayList();

        for (SessionDto sessionDto : sessionDtoList) {
            SessionTm sessionTm = new SessionTm();
            sessionTm.setSession_id(sessionDto.getSession_id());
            sessionTm.setDate(sessionDto.getDate());
            sessionTm.setStatus(sessionDto.getStatus());
            sessionTm.setTime(sessionDto.getTime());
            sessionTm.setPatient_id(sessionDto.getPatient_id());
            sessionTm.setProgram_id(sessionDto.getProgram_id());
            sessionTm.setTherapist_id(sessionDto.getTherapist_id());

            sessions.add(sessionTm);
        }
        tblSessions.setItems(sessions);
    }

    @FXML
    void tblSessionOncliked(MouseEvent event) {
        SessionTm selectedItem = tblSessions.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblSessionIdShow.setText(selectedItem.getSession_id());
            cmbPatientIds.setValue(selectedItem.getPatient_id());
            cmbThearapistId.setValue(selectedItem.getTherapist_id());

            String time = String.valueOf(selectedItem.getTime());
            String showTime = time.substring(0, 5);


            txtTime.setText(showTime);
            datePicker.setValue(selectedItem.getDate().toLocalDate());
            cmbStatus.setValue(selectedItem.getStatus());
            cmbPrograms.getSelectionModel().select(selectedItem.getProgram_id());
        } else {
            new Alert(Alert.AlertType.ERROR, "Row have no data..!").show();
        }


    }
}
