package controller;

import model.Paciente;
import service.GestionCitasService;
import service.GestionUsuariosService;

public class PacienteController {
    GestionCitasService gcs;
    GestionUsuariosService gus;
    Paciente paciente;

    public PacienteController(GestionCitasService gcs, GestionUsuariosService gus){
        this.gcs = gcs;
        this.gus = gus;
    }

    public String listadoCitas(){
        return gcs.listadoCitasPaciente(paciente);
    }

    public void reservarCita(int idMed){
        gcs.reservarCita(paciente.getId(), idMed);
    }

    public void cancelarCita(int idCit){
        gcs.cancelarCita(idCit);
    }
}
