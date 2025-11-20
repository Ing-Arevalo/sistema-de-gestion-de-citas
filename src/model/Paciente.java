package model;


public class Paciente extends Usuario{

    public Paciente(int id, String nombre, String numId, String email, String telefono) {
        super(id, nombre, numId, email, telefono, TipoUsuario.PACIENTE);
    }

    public String toCSV(){
        return getId() + ";" + getNombre() + ";" + getNumId() + ";" + getEmail() + ";" + getTelefono();
    }
}
