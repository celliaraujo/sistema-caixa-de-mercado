package com.marcelli;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MainApp extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = new SpringApplicationBuilder(SpringBootApp.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Carrega a tela inicial (menu)
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/menu-view.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean); // injeta beans do Spring
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Sistema Caixa");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        springContext.close();
    }

    public static void main(String[] args) {
        launch(args); // inicia JavaFX
    }
}
