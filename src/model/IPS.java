package model;

import java.util.ArrayList;
import java.util.List;

public class IPS {
    private final String nombre;
    private final String NIT;
    private final ArrayList<Recepcionista> recepcionistas;
    private final ArrayList<Usuario> usuarios;
    private final ArrayList<Paciente> pacientes;
    private final ArrayList<Medico> medicos;
    private final ArrayList<Cita> citas;
    private final ArrayList<Consultorio> consultorios;

    public IPS(String nombre, String NIT) {
        this.nombre = nombre;
        this.NIT = NIT;
        this.recepcionistas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.citas = new ArrayList<>();
        this.consultorios = new ArrayList<>();
    }

    public ArrayList<Usuario> getUsuarios(){
        return usuarios;
    }

    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }

    public ArrayList<Medico> getMedicos() {
        return medicos;
    }

    public ArrayList<Recepcionista> getRecepcionistas() {
        return recepcionistas;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNIT() {
        return NIT;
    }

    public List<Consultorio> getConsultorios() {
        return consultorios;
    }

    public void agregarUsuario(Usuario usr){
        usuarios.add(usr);

        if(usr instanceof Paciente){
            agregarPaciente((Paciente) usr);
        }
        if(usr instanceof Medico){
            agregarMedico((Medico) usr);
        }
        if(usr instanceof Recepcionista){
            agregarRecepcionista((Recepcionista) usr);
        }
    }

    public void actualizarUsuario(Usuario oldUsr, Usuario newUsr){
        if(oldUsr.getClass() == newUsr.getClass()) {
            if (newUsr instanceof Paciente) {
                actualizarPaciente((Paciente) oldUsr, (Paciente) newUsr);
            }
            if (newUsr instanceof Medico) {
                actualizarMedico((Medico) oldUsr, (Medico) newUsr);
            }
            if (newUsr instanceof Recepcionista) {
                actualizarRecepcionista((Recepcionista) oldUsr, (Recepcionista) newUsr);
            }
            usuarios.set(oldUsr.getId(), newUsr);
        }
    }

    public void agregarRecepcionista(Recepcionista rec){
        recepcionistas.add(rec);
    }

    public void actualizarRecepcionista(Recepcionista oldRec, Recepcionista newRec){
        recepcionistas.set(recepcionistas.indexOf(oldRec), newRec);
    }

    public void eliminarRecepcionistas(int idRep){
        recepcionistas.remove(idRep);
    }

    public void agregarMedico(Medico med){
        medicos.add(med);
    }

    public Medico getMedicoXid(int idMed){
        for(Medico med : medicos){
            if(med.getId() == idMed){
                return med;
            }
        }
        return null;
    }

    public void actualizarMedico(Medico oldMed, Medico newMed){
        medicos.set(medicos.indexOf(oldMed), newMed);
    }

    public void eliminarMedico(int idMed){
        medicos.remove(idMed);
        usuarios.remove(idMed);
    }

    public void agregarPaciente(Paciente pac){
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

    public void actualizarPaciente(Paciente oldPac, Paciente newPac){
        pacientes.set(pacientes.indexOf(oldPac), newPac);
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
