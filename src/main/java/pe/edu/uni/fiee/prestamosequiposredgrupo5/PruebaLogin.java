
package pe.edu.uni.fiee.prestamosequiposredgrupo5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PruebaLogin extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                PruebaLogin.class.getResource("login-view.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Sistema de Préstamo de Equipos de Red");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
