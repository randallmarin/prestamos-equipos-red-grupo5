
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

    // Credenciales ficticias solo para pruebas de Semana 13.
    // No deben utilizarse en el sistema final.
    private static final String USUARIO_PRUEBA = "operador_demo";
    private static final String CLAVE_PRUEBA = "Demo1234!";

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

        if (usuario.equals(USUARIO_PRUEBA)
                && contrasena.equals(CLAVE_PRUEBA)) {

            mensajeEstado.setText(
                    "Acceso de prueba exitoso."
            );

        } else {

            mensajeEstado.setText(
                    "Usuario o contraseña incorrectos."
            );
        }
    }
}
