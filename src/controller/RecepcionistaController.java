package controller;

import service.GestionCitasService;
import service.GestionUsuariosService;
import java.time.LocalDateTime;

public class RecepcionistaController {
    GestionCitasService gcs;
    GestionUsuariosService gus;

    public RecepcionistaController(GestionCitasService gcs, GestionUsuariosService gus){
        this.gcs = gcs;
        this.gus = gus;
    }

    public void reservarCita(int idMed, int idPac, LocalDateTime fechaHora){
        gcs.reservarCita(idMed, idPac, fechaHora);
    }

    public void registrarPaciente(String nombre, String numId, String email, String telefono){
        gus.registrarPaciente(nombre, numId, email, telefono);
    }

}
