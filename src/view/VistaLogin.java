package view;

import controller.LoginController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class VistaLogin extends JFrame {

    private final LoginController lc;

    private final JTextField txtDocumento;
    private final JTextField txtUsuarioAdmin;
    private final JPasswordField txtClaveAdmin;
    private final JLabel lblMensaje;

    public VistaLogin(LoginController lc) {
        this.lc = lc;

        setTitle("Login - Sistema de gestión de citas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        Color bg = new Color(245, 247, 250);
        Color primary = new Color(56, 128, 255);

        // ====== PANEL PRINCIPAL ======
        JPanel main = new JPanel();
        main.setBackground(bg);
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel lblTitulo = new JLabel("Sistema de gestión de citas");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));

        JLabel lblSub = new JLabel("Inicia sesión");
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSub.setForeground(new Color(100, 100, 100));

        main.add(lblTitulo);
        main.add(Box.createVerticalStrut(4));
        main.add(lblSub);
        main.add(Box.createVerticalStrut(20));

        // ====== LOGIN USUARIO NORMAL ======
        JPanel panelUsuario = new JPanel(new GridBagLayout());
        panelUsuario.setBackground(bg);
        panelUsuario.setBorder(BorderFactory.createTitledBorder("Usuario (paciente / médico / recepcionista)"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0;
        panelUsuario.add(new JLabel("Documento:"), c);
        txtDocumento = new JTextField(15);
        c.gridx = 1;
        panelUsuario.add(txtDocumento, c);

        JButton btnLoginUsuario = new JButton("Entrar");
        btnLoginUsuario.setBackground(primary);
        btnLoginUsuario.setForeground(Color.WHITE);
        btnLoginUsuario.setFocusPainted(false);

        c.gridx = 0; c.gridy = 1; c.gridwidth = 2;
        panelUsuario.add(btnLoginUsuario, c);

        // ====== LOGIN ADMIN ======
        JPanel panelAdmin = new JPanel(new GridBagLayout());
        panelAdmin.setBackground(bg);
        panelAdmin.setBorder(BorderFactory.createTitledBorder("Administrador"));

        GridBagConstraints c2 = new GridBagConstraints();
        c2.insets = new Insets(4, 4, 4, 4);
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.anchor = GridBagConstraints.WEST;

        c2.gridx = 0; c2.gridy = 0;
        panelAdmin.add(new JLabel("Usuario:"), c2);
        txtUsuarioAdmin = new JTextField(15);
        c2.gridx = 1;
        panelAdmin.add(txtUsuarioAdmin, c2);

        c2.gridx = 0; c2.gridy = 1;
        panelAdmin.add(new JLabel("Clave:"), c2);
        txtClaveAdmin = new JPasswordField(15);
        c2.gridx = 1;
        panelAdmin.add(txtClaveAdmin, c2);

        JButton btnLoginAdmin = new JButton("Entrar como admin");
        btnLoginAdmin.setBackground(new Color(33, 37, 41));
        btnLoginAdmin.setForeground(Color.WHITE);
        btnLoginAdmin.setFocusPainted(false);

        c2.gridx = 0; c2.gridy = 2; c2.gridwidth = 2;
        panelAdmin.add(btnLoginAdmin, c2);

        // ====== MENSAJE ======
        lblMensaje = new JLabel(" ");
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensaje.setForeground(new Color(200, 0, 0));

        main.add(panelUsuario);
        main.add(Box.createVerticalStrut(10));
        main.add(panelAdmin);
        main.add(Box.createVerticalStrut(10));
        main.add(lblMensaje);

        add(main, BorderLayout.CENTER);

        // ====== EVENTOS ======
        btnLoginUsuario.addActionListener(e -> loginUsuario());
        btnLoginAdmin.addActionListener(e -> loginAdmin());
    }

    private void loginUsuario() {
        String doc = txtDocumento.getText().trim();

        if (doc.isEmpty()) {
            mostrarMensaje("Ingresa el documento del usuario.");
            return;
        }

        Usuario u = lc.validarLoginUsuario(doc);
        if (u == null) {
            mostrarMensaje("No se encontró un usuario con ese documento.");
            return;
        }

        // Éxito → abrimos el panel correspondiente
        mostrarMensaje("Inicio de sesión correcto como " + u.getTipoUsuario());
        lc.abrirPanelParaUsuario(u);
    }

    private void loginAdmin() {
        String usuario = txtUsuarioAdmin.getText().trim();
        String clave = new String(txtClaveAdmin.getPassword());

        if (usuario.isEmpty() || clave.isEmpty()) {
            mostrarMensaje("Usuario y clave de administrador son obligatorios.");
            return;
        }

        if (lc.validarLoginAdmin(usuario, clave)) {
            mostrarMensaje("Inicio de sesión correcto como ADMIN.");
            lc.abrirPanelAdministrador();
        } else {
            mostrarMensaje("Credenciales de administrador incorrectas.");
        }
    }

    private void mostrarMensaje(String msg) {
        lblMensaje.setText(msg);
    }

    // Para seguir el estilo de las otras vistas
    public void mostrar() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void ocultar() {
        setVisible(false);
    }
}
