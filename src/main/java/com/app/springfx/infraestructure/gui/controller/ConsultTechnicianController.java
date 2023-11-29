package com.app.springfx.infraestructure.gui.controller;

import com.app.springfx.application.TechnicianService;
import com.app.springfx.domain.Technician;
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
public class ConsultTechnicianController extends AbstractGuiController{

    @Value("classpath:/fxml/RegisterTechnician.fxml")
    Resource RegisterTechnicianView;

    private final TechnicianService technicianService;
    public TableView<Technician> technicianTable;

    public void initialize(){
        Platform.runLater(() -> {
        var technicians = FXCollections.observableArrayList(technicianService.getAll());
        technicianTable.setItems(technicians);
        Stage stage = (Stage) technicianTable.getScene().getWindow();
        stage.sizeToScene();
        stage.setMaxWidth(784);
        stage.setMaxHeight(500);
        stage.setMinHeight(500);
        stage.setMinWidth(784);
        });
    }

    public ConsultTechnicianController(ApplicationContext applicationContext, TechnicianService technicianService) {
        super(applicationContext);
        this.technicianService = technicianService;
    }

    public void deleteTechnician(ActionEvent actionEvent) {
        var technician = technicianTable.getSelectionModel().getSelectedItem();
        if (technician != null) {
            ButtonType result = showAlert(Alert.AlertType.CONFIRMATION, "Confirmación", "¿Deseas eliminar este elemento: " + technician.getName());
            if (result == ButtonType.OK){
                try {
                    technicianService.delete(technician.getId());
                    showAlert(Alert.AlertType.INFORMATION, "Eliminado Exitoso", "El Tecnico Fue Eliminado Exitosamente");
                    initialize();
                }catch (Exception e){
                    showAlert(Alert.AlertType.ERROR, "Error", "No se ha podido eliminar el tecnico");
                }
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un tecnico");
        }
    }

    public void updateTechnician(ActionEvent actionEvent) {
        var technician = technicianTable.getSelectionModel().getSelectedItem();
        if (technician != null) {
            TechnicianGuiController technicianGuiController = openWindowInitializateView("Technicians", RegisterTechnicianView);
            technicianGuiController.isUpdate = true;
            technicianGuiController.technician = technician;
            technicianGuiController.initialize();
            ((Stage) technicianTable.getScene().getWindow()).close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un tecnico");
        }
    }

    public void back(ActionEvent actionEvent) {
        openMainWindowAndCloseCurrent((javafx.stage.Stage) technicianTable.getScene().getWindow());
    }
}
