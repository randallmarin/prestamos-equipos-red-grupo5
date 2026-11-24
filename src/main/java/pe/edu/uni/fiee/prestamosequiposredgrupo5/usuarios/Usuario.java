
package pe.edu.uni.fiee.prestamosequiposredgrupo5.usuarios;

public class Usuario {

    // Atributos del usuario
    private int id;
    private String nombreUsuario;
    private String hashContrasena;
    private String rol;
    private boolean activo;

    // Constructor
    public Usuario(int id, String nombreUsuario,
                   String hashContrasena, String rol,
                   boolean activo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.hashContrasena = hashContrasena;
        this.rol = rol;
        this.activo = activo;
    }

    // Métodos Getters
    public int getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getHashContrasena() {
        return hashContrasena;
    }

    public String getRol() {
        return rol;
    }

    public boolean isActivo() {
        return activo;
    }

    // Métodos Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setHashContrasena(String hashContrasena) {
        this.hashContrasena = hashContrasena;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
