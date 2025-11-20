package service;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GestionArchivosService {
    IPS ips;
    final String rutaMeds = "data/medicos.txt";
    final String rutaPacs = "data/pacientes.txt";
    final String rutaCits = "data/citas.txt";
    final String rutaCons = "data/consultorios.txt";

    public GestionArchivosService(IPS ips){
        this.ips = ips;
    }

    public void descargarDatos() {
        descargarPacientes(rutaPacs);
        descargarMedicos(rutaMeds);
        descargarConsultorios(rutaCons);
        descargarCitas(rutaCits);
    }

    public void descargarPacientes(String ruta) {
        File file = new File(ruta);

        try(Scanner reader = new Scanner(file)){
            while (reader.hasNextLine()){
                String[] pacCSV = reader.nextLine().split(";");
                ips.agregarPaciente(new Paciente(Integer.parseInt(pacCSV[0]), pacCSV[1], pacCSV[2], pacCSV[3], pacCSV[4]));
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
                ips.agregarMedico(new Medico(Integer.parseInt(medCSV[0]), medCSV[1], medCSV[2], medCSV[3], medCSV[4], medCSV[5]));
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
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        File file = new File(ruta);

        try(Scanner reader = new Scanner(file)){
            while (reader.hasNextLine()){
                String[] citCSV = reader.nextLine().split(";");
                int idCit = Integer.parseInt(citCSV[0]);
                Paciente pacCit = ips.getPacienteXid(Integer.parseInt(citCSV[1]));
                Medico medCit = ips.getMedicoXid(Integer.parseInt(citCSV[2]));
                LocalDateTime fecCit = LocalDateTime.parse(citCSV[3], formato);
                ips.agregarCita(new Cita(idCit, pacCit, medCit, fecCit));
            }
        }catch (FileNotFoundException e){
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

}
