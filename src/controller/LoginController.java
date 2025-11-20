package controller;

import model.TipoUsuario;
import model.Usuario;
import view.VistaAdministrador;
import view.VistaMedico;
import view.VistaPaciente;
import view.VistaRecepcionista;
import service.GestionUsuariosService;

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

    // --------- Lógica de login ----------

    /**
     * Login de usuario normal (médico / paciente / recepcionista) usando numId.
     */
    public Usuario validarLoginUsuario(String numId) {
        return gus.buscarUsuarioPorDocumento(numId);
    }

    /**
     * Login de administrador "simple" con credenciales fijas.
     * Puedes cambiarlas por lo que te pida el profe.
     */
    public boolean validarLoginAdmin(String usuario, String clave) {
        return "admin".equals(usuario) && "admin123".equals(clave);
    }

    /**
     * Abre la vista correspondiente según el tipo de usuario.
     */
    public void abrirPanelParaUsuario(Usuario usr) {
        if (usr == null) return;

        TipoUsuario tipo = usr.getTipoUsuario();
        switch (tipo) {
            case PACIENTE -> vp.mostrar();
            case MEDICO -> vm.mostrar();
            case RECEPCIONISTA -> vr.mostrar();
            default -> {
                // Por si en el futuro agregas más tipos
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
