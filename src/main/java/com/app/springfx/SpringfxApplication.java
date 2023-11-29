package com.app.springfx;

import com.app.springfx.infraestructure.gui.config.GuiApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringfxApplication {

    public static void main(String[] args) {
        Application.launch(GuiApplication.class, args);
    }

}
