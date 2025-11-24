package controller;

import model.Paciente;
import service.GestionCitasService;
import service.GestionUsuariosService;

import java.time.LocalDateTime;

public class PacienteController {
    GestionCitasService gcs;
    GestionUsuariosService gus;
    Paciente paciente;

    public PacienteController(GestionCitasService gcs, GestionUsuariosService gus){
        this.gcs = gcs;
        this.gus = gus;
    }

    public void reservarCita(int idMed, LocalDateTime fechaHora){
        gcs.reservarCita(paciente.getId(), idMed, fechaHora);
    }

    public void cancelarCita(int idCit){
        gcs.actulizarEstadoCita(idCit, 2);
    }

}
