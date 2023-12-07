package com.app.springfx.infraestructure.gui.controller;

import com.app.springfx.application.ComputerDeviceService;
import com.app.springfx.application.MaintenanceLogService;
import com.app.springfx.application.PeripheralService;
import com.app.springfx.application.TechnicianService;
import com.app.springfx.domain.ComputerDevice;
import com.app.springfx.domain.MaintenanceLog;
import com.app.springfx.domain.Peripheral;
import com.app.springfx.domain.Technician;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LogGuiController extends AbstractGuiController{
    public MaintenanceLogService maintenanceLogService;

    public ComputerDeviceService computerDeviceService;
    public PeripheralService peripheralService;
    public TechnicianService technicianService;
    @FXML
    public ComboBox<ComputerDevice> cbComputerEquipment;
    @FXML
    public ComboBox<Peripheral> cbPeripheral = new ComboBox<>();
    @FXML
    public ComboBox<Technician> cbTechnician = new ComboBox<>();
    @FXML
    public TextArea taDescription;
    @FXML
    public DatePicker dpMaintenanceDate;
    public boolean isUpdate = false;
    public MaintenanceLog maintenanceLog = new MaintenanceLog();
    public Button btnSave;
    public Label lbBitacora;



    public LogGuiController(
            ApplicationContext applicationContext,
            MaintenanceLogService maintenanceLogService,
            ComputerDeviceService computerDeviceService,
            PeripheralService peripheralService,
            TechnicianService technicianService
    ) throws IOException {
        super(applicationContext);
        this.maintenanceLogService = maintenanceLogService;
        this.computerDeviceService = computerDeviceService;
        this.peripheralService = peripheralService;
        this.technicianService = technicianService;
    }
    public void initialize(){
        Platform.runLater(() -> {
            Stage stage = (Stage) cbComputerEquipment.getScene().getWindow();
            stage.sizeToScene();
            stage.setMaxWidth(650);
            stage.setMaxHeight(500);
            stage.setMinHeight(500);
            stage.setMinWidth(650);
            initializeComboBox();
            if (isUpdate) {
                updateFields();
            }else{
                ComboBox<?>[] combo = {cbTechnician, cbPeripheral, cbComputerEquipment};
                cleanFields(combo);
            }
        });

    }

    public void backToMain(javafx.event.ActionEvent actionEvent) {
        isUpdate = false;
        maintenanceLog = new MaintenanceLog();
        openMainWindowAndCloseCurrent((javafx.stage.Stage) cbPeripheral.getScene().getWindow());
    }

    public void initializeComboBox(){

        var devices = FXCollections.observableArrayList(computerDeviceService.getAll());
        var peripherals = FXCollections.observableArrayList(peripheralService.getAll());
        var technician = FXCollections.observableArrayList(technicianService.getAll());

        this.cbComputerEquipment.setItems(devices);
        this.cbPeripheral.setItems(peripherals);
        this.cbTechnician.setItems(technician);
    }

    public void save(javafx.event.ActionEvent actionEvent) {
        ComboBox<?>[] combo = {cbTechnician, cbPeripheral, cbComputerEquipment};
        if(validateFields(combo)){
            var maintenanceLog = MaintenanceLog.builder()
                    .computerDevice(cbComputerEquipment.getSelectionModel().getSelectedItem())
                    .peripheral(cbPeripheral.getSelectionModel().getSelectedItem())
                    .technician(cbTechnician.getSelectionModel().getSelectedItem())
                    .description(taDescription.getText())
                    .date(dpMaintenanceDate.getValue())
                    .build();

            var response = maintenanceLogService.save(maintenanceLog);
            if (response != null){
                showAlert(Alert.AlertType.INFORMATION, "Guardado Exitoso", "La bitacora Fue Guardada Exitosamente");
                cleanFields(combo);
                initializeComboBox();
            }else {
                showAlert(Alert.AlertType.ERROR, "Error", "La Bitacora No Pudo Ser Guardada");
            }
        }else {
            showAlert(Alert.AlertType.ERROR, "Campos Invalidos", "Existen Campos Invalidos porfavor corroboralos");
        }
    }

    public boolean validateFields(ComboBox<?>[] comboBoxes) {
        boolean validComboBoxes = true;

        for (ComboBox<?> comboBox : comboBoxes) {
            if (comboBox.getValue() == null) {
                comboBox.setStyle("-fx-background-color: red;");
                validComboBoxes = false;
            } else {
                comboBox.setStyle("");
            }
        }

        if (taDescription.getText().isEmpty()){
            taDescription.setStyle("-fx-control-inner-background: red;");
            validComboBoxes = false;
        }else {
            taDescription.setStyle("");
        }

        if (dpMaintenanceDate.getValue() == null){
            dpMaintenanceDate.setStyle("-fx-background-color: red;");
            validComboBoxes = false;
        }else {
            dpMaintenanceDate.setStyle("");
        }

        return validComboBoxes;
    }

    public void cleanFields(ComboBox<?>[] comboBoxes){
        for (var comboBox : comboBoxes ){
            comboBox.getSelectionModel().clearSelection();
        }
        dpMaintenanceDate.setValue(null);
        taDescription.setText("");
    }

    public void updateFields(){
        cbComputerEquipment.getSelectionModel().select(maintenanceLog.getComputerDevice());
        cbPeripheral.getSelectionModel().select(maintenanceLog.getPeripheral());
        cbTechnician.getSelectionModel().select(maintenanceLog.getTechnician());
        taDescription.setText(maintenanceLog.getDescription());
        dpMaintenanceDate.setValue(maintenanceLog.getDate());
        btnSave.setText("Actualizar");
        lbBitacora.setText("Actualizar Bitacora");
    }
}