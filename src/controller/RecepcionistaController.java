package controller;

import model.Cita;
import model.Medico;
import model.Paciente;
import service.GestionCitasService;
import service.GestionUsuariosService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecepcionistaController {

    private final GestionCitasService gcs;
    private final GestionUsuariosService gus;
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public RecepcionistaController(GestionCitasService gcs, GestionUsuariosService gus) {
        this.gcs = gcs;
        this.gus = gus;
    }

    // ===================== PACIENTES =====================

    public List<Paciente> listarPacientes() {
        return gus.listarPacientes();
    }

    public void registrarPaciente(String nombre, String numId, String email, String telefono) {
        gus.registrarPaciente(nombre, numId, email, telefono);
    }

    public void actualizarPaciente(int idPac, Paciente pacActualizado) {
        gus.actulizarPaciente(idPac, pacActualizado);
    }

    public void eliminarPaciente(int idPac) {
        gus.eliminarPaciente(idPac);
    }

    // ===================== MÉDICOS (para combos) =====================

    public List<Medico> listarMedicos() {
        return gus.listarMedicos();
    }

    // ===================== CITAS =====================

    public List<Cita> listarCitas() {
        return gcs.listarCitas();
    }

    public void crearCita(int idMed, int idPac, String fechaHoraStr) {
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, formato);
        gcs.reservarCita(idMed, idPac, fechaHora);
    }

    public void cancelarCita(int idCit) {
        gcs.actulizarEstadoCita(idCit, 1); // 1 = CANCELADA
    }

    public void eliminarCita(int idCit) {
        gcs.eliminarCita(idCit);
    }
}
