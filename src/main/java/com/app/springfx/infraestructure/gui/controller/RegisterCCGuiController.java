package com.app.springfx.infraestructure.gui.controller;

import com.app.springfx.application.ComputerCenterService;
import com.app.springfx.application.PeripheralService;
import com.app.springfx.domain.ComputerCenter;
import com.app.springfx.domain.Peripheral;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
@Component
public class RegisterCCGuiController extends AbstractGuiController{
    private final ComputerCenterService computerCenterService;
    public Button btnRegister;
    public Label lbCC;

    public TextField tfNombre;
    public boolean isUpdate = false;
    public ComputerCenter ComputerCenter;


    public void initialize(){
        Platform.runLater(() -> {
        Stage stage = (Stage) lbCC.getScene().getWindow();
        stage.sizeToScene();
            stage.setMinWidth(612);
            stage.setMinHeight(500);
            stage.setMaxHeight(500);
            stage.setMaxWidth(612);
        if(isUpdate){
            updateFields();
        }
        });
    }
    public RegisterCCGuiController(
            ApplicationContext applicationContext,
            ComputerCenterService computerCenterService
    ) {
        super(applicationContext);
        this.computerCenterService = computerCenterService;
    }

    public void backToMain(ActionEvent actionEvent) {
        openMainWindowAndCloseCurrent((javafx.stage.Stage) tfNombre.getScene().getWindow());
    }

    public void save(ActionEvent actionEvent) {
        TextField[] textField = {tfNombre};
        if (validateFields(textField)){
        var computerCenter = ComputerCenter.builder()
                .name(tfNombre.getText())
                .build();
        if (isUpdate){
            computerCenter.setId(this.ComputerCenter.getId());
        }
        var responce = computerCenterService.save(computerCenter);
            if(responce != null){
                showAlert(Alert.AlertType.INFORMATION, "Guardado Exitoso", "El Centro de Cómputo Fue Guardado Exitosamente");
                cleanFields(textField);
            }else {
                showAlert(Alert.AlertType.ERROR, "Error en la conexion", "El Centro de Cómputo No Pudo Ser Guardado Con Exito");
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
        }

        return  validTextFields;
    }

    public void cleanFields(TextField[] textFields){
        for(var textField : textFields){
            textField.clear();
        }
    }

    public void updateFields(){
        lbCC.setText("Actualizar Centro de Cómputo");
        btnRegister.setText("Actualizar");
        tfNombre.setText(ComputerCenter.getName());
    }
}
