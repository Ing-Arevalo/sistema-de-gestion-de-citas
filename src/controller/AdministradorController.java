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

    public String getListadoUsuarios(){
        return gus.listadoUsuarios();
    }

    public String getDatosUsuario(int idUsr){
        String datos = "";
        return datos;
    }

    public void registrarRecepcionista(){

    }

    public void registrarMedico(String nombre, String numId, String email, String  telefono, String especialidad){
        gus.registrarMedico(nombre, numId, email, telefono, especialidad);
    }

    public void registrarUsuario(String nombre, String numId, String email, String  telefono, String especialidad){

    }

    public void editarUsuario(){

    }

    public void eliminarUsuario(){

    }

    public String getListadoConsultorios(){
        String listado = "";
        return listado;
    }

    public void registrarConsultorio(String especialidad, int piso){
        gos.agregarConsultorio(especialidad, piso);
    }

    public String getDatosConsultorio(int idCon){
        String datos = "";
        return datos;
    }

    public void editarConsultorio(int idCon){

    }

    public void eliminarConsultorio(int idCon){

    }
}
