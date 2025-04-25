package lk.ijse.gdse71.orm_course_work.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.gdse71.orm_course_work.HelloApplication;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.PatientBo;
import lk.ijse.gdse71.orm_course_work.bo.custom.ProgramsBo;
import lk.ijse.gdse71.orm_course_work.dto.PatientDto;
import lk.ijse.gdse71.orm_course_work.dto.tm.PatientTm;
import lk.ijse.gdse71.orm_course_work.entity.PatinetProgramsDetailsIds;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PatientManagmentController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnFilter;

    @FXML
    private Button btnPatinetDetails;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpadate;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableColumn<PatientTm, String> colContact;

    @FXML
    private TableColumn<PatientTm, String> colEmail;

    @FXML
    private TableColumn<PatientTm, String> colHistory;

    @FXML
    private TableColumn<PatientTm, String> colName;

    @FXML
    private TableColumn<PatientTm, String> colDate;

    @FXML
    private TableColumn<PatientTm, String> colPatinetId;

    @FXML
    private DatePicker datePickcker;

    @FXML
    private Label lblContact;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblHeader;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPatient;

    @FXML
    private Label lblPatinetHistory;

    @FXML
    private Label lblPatinetIdshow;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblchoosePragrams;

    @FXML
    private ListView<String> lstView;

    @FXML
    private TableView<PatientTm> tblPatient;

    @FXML
    private TextField txtContact;

    @FXML
    private TextArea txtHistory;

    @FXML
    private TextField txtName;

    @FXML
    private Label lblEmail;

    @FXML
    private TextField txtEmail;

    private final PatientBo patientBo = BoFactory.getInstance().getBo(BoFactory.BOType.PATIENT);
    private final ProgramsBo programsBo = BoFactory.getInstance().getBo(BoFactory.BOType.PROGRAMS);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPatinetId.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colHistory.setCellValueFactory(new PropertyValueFactory<>("medical_history"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        lstView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        refresh();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String contact = txtContact.getText().trim();
        String history = txtHistory.getText().trim();
        LocalDate date = datePickcker.getValue();
        String status = cmbStatus.getValue();
        ObservableList<String> programNames = lstView.getSelectionModel().getSelectedItems();

        // Regex patterns
        String nameRegex = "^[A-Za-z\\s]{3,50}$";
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String contactRegex = "^0\\d{9}$";
        String historyRegex = "^[A-Za-z0-9\\s.,!?-]{10,500}$";

        // Validate name
        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name cannot be empty!").show();
            return;
        } else if (!name.matches(nameRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid name! Use 3-50 characters (letters and spaces only).").show();
            return;
        }

        // Validate email
        if (email.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Email cannot be empty!").show();
            return;
        } else if (!email.matches(emailRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid email format! (e.g., example@domain.com)").show();
            return;
        }

        // Validate contact
        if (contact.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Contact cannot be empty!").show();
            return;
        } else if (!contact.matches(contactRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact number! Must be 10 digits starting with 0 (e.g., 0712345678).").show();
            return;
        }

        // Validate medical history
        if (history.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Medical history cannot be empty!").show();
            return;
        } else if (!history.matches(historyRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid medical history! Use 10-500 characters (letters, numbers, spaces, basic punctuation).").show();
            return;
        }

        // Validate date
        if (date == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a date!").show();
            return;
        } else if (date.isAfter(LocalDate.now())) {
            new Alert(Alert.AlertType.ERROR, "Date cannot be in the future!").show();
            return;
        }

        // Validate status
        if (status == null || status.equals("Choose one")) {
            new Alert(Alert.AlertType.ERROR, "Please select a status!").show();
            return;
        }

        // Validate programs selection
        if (programNames.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select at least one program!").show();
            return;
        }

        PatientDto patientDto = new PatientDto();
        patientDto.setPatient_id(lblPatinetIdshow.getText());
        patientDto.setName(name);
        patientDto.setDate(Date.valueOf(date));
        patientDto.setContact(contact);
        patientDto.setMedical_history(history);
        patientDto.setEmail(email);

        try {
            boolean isSaved = patientBo.patinetSave(patientDto, status, programNames);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Patient is saved...!").show();
                refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "Patient is not saved...!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpadateOnAction(ActionEvent event) {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String contact = txtContact.getText().trim();
        String history = txtHistory.getText().trim();
        LocalDate date = datePickcker.getValue();
        String status = cmbStatus.getValue();
        ObservableList<String> programNames = lstView.getSelectionModel().getSelectedItems();

        // Regex patterns
        String nameRegex = "^[A-Za-z\\s]{3,50}$";
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String contactRegex = "^0\\d{9}$";
        String historyRegex = "^[A-Za-z0-9\\s.,!?-]{10,500}$";

        // Validate name
        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name cannot be empty!").show();
            return;
        } else if (!name.matches(nameRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid name! Use 3-50 characters (letters and spaces only).").show();
            return;
        }

        // Validate email
        if (email.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Email cannot be empty!").show();
            return;
        } else if (!email.matches(emailRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid email format! (e.g., example@domain.com)").show();
            return;
        }

        // Validate contact
        if (contact.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Contact cannot be empty!").show();
            return;
        } else if (!contact.matches(contactRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact number! Must be 10 digits starting with 0 (e.g., 0712345678).").show();
            return;
        }

        // Validate medical history
        if (history.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Medical history cannot be empty!").show();
            return;
        } else if (!history.matches(historyRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid medical history! Use 10-500 characters (letters, numbers, spaces, basic punctuation).").show();
            return;
        }

        // Validate date
        if (date == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a date!").show();
            return;
        } else if (date.isAfter(LocalDate.now())) {
            new Alert(Alert.AlertType.ERROR, "Date cannot be in the future!").show();
            return;
        }

        // Validate status
        if (status == null || status.equals("Choose one")) {
            new Alert(Alert.AlertType.ERROR, "Please select a status!").show();
            return;
        }

        // Validate programs selection
        if (programNames.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select at least one program!").show();
            return;
        }

        PatientDto patientDto = new PatientDto();
        patientDto.setPatient_id(lblPatinetIdshow.getText());
        patientDto.setName(name);
        patientDto.setDate(Date.valueOf(date));
        patientDto.setContact(contact);
        patientDto.setMedical_history(history);
        patientDto.setEmail(email);

        try {
            boolean isUpdate = patientBo.update(patientDto, status, programNames);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Patient is update...!").show();
                refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "Patient is not update...!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            boolean isDelete = patientBo.delete(lblPatinetIdshow.getText());
            if (isDelete) {
                new Alert(Alert.AlertType.INFORMATION, "Patient is delete...!").show();
                refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "Patient is not delete...!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnFilterOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/FilterBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage1 = new Stage();
        stage1.setTitle("Filter Patient..!");
        stage1.setScene(scene);
        stage1.show();
    }

    @FXML
    void btnPatinetDetailsOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/PatinetMoreDetails.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage1 = new Stage();
        stage1.setTitle("Patient more details..!");
        stage1.setScene(scene);
        stage1.show();
    }

    @FXML
    void tblPatinetOnCliked(MouseEvent event) {
        PatientTm selectedItem = tblPatient.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblPatinetIdshow.setText(selectedItem.getPatient_id());
            txtName.setText(selectedItem.getName());
            txtContact.setText(selectedItem.getContact());
            txtHistory.setText(selectedItem.getMedical_history());
            datePickcker.setValue(selectedItem.getDate().toLocalDate());
            txtEmail.setText(selectedItem.getEmail());

            try {
                List<TheraphyProgram> patientPrograms = patientBo.getPatientPrograms(selectedItem.getPatient_id());
                lstView.getSelectionModel().clearSelection();

                for (TheraphyProgram theraphyProgram : patientPrograms) {
                    lstView.getSelectionModel().select(theraphyProgram.getName());

                    PatinetProgramsDetailsIds ids = new PatinetProgramsDetailsIds();
                    ids.setPatient_id(selectedItem.getPatient_id());
                    ids.setTheraphy_pro_id(theraphyProgram.getTheraphy_pro_id());

                    cmbStatus.setValue(patientBo.getPatinetStatus(ids));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Table row is empty..!").show();
        }
    }

    public void refresh() {
        try {
            lblPatinetIdshow.setText(patientBo.getNextId());
            getProgramsNames();
            setValuesCombo();
            getAllPatients();

            txtName.setText("");
            txtContact.setText("");
            txtHistory.setText("");
            cmbStatus.setValue("Choose one");
            txtEmail.setText("");
            datePickcker.setValue(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getProgramsNames() {
        try {
            List<String> allPrograms = programsBo.getAllPrograms();
            ObservableList<String> namesProgrmas = FXCollections.observableArrayList(allPrograms);
            lstView.setItems(namesProgrmas);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setValuesCombo() {
        ObservableList<String> values = FXCollections.observableArrayList("Active", "Dropped", "Completed");
        cmbStatus.setItems(values);
    }

    public void getAllPatients() {
        try {
            List<PatientDto> all = patientBo.getAll();
            ObservableList<PatientTm> patientTms = FXCollections.observableArrayList();

            for (PatientDto patient : all) {
                PatientTm patientTm = new PatientTm();
                patientTm.setPatient_id(patient.getPatient_id());
                patientTm.setName(patient.getName());
                patientTm.setContact(patient.getContact());
                patientTm.setEmail(patient.getEmail());
                patientTm.setMedical_history(patient.getMedical_history());
                patientTm.setDate(patient.getDate());
                patientTms.add(patientTm);
            }
            tblPatient.setItems(patientTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}