package com.app.springfx.infraestructure.gui.controller;

import com.app.springfx.application.ComputerDeviceService;
import com.app.springfx.application.SoftwareService;
import com.app.springfx.domain.ComputerDevice;
import com.app.springfx.domain.Software;
import com.app.springfx.domain.Assignment;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static javafx.collections.FXCollections.observableArrayList;

@Component
public class ComputerDPController extends AbstractGuiController {
    public ComputerDeviceService computerDeviceService;
    public SoftwareService softwareService;
    @FXML
    public ComboBox<Software> cbSoftware = new ComboBox<>();
    @FXML
    private TableView<ComputerDevice> computerDevicesTable;
    @FXML
    private TableView<Assignment> asingTable;
    @FXML
    private TableColumn<Assignment, String> columComputer;
    @FXML
    private TableColumn<Assignment, String> columProgram;
    private ObservableList<Assignment> assignmentList = FXCollections.observableArrayList();



    public ComputerDPController(ApplicationContext applicationContext, ComputerDeviceService computerDeviceService, SoftwareService softwareService) throws IOException {

        super(applicationContext);
        this.computerDeviceService = computerDeviceService;
        this.softwareService = softwareService;

    }

    public void initialize() {
        Platform.runLater(() -> {
        var software = FXCollections.observableArrayList(softwareService.getAll());
        this.cbSoftware.setItems(software);
        Stage stage = (Stage) cbSoftware.getScene().getWindow();
        stage.sizeToScene();
        stage.setMaxHeight(600);
        stage.setMaxWidth(720);
        stage.setMinHeight(600);
        stage.setMinWidth(720);
        });
        cbSoftware.setConverter(new javafx.util.StringConverter<Software>() {

            @Override
            public String toString(Software software) {
                if (software != null) {
                    String name = software.getName();
                    return name;
                }
                return "";
            }

            @Override
            public Software fromString(String string) {
                return null;
            }
        });
        var computerDevices = computerDeviceService.getAll();
        ObservableList<ComputerDevice> observableList = observableArrayList(computerDevices);
        computerDevicesTable.setItems(observableList);


        this.columComputer.setCellValueFactory(new PropertyValueFactory<>("identificationKey"));
        this.columProgram.setCellValueFactory(new PropertyValueFactory<>("nameProgram"));


        asingTable.setItems(assignmentList);
    }

    public void backtomain(ActionEvent actionEvent) {
        openMainWindowAndCloseCurrent((javafx.stage.Stage) computerDevicesTable.getScene().getWindow());
    }


    public void vievAsing(ActionEvent event) {

        ComputerDevice selectedComputerDevice = computerDevicesTable.getSelectionModel().getSelectedItem();
        Software selectedSoftware = cbSoftware.getSelectionModel().getSelectedItem();


        if (selectedComputerDevice != null && selectedSoftware != null) {

            if (!assignmentList.stream().anyMatch(a ->
                    a.getIdentificationKey().equals(selectedComputerDevice.getIdentificationKey()) &&
                            a.getNameProgram().equals(selectedSoftware.getName()))) {


                Assignment assignment = new Assignment(selectedComputerDevice.getIdentificationKey(), selectedSoftware.getName());
                assignmentList.add(assignment);
            } else {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cuidado");
                alert.setHeaderText("Ya existe una asignación para este equipo y programa.");
                alert.showAndWait();


                asingTable.getItems().clear();
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cuidado");
            alert.setHeaderText("Debes seleccionar un equipo y un programa");
            alert.showAndWait();
        }
    }



    public void addAsing(ActionEvent event) {

        Software selectedSoftware = cbSoftware.getSelectionModel().getSelectedItem();
        ComputerDevice selectedComputerDevice = computerDevicesTable.getSelectionModel().getSelectedItem();

        if (selectedSoftware != null && selectedComputerDevice != null) {

            selectedComputerDevice.getSoftwares().add(selectedSoftware);

            computerDeviceService.save(selectedComputerDevice);

            // Limpiar la tabla y recargar los datos
            computerDevicesTable.getItems().clear();
            computerDevicesTable.getItems().addAll(computerDeviceService.getAll());

            // Limpiar la tabla de asignaciones y recargar los datos
            asingTable.getItems().clear();
            asingTable.getItems().addAll(assignmentList);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("Asignación exitosa");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cuidado");
            alert.setHeaderText("Debes seleccionar un programa y un equipo para asignarlo.");
            alert.showAndWait();
        }

    }
    }
