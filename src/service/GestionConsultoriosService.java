package service;

import model.Consultorio;
import model.IPS;

public class GestionConsultoriosService {
    IPS ips;
    GestionArchivosService gas;

    public GestionConsultoriosService(IPS ips, GestionArchivosService gas){
        this.ips = ips;
        this.gas = gas;
    }

    public void agregarConsultorio(String especialidad, int piso){
        int idCon = ips.genIdCon();
        Consultorio con = new Consultorio(idCon, especialidad, piso);
        ips.agregarConsultorio(con);
        gas.cargarConsultorio(con);
    }

    public void actulizarConsultorio(int idCon, Consultorio con){
        ips.actualizarConsultorio(idCon, con);
    }

    public void eliminarConsultorio(int idCon){
        ips.eliminarConsultorio(idCon);
    }

}
