package lk.ijse.gdse71.orm_course_work.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.gdse71.orm_course_work.HelloApplication;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.TheraphistsBo;
import lk.ijse.gdse71.orm_course_work.dto.TherapistReportDto;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TheraphistReportController implements Initializable {

    public TableColumn<TherapistReportDto, String> colClassNameName;
    @FXML
    private Button btnSessionStats;

    @FXML
    private BarChart<String ,Number>chartTherapistPerformance;

    @FXML
    private TableColumn<TherapistReportDto,String> colSessionCount;

    @FXML
    private TableColumn<TherapistReportDto, Double> colSuccessRate;

    @FXML
    private TableColumn<TherapistReportDto, String> colTherapistId;

    @FXML
    private TableColumn<TherapistReportDto,Integer> colWorkload;

    @FXML
    private TableView<TherapistReportDto> tblTherapistReport;

    private final TheraphistsBo theraphistsBo = BoFactory.getInstance().getBo(BoFactory.BOType.THERAPIST);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("theraphists_id"));
        colClassNameName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSessionCount.setCellValueFactory(new PropertyValueFactory<>("sessionCount"));
        colSuccessRate.setCellValueFactory(new PropertyValueFactory<>("successRate"));
        colWorkload.setCellValueFactory(new PropertyValueFactory<>("workload"));

        loadTherapistPerformance();
    }

    private void loadTherapistPerformance() {
        List<TherapistReportDto> therapistList = theraphistsBo.getTherapistPerformance();
        tblTherapistReport.getItems().setAll(therapistList);

        XYChart.Series<String, Number> sessionCountSeries = new XYChart.Series<>();
        sessionCountSeries.setName("Session Count");
        XYChart.Series<String, Number> successRateSeries = new XYChart.Series<>();
        successRateSeries.setName("Success Rate (%)");

        for (TherapistReportDto therapist : therapistList) {
            sessionCountSeries.getData().add(new XYChart.Data<>(therapist.getName(), therapist.getSessionCount()));
            successRateSeries.getData().add(new XYChart.Data<>(therapist.getName(), therapist.getSuccessRate()));
        }

        chartTherapistPerformance.getData().addAll(sessionCountSeries, successRateSeries);
    }

    @FXML
    void onSessionStatsAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/SessionStaticsReports.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
