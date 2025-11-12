package controller;

import service.GestionCitasService;
import service.GestionUsuariosService;
import service.ValidacionDatosService;

import java.util.Scanner;

public class RecepcionistaController {
    GestionCitasService gcs;
    GestionUsuariosService gus;
    Scanner input;

    public RecepcionistaController(GestionCitasService gcs, GestionUsuariosService gus, Scanner input){
        this.gcs = gcs;
        this.gus = gus;
        this.input = input;
    }

    public String listadoCitas(){
        return gcs.listadoCitas();
    }

    public void reservarCita(int idPac, int idMed){
        gcs.reservarCita(idPac, idMed);
    }

    public String listadoUsuarios(){
        return gus.listadoUsuarios();
    }
    public void registrarPaciente(String nombre, String numId, String email, String telefono){
        gus.registrarPaciente(nombre, numId,email, telefono);
    }

    public String getInfoUsuario(int idUsr){
        return gus.getInfoUsuario(idUsr);
    }

}
