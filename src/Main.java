import model.*;
import view.*;
import controller.*;
import service.*;

public class Main {

    public static void main(String[] args) {

        IPS saludTron = new IPS("SaludTron", "123456");

        GestionArchivosService gas = new GestionArchivosService(saludTron);
        GestionCitasService gcs = new GestionCitasService(saludTron, gas);
        GestionUsuariosService gus = new GestionUsuariosService(saludTron, gas);
        GestionConsultoriosService gos = new GestionConsultoriosService(saludTron, gas);

        AdministradorController ac = new AdministradorController(gcs, gus, gas, gos);
        RecepcionistaController rc = new RecepcionistaController(gcs, gus);
        MedicoController mc = new MedicoController(gcs, gus);
        PacienteController pc = new PacienteController(gcs, gus);

        VistaRecepcionista vr = new VistaRecepcionista(rc);
        VistaAdministrador va = new VistaAdministrador(ac);
        VistaMedico vm = new VistaMedico(mc);
        VistaPaciente vp = new VistaPaciente(pc);

        gas.descargarDatos();

    }
}
