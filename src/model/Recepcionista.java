package model;

public class Recepcionista extends Usuario{

    public Recepcionista(int id, String nombre, String nIdentificacion,String email, String telefono, TipoUsuario tipoUsuario) {
        super(id, nombre, nIdentificacion,email, telefono, tipoUsuario);
    }

    public String toCSV(){
        return getId() + ";" + getNombre() + ";" + getNumId() + ";" + getEmail() + ";" + getTelefono();
    }

}
