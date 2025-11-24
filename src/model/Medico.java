package model;

import java.util.ArrayList;

public class Medico extends Usuario{
    private String especialidad;
    private Consultorio consultorioAsignado;
    private final ArrayList<Integer> horario;

    public Medico(int id, String nombre, String numId, String email, String telefono, String especialidad) {
        super(id, nombre, numId, email, telefono, TipoUsuario.MEDICO);
        this.especialidad = especialidad;
        this.horario = new ArrayList<>();
    }

    public String toCSV(){
        return getId() + ";" + getNombre() + ";" + getNumId() + ";" + getEmail() + ";" + getTelefono() + ";" + getEspecialidad();
    }

    public void asignarConsultorio(Consultorio con){
        this.consultorioAsignado = con;
    }

    public void asignarCita(Cita cita){
        Integer idCit = cita.getId();
        if(!horario.contains(idCit)){
            horario.add(idCit);
        }
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Consultorio getConsultorioAsignado() {
        return consultorioAsignado;
    }

    public void setConsultorioAsignado(Consultorio consultorioAsignado) {
        this.consultorioAsignado = consultorioAsignado;
    }

    public ArrayList<Integer> getHorario() {
        return horario;
    }
}
