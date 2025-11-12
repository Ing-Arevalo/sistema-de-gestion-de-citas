import model.*;
import view.*;
import controller.*;
import service.*;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        IPS saludTron = new IPS("SaludTron", "123456");

        GestionCitasService gcs = new GestionCitasService(saludTron);
        GestionUsuariosService gus = new GestionUsuariosService(saludTron);
        GestionArchivosService gas = new GestionArchivosService(saludTron);

        AdministradorController ac = new AdministradorController(gcs, gus, input);
        RecepcionistaController rc = new RecepcionistaController(gcs, gus, input);
        MedicoController mc = new MedicoController(gcs, gus, input);
        PacienteController pc = new PacienteController(gcs, gus, input);

        VistaRecepcionista vr = new VistaRecepcionista(rc, input);
        VistaAdministrador va = new VistaAdministrador(ac, input);
        VistaMedico vm = new VistaMedico(mc, input);
        VistaPaciente vp = new VistaPaciente(pc, input);

        gas.descargarDatos();

    }
}
