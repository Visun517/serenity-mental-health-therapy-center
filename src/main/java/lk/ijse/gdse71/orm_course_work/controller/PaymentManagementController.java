package lk.ijse.gdse71.orm_course_work.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.gdse71.orm_course_work.HelloApplication;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.PaymentBo;
import lk.ijse.gdse71.orm_course_work.bo.custom.ProgramsBo;
import lk.ijse.gdse71.orm_course_work.bo.custom.SessionBo;
import lk.ijse.gdse71.orm_course_work.bo.exception.PaymentProcessingException;
import lk.ijse.gdse71.orm_course_work.dto.PaymentDto;
import lk.ijse.gdse71.orm_course_work.dto.ProgramDto;
import lk.ijse.gdse71.orm_course_work.dto.SessionDto;
import lk.ijse.gdse71.orm_course_work.dto.tm.PaymentTm;
import lk.ijse.gdse71.orm_course_work.entity.PatinetProgramsDetailsIds;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentManagementController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnPay;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbSessionId;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableColumn<PaymentTm, Double> colAmount;

    @FXML
    private TableColumn<PaymentTm, Date> colDate;

    @FXML
    private TableColumn<PaymentTm, String> colPatientId;

    @FXML
    private TableColumn<PaymentTm, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTm, String> colProgramId;

    @FXML
    private TableColumn<PaymentTm, String> colSessionId;

    @FXML
    private TableColumn<PaymentTm, String> colStatus;

    @FXML
    private TableColumn<PaymentTm, String> colPayemntType;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDuePaymentShow;

    @FXML
    private Label lblHeader;

    @FXML
    private Label lblPaymentIDShow;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblProgramNameShow;

    @FXML
    private Label lblSessionId;

    @FXML
    private Label lblStatus;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private Button btnReport;

    @FXML
    private ComboBox<String> cmbPayemntType;

    private final PaymentBo paymentBo = BoFactory.getInstance().getBo(BoFactory.BOType.PAYMENT);
    private final SessionBo sessionBo = BoFactory.getInstance().getBo(BoFactory.BOType.SESSION);
    private final ProgramsBo programsBo = BoFactory.getInstance().getBo(BoFactory.BOType.PROGRAMS);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("theraphyProgramId"));
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPayemntType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));

        btnReport.setDisable(true);
        refresh();
    }

    @FXML
    void btnPayOnAction(ActionEvent event) {
        String paymentId = lblPaymentIDShow.getText();
        String amountText = txtAmount.getText().trim();
        LocalDate date = datePicker.getValue();
        String status = cmbStatus.getValue();
        String paymentType = cmbPayemntType.getValue();
        String sessionId = cmbSessionId.getValue();

        // Regex patterns
        String amountRegex = "^\\d+(\\.\\d{1,2})?$";
        String sessionIdRegex = "^S\\d{3}$";

        // Validate amount
        if (amountText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Amount cannot be empty!").show();
            return;
        } else if (!amountText.matches(amountRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid amount! Must be a positive number (e.g., 1500 or 1500.00).").show();
            return;
        }
        double amount = Double.parseDouble(amountText);
        if (amount <= 0) {
            new Alert(Alert.AlertType.ERROR, "Amount must be greater than 0!").show();
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
        if (status == null || status.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a status!").show();
            return;
        }

        // Validate payment type
        if (paymentType == null || paymentType.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment type!").show();
            return;
        }

        // Validate session ID
        if (sessionId == null || sessionId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a session ID!").show();
            return;
        } else if (!sessionId.matches(sessionIdRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid session ID! Format: S followed by 3 digits (e.g., S001).").show();
            return;
        }

        // Additional validation: Check if the amount is within the due payment
        try {
            SessionDto session = sessionBo.getSession(sessionId);
            PatinetProgramsDetailsIds patinetProgramsDetailsIds = new PatinetProgramsDetailsIds();
            patinetProgramsDetailsIds.setPatient_id(session.getPatient_id());
            patinetProgramsDetailsIds.setTheraphy_pro_id(session.getProgram_id());
            double duePayment = paymentBo.getDuePayment(patinetProgramsDetailsIds);

            if (amount > duePayment) {
                new Alert(Alert.AlertType.ERROR, "Amount cannot exceed the due payment of LKR " + duePayment + "!").show();
                return;
            }

            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setPayment_id(paymentId);
            paymentDto.setAmount(amount);
            paymentDto.setDate(Date.valueOf(date));
            paymentDto.setStatus(status);
            paymentDto.setSessionId(sessionId);
            paymentDto.setPatientId(session.getPatient_id());
            paymentDto.setTheraphyProgramId(session.getProgram_id());
            paymentDto.setPaymentType(paymentType);

            boolean isPaymentSave = paymentBo.save(paymentDto);
            if (isPaymentSave) {
                new Alert(Alert.AlertType.INFORMATION, "Payment is saved").show();
                refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment is not saved").show();
            }
        } catch (SQLException | PaymentProcessingException e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateONAction(ActionEvent event) {
        String paymentId = lblPaymentIDShow.getText();
        String amountText = txtAmount.getText().trim();
        LocalDate date = datePicker.getValue();
        String status = cmbStatus.getValue();
        String paymentType = cmbPayemntType.getValue();
        String sessionId = cmbSessionId.getValue();

        // Regex patterns
        String amountRegex = "^\\d+(\\.\\d{1,2})?$";
        String sessionIdRegex = "^S\\d{3}$";

        // Validate amount
        if (amountText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Amount cannot be empty!").show();
            return;
        } else if (!amountText.matches(amountRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid amount! Must be a positive number (e.g., 1500 or 1500.00).").show();
            return;
        }
        double amount = Double.parseDouble(amountText);
        if (amount <= 0) {
            new Alert(Alert.AlertType.ERROR, "Amount must be greater than 0!").show();
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
        if (status == null || status.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a status!").show();
            return;
        }

        // Validate payment type
        if (paymentType == null || paymentType.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment type!").show();
            return;
        }

        // Validate session ID
        if (sessionId == null || sessionId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a session ID!").show();
            return;
        } else if (!sessionId.matches(sessionIdRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid session ID! Format: S followed by 3 digits (e.g., S001).").show();
            return;
        }

        // Additional validation: Check if the amount is within the due payment
        try {
            SessionDto session = sessionBo.getSession(sessionId);
            PatinetProgramsDetailsIds patinetProgramsDetailsIds = new PatinetProgramsDetailsIds();
            patinetProgramsDetailsIds.setPatient_id(session.getPatient_id());
            patinetProgramsDetailsIds.setTheraphy_pro_id(session.getProgram_id());
            double duePayment = paymentBo.getDuePayment(patinetProgramsDetailsIds);

            if (amount > duePayment) {
                new Alert(Alert.AlertType.ERROR, "Amount cannot exceed the due payment of LKR " + duePayment + "!").show();
                return;
            }

            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setPayment_id(paymentId);
            paymentDto.setAmount(amount);
            paymentDto.setDate(Date.valueOf(date));
            paymentDto.setStatus(status);
            paymentDto.setSessionId(sessionId);
            paymentDto.setPatientId(session.getPatient_id());
            paymentDto.setTheraphyProgramId(session.getProgram_id());
            paymentDto.setPaymentType(paymentType);

            boolean isPaymentUpdate = paymentBo.update(paymentDto);
            if (isPaymentUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Payment is updated").show();
                refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment is not updated").show();
            }
        } catch (SQLException | PaymentProcessingException e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String paymentId = lblPaymentIDShow.getText();
        try {
            boolean isDelete = paymentBo.delete(paymentId);
            if (isDelete) {
                new Alert(Alert.AlertType.INFORMATION, "Payment is deleted").show();
                refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment is not deleted").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tblPaymentOnCliked(MouseEvent event) {
        btnReport.setDisable(false);
        PaymentTm paymentTm = tblPayment.getSelectionModel().getSelectedItem();

        lblPaymentIDShow.setText(paymentTm.getPayment_id());
        txtAmount.setText(String.valueOf(paymentTm.getAmount()));

        Timestamp timestamp = (Timestamp) paymentTm.getDate();
        Date sqlDate = new Date(timestamp.getTime());
        datePicker.setValue(sqlDate.toLocalDate());

        cmbStatus.setValue(paymentTm.getStatus());
        cmbSessionId.setValue(paymentTm.getSessionId());
        cmbPayemntType.setValue(paymentTm.getPaymentType());

        try {
            SessionDto session = sessionBo.getSession(paymentTm.getSessionId());
            lblProgramNameShow.setText(programsBo.getProgramName(session.getProgram_id()));

            PatinetProgramsDetailsIds patinetProgramsDetailsIds = new PatinetProgramsDetailsIds();
            patinetProgramsDetailsIds.setPatient_id(session.getPatient_id());
            patinetProgramsDetailsIds.setTheraphy_pro_id(session.getProgram_id());

            double duePayment = paymentBo.getDuePayment(patinetProgramsDetailsIds);
            lblDuePaymentShow.setText(String.valueOf(duePayment));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnReportOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/PaymentInvoice.fxml"));
        Parent parent = fxmlLoader.load();
        PaymentInvoiceController paymentInvoiceController = fxmlLoader.getController();
        paymentInvoiceController.setPaymentId(lblPaymentIDShow.getText());

        Scene scene = new Scene(parent);
        Stage stage1 = new Stage();
        stage1.setTitle("Invoice..!");
        stage1.setScene(scene);
        stage1.show();
    }

    public void refresh() {
        setStatus();
        getAll();
        txtAmount.setText("");
        cmbStatus.setValue("");
//        cmbSessionId.setValue("");
        cmbPayemntType.setValue("");
        lblProgramNameShow.setText("");
        lblDuePaymentShow.setText("");
        datePicker.setValue(LocalDate.now());
        setPaymentTypes();
        try {
            lblPaymentIDShow.setText(paymentBo.getNextId());
            setSessionIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStatus() {
        ObservableList<String> status = FXCollections.observableArrayList("Complete", "Pending", "Overdue");
        cmbStatus.setItems(status);
    }

    public void setPaymentTypes() {
        ObservableList<String> status = FXCollections.observableArrayList("Transfer", "Cash", "Credit Card");
        cmbPayemntType.setItems(status);
    }

    public void setSessionIds() {
        List<SessionDto> all = null;
        try {
            all = sessionBo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> sessionIds = FXCollections.observableArrayList();

        for (SessionDto sessionDto : all) {
            if (!sessionDto.getStatus().equalsIgnoreCase("Completed")) {
                sessionIds.add(sessionDto.getSession_id());
            }
        }
        cmbSessionId.setItems(sessionIds);
    }

    @FXML
    void cmbSessionIdOnAction(ActionEvent event) {
        String sessionId = cmbSessionId.getValue();
        try {
            SessionDto sessionDto = sessionBo.getSession(sessionId);
            ProgramDto program = programsBo.getProgram(sessionDto.getProgram_id());
            lblProgramNameShow.setText(program.getName());

            PatinetProgramsDetailsIds patinetProgramsDetailsIds = new PatinetProgramsDetailsIds();
            patinetProgramsDetailsIds.setTheraphy_pro_id(program.getTheraphy_pro_id());
            patinetProgramsDetailsIds.setPatient_id(sessionDto.getPatient_id());

            double duePayment = paymentBo.getDuePayment(patinetProgramsDetailsIds);
            lblDuePaymentShow.setText(String.valueOf(duePayment));

            if (program.getFee() == duePayment) {
                new Alert(Alert.AlertType.ERROR, "Because this patient has not made the upfront payment, they need to pay LKR 10,000 as the upfront payment.").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAll() {
        try {
            List<PaymentDto> all = paymentBo.getAll();
            ObservableList<PaymentTm> paymentTms = FXCollections.observableArrayList();

            for (PaymentDto paymentDto : all) {
                PaymentTm paymentTm = new PaymentTm();
                paymentTm.setPayment_id(paymentDto.getPayment_id());
                paymentTm.setDate(paymentDto.getDate());
                paymentTm.setStatus(paymentDto.getStatus());
                paymentTm.setAmount(paymentDto.getAmount());
                paymentTm.setPatientId(paymentDto.getPatientId());
                paymentTm.setSessionId(paymentDto.getSessionId());
                paymentTm.setTheraphyProgramId(paymentDto.getTheraphyProgramId());
                paymentTm.setPaymentType(paymentDto.getPaymentType());

                paymentTms.add(paymentTm);
            }
            tblPayment.setItems(paymentTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}