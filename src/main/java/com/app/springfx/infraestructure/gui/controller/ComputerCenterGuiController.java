package com.app.springfx.infraestructure.gui.controller;

import com.app.springfx.application.ComputerCenterService;
import com.app.springfx.application.PeripheralService;
import com.app.springfx.domain.ComputerCenter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
public class ComputerCenterGuiController extends AbstractGuiController {
    @Value("classpath:/fxml/RegisterCC.fxml")
    Resource RegisterCCView;
    private final ComputerCenterService computerCenterService;


    @FXML
    private TableView<ComputerCenter> computerCenterTable;



    public ComputerCenterGuiController(
            ApplicationContext applicationContext,
            ComputerCenterService computerCenterService
    ){
        super(applicationContext);
        this.computerCenterService = computerCenterService;
    }

    public void initialize(){
        Platform.runLater(() -> {
        var computerCenters = FXCollections.observableArrayList(computerCenterService.getAll());
        computerCenterTable.setItems(computerCenters);
        Stage stage = (Stage) computerCenterTable.getScene().getWindow();
        stage.sizeToScene();
        stage.setMaxHeight(500);
        stage.setMaxWidth(784);
        stage.setMinHeight(500);
        stage.setMinWidth(784);
        });
    }

    public void deleteCC(ActionEvent actionEvent) {
        var computerCenter = (com.app.springfx.domain.ComputerCenter) computerCenterTable.getSelectionModel().getSelectedItem();
        if (computerCenter != null) {
            ButtonType result = showAlert(Alert.AlertType.CONFIRMATION, "Confirmación", "¿Deseas eliminar este elemento: " + computerCenter.getName());
            if (result == ButtonType.OK) {
                System.out.println("Eliminando");
                computerCenterService.delete(computerCenter.getId());
                initialize();
            } else {
                System.out.println("No Eliminando");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un Centro de Cómputo");
        }
    }

    public void updateCC(ActionEvent actionEvent) {
        var computerCenter = (com.app.springfx.domain.ComputerCenter) computerCenterTable.getSelectionModel().getSelectedItem();
        if (computerCenter != null) {
            RegisterCCGuiController computerCenterGuiController = openWindowInitializateView("Computer Centers", RegisterCCView);
            computerCenterGuiController.isUpdate = true;
            computerCenterGuiController.ComputerCenter = computerCenter;
            computerCenterGuiController.initialize();
            ((Stage) computerCenterTable.getScene().getWindow()).close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un centro de cómputo");
        }
    }

    public void backtomain(ActionEvent actionEvent) {
        openMainWindowAndCloseCurrent((javafx.stage.Stage) computerCenterTable.getScene().getWindow());
    }
}
