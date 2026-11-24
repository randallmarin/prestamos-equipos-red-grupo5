
package pe.edu.uni.fiee.prestamosequiposredgrupo5.usuarios;

public class PruebaGestorEstudiantes {

    public static void main(String[] args) {

        GestorEstudiantes gestor = new GestorEstudiantes();

        // Crear estudiantes de prueba
        Estudiante estudiante1 = new Estudiante(
                1, "20260001", "Ana", "Rojas", true);

        Estudiante estudiante2 = new Estudiante(
                2, "20260001", "Luis", "Perez", true);

        // Registrar estudiantes
        System.out.println("Registro 1: " +
                gestor.registrarEstudiante(estudiante1));

        System.out.println("Registro duplicado: " +
                gestor.registrarEstudiante(estudiante2));

        // Buscar estudiante
        Estudiante encontrado =
                gestor.buscarEstudiante("20260001");

        System.out.println("Estudiante encontrado: " +
                (encontrado != null
                        ? encontrado.getNombreCompleto()
                        : "No encontrado"));

        // Contar registros
        System.out.println("Total de estudiantes: " +
                gestor.cantidadEstudiantes());
    }
}
