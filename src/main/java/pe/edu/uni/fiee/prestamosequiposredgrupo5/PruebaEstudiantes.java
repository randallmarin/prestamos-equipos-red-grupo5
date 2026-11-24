
package pe.edu.uni.fiee.prestamosequiposredgrupo5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PruebaEstudiantes extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                PruebaEstudiantes.class.getResource("estudiantes-view.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Gestión de Estudiantes");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
