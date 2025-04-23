package lk.ijse.gdse71.orm_course_work.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.PaymentBo;
import lk.ijse.gdse71.orm_course_work.bo.custom.ProgramsBo;
import lk.ijse.gdse71.orm_course_work.dto.PaymentDto;
import lk.ijse.gdse71.orm_course_work.dto.tm.PatientTm;
import lk.ijse.gdse71.orm_course_work.dto.tm.PaymentTm;
import lk.ijse.gdse71.orm_course_work.entity.PatinetProgramsDetailsIds;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class financialReportGenerateController implements Initializable {

    @FXML
    private Button btnGenerate;

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
    private Label lblPendingPayments;

    @FXML
    private Label lblReceivedPayments;

    @FXML
    private Label lblTotalIncome;

    @FXML
    private TableView<PaymentTm> tblPendingPayments;

    private final ProgramsBo programsBo = BoFactory.getInstance().getBo(BoFactory.BOType.PROGRAMS);
    private final PaymentBo paymentBo = BoFactory.getInstance().getBo(BoFactory.BOType.PAYMENT);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("theraphyProgramId"));
        getAll();

    }

    @FXML
    void generateReportOnAction(ActionEvent event) {
        try {
            double totalIncome = programsBo.getTotalIncome();
            lblTotalIncome.setText(String.valueOf(totalIncome));

            double receivedPayment = paymentBo.getReceivedPayment();
            lblReceivedPayments.setText(String.valueOf(receivedPayment));

            double pendingPayment = paymentBo.getPendingPayment();
            lblPendingPayments.setText(String.valueOf(pendingPayment));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getAll(){
        try {
            List<PaymentDto> all = paymentBo.getAll();
            ObservableList<PaymentTm> paymentTms = FXCollections.observableArrayList();

            for (PaymentDto paymentDto : all){
                if (paymentDto.getStatus().equalsIgnoreCase("Pending")){
                    PaymentTm paymentTm = new PaymentTm();
                    paymentTm.setPayment_id(paymentDto.getPayment_id());
                    paymentTm.setDate(paymentDto.getDate());
                    paymentTm.setStatus(paymentDto.getStatus());
                    paymentTm.setAmount(paymentDto.getAmount());
                    paymentTm.setPatientId(paymentDto.getPatientId());
                    paymentTm.setSessionId(paymentDto.getSessionId());
                    paymentTm.setTheraphyProgramId(paymentDto.getTheraphyProgramId());

                    paymentTms.add(paymentTm);
                }
            }
            tblPendingPayments.setItems(paymentTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
