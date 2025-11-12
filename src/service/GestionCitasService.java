package service;

import model.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class GestionCitasService {
    IPS ips;
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public GestionCitasService(IPS ips){
        this.ips = ips;
    }

    public String listadoCitas(){
        String listado = "";
        for (Cita cita : ips.getCitas()) {
            listado = listado.concat(cita.toString().concat("\n"));
        }
        return listado;
    }

    public void reservarCita(int idPac, int idMed){
        int idCit = ips.getCitas().size() + 1;
        LocalDateTime fechaHora = LocalDateTime.now();
        Paciente pac = ips.getPacienteXid(idPac);
        Medico med = ips.getMedicoXid(idMed);
        Cita cit = new Cita(idCit, pac, med, fechaHora);
        ips.agregarCita(cit);
    }

    public String listadoCitasPaciente(Paciente pac){
        String listado = "";
        for (Cita cita : ips.getCitas()) {
            if(pac.getId() == cita.getPaciente().getId()){
                listado = listado.concat(cita.toString().concat("\n"));
            }
        }
        return listado;
    }

    public String listadoCitasMedico(Medico med){
        String listado = "";
        for (Cita cita : ips.getCitas()) {
            if(med.getId() == cita.getMedico().getId()){
                listado = listado.concat(cita.toString().concat("\n"));
            }
        }
        return listado;
    }

    public String getDatosCita(int idCit){
        String infoCita = "";
        Cita cit = ips.getCitaXid(idCit);
        infoCita = cit.toString();
        return infoCita;
    }

    public void actulizarEstadoCita(int idCit, int nuevoEstado){
        Cita nuevaCita = ips.getCitaXid(idCit);
        switch(nuevoEstado){
            case 0:
                nuevaCita.setEstado(EstadoCita.RESERVADA);
                break;
            case 1:
                nuevaCita.setEstado(EstadoCita.COMPLETADA);
                break;
            case 2:
                nuevaCita.setEstado(EstadoCita.CANCELADA);
                break;
        }
        ips.actualizarCita(idCit, nuevaCita);
    }

    public void cancelarCita(int idCit){
        Cita cita = ips.getCitas().get(idCit);
        cita.setEstado(EstadoCita.CANCELADA);
        ips.actualizarCita(idCit, cita);
    }
}
