package com.app.springfx.infraestructure.gui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractGuiController {
    protected final ApplicationContext applicationContext;

    @Value("{spring.application.ui.title}")
    private String applicationTitle;
    @Value("classpath:/fxml/main.fxml")
    private Resource mainFxml;


    protected void openWindow(String name, Resource fxml){
        try {
            FXMLLoader loader = new FXMLLoader(fxml.getURL());
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(applicationTitle);
            stage.setScene(new Scene(root, 640, 480));
            stage.setTitle(name);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> T openWindowInitializateView(String name, Resource fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(fxml.getURL());
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(applicationTitle);
            stage.setScene(new Scene(root, 640, 480));
            stage.setTitle(name);
            stage.show();

            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void voidOpenWindowAndCloseCurrent(String name, Resource fxml, Stage currentStage){
        openWindow(name, fxml);
        currentStage.close();
    }

    protected void openMainWindowAndCloseCurrent(Stage currentStage){
        voidOpenWindowAndCloseCurrent(applicationTitle, mainFxml, currentStage);
    }

    protected ButtonType showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().orElse(ButtonType.CANCEL);  // Devuelve el resultado del botón presionado, por defecto es CANCEL
    }

    protected void cleanFields(javafx.scene.control.TextField[] textFields) {
        for (javafx.scene.control.TextField textField : textFields) {
            textField.setText("");
        }
    }

    protected boolean validateTextFields(TextField[] textFields) {
        boolean validFields = true;

        for (javafx.scene.control.TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                validFields = false;
                textField.setStyle("-fx-background-color: red;");
                break;
            }
        }

        return validFields;
    }

    protected boolean validateComboBoxes(ComboBox[] comboBoxes) {
        boolean validFields = true;

        for (var comboBox : comboBoxes) {
            if (comboBox.getSelectionModel().isEmpty()) {
                validFields = false;
                comboBox.setStyle("-fx-background-color: red;");
                break;
            }
        }

        return validFields;
    }

    protected boolean validateDatePicker(DatePicker datePicker) {
        boolean validFields = true;

        if (datePicker.getValue() == null) {
            validFields = false;
            datePicker.setStyle("-fx-background-color: red;");
        }

        return validFields;
    }

    protected boolean validateFieldsAbstract(TextField[] textFields, ComboBox[] comboBoxes, DatePicker datePicker) {
        boolean validFields = true;

        if (!validateTextFields(textFields)) {
            validFields = false;
        }

        if (!validateComboBoxes(comboBoxes)) {
            validFields = false;
        }

        if (!validateDatePicker(datePicker)) {
            validFields = false;
        }

        return validFields;
    }
}
