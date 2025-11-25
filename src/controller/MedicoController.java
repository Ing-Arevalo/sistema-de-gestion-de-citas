package controller;

import model.Cita;
import model.EstadoCita;
import model.Medico;
import service.GestionCitasService;
import service.GestionUsuariosService;

import java.util.List;
import java.util.stream.Collectors;

public class MedicoController {

    private final GestionCitasService gcs;
    private final GestionUsuariosService gus;
    private Medico medico; // médico actualmente logueado

    public MedicoController(GestionCitasService gcs, GestionUsuariosService gus){
        this.gcs = gcs;
        this.gus = gus;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Medico getMedico() {
        return medico;
    }

    // ===================== CITAS DEL MÉDICO =====================

    public List<Cita> listarCitasDelMedico() {
        if (medico == null) return List.of();
        return gcs.listarCitas()
                .stream()
                .filter(c -> c.getMedico() != null && c.getMedico().getId() == medico.getId())
                .collect(Collectors.toList());
    }

    /**
     * Marcar una cita como ATENDIDA (COMPLETADA).
     */
    public void marcarCitaAtendida(int idCit) {
        gcs.actulizarEstadoCita(idCit, 2); // 2 = COMPLETADA
    }

    /**
     * Marcar una cita como CANCELADA.
     */
    public void cancelarCita(int idCit) {
        gcs.actulizarEstadoCita(idCit, 1); // 1 = CANCELADA
    }
}
