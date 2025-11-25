package view;

import controller.LoginController;
import model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VistaLogin extends JFrame {

    private final LoginController lc;

    private final JTextField txtDocumento;
    private final JPasswordField txtClaveUsuario;
    private final JTextField txtUsuarioAdmin;
    private final JPasswordField txtClaveAdmin;
    private final JLabel lblMensaje;

    public VistaLogin(LoginController lc) {
        this.lc = lc;

        txtDocumento = new JTextField(18);
        txtClaveUsuario = new JPasswordField(18);
        txtUsuarioAdmin = new JTextField(18);
        txtClaveAdmin = new JPasswordField(18);
        lblMensaje = new JLabel(" ");

        setTitle("Login - Sistema de gestión de citas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        Color bg = new Color(245, 247, 250);
        Color primary = new Color(56, 128, 255);

        // ====== PANEL PRINCIPAL (FONDO) ======
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(bg);
        main.setBorder(new EmptyBorder(24, 24, 24, 24));
        setContentPane(main);

        // ====== CARD CENTRAL ======
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230), 1, true),
                new EmptyBorder(24, 24, 24, 24)
        ));

        main.add(card, BorderLayout.CENTER);

        // ====== PANEL IZQUIERDO (BRANDING) ======
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(new EmptyBorder(0, 0, 0, 32));

        JLabel lblTitulo = new JLabel("SaludTron");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(33, 37, 41));

        JLabel lblSub = new JLabel("<html>Sistema de gestión de citas<br>simple, claro y eficiente.</html>");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSub.setForeground(new Color(110, 117, 125));

        left.add(lblTitulo);
        left.add(Box.createVerticalStrut(8));
        left.add(lblSub);
        left.add(Box.createVerticalGlue());

        card.add(left, BorderLayout.WEST);

        // ====== PANEL DERECHO (TABS LOGIN) ======
        JPanel right = new JPanel(new BorderLayout());
        right.setOpaque(false);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.PLAIN, 13));

        tabs.addTab("Usuario", crearPanelUsuario(primary));
        tabs.addTab("Administrador", crearPanelAdmin());

        right.add(tabs, BorderLayout.CENTER);

        card.add(right, BorderLayout.CENTER);

        // ====== MENSAJE INFERIOR ======
        lblMensaje.setForeground(new Color(200, 0, 0));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setBorder(new EmptyBorder(12, 4, 0, 4));

        card.add(lblMensaje, BorderLayout.SOUTH);
    }

    // ----------------------------------------------------------------
    // PANEL USUARIO (PACIENTE / MÉDICO / RECEPCIONISTA)
    // ----------------------------------------------------------------
    private JPanel crearPanelUsuario(Color primary) {
        JPanel panelUsuario = new JPanel(new GridBagLayout());
        panelUsuario.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 4, 6, 4);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;

        JLabel lblTitulo = new JLabel("Acceso de usuario");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(33, 37, 41));

        JLabel lblDesc = new JLabel("Ingresa con tu número de documento y contraseña.");
        lblDesc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblDesc.setForeground(new Color(110, 117, 125));

        panelUsuario.add(lblTitulo, c);
        c.gridy++;
        panelUsuario.add(lblDesc, c);

        c.gridy++;
        panelUsuario.add(new JLabel("Documento:"), c);
        c.gridy++;
        panelUsuario.add(txtDocumento, c);

        c.gridy++;
        panelUsuario.add(new JLabel("Contraseña:"), c);
        c.gridy++;
        panelUsuario.add(txtClaveUsuario, c);

        JButton btnLoginUsuario = new JButton("Entrar");
        btnLoginUsuario.setBackground(primary);
        btnLoginUsuario.setForeground(Color.WHITE);
        btnLoginUsuario.setFocusPainted(false);
        btnLoginUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        c.gridy++;
        c.insets = new Insets(14, 4, 4, 4);
        panelUsuario.add(btnLoginUsuario, c);

        btnLoginUsuario.addActionListener(e -> loginUsuario());

        return panelUsuario;
    }

    // ----------------------------------------------------------------
    // PANEL ADMINISTRADOR
    // ----------------------------------------------------------------
    private JPanel crearPanelAdmin() {
        JPanel panelAdmin = new JPanel(new GridBagLayout());
        panelAdmin.setOpaque(false);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.insets = new Insets(6, 4, 6, 4);
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.anchor = GridBagConstraints.WEST;
        c2.gridx = 0;
        c2.gridy = 0;
        c2.weightx = 1;

        JLabel lblTitulo = new JLabel("Acceso administrador");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(33, 37, 41));

        JLabel lblDesc = new JLabel("Gestiona médicos, pacientes, citas y consultorios.");
        lblDesc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblDesc.setForeground(new Color(110, 117, 125));

        panelAdmin.add(lblTitulo, c2);
        c2.gridy++;
        panelAdmin.add(lblDesc, c2);

        c2.gridy++;
        panelAdmin.add(new JLabel("Usuario:"), c2);
        c2.gridy++;
        panelAdmin.add(txtUsuarioAdmin, c2);

        c2.gridy++;
        panelAdmin.add(new JLabel("Clave:"), c2);
        c2.gridy++;
        panelAdmin.add(txtClaveAdmin, c2);

        JButton btnLoginAdmin = new JButton("Entrar como admin");
        btnLoginAdmin.setBackground(new Color(33, 37, 41));
        btnLoginAdmin.setForeground(Color.WHITE);
        btnLoginAdmin.setFocusPainted(false);
        btnLoginAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        c2.gridy++;
        c2.insets = new Insets(14, 4, 4, 4);
        panelAdmin.add(btnLoginAdmin, c2);

        btnLoginAdmin.addActionListener(e -> loginAdmin());

        return panelAdmin;
    }

    // ----------------------------------------------------------------
    // LÓGICA LOGIN USUARIO
    // ----------------------------------------------------------------
    private void loginUsuario() {
        String doc = txtDocumento.getText().trim();
        String pass = new String(txtClaveUsuario.getPassword());

        if (doc.isEmpty() || pass.isEmpty()) {
            mostrarMensaje("Ingresa documento y contraseña.");
            return;
        }

        Usuario u = lc.validarLoginUsuario(doc, pass);
        if (u == null) {
            mostrarMensaje("Credenciales incorrectas o usuario no encontrado.");
            return;
        }

        mostrarMensaje("Inicio de sesión correcto como " + u.getTipoUsuario());
        ocultar();
        lc.abrirPanelParaUsuario(u);
    }

    // ----------------------------------------------------------------
    // LÓGICA LOGIN ADMIN
    // ----------------------------------------------------------------
    private void loginAdmin() {
        String usuario = txtUsuarioAdmin.getText().trim();
        String clave = new String(txtClaveAdmin.getPassword());

        if (usuario.isEmpty() || clave.isEmpty()) {
            mostrarMensaje("Usuario y clave de administrador son obligatorios.");
            return;
        }

        if (lc.validarLoginAdmin(usuario, clave)) {
            mostrarMensaje("Inicio de sesión correcto como ADMIN.");
            ocultar();
            lc.abrirPanelAdministrador();
        } else {
            mostrarMensaje("Credenciales de administrador incorrectas.");
        }
    }

    // ----------------------------------------------------------------
    // UTILIDADES
    // ----------------------------------------------------------------
    private void mostrarMensaje(String msg) {
        lblMensaje.setText(msg);
    }

    public void mostrar() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void ocultar() {
        setVisible(false);
    }
}
