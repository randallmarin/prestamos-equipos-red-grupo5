package pe.edu.uni.fiee.prestamosequiposredgrupo5.usuarios;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EstudiantesController {

    @FXML
    private TextField campoCodigo;

    @FXML
    private TextField campoNombres;

    @FXML
    private TextField campoApellidos;

    @FXML
    private Label mensajeEstado;

    private final GestorEstudiantes gestor =
            new GestorEstudiantes();

    @FXML
    private void registrar() {

        String codigo = campoCodigo.getText().trim();
        String nombres = campoNombres.getText().trim();
        String apellidos = campoApellidos.getText().trim();

        if (codigo.isEmpty() ||
                nombres.isEmpty() ||
                apellidos.isEmpty()) {

            mensajeEstado.setText(
                    "Debe completar todos los campos."
            );
            return;
        }

        int id = gestor.cantidadEstudiantes() + 1;

        Estudiante estudiante = new Estudiante(
                id,
                codigo,
                nombres,
                apellidos,
                true
        );

        boolean registrado =
                gestor.registrarEstudiante(estudiante);

        if (registrado) {
            mensajeEstado.setText(
                    "Estudiante registrado correctamente."
            );
        } else {
            mensajeEstado.setText(
                    "Ya existe un estudiante con ese código."
            );
        }
    }

    @FXML
    private void buscar() {

        String codigo = campoCodigo.getText().trim();

        if (codigo.isEmpty()) {
            mensajeEstado.setText(
                    "Ingrese el código universitario."
            );
            return;
        }

        Estudiante estudiante =
                gestor.buscarEstudiante(codigo);

        if (estudiante != null) {

            campoNombres.setText(
                    estudiante.getNombres()
            );

            campoApellidos.setText(
                    estudiante.getApellidos()
            );

            mensajeEstado.setText(
                    "Estudiante encontrado correctamente."
            );

        } else {
            mensajeEstado.setText(
                    "Estudiante no encontrado."
            );
        }
    }
}
