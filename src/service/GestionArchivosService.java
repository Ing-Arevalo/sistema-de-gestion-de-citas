package service;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class GestionArchivosService {
    IPS ips;
    final String rutaMeds = "data/medicos.txt";
    final String rutaPacs = "data/pacientes.txt";
    final String rutaCits = "data/citas.txt";
    final String rutaCons = "data/consultorios.txt";
    final String rutaRecs = "data/recepcionistas.txt";
    final String rutaCreds = "data/credenciales.txt"; // credenciales

    public GestionArchivosService(IPS ips){
        this.ips = ips;
    }

    // =====================================================
    // DESCARGAR TODOS LOS ARCHIVOS
    // =====================================================
    public void descargarDatos() {
        descargarConsultorios(rutaCons);
        descargarRecepcionistas(rutaRecs);
        descargarPacientes(rutaPacs);
        descargarMedicos(rutaMeds);
        descargarCitas(rutaCits);
        // credenciales se leen a demanda
    }

    // =====================================================
    // RECEPCIONISTAS
    // =====================================================
    public void descargarRecepcionistas(String ruta){
        File file = new File(ruta);

        try(Scanner reader = new Scanner(file)){
            while (reader.hasNextLine()){
                String linea = reader.nextLine().trim();
                if (linea.isEmpty()) continue;

                String[] repCSV = linea.split(";");
                if (repCSV.length < 5) continue;

                Recepcionista rec = new Recepcionista(
                        Integer.parseInt(repCSV[0]),
                        repCSV[1],
                        repCSV[2],
                        repCSV[3],
                        repCSV[4]
                );
                ips.agregarUsuario(rec);
            }
        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado (recepcionistas): " + e);
        }
    }

    // =====================================================
    // PACIENTES
    // =====================================================
    public void descargarPacientes(String ruta) {
        File file = new File(ruta);

        try(Scanner reader = new Scanner(file)){
            while (reader.hasNextLine()){
                String linea = reader.nextLine().trim();
                if (linea.isEmpty()) continue;

                String[] pacCSV = linea.split(";");
                if (pacCSV.length < 5) continue;

                Paciente pac = new Paciente(
                        Integer.parseInt(pacCSV[0]),
                        pacCSV[1],
                        pacCSV[2],
                        pacCSV[3],
                        pacCSV[4]
                );
                ips.agregarUsuario(pac);
            }
        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado (pacientes): " + e);
        }
    }

    // =====================================================
    // MÉDICOS
    // =====================================================
    public void descargarMedicos(String ruta){
        File file = new File(ruta);

        try(Scanner reader = new Scanner(file)){
            while (reader.hasNextLine()){
                String linea = reader.nextLine().trim();
                if (linea.isEmpty()) continue;

                String[] medCSV = linea.split(";");
                // id;nombre;numId;email;telefono;especialidad;[idConsultorio]
                if (medCSV.length < 6) {
                    System.out.println("Línea de médico mal formada, se omite: " + linea);
                    continue;
                }

                int id = Integer.parseInt(medCSV[0]);
                String nombre = medCSV[1];
                String numId = medCSV[2];
                String email = medCSV[3];
                String tel = medCSV[4];
                String esp = medCSV[5];

                Medico med = new Medico(id, nombre, numId, email, tel, esp);

                if (medCSV.length >= 7) {
                    try {
                        int idCon = Integer.parseInt(medCSV[6]);
                        Consultorio conMed = ips.getConsultorioXid(idCon);
                        if (conMed != null) {
                            med.asignarConsultorio(conMed);
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("ID de consultorio inválido en línea de médico: " + linea);
                    }
                }

                ips.agregarUsuario(med);
            }
        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado (medicos): " + e);
        }
    }

    // =====================================================
    // CONSULTORIOS
    // =====================================================
    public void descargarConsultorios(String ruta){
        File file = new File(ruta);

        try(Scanner reader = new Scanner(file)){
            while (reader.hasNextLine()){
                String linea = reader.nextLine().trim();
                if (linea.isEmpty()) continue;

                String[] conCSV = linea.split(";");
                if (conCSV.length < 3) continue;

                Consultorio con = new Consultorio(
                        Integer.parseInt(conCSV[0]),
                        conCSV[1],
                        Integer.parseInt(conCSV[2])
                );
                ips.agregarConsultorio(con);
            }
        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado (consultorios): " + e);
        }
    }

    // =====================================================
    // CITAS
    // =====================================================
    public void descargarCitas(String ruta){
        File file = new File(ruta);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try(Scanner reader = new Scanner(file)){
            while (reader.hasNextLine()){
                String linea = reader.nextLine().trim();
                if (linea.isEmpty()) continue;

                String[] citCSV = linea.split(";");
                if (citCSV.length < 5) continue;

                int idCit = Integer.parseInt(citCSV[0]);
                Medico medCit = ips.getMedicoXid(Integer.parseInt(citCSV[1]));
                Paciente pacCit = ips.getPacienteXid(Integer.parseInt(citCSV[2]));
                LocalDateTime fecCit = LocalDateTime.parse(citCSV[3], formato);
                EstadoCita estCit = EstadoCita.toEstadoCita(Integer.parseInt(citCSV[4]));
                ips.agregarCita(new Cita(idCit, medCit, pacCit, fecCit, estCit));
            }
            asignarCitas();
        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado (citas): " + e);
        }
    }

    // =====================================================
    // MÉTODOS APPEND (AGREGAR AL FINAL DEL TXT)
    // =====================================================
    public void cargarRecepcionista(Recepcionista rec){
        appendLinea(rutaRecs, rec.toCSV());
    }

    public void cargarPaciente(Paciente pac){
        appendLinea(rutaPacs, pac.toCSV());
    }

    public void cargarMedico(Medico med){
        appendLinea(rutaMeds, med.toCSV());
    }

    public void cargarCita(Cita cit){
        appendLinea(rutaCits, cit.toCSV());
    }

    public void cargarConsultorio(Consultorio con){
        appendLinea(rutaCons, con.toCSV());
    }

    private void appendLinea(String ruta, String linea){
        try (FileWriter writer = new FileWriter(ruta, true)) {
            writer.write("\n" + linea);
        } catch (IOException e) {
            System.out.println("Error escribiendo archivo (" + ruta + "): " + e);
        }
    }

    // =====================================================
    // CREDENCIALES (DOCUMENTO + PASSWORD)
    // =====================================================

    /**
     * Tu archivo credenciales.txt tiene formato:
     * idInterno;documento;password
     * Ej: 6;101234566;123456
     */
    public boolean validarCredencial(String numId, String password) {
        File file = new File(rutaCreds);
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String linea = reader.nextLine().trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(";");
                if (partes.length < 3) continue;

                String docArchivo = partes[1].trim();   // documento
                String passArchivo = partes[2].trim();  // contraseña

                if (docArchivo.equals(numId.trim()) && passArchivo.equals(password)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de credenciales no encontrado: " + e);
        }
        return false;
    }

    // =====================================================
    // ASIGNAR CITAS A LOS MÉDICOS (DESPUÉS DE DESCARGAR)
    // =====================================================
    private void asignarCitas(){
        List<Cita> cits = ips.getCitas();
        for(Cita cit: cits){
            int idMed = cit.getMedico().getId();
            Medico med = ips.getMedicoXid(idMed);
            med.asignarCita(cit);
            ips.actualizarMedico(med, med);
        }
    }

    // =====================================================
    // GUARDAR COMPLETO (REESCRIBIR ARCHIVOS)
    // =====================================================

    public void guardarRecepcionistas() {
        File file = new File(rutaRecs);

        try (FileWriter writer = new FileWriter(file, false)) {
            boolean first = true;
            for (Recepcionista rec : ips.getRecepcionistas()) {
                if (!first) {
                    writer.write(System.lineSeparator());
                }
                writer.write(rec.toCSV());
                first = false;
            }
        } catch (IOException e) {
            System.out.println("Error guardando recepcionistas: " + e.getMessage());
        }
    }

    public void guardarPacientes() {
        File file = new File(rutaPacs);

        try (FileWriter writer = new FileWriter(file, false)) {
            boolean first = true;
            for (Paciente pac : ips.getPacientes()) {
                if (!first) {
                    writer.write(System.lineSeparator());
                }
                writer.write(pac.toCSV());
                first = false;
            }
        } catch (IOException e) {
            System.out.println("Error guardando pacientes: " + e.getMessage());
        }
    }

    public void guardarMedicos() {
        File file = new File(rutaMeds);

        try (FileWriter writer = new FileWriter(file, false)) {
            boolean first = true;
            for (Medico med : ips.getMedicos()) {
                if (!first) {
                    writer.write(System.lineSeparator());
                }
                writer.write(med.toCSV());
                first = false;
            }
        } catch (IOException e) {
            System.out.println("Error guardando médicos: " + e.getMessage());
        }
    }

    public void guardarConsultorios() {
        File file = new File(rutaCons);

        try (FileWriter writer = new FileWriter(file, false)) {
            boolean first = true;
            for (Consultorio con : ips.getConsultorios()) {
                if (!first) {
                    writer.write(System.lineSeparator());
                }
                writer.write(con.toCSV());
                first = false;
            }
        } catch (IOException e) {
            System.out.println("Error guardando consultorios: " + e.getMessage());
        }
    }

    public void guardarCitas() {
        File file = new File(rutaCits);

        try (FileWriter writer = new FileWriter(file, false)) {
            boolean first = true;
            for (Cita cit : ips.getCitas()) {
                if (!first) {
                    writer.write(System.lineSeparator());
                }
                writer.write(cit.toCSV());
                first = false;
            }
        } catch (IOException e) {
            System.out.println("Error guardando citas: " + e.getMessage());
        }
    }
}
