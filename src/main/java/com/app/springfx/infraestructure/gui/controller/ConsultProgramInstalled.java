package com.app.springfx.infraestructure.gui.controller;

import com.app.springfx.application.ComputerDeviceService;
import com.app.springfx.domain.ComputerDevice;
import com.app.springfx.domain.Software;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static javafx.collections.FXCollections.observableArrayList;

@Component
public class ConsultProgramInstalled extends AbstractGuiController {

    private final ComputerDeviceService computerDeviceService;

    @FXML
    private TableView<ComputerDevice> computerDevicesTable;

    @FXML
    private TableView<Software> tableProgram;


    public ConsultProgramInstalled(ApplicationContext applicationContext, ComputerDeviceService computerDeviceService) {
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
        stage.setMaxWidth(794);
        stage.setMaxHeight(500);
        stage.setMinWidth(794);
        stage.setMinHeight(500);
        });
    }

    public void  deleteInstalled(ActionEvent actionEvent) {
        ComputerDevice selectedComputerDevice = computerDevicesTable.getSelectionModel().getSelectedItem();
        Software selectedSoftware = tableProgram.getSelectionModel().getSelectedItem();

        if (selectedComputerDevice != null && selectedSoftware != null) {
            // Confirmar la eliminación
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmar eliminación");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("¿Estás seguro de que quieres eliminar este programa instalado?");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Eliminar el programa instalado del equipo
                selectedComputerDevice.getSoftwares().remove(selectedSoftware);
                computerDeviceService.save(selectedComputerDevice);

                // Actualizar la tabla de programas
                List<Software> installedPrograms = selectedComputerDevice.getSoftwares();
                ObservableList<Software> observablePrograms = FXCollections.observableArrayList(installedPrograms);
                tableProgram.setItems(observablePrograms);

                // Mostrar un mensaje de éxito
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Éxito");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Programa eliminado exitosamente.");
                successAlert.showAndWait();
            }
        } else {
            // Muestra una alerta si no se selecciona un equipo de cómputo o un programa
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cuidado");
            alert.setHeaderText("Debes seleccionar un equipo de cómputo y un programa para eliminar.");
            alert.showAndWait();
        }

    }

    public void consultProgram(ActionEvent actionEvent) {
        ComputerDevice selectedComputerDevice = computerDevicesTable.getSelectionModel().getSelectedItem();

        if (selectedComputerDevice != null) {
            List<Software> installedPrograms = selectedComputerDevice.getSoftwares();

            if (installedPrograms.isEmpty()) {
                // Mostrar un mensaje de que no hay programas instalados en el equipo
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("El equipo seleccionado no tiene programas instalados.");
                alert.showAndWait();
                tableProgram.getItems().clear();
            } else {
                // Mostrar los programas instalados en la tabla
                ObservableList<Software> observablePrograms = FXCollections.observableArrayList(installedPrograms);
                tableProgram.setItems(observablePrograms);
            }
        } else {
            // Muestra una alerta si no se selecciona un equipo de cómputo
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cuidado");
            alert.setHeaderText("Debes seleccionar un equipo de cómputo.");
            alert.showAndWait();
        }
    }

    public void backtomain(ActionEvent actionEvent) {
        openMainWindowAndCloseCurrent((Stage) computerDevicesTable.getScene().getWindow());
    }
}
