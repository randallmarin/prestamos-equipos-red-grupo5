package pe.edu.uni.fiee.prestamosequiposredgrupo5.usuarios;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField campoUsuario;

    @FXML
    private PasswordField campoContrasena;

    @FXML
    private Label mensajeEstado;

    @FXML
    private void ingresar() {

        String usuario = campoUsuario.getText().trim();
        String contrasena = campoContrasena.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mensajeEstado.setText(
                    "Debe ingresar usuario y contraseña."
            );
            return;
        }

        mensajeEstado.setText(
                "Formulario completado. Autenticación pendiente."
        );
    }
}
