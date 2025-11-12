package controller;

import model.Medico;
import service.GestionCitasService;
import service.GestionUsuariosService;
import java.util.Scanner;

public class MedicoController {
    GestionCitasService gcs;
    GestionUsuariosService gus;
    Scanner input;
    Medico medico;

    public MedicoController(GestionCitasService gcs, GestionUsuariosService gus, Scanner input){
        this.gcs = gcs;
        this.gus = gus;
        this.input = input;
    }

    public void atenderCita(int idCit){
        gcs.actulizarEstadoCita(idCit, 1);
    }

    public String getListadoCitas(){
        return gcs.listadoCitasMedico(medico);
    }

    public String getDatosCita(int idCit){
        return gcs.getDatosCita(idCit);
    }
}
