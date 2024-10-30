package TipsUY.backend.controllers.Usuario;

public class LoginResponse {
    private String tipo;
    private String nombre;
    private String cedula;

    public LoginResponse(String tipo, String nombre, String cedula) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

}