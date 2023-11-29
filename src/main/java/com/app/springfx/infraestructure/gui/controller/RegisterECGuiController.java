package com.app.springfx.infraestructure.gui.controller;

import com.app.springfx.application.ComputerCenterService;
import com.app.springfx.application.ComputerDeviceService;
import com.app.springfx.domain.ComputerDevice;
import com.app.springfx.domain.Peripheral;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RegisterECGuiController extends AbstractGuiController{
    private final ComputerDeviceService computerDeviceService;
    public TextField tfClave;
    public TextField tfNombre;
    public TextField tfMarca;
    public TextField tfModelo;
    public TextField tfPrecio;
    public DatePicker dpFechaA;
    public Label lbEC;
    public boolean isUpdate = false;
    public ComputerDevice computerDevice;
    public Button btnRegister;


    public void initialize(){
        Platform.runLater(() -> {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.sizeToScene();
        stage.setMinWidth(620);
        stage.setMinHeight(500);
        stage.setMaxHeight(500);
        stage.setMaxWidth(620);
        if(isUpdate){
            updateFields();
        }
        });
    }

    public RegisterECGuiController(
            ApplicationContext applicationContext,
            ComputerDeviceService computerDeviceService
    ) {
        super(applicationContext);
        this.computerDeviceService = computerDeviceService;
    }

    public void backToMain(ActionEvent actionEvent) {
        openMainWindowAndCloseCurrent((javafx.stage.Stage) tfNombre.getScene().getWindow());
    }

    public void save(ActionEvent actionEvent) {
        TextField[] textField = {tfClave, tfNombre,tfMarca,tfModelo,tfPrecio};
        if (validateFields(textField)) {
            var computerDevice = ComputerDevice.builder()
                    .identificationKey(tfClave.getText())
                    .name(tfNombre.getText())
                    .brand(tfMarca.getText())
                    .model(tfModelo.getText())
                    .price(Double.valueOf(tfPrecio.getText()))
                    .type("TIPO")
                    .adquisitionDate(dpFechaA.getValue())
                    .build();
            if (isUpdate){
                computerDevice.setId(this.computerDevice.getId());
            }
            var responce = computerDeviceService.save(computerDevice);
            if (responce != null) {
                showAlert(Alert.AlertType.INFORMATION, "Guardado Exitoso", "El Periferico Fue Guardado Exitosamente");
                cleanFields(textField);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error en la conexion", "El Periferico No Pudo Ser Guardado Con Exito");
            }
        }
    }

    public boolean validateFields(TextField[] textFields) {
        boolean validTextFields = true;

        for(var textField : textFields){
            if (textField.getText().isEmpty()){
                textField.setStyle("-fx-background-color: red;");
                validTextFields = false;
            }else{
                textField.setStyle("");
            }

            if (dpFechaA.getValue() == null){
                dpFechaA.setStyle("-fx-background-color: red;");
                validTextFields = false;
            }
        }

        return  validTextFields;
    }

    public void cleanFields(TextField[] textFields){
        for(var textField : textFields){
            textField.clear();
        }
        dpFechaA.setValue(null);
    }

    public void updateFields(){
        lbEC.setText("Actualizar Equipo de Cómputo");
        btnRegister.setText("Actualizar");
        tfClave.setText(computerDevice.getIdentificationKey());
        tfNombre.setText(computerDevice.getName());
        tfMarca.setText(computerDevice.getBrand());
        tfModelo.setText(computerDevice.getModel());
        tfPrecio.setText(String.valueOf(computerDevice.getPrice()));
        dpFechaA.setValue(computerDevice.getAdquisitionDate());
    }

}
