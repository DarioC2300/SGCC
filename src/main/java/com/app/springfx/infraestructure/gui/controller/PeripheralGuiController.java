package com.app.springfx.infraestructure.gui.controller;

import com.app.springfx.application.PeripheralService;
import com.app.springfx.domain.Peripheral;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PeripheralGuiController extends AbstractGuiController{
    public PeripheralService peripheralService;
    public DatePicker dpFechaAdquisicion;
    public TextField tfClave;
    public TextField tfModelo;
    public TextField tfMarca;
    public TextField tfPrecio;
    public Label lbPeripheral;
    public boolean isUpdate = false;
    public Peripheral peripheral;
    public Button btnRegister;




    public void initialize(){
        Platform.runLater(() -> {
            Stage stage = (Stage) lbPeripheral.getScene().getWindow();
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

    public PeripheralGuiController(
            ApplicationContext applicationContext,
            PeripheralService peripheralService
    ) {
        super(applicationContext);
        this.peripheralService = peripheralService;
    }

    public void backToMain(ActionEvent actionEvent) {
        openMainWindowAndCloseCurrent((javafx.stage.Stage) dpFechaAdquisicion.getScene().getWindow());
    }

    public void save(ActionEvent actionEvent) {
        TextField[] textField = {tfClave,tfMarca,tfModelo,tfPrecio};
        if (validateFields(textField)){
            var peripheral = Peripheral.builder()
                    .identificationKey(tfClave.getText())
                    .model(tfModelo.getText())
                    .brand(tfMarca.getText())
                    .price(Double.valueOf(tfPrecio.getText()))
                    .type("TIPO")
                    .adquisitionDate(dpFechaAdquisicion.getValue())
                    .build();
            if (isUpdate){
                peripheral.setId(this.peripheral.getId());
            }
            var responce = peripheralService.save(peripheral);
            if(responce != null){
                showAlert(Alert.AlertType.INFORMATION, "Guardado Exitoso", "El Periferico Fue Guardado Exitosamente");
                cleanFields(textField);
            }else {
                showAlert(Alert.AlertType.ERROR, "Error en la conexion", "El Periferico No Pudo Ser Guardado Con Exito");
            }

        }else{
            showAlert(Alert.AlertType.ERROR, "Campos Invalidos", "Existen Campos Invalidos porfavor corroboralos");
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

            if (dpFechaAdquisicion.getValue() == null){
                dpFechaAdquisicion.setStyle("-fx-background-color: red;");
                validTextFields = false;
            }
        }

        return  validTextFields;
    }

    public void cleanFields(TextField[] textFields){
        for(var textField : textFields){
            textField.clear();
        }
        dpFechaAdquisicion.setValue(null);
    }

    public void updateFields(){
        lbPeripheral.setText("Actualizar Periferico");
        btnRegister.setText("Actualizar");
        tfClave.setText(peripheral.getIdentificationKey());
        tfModelo.setText(peripheral.getModel());
        tfMarca.setText(peripheral.getBrand());
        tfPrecio.setText(String.valueOf(peripheral.getPrice()));
        dpFechaAdquisicion.setValue(peripheral.getAdquisitionDate());
    }
}
