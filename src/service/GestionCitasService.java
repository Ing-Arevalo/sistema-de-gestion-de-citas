package service;

import model.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class GestionCitasService {
    IPS ips;
    GestionArchivosService gas;
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public GestionCitasService(IPS ips, GestionArchivosService gas){
        this.ips = ips;
        this.gas = gas;
    }

    public void reservarCita(int idMed, int idPac, LocalDateTime fechaHora){
        int idCit = ips.genIdCit();
        Paciente pac = ips.getPacienteXid(idPac);
        Medico med = ips.getMedicoXid(idMed);
        Cita cit = new Cita(idCit, med, pac, fechaHora, EstadoCita.RESERVADA);
        ips.agregarCita(cit);
        gas.cargarCita(cit);
    }

    public void actulizarEstadoCita(int idCit, int nuevoEstado){
        Cita nuevaCita = ips.getCitaXid(idCit);
        switch(nuevoEstado){
            case 0:
                nuevaCita.setEstado(EstadoCita.RESERVADA);
                break;
            case 1:
                nuevaCita.setEstado(EstadoCita.CANCELADA);
                break;
            case 2:
                nuevaCita.setEstado(EstadoCita.COMPLETADA);
                break;
        }
        ips.actualizarCita(idCit, nuevaCita);
    }

}
