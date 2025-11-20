package controller;

import service.GestionCitasService;
import service.GestionUsuariosService;
import service.ValidacionDatosService;

public class RecepcionistaController {
    GestionCitasService gcs;
    GestionUsuariosService gus;

    public RecepcionistaController(GestionCitasService gcs, GestionUsuariosService gus){
        this.gcs = gcs;
        this.gus = gus;
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
        gus.registrarPaciente(nombre, numId, email, telefono);
    }

    public String getInfoUsuario(int idUsr){
        return gus.getInfoUsuario(idUsr);
    }

}
