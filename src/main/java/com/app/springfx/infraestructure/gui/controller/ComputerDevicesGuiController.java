package com.app.springfx.infraestructure.gui.controller;

import com.app.springfx.application.ComputerDeviceService;
import com.app.springfx.domain.ComputerDevice;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import static javafx.collections.FXCollections.observableArrayList;

@Component
public class ComputerDevicesGuiController extends AbstractGuiController {
    @Value("classpath:/fxml/RegisterEC.fxml")
    Resource RegisterECView;
    private final ComputerDeviceService computerDeviceService;

    @FXML
    private TableView<ComputerDevice> computerDevicesTable;



    public ComputerDevicesGuiController(ApplicationContext applicationContext, ComputerDeviceService computerDeviceService) {
        super(applicationContext);
        this.computerDeviceService = computerDeviceService;
    }

    public void initialize() {
        Platform.runLater(() -> {
        var computerDevices = computerDeviceService.getAll();
        ObservableList<ComputerDevice> observableList = observableArrayList(computerDevices);
        computerDevicesTable.setItems(observableList);
        Stage stage = (Stage) computerDevicesTable.getScene().getWindow();
        stage.sizeToScene();
            stage.setMaxHeight(500);
            stage.setMaxWidth(784);
            stage.setMinHeight(500);
            stage.setMinWidth(784);
        });
    }

    public void deleteEC(ActionEvent actionEvent) {
        var computerDevice = (com.app.springfx.domain.ComputerDevice) computerDevicesTable.getSelectionModel().getSelectedItem();
        if (computerDevice != null) {
            ButtonType result = showAlert(Alert.AlertType.CONFIRMATION, "Confirmación", "¿Deseas eliminar este elemento: " + computerDevice.getName());
            if (result == ButtonType.OK) {
                System.out.println("Eliminando");
                computerDeviceService.delete(computerDevice.getId());
                initialize();
            } else {
                System.out.println("No Eliminando");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un Equipo de Cómputo");
        }
    }

    public void updateEC(ActionEvent actionEvent) {
        var computerDevice = (com.app.springfx.domain.ComputerDevice) computerDevicesTable.getSelectionModel().getSelectedItem();
        if (computerDevice != null) {
            RegisterECGuiController registerECGuiController = openWindowInitializateView("Peripherals", RegisterECView);
            registerECGuiController.isUpdate = true;
            registerECGuiController.computerDevice = computerDevice;
            registerECGuiController.initialize();
            ((Stage) computerDevicesTable.getScene().getWindow()).close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un periférico");
        }
    }

    public void backtomain(ActionEvent actionEvent) {
        openMainWindowAndCloseCurrent((javafx.stage.Stage) computerDevicesTable.getScene().getWindow());
    }
}
