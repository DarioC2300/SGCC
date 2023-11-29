package com.app.springfx.infraestructure.gui.controller;

import com.app.springfx.application.MaintenanceLogService;
import com.app.springfx.domain.MaintenanceLog;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ConsultMaintenanceLogGuiController extends AbstractGuiController {

    @Value("classpath:/fxml/Log.fxml")
    Resource RegisterLogView;
    public TableView<MaintenanceLog> maintenanceLogTable;

    private final MaintenanceLogService maintenanceLogService;


    public ConsultMaintenanceLogGuiController(MaintenanceLogService maintenanceLogService, ApplicationContext applicationContext) {
        super(applicationContext);
        this.maintenanceLogService = maintenanceLogService;
    }

    public void initialize() {
        Platform.runLater(() -> {
        var logs = FXCollections.observableArrayList(maintenanceLogService.getAll());
        maintenanceLogTable.setItems(logs);
        Stage stage = (Stage) maintenanceLogTable.getScene().getWindow();
        stage.sizeToScene();
        stage.setMaxWidth(784);
        stage.setMaxHeight(500);
        stage.setMinWidth(784);
        stage.setMinHeight(500);
        });
    }

    public void updateMaintenanceLog(ActionEvent actionEvent) {
        var maintenanceLog = maintenanceLogTable.getSelectionModel().getSelectedItem();
        if (maintenanceLog != null) {
            LogGuiController maintenanceLogGuiController = openWindowInitializateView("Log de mantenimiento", RegisterLogView);
            maintenanceLogGuiController.isUpdate = true;
            maintenanceLogGuiController.maintenanceLog = maintenanceLog;
            maintenanceLogGuiController.initialize();
            ((Stage) maintenanceLogTable.getScene().getWindow()).close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un registro de mantenimiento");
        }
    }

    public void deleteMaintenanceLog(ActionEvent actionEvent) {
        var maintenanceLog = (com.app.springfx.domain.MaintenanceLog) maintenanceLogTable.getSelectionModel().getSelectedItem();
        if (maintenanceLog != null) {
            ButtonType result = showAlert(Alert.AlertType.CONFIRMATION, "Confirmación", "¿Deseas eliminar este elemento: " + maintenanceLog.getTechnician());
            if (result == ButtonType.OK){
                try {
                    maintenanceLogService.delete(maintenanceLog.getId());
                    showAlert(Alert.AlertType.INFORMATION, "Información", "Se ha eliminado el registro de mantenimiento");
                    initialize();
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se ha podido eliminar el registro de mantenimiento");
                }
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un registro de mantenimiento");
        }
    }

    public void back(ActionEvent actionEvent) {
        openMainWindowAndCloseCurrent((javafx.stage.Stage) maintenanceLogTable.getScene().getWindow());
    }
}
