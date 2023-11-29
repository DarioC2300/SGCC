package com.app.springfx.infraestructure.gui.controller;

import com.app.springfx.application.PeripheralService;
import com.app.springfx.domain.Peripheral;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ConsultPeripheralGuiController extends AbstractGuiController{
    @Value("classpath:/fxml/RegisterPeripheral.fxml")
    Resource RegisterPeripheralView;
    private final PeripheralService peripheralService;
    public TableView<Peripheral> tPheripheralTable;


    public ConsultPeripheralGuiController(
            ApplicationContext applicationContext,
            PeripheralService peripheralService
    ){
        super(applicationContext);
        this.peripheralService = peripheralService;
    }

    public void initialize(){
        Platform.runLater(() -> {
        var peripherals = FXCollections.observableArrayList(peripheralService.getAll());
        tPheripheralTable.setItems(peripherals);
        Stage stage = (Stage) tPheripheralTable.getScene().getWindow();
        stage.sizeToScene();
        stage.setMaxWidth(784);
        stage.setMaxHeight(500);
        stage.setMinWidth(784);
        stage.setMinHeight(500);
        });
    }

    public void back(ActionEvent actionEvent) {
        openMainWindowAndCloseCurrent((javafx.stage.Stage) tPheripheralTable.getScene().getWindow());
    }

    public void deletePeripheral(ActionEvent actionEvent) {
        var peripheral = tPheripheralTable.getSelectionModel().getSelectedItem();
        if (peripheral != null) {
            ButtonType result = showAlert(Alert.AlertType.CONFIRMATION, "Confirmación", "¿Deseas eliminar este elemento: " + peripheral.getBrand());
            if (result == ButtonType.OK) {
                System.out.println("Eliminando");
                try {
                    peripheralService.delete(peripheral.getId());
                    showAlert(Alert.AlertType.INFORMATION, "Información", "Se ha eliminado el periférico");
                    initialize();
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se ha podido eliminar el periférico");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un periférico");
            }
        }
    }
    public void updatePeripheral(ActionEvent actionEvent) {
        var peripheral = tPheripheralTable.getSelectionModel().getSelectedItem();
        if (peripheral != null) {
            PeripheralGuiController peripheralGuiController = openWindowInitializateView("Peripherals", RegisterPeripheralView);
            peripheralGuiController.isUpdate = true;
            peripheralGuiController.peripheral = peripheral;
            peripheralGuiController.initialize();
            ((Stage) tPheripheralTable.getScene().getWindow()).close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un periférico");
        }
    }

}
