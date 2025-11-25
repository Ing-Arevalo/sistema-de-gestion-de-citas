package controller;

import model.Medico;
import model.Paciente;
import model.TipoUsuario;
import model.Usuario;
import service.GestionUsuariosService;
import view.VistaAdministrador;
import view.VistaMedico;
import view.VistaPaciente;
import view.VistaRecepcionista;

public class LoginController {

    private final GestionUsuariosService gus;
    private final VistaAdministrador va;
    private final VistaRecepcionista vr;
    private final VistaMedico vm;
    private final VistaPaciente vp;

    public LoginController(GestionUsuariosService gus,
                           VistaAdministrador va,
                           VistaRecepcionista vr,
                           VistaMedico vm,
                           VistaPaciente vp) {
        this.gus = gus;
        this.va = va;
        this.vr = vr;
        this.vm = vm;
        this.vp = vp;
    }

    /**
     * Login de usuario (médico / paciente / recepcionista) con documento + password.
     */
    public Usuario validarLoginUsuario(String numId, String password) {
        return gus.validarLoginUsuario(numId, password);
    }

    /**
     * Login de administrador.
     */
    public boolean validarLoginAdmin(String usuario, String clave) {
        return gus.validarAdministrador(usuario, clave);
    }

    /**
     * Abre la vista correspondiente según el tipo de usuario.
     */
    public void abrirPanelParaUsuario(Usuario usr) {
        if (usr == null) return;

        TipoUsuario tipo = usr.getTipoUsuario();
        switch (tipo) {
            case PACIENTE -> {
                if (usr instanceof Paciente p) {
                    vp.mostrar(p);
                }
            }
            case MEDICO -> {
                if (usr instanceof Medico m) {
                    vm.mostrar(m);
                }
            }
            case RECEPCIONISTA -> vr.mostrar();
            default -> {
                // otros tipos si los agregas
            }
        }
    }

    /**
     * Abre el panel del administrador.
     */
    public void abrirPanelAdministrador() {
        va.mostrar();
    }
}
