package view;

import controller.MedicoController;
import model.Cita;
import model.Medico;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VistaMedico extends JFrame {

    private final MedicoController mc;
    private Medico medico;

    private JTable tablaCitas;
    private DefaultTableModel modeloCitas;
    private JLabel lblTitulo;

    public VistaMedico(MedicoController mc) {
        this.mc = mc;

        setTitle("Panel Médico");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(900, 500);
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
        lblTitulo = new JLabel("Panel Médico");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(33, 37, 41));
        main.add(lblTitulo, BorderLayout.NORTH);

        // Tabla de citas
        modeloCitas = new DefaultTableModel(
                new Object[]{"ID", "Paciente", "Fecha/Hora", "Estado"},
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
        scroll.setBorder(BorderFactory.createTitledBorder("Citas asignadas"));
        main.add(scroll, BorderLayout.CENTER);

        // Botones inferiores
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAtender = new JButton("Marcar como atendida");
        JButton btnCancelar = new JButton("Cancelar cita");
        JButton btnRefrescar = new JButton("Refrescar");

        styleButton(btnAtender);
        styleButton(btnCancelar);
        styleButton(btnRefrescar);

        panelBotones.add(btnRefrescar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnAtender);

        main.add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnRefrescar.addActionListener(e -> cargarCitas());
        btnAtender.addActionListener(e -> marcarComoAtendida());
        btnCancelar.addActionListener(e -> cancelarCita());
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
        if (medico == null) return;

        List<Cita> citas = mc.listarCitasDelMedico();
        for (Cita c : citas) {
            // Tomamos la fecha desde toCSV (posición 3)
            String[] partes = c.toCSV().split(";");
            String fecha = partes.length > 3 ? partes[3] : "";

            modeloCitas.addRow(new Object[]{
                    c.getId(),
                    c.getPaciente().getNombre(),
                    fecha,
                    c.getEstado().name()
            });
        }
    }

    private void marcarComoAtendida() {
        int fila = tablaCitas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita para marcar como atendida.");
            return;
        }
        int idCit = Integer.parseInt(modeloCitas.getValueAt(fila, 0).toString());
        mc.marcarCitaAtendida(idCit);  // COMPLETADA
        cargarCitas();
    }

    private void cancelarCita() {
        int fila = tablaCitas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita para cancelar.");
            return;
        }
        int idCit = Integer.parseInt(modeloCitas.getValueAt(fila, 0).toString());
        mc.cancelarCita(idCit);  // CANCELADA
        cargarCitas();
    }

    // ============================================================
    // VISIBILIDAD / INICIALIZACIÓN
    // ============================================================
    /**
     * Se llama desde el LoginController cuando el médico inicia sesión.
     */
    public void mostrar(Medico medico) {
        this.medico = medico;
        mc.setMedico(medico);

        if (medico != null) {
            lblTitulo.setText("Panel Médico - " + medico.getNombre());
        } else {
            lblTitulo.setText("Panel Médico");
        }

        cargarCitas();
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void ocultar() {
        setVisible(false);
    }
}
