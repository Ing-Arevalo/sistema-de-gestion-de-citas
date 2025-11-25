package service;

import model.*;

import java.util.List;

public class GestionUsuariosService {
    IPS ips;
    GestionArchivosService gas;

    public GestionUsuariosService(IPS ips, GestionArchivosService gas){
        this.ips = ips;
        this.gas = gas;
    }

    // ===================== RECEPCIONISTAS =====================

    public void registrarRecepcionista(String nombre, String numId, String email, String telefono, String especialidad){
        int idRep = ips.genIdUsr();
        Recepcionista rep = new Recepcionista(idRep, nombre, numId, email, telefono);
        ips.agregarRecepcionista(rep);
        gas.cargarRecepcionista(rep);
    }

    public void actulizarRecepcionista(int idRep, Recepcionista rep){
        Recepcionista old = ips.getRecepcionistas().get(idRep);
        ips.actualizarRecepcionista(old, rep);
        gas.guardarRecepcionistas();
    }

    public void eliminarRecepcionista(int idRep){
        ips.eliminarRecepcionistas(idRep);
        gas.guardarRecepcionistas();
    }

    // ===================== PACIENTES =====================

    public void registrarPaciente(String nombre, String numId, String email, String telefono){
        int idPac = ips.genIdUsr();
        Paciente pac = new Paciente(idPac, nombre, numId, email, telefono);
        ips.agregarPaciente(pac);
        gas.cargarPaciente(pac);
    }

    public void actulizarPaciente(int idPac, Paciente pac){
        Paciente old = ips.getPacienteXid(idPac);
        ips.actualizarPaciente(old, pac);
        gas.guardarPacientes();
    }

    public void eliminarPaciente(int idPac){
        ips.eliminarPaciente(idPac);
        gas.guardarPacientes();
    }

    public List<Paciente> listarPacientes() {
        return ips.getPacientes();
    }

    // ===================== MÉDICOS =====================

    public void registrarMedico(String nombre, String numId, String email, String telefono, String especialidad){
        int idMed = ips.genIdUsr();
        Medico med = new Medico(idMed, nombre, numId, email, telefono, especialidad);
        ips.agregarMedico(med);
        gas.cargarMedico(med);
    }

    public void actulizarMedico(int idMed, Medico med){
        Medico old = ips.getMedicoXid(idMed);
        ips.actualizarMedico(old, med);
        gas.guardarMedicos();
    }

    public void eliminarMedico(int idMed){
        ips.eliminarMedico(idMed);
        gas.guardarMedicos();
    }

    public List<Medico> listarMedicos() {
        return ips.getMedicos();
    }

    // ===================== LOGIN / BÚSQUEDA =====================

    public Usuario buscarUsuarioPorDocumento(String numId) {
        for (Usuario usr : ips.getUsuarios()) {
            if (usr.getNumId().equals(numId)) {
                return usr;
            }
        }
        return null;
    }

    /**
     * Login de usuario (médico / paciente / recepcionista) con documento + password.
     * Retorna el Usuario si las credenciales son correctas; null en caso contrario.
     */
    public Usuario validarLoginUsuario(String numId, String password) {
        Usuario u = buscarUsuarioPorDocumento(numId);
        if (u == null) return null;

        boolean ok = gas.validarCredencial(numId, password);
        if (!ok) return null;

        return u;
    }

    /**
     * Validación simple de administrador (usuario/clave).
     */
    public boolean validarAdministrador(String usuario, String clave) {
        String adminUser = "admin";
        String adminPass = "admin123";
        return adminUser.equals(usuario) && adminPass.equals(clave);
    }
}
