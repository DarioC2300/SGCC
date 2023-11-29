package com.app.springfx.infraestructure.gui.controller;
import com.app.springfx.application.SoftwareService;
import com.app.springfx.domain.Peripheral;
import com.app.springfx.domain.Software;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RegisterProgramGuiController extends AbstractGuiController{
    private final SoftwareService softwareService;

    public TextField tfNombre;
    public TextField tfDescripcion;
    public TextField tfTamaño;
    public TextField tfVersion;
    public DatePicker dpFechaA;
     public Label lbProgram;

     public Button btnRegister;



    public boolean isUpdate = false;
    public Software software;
    public void initialize(){
        Platform.runLater(() -> {
        Stage stage = (Stage) lbProgram.getScene().getWindow();
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
    public RegisterProgramGuiController(ApplicationContext applicationContext, SoftwareService softwareService) {
        super(applicationContext);
        this.softwareService= softwareService;
    }

    public void backToMain(ActionEvent actionEvent) {
        openMainWindowAndCloseCurrent((javafx.stage.Stage) tfNombre.getScene().getWindow());
    }

    public void save(ActionEvent actionEvent) {
        TextField[] textField = {tfNombre,tfVersion,tfDescripcion,tfTamaño};
        if (validateFields(textField)) {
            var software = Software.builder()

                    .name(tfNombre.getText())
                    .version(tfVersion.getText())
                    .description(tfDescripcion.getText())
                    .installationDate(dpFechaA.getValue())
                    .size(tfTamaño.getText())
            .build();

            var responce = softwareService.save(software);
            if (responce != null) {
                showAlert(Alert.AlertType.INFORMATION, "Guardado Exitoso", "El Programa Fue Guardado Exitosamente");
                cleanFields(textField);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error en la conexion", "El Programa No Pudo Ser Guardado Con Exito");
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
        lbProgram.setText("Actualizar Programa");
        btnRegister.setText("Actualizar");
        tfNombre.setText(software.getName());
        tfDescripcion.setText(software.getDescription());
        tfTamaño.setText(software.getSize());
        tfVersion.setText(software.getVersion());
        dpFechaA.setValue(software.getInstallationDate());
    }
}
