
package pe.edu.uni.fiee.prestamosequiposredgrupo5.usuarios;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    @FXML
    private TableView<Estudiante> tablaEstudiantes;

    @FXML
    private TableColumn<Estudiante, String> columnaCodigo;

    @FXML
    private TableColumn<Estudiante, String> columnaNombres;

    @FXML
    private TableColumn<Estudiante, String> columnaApellidos;

    private final GestorEstudiantes gestor =
            new GestorEstudiantes();

    @FXML
    private void initialize() {

        columnaCodigo.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(
                        celda.getValue().getCodigoUniversitario()
                )
        );

        columnaNombres.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(
                        celda.getValue().getNombres()
                )
        );

        columnaApellidos.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(
                        celda.getValue().getApellidos()
                )
        );

        actualizarTabla();
    }

    private void actualizarTabla() {
        tablaEstudiantes.getItems().setAll(
                gestor.listarEstudiantes()
        );
    }

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

            actualizarTabla();

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

            tablaEstudiantes.getSelectionModel().select(
                    estudiante
            );

            tablaEstudiantes.scrollTo(estudiante);

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
