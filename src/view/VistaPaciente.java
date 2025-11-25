package view;

import controller.PacienteController;
import model.Cita;
import model.Medico;
import model.Paciente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VistaPaciente extends JFrame {

    private final PacienteController pc;
    private Paciente paciente;

    private JTable tablaCitas;
    private DefaultTableModel modeloCitas;
    private JLabel lblTitulo;

    private JComboBox<Medico> cbMedico;
    private JTextField txtFechaHora;

    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public VistaPaciente(PacienteController pc) {
        this.pc = pc;

        setTitle("Panel Paciente");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
    }

    private void initUI() {
        Color bg = new Color(245, 247, 250);

        JPanel main = new JPanel(new BorderLayout(12, 12));
        main.setBackground(bg);
        main.setBorder(new EmptyBorder(12, 12, 12, 12));
        setContentPane(main);

        // Título
        lblTitulo = new JLabel("Panel Paciente");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(33, 37, 41));
        main.add(lblTitulo, BorderLayout.NORTH);

        // ================== PANEL SUPERIOR: RESERVA ==================
        JPanel panelReserva = new JPanel(new GridBagLayout());
        panelReserva.setBackground(Color.WHITE);
        panelReserva.setBorder(BorderFactory.createTitledBorder("Reservar nueva cita"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;

        cbMedico = new JComboBox<>();
        txtFechaHora = new JTextField();
        txtFechaHora.setToolTipText("Formato: dd/MM/yyyy HH:mm");

        panelReserva.add(new JLabel("Médico:"), c);
        c.gridx = 1;
        panelReserva.add(cbMedico, c);

        c.gridy++;
        c.gridx = 0;
        panelReserva.add(new JLabel("Fecha y hora:"), c);
        c.gridx = 1;
        panelReserva.add(txtFechaHora, c);

        JButton btnReservar = new JButton("Reservar cita");
        styleButton(btnReservar);
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        panelReserva.add(btnReservar, c);

        main.add(panelReserva, BorderLayout.NORTH);

        // ================== TABLA DE CITAS ==================
        modeloCitas = new DefaultTableModel(
                new Object[]{"ID", "Médico", "Fecha/Hora", "Estado"},
                0
        ) {
            @Override
            public boolean isCellEditable(int r, int col) {
                return false;
            }
        };

        tablaCitas = new JTable(modeloCitas);
        tablaCitas.setRowHeight(24);
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tablaCitas);
        scroll.setBorder(BorderFactory.createTitledBorder("Mis citas"));
        main.add(scroll, BorderLayout.CENTER);

        // ================== BOTONES INFERIORES ==================
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancelar = new JButton("Cancelar cita");
        JButton btnRefrescar = new JButton("Refrescar");
        styleButton(btnCancelar);
        styleButton(btnRefrescar);

        panelBotones.add(btnRefrescar);
        panelBotones.add(btnCancelar);

        main.add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnRefrescar.addActionListener(e -> cargarCitas());
        btnCancelar.addActionListener(e -> cancelarCitaSeleccionada());
        btnReservar.addActionListener(e -> reservarCita());
    }

    private void styleButton(JButton b) {
        b.setFocusPainted(false);
        b.setBackground(new Color(56, 128, 255));
        b.setForeground(Color.WHITE);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // ============================================================
    // LÓGICA
    // ============================================================
    private void cargarCitas() {
        modeloCitas.setRowCount(0);
        if (paciente == null) return;

        List<Cita> citas = pc.listarCitasPaciente();
        for (Cita c : citas) {
            String[] partes = c.toCSV().split(";");
            String fecha = partes.length > 3 ? partes[3] : "";
            modeloCitas.addRow(new Object[]{
                    c.getId(),
                    c.getMedico().getNombre(),
                    fecha,
                    c.getEstado().name()
            });
        }
    }

    private void cargarMedicos() {
        cbMedico.removeAllItems();
        for (Medico m : pc.listarMedicos()) {
            cbMedico.addItem(m);
        }
    }

    private void cancelarCitaSeleccionada() {
        int fila = tablaCitas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita para cancelar.");
            return;
        }
        int idCit = Integer.parseInt(modeloCitas.getValueAt(fila, 0).toString());
        pc.cancelarCita(idCit);
        cargarCitas();
    }

    private void reservarCita() {
        Medico med = (Medico) cbMedico.getSelectedItem();
        String fechaHoraStr = txtFechaHora.getText().trim();

        if (med == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un médico.");
            return;
        }
        if (fechaHoraStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa la fecha y hora (dd/MM/yyyy HH:mm).");
            return;
        }

        try {
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, formato);
            pc.reservarCita(med.getId(), fechaHora);
            txtFechaHora.setText("");
            cargarCitas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en el formato de fecha. Usa dd/MM/yyyy HH:mm");
        }
    }

    // ============================================================
    // VISIBILIDAD
    // ============================================================
    public void mostrar(Paciente paciente) {
        this.paciente = paciente;
        pc.setPaciente(paciente);

        if (paciente != null) {
            lblTitulo.setText("Panel Paciente - " + paciente.getNombre());
        } else {
            lblTitulo.setText("Panel Paciente");
        }

        cargarMedicos();
        cargarCitas();
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void ocultar() {
        setVisible(false);
    }
}
