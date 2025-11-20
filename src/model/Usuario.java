package model;

public abstract class Usuario {
    private final int id;
    private String nombre;
    private String numId;
    private String email;
    private String telefono;
    private final TipoUsuario tipoUsuario;

    public Usuario(int id, String nombre, String numId, String email, String telefono, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.numId = numId;
        this.email = email;
        this.telefono = telefono;
        this.tipoUsuario = tipoUsuario;
    }

    public abstract String toCSV();

    public int getId(){
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }


    @Override
    public String toString() {
        String rol = getTipoUsuario().toString().substring(0, 3);
        return id + " " + rol + " " + nombre;
    }

    public String getNumId() {
        return numId;
    }

    public void setNumId(String numId) {
        this.numId = numId;
    }

}
