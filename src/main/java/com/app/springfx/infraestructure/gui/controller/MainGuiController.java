package com.app.springfx.infraestructure.gui.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class MainGuiController extends AbstractGuiController {
    @FXML
    public Button btnComputerCenters;
    public Button btnPeripheral;
    public Button btnRegisterCC;
    public Button btnRegisterEC;
    public Button btnComputerDevices;

    public Button btnTechnician;
    public Button btnLog;
    public Button btnConsultPeripherals;
    public Button btnConsultLog;

    public Button btnRegisterProgram;
    public Button btnConsultProgram;
    public Button btnAssingProgram;
    public Button btnConsultECP;



    @Value("classpath:/fxml/ComputerCenter.fxml")
    private Resource computerCenterView;

    @Value("classpath:/fxml/RegisterPeripheral.fxml")
    private Resource peripheralView;

    @Value("classpath:/fxml/RegisterCC.fxml")
    private Resource registerCCView;

    @Value("classpath:/fxml/RegisterEC.fxml")
    private Resource registerECView;

    @Value("classpath:/fxml/ComputerDevices.fxml")
    private Resource computerDevicesView;

    @Value("classpath:/fxml/RegisterTechnician.fxml")
    private Resource technicianView;

    @Value("classpath:/fxml/Log.fxml")
    private Resource logView;

    @Value("classpath:/fxml/ConsultPeripheral.fxml")
    private Resource consultPeripherals;

    @Value("classpath:/fxml/ConsultMaintenanceLog.fxml")
    private Resource consultMaintenanceLog;

    @Value("classpath:/fxml/ConsultTechnician.fxml")
    private Resource consultTechnician;

    @Value("classpath:/fxml/registerProgram.fxml")
    private Resource registerProgram;

    @Value("classpath:/fxml/ConsultProgram.fxml")
    private Resource consultProgram;

    @Value("classpath:/fxml/ComputerDP.fxml")
    private Resource assingProgram;

    @Value("classpath:/fxml/ConsultProgramInstalled.fxml")
    private Resource consultInstalled;

    public MainGuiController(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    public void initialize(){
        Platform.runLater(() -> {
            Stage stage = (Stage) btnComputerCenters.getScene().getWindow();
            stage.sizeToScene();
            stage.setMinWidth(850);
            stage.setMinHeight(500);
            stage.setMaxHeight(500);
            stage.setMaxWidth(850);
        });
    }

    @FXML
    private void showComputerCentersView(ActionEvent actionEvent) throws RuntimeException {
        voidOpenWindowAndCloseCurrent("Computer Centers", computerCenterView, (Stage) btnComputerCenters.getScene().getWindow());
    }

    public void showPeripheralView(ActionEvent actionEvent) throws RuntimeException{
        voidOpenWindowAndCloseCurrent("Peripherals", peripheralView, (Stage) btnPeripheral.getScene().getWindow());
    }

    public void showRegisterCCView(ActionEvent actionEvent) throws RuntimeException{
        voidOpenWindowAndCloseCurrent("Register Computer Centers", registerCCView, (Stage) btnRegisterCC.getScene().getWindow());
    }

    public void showRegisterECView(ActionEvent actionEvent) throws RuntimeException{
        voidOpenWindowAndCloseCurrent("Register Computer Devices", registerECView, (Stage) btnRegisterEC.getScene().getWindow());
    }

    public void showComputerDevicesView(ActionEvent actionEvent) throws RuntimeException{
        voidOpenWindowAndCloseCurrent("Show Computer Devices", computerDevicesView, (Stage) btnComputerDevices.getScene().getWindow());
    }

    public void registerTechnician(ActionEvent actionEvent) {
        voidOpenWindowAndCloseCurrent("Register Technician", technicianView, (Stage) btnTechnician.getScene().getWindow());
    }

    public void registerLog(ActionEvent actionEvent) {
        voidOpenWindowAndCloseCurrent("Register Maintenance Log", logView, (Stage) btnLog.getScene().getWindow());
    }

    public void consultPeripherals(ActionEvent actionEvent) {
        voidOpenWindowAndCloseCurrent("Consultar Perifericos", consultPeripherals, (Stage) btnConsultPeripherals.getScene().getWindow());
    }

    public void consultLog(ActionEvent actionEvent) {
        voidOpenWindowAndCloseCurrent("Consultar Mantenimientos", consultMaintenanceLog, (Stage) btnConsultLog.getScene().getWindow());
    }

    public void ConsultTechnician(ActionEvent actionEvent) {
        voidOpenWindowAndCloseCurrent("Consultar Tecnicos", consultTechnician, (Stage) btnTechnician.getScene().getWindow());
    }

    public void registerProgram(ActionEvent actionEvent) {
        voidOpenWindowAndCloseCurrent("Registrar Programa", registerProgram, (Stage) btnRegisterProgram.getScene().getWindow());
    }

    public void consultProgram(ActionEvent actionEvent) {
        voidOpenWindowAndCloseCurrent("Consultar Programa", consultProgram, (Stage) btnConsultProgram.getScene().getWindow());
    }

    public void assingProgram(ActionEvent actionEvent) {
        voidOpenWindowAndCloseCurrent("Asignar Programa", assingProgram, (Stage) btnAssingProgram.getScene().getWindow());
    }
    public void consultECP(ActionEvent actionEvent) {
        voidOpenWindowAndCloseCurrent("Cosultar Programa Instalados", consultInstalled, (Stage) btnConsultProgram.getScene().getWindow());
    }

    public void consultProgramInstalled(ActionEvent actionEvent) {
        voidOpenWindowAndCloseCurrent("Registrar Programa", registerProgram, (Stage) btnRegisterProgram.getScene().getWindow());
    }
}
