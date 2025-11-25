package view;

import controller.AdministradorController;
import model.Medico;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VistaAdministrador extends JFrame {

    private final AdministradorController ac;

    private JTable tablaMedicos;
    private DefaultTableModel modeloMedicos;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtDocumento;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextField txtEspecialidad;

    public VistaAdministrador(AdministradorController ac) {
        this.ac = ac;

        setTitle("Panel Administrador - Gestión de Médicos");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(950, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
        cargarMedicos();
    }

    private void initUI() {
        Color bg = new Color(245, 247, 250);
        Color primary = new Color(56, 128, 255);

        JPanel main = new JPanel(new BorderLayout(16, 16));
        main.setBorder(new EmptyBorder(16, 16, 16, 16));
        main.setBackground(bg);
        setContentPane(main);

        // Título
        JLabel lblTitulo = new JLabel("Administrador - Gestión de Médicos");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(33, 37, 41));
        main.add(lblTitulo, BorderLayout.NORTH);

        // Panel central
        JPanel centro = new JPanel(new GridLayout(1, 2, 16, 0));
        centro.setOpaque(false);
        main.add(centro, BorderLayout.CENTER);

        // ============================================================
        // TABLA DE MÉDICOS
        // ============================================================
        modeloMedicos = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Documento", "Email", "Teléfono", "Especialidad"},
                0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tablaMedicos = new JTable(modeloMedicos);
        tablaMedicos.setRowHeight(24);
        tablaMedicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tablaMedicos);
        scroll.setBorder(BorderFactory.createTitledBorder("Médicos registrados"));

        centro.add(scroll);

        // ============================================================
        // FORMULARIO A LA DERECHA
        // ============================================================
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del médico"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNombre = new JTextField();
        txtDocumento = new JTextField();
        txtEmail = new JTextField();
        txtTelefono = new JTextField();
        txtEspecialidad = new JTextField();

        addField(formPanel, c, "ID:", txtId);
        addField(formPanel, c, "Nombre:", txtNombre);
        addField(formPanel, c, "Documento:", txtDocumento);
        addField(formPanel, c, "Email:", txtEmail);
        addField(formPanel, c, "Teléfono:", txtTelefono);
        addField(formPanel, c, "Especialidad:", txtEspecialidad);

        // ============================================================
        // BOTONES
        // ============================================================
        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 8, 8));

        JButton btnNuevo = new JButton("Nuevo");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnRecargar = new JButton("Recargar");

        styleButton(btnNuevo);
        styleButton(btnGuardar);
        styleButton(btnEliminar);
        styleButton(btnRecargar);

        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRecargar);

        c.gridy++;
        c.gridwidth = 2;
        formPanel.add(panelBotones, c);

        centro.add(formPanel);

        // ============================================================
        // EVENTOS
        // ============================================================
        tablaMedicos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarMedicoSeleccionado();
        });

        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnRecargar.addActionListener(e -> cargarMedicos());
        btnEliminar.addActionListener(e -> eliminarMedicoSeleccionado());
        btnGuardar.addActionListener(e -> guardarMedico());
    }

    private void addField(JPanel panel, GridBagConstraints c, String label, JTextField field) {
        c.gridx = 0;
        panel.add(new JLabel(label), c);

        c.gridx = 1;
        panel.add(field, c);

        c.gridy++;
    }

    private void styleButton(JButton b) {
        b.setFocusPainted(false);
        b.setBackground(new Color(56, 128, 255));
        b.setForeground(Color.WHITE);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // ============================================================
    // FUNCIONES CRUD
    // ============================================================
    private void cargarMedicos() {
        modeloMedicos.setRowCount(0);
        List<Medico> medicos = ac.listarMedicos();

        for (Medico m : medicos) {
            modeloMedicos.addRow(new Object[]{
                    m.getId(),
                    m.getNombre(),
                    m.getNumId(),
                    m.getEmail(),
                    m.getTelefono(),
                    m.getEspecialidad()
            });
        }
        limpiarFormulario();
    }

    private void cargarMedicoSeleccionado() {
        int fila = tablaMedicos.getSelectedRow();
        if (fila == -1) return;

        txtId.setText(modeloMedicos.getValueAt(fila, 0).toString());
        txtNombre.setText(modeloMedicos.getValueAt(fila, 1).toString());
        txtDocumento.setText(modeloMedicos.getValueAt(fila, 2).toString());
        txtEmail.setText(modeloMedicos.getValueAt(fila, 3).toString());
        txtTelefono.setText(modeloMedicos.getValueAt(fila, 4).toString());
        txtEspecialidad.setText(modeloMedicos.getValueAt(fila, 5).toString());
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtDocumento.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtEspecialidad.setText("");
        tablaMedicos.clearSelection();
    }

    private void eliminarMedicoSeleccionado() {
        int fila = tablaMedicos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un médico para eliminar.");
            return;
        }

        int id = Integer.parseInt(modeloMedicos.getValueAt(fila, 0).toString());
        ac.eliminarMedico(id);
        cargarMedicos();
    }

    private void guardarMedico() {
        String idTxt = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String doc = txtDocumento.getText().trim();
        String email = txtEmail.getText().trim();
        String tel = txtTelefono.getText().trim();
        String esp = txtEspecialidad.getText().trim();

        if (nombre.isEmpty() || doc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y documento son obligatorios.");
            return;
        }

        if (idTxt.isEmpty()) {
            ac.registrarMedico(nombre, doc, email, tel, esp);
        } else {
            int id = Integer.parseInt(idTxt);
            Medico nuevo = new Medico(id, nombre, doc, email, tel, esp);
            ac.actualizarMedico(id, nuevo);
        }

        cargarMedicos();
    }

    public void mostrar() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void ocultar() {
        setVisible(false);
    }
}
