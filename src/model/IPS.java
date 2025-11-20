package model;

import java.util.ArrayList;
import java.util.List;

public class IPS {
    private final String nombre;
    private final String NIT;
    private final List<Usuario> usuarios;
    private final List<Paciente> pacientes;
    private final List<Medico> medicos;
    private final List<Cita> citas;
    private final List<Consultorio> consultorios;

    public IPS(String nombre, String NIT) {
        this.nombre = nombre;
        this.NIT = NIT;
        this.usuarios = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.citas = new ArrayList<>();
        this.consultorios = new ArrayList<>();
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNIT() {
        return NIT;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public List<Consultorio> getConsultorios() {
        return consultorios;
    }

    public void agregarUsuario(Usuario usr){
        usuarios.add(usr);

        if(usr instanceof Paciente){
            agregarPaciente((Paciente) usr);
        }else
        if(usr instanceof Medico){
            agregarMedico((Medico) usr);
        }
    }


    public void agregarMedico(Medico med){
        medicos.add(med);
        usuarios.add(med);
    }

    public Medico getMedicoXid(int idMed){
        for(Medico med : medicos){
            if(med.getId() == idMed){
                return med;
            }
        }
        return null;
    }

    public void actualizarMedico(int idMed, Medico med){
        medicos.set(idMed, med);
        usuarios.set(idMed, med);
    }

    public void eliminarMedico(int idMed){
        medicos.remove(idMed);
        usuarios.remove(idMed);
    }

    public void agregarPaciente(Paciente pac){
        usuarios.add(pac);
        pacientes.add(pac);
    }

    public Paciente getPacienteXid(int idPac){
        for(Paciente pac : pacientes){
            if(pac.getId() == idPac){
                return pac;
            }
        }
        return null;
    }

    public void actualizarPaciente(int idPac, Paciente pac){
        pacientes.set(idPac, pac);
        usuarios.set(idPac, pac);
    }

    public void eliminarPaciente(int idPac){
        pacientes.remove(idPac);
        usuarios.remove(idPac);
    }

    public void agregarConsultorio(Consultorio con){
        consultorios.add(con);
    }

    public Consultorio getConsultorioXid(int idCon){
        return consultorios.get(idCon);
    }

    public void actualizarConsultorio(int idCon, Consultorio con){
        consultorios.set(idCon, con);
    }

    public void eliminarConsultorio(int idCon){
        consultorios.remove(idCon);
    }

    public void agregarCita(Cita c){
        citas.add(c);
    }

    public Cita getCitaXid(int idCit){
        return citas.get(idCit);
    }

    public void actualizarCita(int idCita, Cita c){
        citas.set(idCita, c);
    }

    public Usuario getUsuarioXid(int idUsr){
        return usuarios.get(idUsr);
    }

    public int genIdUsr(){
        return (usuarios.size() + 1);
    }

    public int genIdCon(){
        return (consultorios.size() + 1);
    }

    public int genIdCit(){
        return (citas.size() + 1);
    }

}
