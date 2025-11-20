package view;

import controller.LoginController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VistaLogin extends JFrame {

    private final LoginController lc;

    // estos YA NO son final
    private JTextField txtDocumento;
    private JTextField txtUsuarioAdmin;
    private JPasswordField txtClaveAdmin;

    private final JLabel lblMensaje;

    // para tabs
    private final JLabel tabUsuario;
    private final JLabel tabAdmin;
    private final JPanel panelUsuario;
    private final JPanel panelAdmin;

    // paleta fija
    private final Color primary = new Color(56, 128, 255);
    private final Color primaryDark = new Color(32, 73, 155);
    private final Color bgLight = new Color(245, 247, 250);
    private final Color textMuted = new Color(120, 120, 120);

    public VistaLogin(LoginController lc) {
        this.lc = lc;

        setTitle("Login - Sistema de gestión de citas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 580);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // ====== PANEL IZQUIERDO (branding) ======
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(primaryDark);
        left.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        left.setPreferredSize(new Dimension(360, getHeight()));

        JLabel lblLogo = new JLabel("SaludTron");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTagline = new JLabel("Sistema de gestión de citas médicas");
        lblTagline.setForeground(new Color(220, 230, 255));
        lblTagline.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblTagline.setAlignmentX(Component.LEFT_ALIGNMENT);

        left.add(lblLogo);
        left.add(Box.createVerticalStrut(8));
        left.add(lblTagline);
        left.add(Box.createVerticalStrut(40));

        JLabel lblBullet1 = new JLabel("• Agenda tus citas de forma rápida");
        JLabel lblBullet2 = new JLabel("• Acceso para pacientes, médicos y recepción");
        JLabel lblBullet3 = new JLabel("• Control centralizado para administradores");

        for (JLabel l : new JLabel[]{lblBullet1, lblBullet2, lblBullet3}) {
            l.setForeground(new Color(224, 231, 255));
            l.setFont(new Font("SansSerif", Font.PLAIN, 13));
            l.setAlignmentX(Component.LEFT_ALIGNMENT);
            left.add(l);
            left.add(Box.createVerticalStrut(6));
        }

        left.add(Box.createVerticalGlue());

        JLabel lblFooter = new JLabel("© 2024 SaludTron IPS");
        lblFooter.setForeground(new Color(190, 200, 230));
        lblFooter.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblFooter.setAlignmentX(Component.LEFT_ALIGNMENT);
        left.add(lblFooter);

        add(left, BorderLayout.WEST);

        // ====== PANEL DERECHO (card) ======
        JPanel rightWrapper = new JPanel(new GridBagLayout());
        rightWrapper.setBackground(bgLight);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 234, 239)),
                BorderFactory.createEmptyBorder(26, 30, 26, 30)
        ));
        card.setPreferredSize(new Dimension(420, 430));

        JLabel lblTitulo = new JLabel("Inicia sesión");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel lblSub = new JLabel("Accede al panel correspondiente a tu rol");
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSub.setForeground(textMuted);

        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(4));
        card.add(lblSub);
        card.add(Box.createVerticalStrut(16));

        // ====== TABS ======
        JPanel tabs = new JPanel();
        tabs.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 0));
        tabs.setOpaque(false);

        tabUsuario = new JLabel("Usuario");
        tabAdmin = new JLabel("Administrador");

        tabUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tabAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        tabs.add(tabUsuario);
        tabs.add(new JLabel("|"));
        tabs.add(tabAdmin);

        card.add(tabs);
        card.add(Box.createVerticalStrut(16));

        // ====== PANEL FORMULARIOS ======
        panelUsuario = crearPanelUsuario();
        panelAdmin = crearPanelAdmin();

        card.add(panelUsuario);
        card.add(panelAdmin);

        card.add(Box.createVerticalStrut(12));

        // MENSAJE
        lblMensaje = new JLabel(" ");
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensaje.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblMensaje.setForeground(new Color(200, 0, 0));
        card.add(lblMensaje);

        GridBagConstraints wrapC = new GridBagConstraints();
        wrapC.gridx = 0;
        wrapC.gridy = 0;
        rightWrapper.add(card, wrapC);

        add(rightWrapper, BorderLayout.CENTER);

        // ====== EVENTOS TABS ======
        tabUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                activarTabUsuario();
            }
        });

        tabAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                activarTabAdmin();
            }
        });

        // Estado inicial
        activarTabUsuario();
    }

    private JPanel crearPanelUsuario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints c1 = new GridBagConstraints();
        c1.insets = new Insets(4, 4, 4, 4);
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.anchor = GridBagConstraints.WEST;

        c1.gridx = 0;
        c1.gridy = 0;
        c1.gridwidth = 2;
        JLabel lblDoc = new JLabel("Documento");
        lblDoc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panel.add(lblDoc, c1);

        txtDocumento = new JTextField(18);
        txtDocumento.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        c1.gridy = 1;
        panel.add(txtDocumento, c1);

        JLabel lblHintUsr = new JLabel("Usa el número de identificación registrado en el sistema.");
        lblHintUsr.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblHintUsr.setForeground(textMuted);
        c1.gridy = 2;
        panel.add(lblHintUsr, c1);

        JButton btnLoginUsuario = new JButton("Entrar como usuario");
        btnLoginUsuario.setBackground(primary);
        btnLoginUsuario.setForeground(Color.WHITE);
        btnLoginUsuario.setFocusPainted(false);
        btnLoginUsuario.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        btnLoginUsuario.setFont(new Font("SansSerif", Font.BOLD, 13));

        c1.gridy = 3;
        panel.add(btnLoginUsuario, c1);

        btnLoginUsuario.addActionListener(e -> loginUsuario());

        return panel;
    }

    private JPanel crearPanelAdmin() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(235, 238, 242)));

        GridBagConstraints c2 = new GridBagConstraints();
        c2.insets = new Insets(4, 4, 4, 4);
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.anchor = GridBagConstraints.WEST;

        c2.gridx = 0;
        c2.gridy = 0;
        c2.gridwidth = 2;
        JLabel lblAdminTitle = new JLabel("Acceso administrador");
        lblAdminTitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panel.add(lblAdminTitle, c2);

        // Usuario
        c2.gridy = 1;
        c2.gridwidth = 1;
        panel.add(new JLabel("Usuario"), c2);
        txtUsuarioAdmin = new JTextField(14);
        txtUsuarioAdmin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        c2.gridx = 1;
        panel.add(txtUsuarioAdmin, c2);

        // Clave
        c2.gridx = 0;
        c2.gridy = 2;
        panel.add(new JLabel("Clave"), c2);
        txtClaveAdmin = new JPasswordField(14);
        txtClaveAdmin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        c2.gridx = 1;
        panel.add(txtClaveAdmin, c2);

        JButton btnLoginAdmin = new JButton("Entrar como admin");
        btnLoginAdmin.setBackground(new Color(33, 37, 41));
        btnLoginAdmin.setForeground(Color.WHITE);
        btnLoginAdmin.setFocusPainted(false);
        btnLoginAdmin.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        btnLoginAdmin.setFont(new Font("SansSerif", Font.BOLD, 12));

        c2.gridx = 0;
        c2.gridy = 3;
        c2.gridwidth = 2;
        panel.add(btnLoginAdmin, c2);

        btnLoginAdmin.addActionListener(e -> loginAdmin());

        return panel;
    }

    private void activarTabUsuario() {
        tabUsuario.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabUsuario.setForeground(primary);
        tabAdmin.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabAdmin.setForeground(textMuted);

        panelUsuario.setVisible(true);
        panelAdmin.setVisible(false);

        revalidate();
        repaint();
    }

    private void activarTabAdmin() {
        tabAdmin.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabAdmin.setForeground(primary);
        tabUsuario.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabUsuario.setForeground(textMuted);

        panelUsuario.setVisible(false);
        panelAdmin.setVisible(true);

        revalidate();
        repaint();
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

    public void mostrar() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void ocultar() {
        setVisible(false);
    }
}
