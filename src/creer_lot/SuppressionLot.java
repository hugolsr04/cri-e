package creer_lot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SuppressionLot extends JFrame {
    private JComboBox<String> comboLots;
    private JButton btnSupprimer;
    private JButton btnRetour;

    public SuppressionLot() {
        setTitle("Supprimer un lot");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        comboLots = new JComboBox<>();
        btnSupprimer = new JButton("Supprimer le lot");
        btnRetour = new JButton("Retour");

        chargerLots();

        btnSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerLot();
            }
        });

        btnRetour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(comboLots);
        add(btnSupprimer);
        add(btnRetour);
    }

    private void chargerLots() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, idBateau FROM lot")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                comboLots.addItem("Lot ID: " + rs.getInt("id") + " - Bateau ID: " + rs.getInt("idBateau"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des lots", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void supprimerLot() {
        if (comboLots.getSelectedItem() != null) {
            String lotInfo = (String) comboLots.getSelectedItem();
            int lotId = Integer.parseInt(lotInfo.split(" - ")[0].split("ID: ")[1]);

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM lot WHERE id = ?")) {
                stmt.setInt(1, lotId);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Lot supprimé avec succès.");
                    comboLots.removeItem(comboLots.getSelectedItem());
                } else {
                    JOptionPane.showMessageDialog(this, "Échec de la suppression du lot.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du lot", "Erreur", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SuppressionLot().setVisible(true);
            }
        });
    }
}
