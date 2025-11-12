package service;

import model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class GestionUsuariosService {
    IPS ips;
    Scanner input = new Scanner(System.in);

    public GestionUsuariosService(IPS ips){
        this.ips = ips;
    }

    public String listadoUsuarios(){
        String listado = "";
        for(Usuario usr: ips.getUsuarios()){
            listado = listado.concat(usr.toString()).concat("\n");
        }
        return listado;
    }

    public String listadoUsuariosv2(){
        String listado = "";
        File archivo = new File("");
        for(Usuario usr: ips.getUsuarios()){
            listado = listado.concat(usr.toString()).concat("\n");
        }
        return listado;
    }

    public ArrayList<String> getListadoPacientes(){
        ArrayList<String> listado = new ArrayList<>();
        for(Paciente pac: ips.getPacientes()){
            listado.add(pac.toString());
        }
        return listado;
    }

    public ArrayList<String> getListadoMedicos(){
        ArrayList<String> listado = new ArrayList<>();
        for(Medico med: ips.getMedicos()){
            listado.add(med.toString());
        }
        return listado;
    }

    public String getInfoUsuario(int idUsr){
        if(ips.getUsuarioXid(idUsr).getTipoUsuario() == TipoUsuario.PACIENTE){
            return getInfoPaciente(idUsr);
        }else
        if(ips.getUsuarioXid(idUsr).getTipoUsuario() == TipoUsuario.MEDICO){
            return getInfoMedico(idUsr);
        }
        return "";
    }

    public void registrarPaciente(String nombre, String numId, String email, String telefono){
        int idPac = ips.genIdUsr();
        Paciente pac = new Paciente(idPac, nombre, numId, email, telefono);
        ips.agregarPaciente(pac);
    }

    public String getInfoPaciente(int idPac){
        Paciente pac = ips.getPacienteXid(idPac);
        return "\n" + pac.getTipoUsuario() + "\nID: " + pac.getId() + "\nNombre: " + pac.getNombre() + "\nIdentificación: " + pac.getNumId() + "\nEmail: " + pac.getEmail() + "\nTelefono: " + pac.getTelefono();
    }

    public void actulizarPaciente(int idPac, Paciente pac){
        ips.actualizarPaciente(idPac, pac);
    }

    public void eliminarPaciente(int idPac){
        ips.eliminarPaciente(idPac);
    }

    public void registrarMedico(String nombre, String numId, String email, String telefono, String especialidad){
        int idMed = ips.genIdUsr();
        Medico med = new Medico(idMed, nombre, numId, email, telefono, especialidad);
        ips.agregarMedico(med);
    }

    public String getInfoMedico(int idMed){
        Medico med = ips.getMedicoXid(idMed);
        return "\n" + med.getTipoUsuario() + "\nID: " + med.getId() + "\nNombre: " + med.getNombre() + "\nIdentificación: " + med.getNumId() + "\nEmail: " + med.getEmail() + "\nTelefono: " + med.getTelefono();
    }

    public void actulizarMedico(int idMed, Medico med){
        ips.actualizarMedico(idMed, med);
    }

    public void eliminarMedico(int idMed){
        ips.eliminarMedico(idMed);
    }

}
