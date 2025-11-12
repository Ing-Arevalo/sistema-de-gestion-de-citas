package service;

import model.Consultorio;
import model.IPS;

public class GestionConsultoriosService {
    IPS ips;

    public GestionConsultoriosService(IPS ips){
        this.ips = ips;
    }

    public void listarConsultorios(){
        for(Consultorio con: ips.getConsultorios()){
            System.out.println(con.toString());
        }
    }

    public void agregarConsultorio(Consultorio con){
        ips.agregarConsultorio(con);
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
}
