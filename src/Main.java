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

        // Primero cargar datos desde los txt
        gas.descargarDatos();

        // Luego controladores
        AdministradorController ac = new AdministradorController(gcs, gus, gas, gos);
        RecepcionistaController rc = new RecepcionistaController(gcs, gus);
        MedicoController mc = new MedicoController(gcs, gus);
        PacienteController pc = new PacienteController(gcs, gus);

        // Vistas
        VistaRecepcionista vr = new VistaRecepcionista(rc);
        VistaAdministrador va = new VistaAdministrador(ac);
        VistaMedico vm = new VistaMedico(mc);
        VistaPaciente vp = new VistaPaciente(pc);

        // Login
        LoginController lc = new LoginController(gus, va, vr, vm, vp);
        VistaLogin vl = new VistaLogin(lc);

        vl.mostrar();
    }
}
