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
import lk.ijse.gdse71.orm_course_work.bo.custom.TheraphistsBo;
import lk.ijse.gdse71.orm_course_work.dto.TherapistDto;
import lk.ijse.gdse71.orm_course_work.dto.tm.TherapistTm;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TheraphistsManagmentController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpadate;

    @FXML
    private ListView<String> lstPrograms;

    @FXML
    private TableColumn<TherapistTm, String> colContact;

    @FXML
    private TableColumn<TherapistTm, String> colEmail;

    @FXML
    private TableColumn<TherapistTm, String> colId;

    @FXML
    private TableColumn<TherapistTm, String> colName;

    @FXML
    private Label lblContact;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblHeader;

    @FXML
    private Label lblName;

    @FXML
    private Label lblProgrmas;

    @FXML
    private Label lblTHeraphistId;

    @FXML
    private Label lblTHeraphistIdShow;

    @FXML
    private TableView<TherapistTm> tblTheraphists;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private Button btnTrackschedule;

     private final ProgramsBo programsBo = BoFactory.getInstance().getBo(BoFactory.BOType.PROGRAMS);
     private final TheraphistsBo theraphistsBo = BoFactory.getInstance().getBo(BoFactory.BOType.THERAPIST);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("theraphists_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        lstPrograms.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        getAll();
        refresh();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblTHeraphistIdShow.getText();

        try {
            boolean isDelete = theraphistsBo.delete(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION, "Therapist is deleted...!").show();
                getAll();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR, "Therapist is not deleted...!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        TherapistDto therapistDto = new TherapistDto();
        therapistDto.setTheraphists_id(lblTHeraphistIdShow.getText());
        therapistDto.setName(txtName.getText());
        therapistDto.setEmail(txtEmail.getText());
        therapistDto.setContact(txtContact.getText());

        List<String> programNames = lstPrograms.getSelectionModel().getSelectedItems();
        try {
            boolean isSaved = theraphistsBo.saveTherapist(therapistDto,programNames);
            if (isSaved){
                getAll();
                refresh();
                new Alert(Alert.AlertType.INFORMATION, "Therapist is saved...!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Therapist is not saved...!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpadateOnAction(ActionEvent event) {
        TherapistDto therapistDto = new TherapistDto();
        therapistDto.setTheraphists_id(lblTHeraphistIdShow.getText());
        therapistDto.setName(txtName.getText());
        therapistDto.setEmail(txtEmail.getText());
        therapistDto.setContact(txtContact.getText());

        List<String> programNames = lstPrograms.getSelectionModel().getSelectedItems();
        try {
            boolean isUpdate = theraphistsBo.update(therapistDto,programNames);
            if (isUpdate){
                getAll();
                refresh();
                new Alert(Alert.AlertType.INFORMATION, "Therapist is updated...!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Therapist is not updated...!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void cmbProgrmasOnAction(ActionEvent event) {

    }

    @FXML
    void tblTheraphistsOncliked(MouseEvent event) {
        TherapistTm selectedItem = tblTheraphists.getSelectionModel().getSelectedItem();

        if (selectedItem != null){
            lblTHeraphistIdShow.setText(selectedItem.getTheraphists_id());
            txtName.setText(selectedItem.getName());
            txtEmail.setText(selectedItem.getEmail());
            txtContact.setText(selectedItem.getContact());

            try{
                List<TheraphyProgram> assigningPrograms = theraphistsBo.getAssigningPrograms(selectedItem.getTheraphists_id());
//                lstPrograms.getSelectionModel().clearSelection();

                for (TheraphyProgram theraphyProgram : assigningPrograms) {
                    lstPrograms.getSelectionModel().select(theraphyProgram.getName());
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Select Theraphist...!").show();
        }

    }

    @FXML
    void btnTrackscheduleOnAction(ActionEvent event) {

    }

    void refresh(){
        setProgramsCmb();
        try {
            lblTHeraphistIdShow.setText(theraphistsBo.getNextId());
            txtEmail.setText("");
            txtName.setText("");
            txtContact.setText("");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setProgramsCmb(){
        try {
            List<String> programs = programsBo.getAllPrograms();
            ObservableList<String> progrmas = FXCollections.observableArrayList(programs);
            lstPrograms.setItems(progrmas);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAll(){

        try {
            List<TherapistDto> therapistDtos = theraphistsBo.getAll();
            ObservableList<TherapistTm> therapistTms = FXCollections.observableArrayList();

            for (TherapistDto therapist : therapistDtos) {
                TherapistTm therapistTm = new TherapistTm();
                therapistTm.setTheraphists_id(therapist.getTheraphists_id());
                therapistTm.setContact(therapist.getContact());
                therapistTm.setName(therapist.getName());
                therapistTm.setEmail(therapist.getEmail());

                therapistTms.add(therapistTm);
            }
            tblTheraphists.setItems(therapistTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
