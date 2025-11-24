package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cita {
    private final int id;
    private EstadoCita estado;
    private Paciente paciente;
    private Medico medico;
    private final Consultorio consultorio;
    private LocalDateTime fechaYhoraInicio;
    private LocalDateTime fechaYhoraFin;
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Cita(int id, Medico medico, Paciente paciente, LocalDateTime fechaYhora, EstadoCita estado) {
        this.id = id;
        this.estado = estado;
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

    public LocalDateTime getFechaYhoraInicio() {
        return fechaYhoraInicio;
    }

    public void setFechaYhoraInicio(LocalDateTime fechaYhoraInicio) {
        this.fechaYhoraInicio = fechaYhoraInicio;
    }

    public String toCSV(){
        return getId() + ";" + getMedico().getId() + ";" + getPaciente().getId() + ";" + getFechaYhoraInicio().format(formato) + ";" + getEstado().ordinal();
    }

    public String toString(){
        return id + " " + getMedico().getNombre() + " " + getPaciente().getNombre() + " " + getFechaYhoraInicio().format(formato) + " " + getEstado();
    }
}
