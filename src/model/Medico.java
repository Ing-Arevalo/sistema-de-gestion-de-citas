package model;

import java.time.LocalTime;
import java.util.ArrayList;

public class Medico extends Usuario{
    private String especialidad;
    private Consultorio consultorioAsignado;
    private String[][] agenda;
    private ArrayList<LocalTime> horario;

    public Medico(int id, String nombre, String numId, String email, String telefono, String especialidad) {
        super(id, nombre, numId, email, telefono, TipoUsuario.MEDICO);
        this.especialidad = especialidad;
    }

    public String toCSV(){
        return getId() + ";" + getNombre() + ";" + getNumId() + ";" + getEmail() + ";" + getTelefono() + ";" + getEspecialidad();
    }

    public void asignarConsultorio(Consultorio con){
        this.consultorioAsignado = con;
    }

    public void asignarHorario(ArrayList<LocalTime> horario){
        this.horario = horario;
    }

    public void asignarAgenda(String[][] agenda){
        this.agenda = agenda;
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

    public String[][] getAgenda() {
        return agenda;
    }

    public void setAgenda(String[][] agenda) {
        this.agenda = agenda;
    }

    public ArrayList<LocalTime> getHorario() {
        return horario;
    }
}
