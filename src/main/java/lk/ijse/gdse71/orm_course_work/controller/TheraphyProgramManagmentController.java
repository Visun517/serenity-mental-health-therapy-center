package lk.ijse.gdse71.orm_course_work.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.orm_course_work.bo.BoFactory;
import lk.ijse.gdse71.orm_course_work.bo.custom.ProgramsBo;
import lk.ijse.gdse71.orm_course_work.bo.exception.DuplicateException;
import lk.ijse.gdse71.orm_course_work.bo.exception.MissingFieldException;
import lk.ijse.gdse71.orm_course_work.dto.ProgramDto;
import lk.ijse.gdse71.orm_course_work.dto.tm.ProgramTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TheraphyProgramManagmentController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpadate;

    @FXML
    private TableColumn<ProgramTm, String> colDescription;

    @FXML
    private TableColumn<ProgramTm, String> colDuration;

    @FXML
    private TableColumn<ProgramTm, Double> colFee;

    @FXML
    private TableColumn<ProgramTm, String> colName;

    @FXML
    private TableColumn<ProgramTm, String> colProId;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblFee;

    @FXML
    private Label lblHeader;

    @FXML
    private Label lblName;

    @FXML
    private Label lblProId;

    @FXML
    private Label lblProIdShow;

    @FXML
    private TableView<ProgramTm> tblProgram;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtName;

    private final ProgramsBo programBo = BoFactory.getInstance().getBo(BoFactory.BOType.PROGRAMS);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProId.setCellValueFactory(new PropertyValueFactory<>("theraphy_pro_id"));
        refresh();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String name = txtName.getText().trim();
        String duration = txtDuration.getText().trim();
        String fee = txtFee.getText().trim();
        String description = txtDescription.getText().trim();

        // Regex patterns
        String nameRegex = "^[A-Za-z0-9\\s]{3,50}$";
        String durationRegex = "^\\d+\\s(weeks|months)$";
        String feeRegex = "^\\d+(\\.\\d{1,2})?$";
        String descriptionRegex = "^[A-Za-z0-9\\s.,!?-]{10,500}$";

        // Validate name
        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name cannot be empty!").show();
            return;
        } else if (!name.matches(nameRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid name! Use 3-50 characters (letters, numbers, spaces).").show();
            return;
        }

        // Validate duration
        if (duration.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Duration cannot be empty!").show();
            return;
        } else if (!duration.matches(durationRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid duration! Format: number followed by 'weeks' or 'months' (e.g., '4 weeks').").show();
            return;
        }

        // Validate fee
        if (fee.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fee cannot be empty!").show();
            return;
        } else if (!fee.matches(feeRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid fee! Must be a positive number (e.g., 1500 or 1500.00).").show();
            return;
        }

        // Validate description
        if (description.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Description cannot be empty!").show();
            return;
        } else if (!description.matches(descriptionRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid description! Use 10-500 characters (letters, numbers, spaces, basic punctuation).").show();
            return;
        }

        ProgramDto programDto = new ProgramDto();
        programDto.setTheraphy_pro_id(lblProIdShow.getText());
        programDto.setName(name);
        programDto.setDuration(duration);
        programDto.setFee(Double.parseDouble(fee));
        programDto.setDescription(description);


            boolean isSaved = false;
            try {
                isSaved = programBo.save(programDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Program is saved...!").show();
                    refresh();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Program is not saved...!").show();
                }
            } catch (SQLException | MissingFieldException | DuplicateException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
    }

    @FXML
    void btnUpadateOnAction(ActionEvent event) {
        String name = txtName.getText().trim();
        String duration = txtDuration.getText().trim();
        String fee = txtFee.getText().trim();
        String description = txtDescription.getText().trim();

        // Regex patterns
        String nameRegex = "^[A-Za-z0-9\\s]{3,50}$";
        String durationRegex = "^\\d+\\s(weeks|months)$";
        String feeRegex = "^\\d+(\\.\\d{1,2})?$";
        String descriptionRegex = "^[A-Za-z0-9\\s.,!?-]{10,500}$";

        // Validate name
        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name cannot be empty!").show();
            return;
        } else if (!name.matches(nameRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid name! Use 3-50 characters (letters, numbers, spaces).").show();
            return;
        }

        // Validate duration
        if (duration.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Duration cannot be empty!").show();
            return;
        } else if (!duration.matches(durationRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid duration! Format: number followed by 'weeks' or 'months' (e.g., '4 weeks').").show();
            return;
        }

        // Validate fee
        if (fee.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fee cannot be empty!").show();
            return;
        } else if (!fee.matches(feeRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid fee! Must be a positive number (e.g., 1500 or 1500.00).").show();
            return;
        }

        // Validate description
        if (description.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Description cannot be empty!").show();
            return;
        } else if (!description.matches(descriptionRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid description! Use 10-500 characters (letters, numbers, spaces, basic punctuation).").show();
            return;
        }

        ProgramDto programDto = new ProgramDto();
        programDto.setTheraphy_pro_id(lblProIdShow.getText());
        programDto.setName(name);
        programDto.setDuration(duration);
        programDto.setFee(Double.parseDouble(fee));
        programDto.setDescription(description);

        try {
            boolean isSaved = programBo.update(programDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Program is updated...!").show();
                refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "Program is not updated...!").show();
            }
        } catch (SQLException | MissingFieldException | DuplicateException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            boolean isDelete = programBo.delete(lblProIdShow.getText());
            if (isDelete) {
                new Alert(Alert.AlertType.INFORMATION, "Program is delete...!").show();
                refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "Program is not delete...!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tblProgramOnCliked(MouseEvent event) {
        ProgramTm programTm = tblProgram.getSelectionModel().getSelectedItem();
        if (programTm != null) {
            lblProIdShow.setText(programTm.getTheraphy_pro_id());
            txtName.setText(programTm.getName());
            txtDuration.setText(programTm.getDuration());
            txtFee.setText(String.valueOf(programTm.getFee()));
            txtDescription.setText(programTm.getDescription());
        }
    }

    public void refresh() {
        try {
            lblProIdShow.setText(programBo.getNextId());
            getAllPrograms();

            txtName.setText("");
            txtDuration.setText("");
            txtFee.setText("");
            txtDescription.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllPrograms() {
        try {
            List<ProgramDto> all = programBo.getAll();
            ObservableList<ProgramTm> programTms = FXCollections.observableArrayList();

            for (ProgramDto program : all) {
                ProgramTm programTm = new ProgramTm();
                programTm.setTheraphy_pro_id(program.getTheraphy_pro_id());
                programTm.setName(program.getName());
                programTm.setDuration(program.getDuration());
                programTm.setFee(program.getFee());
                programTm.setDescription(program.getDescription());

                programTms.add(programTm);
            }
            tblProgram.setItems(programTms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}