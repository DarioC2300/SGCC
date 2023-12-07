package com.app.springfx.infraestructure.gui.controller;
import com.app.springfx.application.TechnicianService;
import com.app.springfx.domain.Technician;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Component
public class TechnicianGuiController extends AbstractGuiController{

    public TechnicianService technicianService;
    public TextField tbName;
    public TextField tbUsername;
    public TextField tbEmail;
    public TextField tbAddress;
    public TextField tbPhone;
    public PasswordField pfPassword;
    public boolean isUpdate;
    public Technician technician;
    public boolean isLogin;
    public Label lbSave;
    public Button btnSave;

    @Value("classpath:/fxml/ventanaLoggin.fxml")
    Resource loginView;

    @Value("classpath:/fxml/ConsultTechnician.fxml")
    Resource tecView;


    public TechnicianGuiController(
        ApplicationContext applicationContext,
        TechnicianService technicianService
    ){
        super(applicationContext);
        this.technicianService = technicianService;
    }

    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) btnSave.getScene().getWindow();
            //stage.sizeToScene();
            stage.setMaxHeight(544);
            stage.setMaxWidth(450);
            stage.setMinHeight(544);
            stage.setMinWidth(450);
            if (isUpdate) {
                updateFields();
            }
        });
    }

    public void backToMain(ActionEvent actionEvent) {
        if (isLogin){
            openWindowInitializateView("Login", loginView);
            ((Stage) tbAddress.getScene().getWindow()).close();
        }else if (isUpdate){
            openWindowInitializateView("Consultar Tecnico", tecView);
            ((Stage) tbAddress.getScene().getWindow()).close();
        }else {
            openMainWindowAndCloseCurrent((javafx.stage.Stage) tbAddress.getScene().getWindow());
        }
    }

    public void save(ActionEvent actionEvent) {
        TextField[] textFields = {tbName, tbUsername, tbEmail, tbPhone, tbAddress};
        if (validateFields(textFields)){

            if (existsByUsername(tbUsername.getText())&& !isUpdate){
                showAlert(Alert.AlertType.ERROR, "Error", "El Tecnico con el username " + tbUsername.getText() + "Ya Existe");
            }else {
                var technician = Technician.builder()
                        .name(tbName.getText())
                        .surname(tbUsername.getText())
                        .email(tbEmail.getText())
                        .phone(tbPhone.getText())
                        .address(tbAddress.getText())
                        .password(pfPassword.getText())
                        .build();
                if (isUpdate){
                    technician.setId(this.technician.getId());
                }
                var responce = technicianService.save(technician);
                if (responce != null){
                    showAlert(Alert.AlertType.INFORMATION, "Guardado Exitoso", "El Tecnico Fue Guardado Exitosamente");
                    cleanFields(textFields);
                }else {
                    showAlert(Alert.AlertType.ERROR, "Error", "El Tecnico No Pudo Ser Guardado");
                }
            }
        }else {
            showAlert(Alert.AlertType.ERROR, "Campos Invalidos", "Existen Campos Invalidos porfavor corroboralos");
        }
    }

    public boolean validateFields(TextField[] textFields) {
        boolean validFields = true;

        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                validFields = false;
                textField.setStyle("-fx-control-inner-background: red;");
            } else {
                textField.setStyle("");
            }
        }

        if (pfPassword.getText().isEmpty()) {
            validFields = false;
            pfPassword.setStyle("-fx-control-inner-background: red;");
        } else {
            pfPassword.setStyle("");
        }
        return validFields;
    }

    public boolean existsByUsername(String username){
        return technicianService.existsByUsername(username);
    }

    public void cleanFields(TextField[] textFields){
        for (var textField : textFields){
            textField.clear();
        }
        pfPassword.clear();
    }

    public void updateFields(){
        tbName.setText(technician.getName());
        tbUsername.setText(technician.getSurname());
        tbEmail.setText(technician.getEmail());
        tbPhone.setText(technician.getPhone());
        tbAddress.setText(technician.getAddress());
        pfPassword.setText(technician.getPassword());
        lbSave.setText("Actualizar Tecnico");
        btnSave.setText("Actualizar");
    }
}