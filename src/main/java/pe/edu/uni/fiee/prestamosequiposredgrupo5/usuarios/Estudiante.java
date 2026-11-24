
package pe.edu.uni.fiee.prestamosequiposredgrupo5.usuarios;

public class Estudiante {

    // Atributos del estudiante
    private int id;
    private String codigoUniversitario;
    private String nombres;
    private String apellidos;
    private boolean activo;

    // Constructor
    public Estudiante(int id, String codigoUniversitario,
                      String nombres, String apellidos,
                      boolean activo) {
        this.id = id;
        this.codigoUniversitario = codigoUniversitario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.activo = activo;
    }

    // Métodos Getters
    public int getId() {
        return id;
    }

    public String getCodigoUniversitario() {
        return codigoUniversitario;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public boolean isActivo() {
        return activo;
    }

    // Métodos Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCodigoUniversitario(String codigoUniversitario) {
        this.codigoUniversitario = codigoUniversitario;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Obtener el nombre completo
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}
