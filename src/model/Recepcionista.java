package model;

public class Recepcionista extends Usuario{

    public Recepcionista(int id, String nombre, String numId, String email, String telefono) {
        super(id, nombre, numId, email, telefono, TipoUsuario.RECEPCIONISTA);
    }

    public String toCSV(){
        return getId() + ";" + getNombre() + ";" + getNumId() + ";" + getEmail() + ";" + getTelefono();
    }

}
