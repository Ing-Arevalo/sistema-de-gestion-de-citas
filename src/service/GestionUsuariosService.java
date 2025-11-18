package service;

import model.*;

public class GestionUsuariosService {
    IPS ips;
    GestionArchivosService gas;

    public GestionUsuariosService(IPS ips, GestionArchivosService gas){
        this.ips = ips;
        this.gas = gas;
    }

    public void registrarRecepcionista(String nombre, String numId, String email, String telefono, String especialidad){
        int idRep = ips.genIdUsr();
        Recepcionista rep = new Recepcionista(idRep, nombre, numId, email, telefono);
        ips.agregarRecepcionista(rep);
        gas.cargarRecepcionista(rep);
    }

    public void actulizarRecepcionista(int idRep, Recepcionista rep){
        ips.actualizarRecepcionista(ips.getRecepcionistas().get(idRep), rep);
    }

    public void eliminarRecepcionista(int idRep){
        ips.eliminarRecepcionistas(idRep);
    }

    public void registrarPaciente(String nombre, String numId, String email, String telefono){
        int idPac = ips.genIdUsr();
        Paciente pac = new Paciente(idPac, nombre, numId, email, telefono);
        ips.agregarPaciente(pac);
        gas.cargarPaciente(pac);
    }

    public void actulizarPaciente(int idPac, Paciente pac){
        ips.actualizarPaciente(ips.getPacienteXid(idPac), pac);
    }

    public void eliminarPaciente(int idPac){
        ips.eliminarPaciente(idPac);
    }

    public void registrarMedico(String nombre, String numId, String email, String telefono, String especialidad){
        int idMed = ips.genIdUsr();
        Medico med = new Medico(idMed, nombre, numId, email, telefono, especialidad);
        ips.agregarMedico(med);
        gas.cargarMedico(med);
    }

    public void actulizarMedico(int idMed, Medico med){
        ips.actualizarMedico(ips.getMedicoXid(idMed), med);
    }

    public void eliminarMedico(int idMed){
        ips.eliminarMedico(idMed);
    }
    public Usuario buscarUsuarioPorDocumento(String numId) {
        for (Usuario usr : ips.getUsuarios()) {
            if (usr.getNumId().equals(numId)) {
                return usr;
            }
        }
        return null;
    }
}
