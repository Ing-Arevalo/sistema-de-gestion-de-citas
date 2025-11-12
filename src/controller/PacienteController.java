package controller;

import model.Paciente;
import service.GestionCitasService;
import service.GestionUsuariosService;

import java.util.Scanner;

public class PacienteController {
    GestionCitasService gcs;
    GestionUsuariosService gus;
    Scanner input;
    Paciente paciente;

    public PacienteController(GestionCitasService gcs, GestionUsuariosService gus, Scanner input){
        this.gcs = gcs;
        this.gus = gus;
        this.input = input;
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
