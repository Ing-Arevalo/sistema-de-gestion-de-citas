package controller;

import model.Cita;
import model.Medico;
import model.Paciente;
import service.GestionCitasService;
import service.GestionUsuariosService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PacienteController {
    private final GestionCitasService gcs;
    private final GestionUsuariosService gus;
    private Paciente paciente;

    public PacienteController(GestionCitasService gcs, GestionUsuariosService gus){
        this.gcs = gcs;
        this.gus = gus;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void reservarCita(int idMed, LocalDateTime fechaHora){
        if (paciente == null) return;
        // reservarCita(idMed, idPac, fechaHora)
        gcs.reservarCita(idMed, paciente.getId(), fechaHora);
    }

    public void cancelarCita(int idCit){
        // 1 = CANCELADA
        gcs.actulizarEstadoCita(idCit, 1);
    }

    public List<Cita> listarCitasPaciente() {
        if (paciente == null) return List.of();
        return gcs.listarCitas()
                .stream()
                .filter(c -> c.getPaciente() != null && c.getPaciente().getId() == paciente.getId())
                .collect(Collectors.toList());
    }

    public List<Medico> listarMedicos() {
        return gus.listarMedicos();
    }
}
