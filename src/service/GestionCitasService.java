package service;

import model.Cita;
import model.EstadoCita;
import model.IPS;
import model.Medico;
import model.Paciente;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GestionCitasService {
    IPS ips;
    GestionArchivosService gas;
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public GestionCitasService(IPS ips, GestionArchivosService gas){
        this.ips = ips;
        this.gas = gas;
    }

    // Crear cita (usado por Recepcionista y PacienteController)
    public void reservarCita(int idMed, int idPac, LocalDateTime fechaHora){
        int idCit = ips.genIdCit();
        Paciente pac = ips.getPacienteXid(idPac);
        Medico med = ips.getMedicoXid(idMed);
        Cita cit = new Cita(idCit, med, pac, fechaHora, EstadoCita.RESERVADA);
        ips.agregarCita(cit);
        gas.cargarCita(cit);      // append al archivo
    }

    // Cambiar estado de la cita POR ID REAL
    public void actulizarEstadoCita(int idCit, int nuevoEstado){
        List<Cita> lista = ips.getCitas();
        Cita objetivo = null;

        for (Cita c : lista) {
            if (c.getId() == idCit) {
                objetivo = c;
                break;
            }
        }

        if (objetivo == null) {
            return; // no existe esa cita
        }

        switch(nuevoEstado){
            case 0 -> objetivo.setEstado(EstadoCita.RESERVADA);
            case 1 -> objetivo.setEstado(EstadoCita.CANCELADA);
            case 2 -> objetivo.setEstado(EstadoCita.COMPLETADA);
            default -> { return; }
        }

        // Ya cambiamos el estado en la lista, ahora persistimos en el archivo
        gas.guardarCitas();
    }

    // Listado de citas (por si lo quieres en tablas)
    public List<Cita> listarCitas() {
        return new ArrayList<>(ips.getCitas());
    }

    // Eliminar cita por ID (no por índice)
    public void eliminarCita(int idCit) {
        List<Cita> lista = ips.getCitas();
        lista.removeIf(c -> c.getId() == idCit);
        gas.guardarCitas();
    }
}
