package com.app.springfx.infraestructure.gui.controller;

import com.app.springfx.application.TechnicianService;
import com.app.springfx.domain.Technician;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class login2GuiController extends AbstractGuiController {

    public Text txtAlertaUserName;
    public PasswordField txtPassword;
    public TextField txtUserName;
    public Text txtIniciarSesion;
    public Button botnLogin;
    public Button btnRegister;
    public Text txtAlertaPassword;
    @Value("classpath:/fxml/main.fxml")
    Resource mainView;
    @Value("classpath:/fxml/RegisterTechnician.fxml")
    Resource technicianView;

    private TechnicianService technicianService;

    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) btnRegister.getScene().getWindow();
            stage.sizeToScene();
            stage.setMinWidth(800);
            stage.setMinHeight(700);
            stage.setMaxHeight(700);
            stage.setMaxWidth(800);
        });
    }

    public login2GuiController(TechnicianService technicianService, ApplicationContext applicationContext) {
        super(applicationContext);
        this.technicianService = technicianService;
    }



    public void closeProgram(ActionEvent actionEvent) {

    }

    public void login(ActionEvent actionEvent) {
        Technician technician =  technicianService.getByUsernameAndPassword(txtUserName.getText(), txtPassword.getText());
        if(technician != null){
            voidOpenWindowAndCloseCurrent("Menu Principal", mainView, (Stage) txtPassword.getScene().getWindow());
        }else {
            showAlert(Alert.AlertType.ERROR, "Campos incorrectos", "Usuario o contraseña incorrectos");
        }
    }

    public boolean validateFields(){
        if (txtPassword.getText().isEmpty() || txtPassword.getText().isEmpty()){
            return false;
        }
        return true;
    }


    public void Register(ActionEvent actionEvent) {
        TechnicianGuiController technicianGuiController = openWindowInitializateView("Technicians", technicianView);
        technicianGuiController.isLogin = true;
        technicianGuiController.initialize();
        ((Stage) txtPassword.getScene().getWindow()).close();

    }
}
