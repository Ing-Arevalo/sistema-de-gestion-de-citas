package controller;

import model.Medico;
import service.GestionArchivosService;
import service.GestionCitasService;
import service.GestionConsultoriosService;
import service.GestionUsuariosService;

import java.util.List;

public class AdministradorController {

    private final GestionCitasService gcs;
    private final GestionUsuariosService gus;
    private final GestionArchivosService gas;
    private final GestionConsultoriosService gos;

    public AdministradorController(GestionCitasService gcs,
                                   GestionUsuariosService gus,
                                   GestionArchivosService gas,
                                   GestionConsultoriosService gos) {
        this.gcs = gcs;
        this.gus = gus;
        this.gas = gas;
        this.gos = gos;
    }

    // =====================================================
    // CRUD MÉDICOS
    // =====================================================
    public List<Medico> listarMedicos() {
        return gus.listarMedicos();
    }

    public void registrarMedico(String nombre, String numId, String email, String telefono, String especialidad) {
        gus.registrarMedico(nombre, numId, email, telefono, especialidad);
    }

    public void actualizarMedico(int idMed, Medico updated) {
        gus.actulizarMedico(idMed, updated);
    }

    public void eliminarMedico(int idMed) {
        gus.eliminarMedico(idMed);
    }

    // =====================================================
    // CONSULTORIOS
    // =====================================================
    public void registrarConsultorio(String especialidad, int piso) {
        gos.agregarConsultorio(especialidad, piso);
        gas.guardarConsultorios();
    }
}
