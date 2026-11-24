
package pe.edu.uni.fiee.prestamosequiposredgrupo5.usuarios;

import java.util.ArrayList;
import java.util.List;

public class GestorEstudiantes {

    // Lista donde se almacenan los estudiantes
    private final List<Estudiante> estudiantes;

    // Constructor
    public GestorEstudiantes() {
        estudiantes = new ArrayList<>();
    }

    // Registrar un estudiante
    public boolean registrarEstudiante(Estudiante estudiante) {

        if (estudiante == null ||
                estudiante.getCodigoUniversitario() == null ||
                estudiante.getCodigoUniversitario().isBlank()) {
            return false;
        }

        // Verificar que no exista el mismo codigo
        if (buscarEstudiante(
                estudiante.getCodigoUniversitario()) != null) {
            return false;
        }

        estudiantes.add(estudiante);
        return true;
    }

    // Buscar estudiante por codigo universitario
    public Estudiante buscarEstudiante(String codigo) {

        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getCodigoUniversitario()
                    .equalsIgnoreCase(codigo.trim())) {
                return estudiante;
            }
        }

        return null;
    }

    // Obtener todos los estudiantes registrados
    public List<Estudiante> listarEstudiantes() {
        return new ArrayList<>(estudiantes);
    }

    // Consultar la cantidad de estudiantes
    public int cantidadEstudiantes() {
        return estudiantes.size();
    }
}
