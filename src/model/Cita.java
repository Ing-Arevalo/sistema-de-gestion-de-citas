package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cita {
    private final int id;
    private EstadoCita estado;
    private Paciente paciente;
    private Medico medico;
    private Consultorio consultorio;
    private LocalDateTime fechaYhoraInicio;
    private LocalDateTime fechaYhoraFin;
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Cita(int id, Paciente paciente, Medico medico, LocalDateTime fechaYhora) {
        this.id = id;
        this.estado = EstadoCita.RESERVADA;
        this.paciente = paciente;
        this.medico = medico;
        this.consultorio = medico.getConsultorioAsignado();
        this.fechaYhoraInicio = fechaYhora;
        this.fechaYhoraFin = fechaYhora.plusMinutes(30);
    }

    public int getId() {
        return id;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public LocalDateTime getFechaYhoraInicio() {
        return fechaYhoraInicio;
    }

    public void setFechaYhoraInicio(LocalDateTime fechaYhoraInicio) {
        this.fechaYhoraInicio = fechaYhoraInicio;
    }

    public String toCSV(){
        return getId() + ";" + getMedico().getId() + ";" + getPaciente().getId() + ";" + getFechaYhoraInicio().format(formato);
    }

    public String toString(){
        return id + " " + getPaciente().getNombre() + " " + getMedico().getNombre();
    }
}
