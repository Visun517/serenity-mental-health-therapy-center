package lk.ijse.gdse71.orm_course_work.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.SessionBo;
import lk.ijse.gdse71.orm_course_work.dto.SessionStaticsDto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SessionStaticsReportsController implements Initializable {

    @FXML
    private BarChart<String,Number> chartSessionStats;

    @FXML
    private TableColumn<SessionStaticsDto, Integer> colCompletedSessions;

    @FXML
    private TableColumn<SessionStaticsDto, Integer> colPendingSessions;

    @FXML
    private TableColumn<SessionStaticsDto, String> colProgramName;

    @FXML
    private TableColumn<SessionStaticsDto, Integer> colTotalSessions;

    @FXML
    private TableView<SessionStaticsDto> tblSessionStats;

    private final SessionBo sessionBo = BoFactory.getInstance().getBo(BoFactory.BOType.SESSION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colTotalSessions.setCellValueFactory(new PropertyValueFactory<>("totalSessions"));
        colCompletedSessions.setCellValueFactory(new PropertyValueFactory<>("completedSessions"));
        colPendingSessions.setCellValueFactory(new PropertyValueFactory<>("pendingSessions"));
        loadSessionStats();

    }
    private void loadSessionStats() {
        List<SessionStaticsDto> statsList = sessionBo.getSessionStatistics();
        tblSessionStats.getItems().setAll(statsList);

        XYChart.Series<String, Number> totalSeries = new XYChart.Series<>();
        totalSeries.setName("Total Sessions");
        XYChart.Series<String, Number> completedSeries = new XYChart.Series<>();
        completedSeries.setName("Completed Sessions");
        XYChart.Series<String, Number> pendingSeries = new XYChart.Series<>();
        pendingSeries.setName("Pending Sessions");

        for (SessionStaticsDto stats : statsList) {
            totalSeries.getData().add(new XYChart.Data<>(stats.getProgramName(), stats.getTotalSessions()));
            completedSeries.getData().add(new XYChart.Data<>(stats.getProgramName(), stats.getCompletedSessions()));
            pendingSeries.getData().add(new XYChart.Data<>(stats.getProgramName(), stats.getPendingSessions()));
        }

        chartSessionStats.getData().addAll(totalSeries, completedSeries, pendingSeries);
    }
}
