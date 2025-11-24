package controller;

import model.Medico;
import service.GestionCitasService;
import service.GestionUsuariosService;

public class MedicoController {
    GestionCitasService gcs;
    GestionUsuariosService gus;
    Medico medico;

    public MedicoController(GestionCitasService gcs, GestionUsuariosService gus){
        this.gcs = gcs;
        this.gus = gus;
    }

    public void atenderCita(int idCit){
        gcs.actulizarEstadoCita(idCit, 1);
    }

    public void cancelarCita(int idCit){
        gcs.actulizarEstadoCita(idCit, 2);
    }

}
