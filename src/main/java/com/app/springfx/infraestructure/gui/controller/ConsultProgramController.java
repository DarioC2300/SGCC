package com.app.springfx.infraestructure.gui.controller;

import com.app.springfx.application.SoftwareService;
import com.app.springfx.domain.Software;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
@Component
public class ConsultProgramController extends AbstractGuiController{
    @Value("classpath:/fxml/RegisterProgram.fxml")
    Resource UpdateProgramView;
        private final SoftwareService softwareService;
        public TableView<Software> tableProgram;

        public ConsultProgramController(
                ApplicationContext applicationContext,
                SoftwareService softwareService
        ){
            super(applicationContext);
            this.softwareService= softwareService;
        }

        public void initialize(){
            Platform.runLater(() -> {
                var software = FXCollections.observableArrayList(softwareService.getAll());
                tableProgram.setItems(software);
                Stage stage = (Stage) tableProgram.getScene().getWindow();
                stage.sizeToScene();
                stage.setMaxWidth(794);
                stage.setMaxHeight(510);
                stage.setMinWidth(794);
                stage.setMinHeight(510);
            });
        }

        public void back(ActionEvent actionEvent) {
            openMainWindowAndCloseCurrent((javafx.stage.Stage) tableProgram.getScene().getWindow());
        }

        public void deleteProgram(ActionEvent actionEvent){
            var program = tableProgram.getSelectionModel().getSelectedItem();
            if (program != null) {
                ButtonType result = showAlert(Alert.AlertType.CONFIRMATION, "Confirmación", "¿Deseas eliminar este elemento: " +program.getName());
                if (result == ButtonType.OK) {
                    System.out.println("Eliminando");
                    softwareService.delete(program.getId());
                    initialize();
                } else {
                    System.out.println("No Eliminando");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un periférico");
            }
        }

        public void updateProgram(ActionEvent actionEvent){
            var program = tableProgram.getSelectionModel().getSelectedItem();
            if (program!= null) {
                RegisterProgramGuiController registerProgramController = openWindowInitializateView("Programs", UpdateProgramView);
                registerProgramController.isUpdate = true;
                registerProgramController.software= program;
                registerProgramController.initialize();
                ((Stage) tableProgram.getScene().getWindow()).close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un programa");
            }
        }
}


