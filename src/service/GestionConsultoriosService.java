package service;

import model.Cita;
import model.Consultorio;
import model.IPS;

import java.util.ArrayList;

public class GestionConsultoriosService {
    IPS ips;
    GestionArchivosService gas;

    public GestionConsultoriosService(IPS ips, GestionArchivosService gas){
        this.ips = ips;
        this.gas = gas;
    }

    public void listarConsultorios(){
        for(Consultorio con: ips.getConsultorios()){
            System.out.println(con.toString());
        }
    }

    public void agregarConsultorio(String especialidad, int piso){
        int idCon = ips.genIdCon();
        Consultorio con = new Consultorio(idCon, especialidad, piso);
        ips.agregarConsultorio(con);
        gas.cargarConsultorio(con);
    }

    public void verConsultorio(int idCon){
        System.out.println(ips.getConsultorioXid(idCon).toString());

    }

    public void actulizarConsultorio(int idCon, Consultorio con){
        ips.actualizarConsultorio(idCon, con);
    }

    public void eliminarConsultorio(int idCon){
        ips.eliminarConsultorio(idCon);
    }

    public ArrayList<String> getListadoConsultorios() {
        ArrayList<String> listado = new ArrayList<>();
        for(Consultorio con: ips.getConsultorios()){
            listado.add(con.toString());
        }
        return listado;
    }
}
