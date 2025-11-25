package view;

import controller.RecepcionistaController;
import model.Cita;
import model.Medico;
import model.Paciente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VistaRecepcionista extends JFrame {

    private final RecepcionistaController rc;

    // Pacientes
    private JTable tablaPacientes;
    private DefaultTableModel modeloPacientes;
    private JTextField txtIdPac;
    private JTextField txtNombrePac;
    private JTextField txtDocumentoPac;
    private JTextField txtEmailPac;
    private JTextField txtTelefonoPac;

    // Citas
    private JTable tablaCitas;
    private DefaultTableModel modeloCitas;
    private JComboBox<Medico> cbMedico;
    private JComboBox<Paciente> cbPaciente;
    private JTextField txtFechaHora; // formato dd/MM/yyyy HH:mm

    public VistaRecepcionista(RecepcionistaController rc) {
        this.rc = rc;

        setTitle("Panel Recepcionista - Pacientes y Citas");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
        cargarPacientes();
        cargarCitas();
        cargarCombosCitas();
    }

    private void initUI() {
        Color bg = new Color(245, 247, 250);

        JPanel main = new JPanel(new BorderLayout(12, 12));
        main.setBackground(bg);
        main.setBorder(new EmptyBorder(12, 12, 12, 12));
        setContentPane(main);

        JLabel lblTitulo = new JLabel("Recepcionista - Gestión de Pacientes y Citas");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(33, 37, 41));
        main.add(lblTitulo, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.PLAIN, 13));
        main.add(tabs, BorderLayout.CENTER);

        tabs.addTab("Pacientes", crearTabPacientes());
        tabs.addTab("Citas", crearTabCitas());
    }

    // ============================================================
    // TAB PACIENTES
    // ============================================================
    private JPanel crearTabPacientes() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setOpaque(false);

        // Tabla
        modeloPacientes = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Documento", "Email", "Teléfono"},
                0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tablaPacientes = new JTable(modeloPacientes);
        tablaPacientes.setRowHeight(24);
        tablaPacientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tablaPacientes);
        scroll.setBorder(BorderFactory.createTitledBorder("Pacientes registrados"));
        panel.add(scroll, BorderLayout.CENTER);

        // Formulario derecha
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder("Datos del paciente"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;

        txtIdPac = new JTextField();
        txtIdPac.setEditable(false);
        txtNombrePac = new JTextField();
        txtDocumentoPac = new JTextField();
        txtEmailPac = new JTextField();
        txtTelefonoPac = new JTextField();

        addField(form, c, "ID:", txtIdPac);
        addField(form, c, "Nombre:", txtNombrePac);
        addField(form, c, "Documento:", txtDocumentoPac);
        addField(form, c, "Email:", txtEmailPac);
        addField(form, c, "Teléfono:", txtTelefonoPac);

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
        form.add(panelBotones, c);

        panel.add(form, BorderLayout.EAST);

        // Eventos
        tablaPacientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarPacienteSeleccionado();
        });
        btnNuevo.addActionListener(e -> limpiarFormularioPaciente());
        btnRecargar.addActionListener(e -> cargarPacientes());
        btnEliminar.addActionListener(e -> eliminarPacienteSeleccionado());
        btnGuardar.addActionListener(e -> guardarPaciente());

        return panel;
    }

    // ============================================================
    // TAB CITAS
    // ============================================================
    private JPanel crearTabCitas() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setOpaque(false);

        // Formulario superior
        JPanel top = new JPanel(new GridBagLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(BorderFactory.createTitledBorder("Reservar nueva cita"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.weightx = 1;

        cbMedico = new JComboBox<>();
        cbPaciente = new JComboBox<>();
        txtFechaHora = new JTextField();
        txtFechaHora.setToolTipText("Formato: dd/MM/yyyy HH:mm");

        c.gridx = 0;
        top.add(new JLabel("Médico:"), c);
        c.gridx = 1;
        top.add(cbMedico, c);

        c.gridy++;
        c.gridx = 0;
        top.add(new JLabel("Paciente:"), c);
        c.gridx = 1;
        top.add(cbPaciente, c);

        c.gridy++;
        c.gridx = 0;
        top.add(new JLabel("Fecha y hora:"), c);
        c.gridx = 1;
        top.add(txtFechaHora, c);

        JButton btnReservar = new JButton("Reservar cita");
        styleButton(btnReservar);

        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        top.add(btnReservar, c);

        panel.add(top, BorderLayout.NORTH);

        // Tabla de citas
        modeloCitas = new DefaultTableModel(
                new Object[]{"ID", "Médico", "Paciente", "Fecha/hora", "Estado"},
                0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tablaCitas = new JTable(modeloCitas);
        tablaCitas.setRowHeight(24);
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tablaCitas);
        scroll.setBorder(BorderFactory.createTitledBorder("Citas registradas"));
        panel.add(scroll, BorderLayout.CENTER);

        // Panel de botones inferior
        JPanel bottomButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancelar = new JButton("Cancelar cita");
        JButton btnEliminar = new JButton("Eliminar cita");
        JButton btnRecargarCitas = new JButton("Recargar");
        styleButton(btnCancelar);
        styleButton(btnEliminar);
        styleButton(btnRecargarCitas);
        bottomButtons.add(btnRecargarCitas);
        bottomButtons.add(btnCancelar);
        bottomButtons.add(btnEliminar);

        panel.add(bottomButtons, BorderLayout.SOUTH);

        // Eventos
        btnReservar.addActionListener(e -> reservarCita());
        btnRecargarCitas.addActionListener(e -> cargarCitas());
        btnCancelar.addActionListener(e -> cancelarCitaSeleccionada());
        btnEliminar.addActionListener(e -> eliminarCitaSeleccionada());

        return panel;
    }

    // ============================================================
    // UTILIDADES UI
    // ============================================================
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
    // LÓGICA PACIENTES
    // ============================================================
    private void cargarPacientes() {
        modeloPacientes.setRowCount(0);
        List<Paciente> pacientes = rc.listarPacientes();
        for (Paciente p : pacientes) {
            modeloPacientes.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getNumId(),
                    p.getEmail(),
                    p.getTelefono()
            });
        }
        limpiarFormularioPaciente();
    }

    private void cargarPacienteSeleccionado() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila == -1) return;

        txtIdPac.setText(modeloPacientes.getValueAt(fila, 0).toString());
        txtNombrePac.setText(modeloPacientes.getValueAt(fila, 1).toString());
        txtDocumentoPac.setText(modeloPacientes.getValueAt(fila, 2).toString());
        txtEmailPac.setText(modeloPacientes.getValueAt(fila, 3).toString());
        txtTelefonoPac.setText(modeloPacientes.getValueAt(fila, 4).toString());
    }

    private void limpiarFormularioPaciente() {
        txtIdPac.setText("");
        txtNombrePac.setText("");
        txtDocumentoPac.setText("");
        txtEmailPac.setText("");
        txtTelefonoPac.setText("");
        tablaPacientes.clearSelection();
    }

    private void eliminarPacienteSeleccionado() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente para eliminar.");
            return;
        }
        int id = Integer.parseInt(modeloPacientes.getValueAt(fila, 0).toString());
        rc.eliminarPaciente(id);
        cargarPacientes();
        cargarCombosCitas();
    }

    private void guardarPaciente() {
        String idTxt = txtIdPac.getText().trim();
        String nombre = txtNombrePac.getText().trim();
        String doc = txtDocumentoPac.getText().trim();
        String email = txtEmailPac.getText().trim();
        String tel = txtTelefonoPac.getText().trim();

        if (nombre.isEmpty() || doc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y documento son obligatorios.");
            return;
        }

        if (idTxt.isEmpty()) {
            rc.registrarPaciente(nombre, doc, email, tel);
        } else {
            int id = Integer.parseInt(idTxt);
            Paciente p = new Paciente(id, nombre, doc, email, tel);
            rc.actualizarPaciente(id, p);
        }

        cargarPacientes();
        cargarCombosCitas();
    }

    // ============================================================
    // LÓGICA CITAS
    // ============================================================
    private void cargarCombosCitas() {
        cbMedico.removeAllItems();
        cbPaciente.removeAllItems();

        for (Medico m : rc.listarMedicos()) {
            cbMedico.addItem(m);
        }
        for (Paciente p : rc.listarPacientes()) {
            cbPaciente.addItem(p);
        }
    }

    private void cargarCitas() {
        modeloCitas.setRowCount(0);
        List<Cita> citas = rc.listarCitas();
        for (Cita c : citas) {
            // Para la fecha usamos el CSV (posición 3) para no depender del nombre del getter
            String[] partes = c.toCSV().split(";");
            String fecha = partes.length > 3 ? partes[3] : "";

            modeloCitas.addRow(new Object[]{
                    c.getId(),
                    c.getMedico().getNombre(),
                    c.getPaciente().getNombre(),
                    fecha,
                    c.getEstado().name()
            });
        }
    }

    private void reservarCita() {
        Medico med = (Medico) cbMedico.getSelectedItem();
        Paciente pac = (Paciente) cbPaciente.getSelectedItem();
        String fechaHoraStr = txtFechaHora.getText().trim();

        if (med == null || pac == null) {
            JOptionPane.showMessageDialog(this, "Selecciona médico y paciente.");
            return;
        }
        if (fechaHoraStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa la fecha y hora (dd/MM/yyyy HH:mm).");
            return;
        }

        try {
            rc.crearCita(med.getId(), pac.getId(), fechaHoraStr);
            txtFechaHora.setText("");
            cargarCitas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear la cita. Verifica la fecha y el formato.\n" + ex.getMessage());
        }
    }

    private void cancelarCitaSeleccionada() {
        int fila = tablaCitas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita para cancelar.");
            return;
        }
        int id = Integer.parseInt(modeloCitas.getValueAt(fila, 0).toString());
        rc.cancelarCita(id);
        cargarCitas();
    }

    private void eliminarCitaSeleccionada() {
        int fila = tablaCitas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita para eliminar.");
            return;
        }
        int id = Integer.parseInt(modeloCitas.getValueAt(fila, 0).toString());
        rc.eliminarCita(id);
        cargarCitas();
    }

    // ============================================================
    // VISIBILIDAD
    // ============================================================
    public void mostrar() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void ocultar() {
        setVisible(false);
    }
}
