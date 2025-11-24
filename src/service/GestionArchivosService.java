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

    public GestionArchivosService(IPS ips){
        this.ips = ips;
    }

    public void descargarDatos() {
        descargarConsultorios(rutaCons);
        descargarRecepcionistas(rutaRecs);
        descargarPacientes(rutaPacs);
        descargarMedicos(rutaMeds);
        descargarCitas(rutaCits);
    }

    public void descargarRecepcionistas(String ruta){
        File file = new File(ruta);

        try(Scanner reader = new Scanner(file)){
            while (reader.hasNextLine()){
                String[] repCSV = reader.nextLine().split(";");
                ips.agregarUsuario(new Paciente(Integer.parseInt(repCSV[0]), repCSV[1], repCSV[2], repCSV[3], repCSV[4]));
            }
        }catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

    public void descargarPacientes(String ruta) {
        File file = new File(ruta);

        try(Scanner reader = new Scanner(file)){
            while (reader.hasNextLine()){
                String[] pacCSV = reader.nextLine().split(";");
                ips.agregarUsuario(new Paciente(Integer.parseInt(pacCSV[0]), pacCSV[1], pacCSV[2], pacCSV[3], pacCSV[4]));
            }
        }catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

    public void descargarMedicos(String ruta){
        File file = new File(ruta);

        try(Scanner reader = new Scanner(file)){
            while (reader.hasNextLine()){
                String[] medCSV = reader.nextLine().split(";");
                Medico med = new Medico(Integer.parseInt(medCSV[0]), medCSV[1], medCSV[2], medCSV[3], medCSV[4], medCSV[5]);
                Consultorio conMed = ips.getConsultorioXid(Integer.parseInt(medCSV[6]));
                med.asignarConsultorio(conMed);
                ips.agregarUsuario(med);
            }
        }catch (FileNotFoundException e){
            System.out.println(e);
        }

    }

    public void descargarConsultorios(String ruta){
        File file = new File(ruta);

        try(Scanner reader = new Scanner(file)){
            while (reader.hasNextLine()){
                String[] conCSV = reader.nextLine().split(";");
                ips.agregarConsultorio(new Consultorio(Integer.parseInt(conCSV[0]), conCSV[1], Integer.parseInt(conCSV[2])));
            }
        }catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

    public void descargarCitas(String ruta){
        File file = new File(ruta);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try(Scanner reader = new Scanner(file)){
            while (reader.hasNextLine()){
                String[] citCSV = reader.nextLine().split(";");
                int idCit = Integer.parseInt(citCSV[0]);
                Medico medCit = ips.getMedicoXid(Integer.parseInt(citCSV[1]));
                Paciente pacCit = ips.getPacienteXid(Integer.parseInt(citCSV[2]));
                LocalDateTime fecCit = LocalDateTime.parse(citCSV[3], formato);
                EstadoCita estCit = EstadoCita.toEstadoCita(Integer.parseInt(citCSV[4]));
                ips.agregarCita(new Cita(idCit, medCit, pacCit, fecCit, estCit));
            }
            asignarCitas();
        }catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

    public void cargarRecepcionista(Recepcionista rec){
        try (FileWriter writer = new FileWriter(rutaRecs, true)) {
            writer.write("\n" + rec.toCSV());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void cargarPaciente(Paciente pac){
        try (FileWriter writer = new FileWriter(rutaPacs, true)) {
            writer.write("\n" + pac.toCSV());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void cargarMedico(Medico med){
        try (FileWriter writer = new FileWriter(rutaMeds, true)) {
            writer.write("\n" + med.toCSV());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void cargarCita(Cita cit){
        try (FileWriter writer = new FileWriter(rutaCits, true)) {
            writer.write("\n" + cit.toCSV());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void cargarConsultorio(Consultorio con){
        try (FileWriter writer = new FileWriter(rutaCons, true)) {
            writer.write("\n" + con.toCSV());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void asignarCitas(){
        List<Cita> cits = ips.getCitas();
        for(Cita cit: cits){
            int idMed = cit.getMedico().getId();
            int idPac = cit.getPaciente().getId();
            Medico med = ips.getMedicoXid(idMed);
            med.asignarCita(cit);
            ips.actualizarMedico(med, med);
        }
    }

}
