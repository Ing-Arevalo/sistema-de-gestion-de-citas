package controller;

import service.GestionArchivosService;
import service.GestionCitasService;
import service.GestionConsultoriosService;
import service.GestionUsuariosService;

public class AdministradorController {
    GestionCitasService gcs;
    GestionUsuariosService gus;
    GestionArchivosService gas;
    GestionConsultoriosService gos;

    public AdministradorController(GestionCitasService gcs, GestionUsuariosService gus, GestionArchivosService gas, GestionConsultoriosService gos){
        this.gcs = gcs;
        this.gus = gus;
        this.gas = gas;
        this.gos = gos;
    }

    public void registarRecepcionista(){

    }

    public void actualizarRecepcionista(){

    }

    public void eliminarRecepcionista(){

    }

    public void registrarMedico(){

    }

    public void actualizarMedico(){

    }

    public void eliminarMedico(){

    }

    public void registrarPaciente(){

    }

    public void actualizarPaciente(){

    }

    public void eliminarPaciente(){

    }

    public void registrarConsultorio(String especialidad, int piso){
        gos.agregarConsultorio(especialidad, piso);
    }

    public void actualizarConsultorio(int idCon){

    }

    public void eliminarConsultorio(int idCon){

    }
}
