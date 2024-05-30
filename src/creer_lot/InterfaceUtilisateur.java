package creer_lot;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import java.util.Date;

public class InterfaceUtilisateur extends JFrame {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> comboBateau;
    private JComboBox<String> comboQualite;
    private JComboBox<String> comboTaille;
    private JComboBox<String> comboEspece;
    private JTextField txtPresentation;
    private JButton btnEnvoyer;
    private JButton btnRetour;

    public InterfaceUtilisateur() {
        initUI();
        setupLayout();
        pack();
        attachListeners();
        setTitle("Création d'un nouveau lot");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initUI() {
        comboBateau = new JComboBox<>();
        comboQualite = new JComboBox<>();
        comboTaille = new JComboBox<>();
        comboEspece = new JComboBox<>();
        txtPresentation = new JTextField(20);
        btnEnvoyer = new JButton("Créer le lot");
        btnRetour = new JButton("Retour");

        chargerComboboxBateaux();
        chargerComboboxQualites();
        chargerComboboxTailles();
        chargerComboboxEspeces();
    }
    
    private void attachListeners() {
        btnEnvoyer.addActionListener(e -> {
            int idBateau = comboBateau.getSelectedIndex() + 1;
            int idEspece = comboEspece.getSelectedIndex() + 1;
            int idTaille = comboTaille.getSelectedIndex() + 1;
            String qualite = ((String) comboQualite.getSelectedItem()).substring(0, 1);
            String presentation = txtPresentation.getText().isEmpty() ? "" : txtPresentation.getText().substring(0, 1);
            Date datePeche = new Date();

            String codeEtat = "C";
            
            Lot lot = new Lot(idBateau, datePeche, idEspece, idTaille, presentation, qualite, 100.0f, 50.0f, 100.0f, codeEtat, "A");
            if (lot.enregistrer()) {
                JOptionPane.showMessageDialog(InterfaceUtilisateur.this, "Le lot a été enregistré avec succès.");
            } else {
                JOptionPane.showMessageDialog(InterfaceUtilisateur.this, "Échec de l'enregistrement du lot.");
            }
        });

        btnRetour.addActionListener(e -> InterfaceUtilisateur.this.dispose());
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        centerPanel.add(new JLabel("Bateau de pêche :"));
        centerPanel.add(comboBateau);
        centerPanel.add(new JLabel("Qualité :"));
        centerPanel.add(comboQualite);
        centerPanel.add(new JLabel("Taille :"));
        centerPanel.add(comboTaille);
        centerPanel.add(new JLabel("Espèces :"));
        centerPanel.add(comboEspece);
        centerPanel.add(new JLabel("Présentation :"));
        centerPanel.add(txtPresentation);
        
        JPanel southPanel = new JPanel();
        southPanel.add(btnRetour);
        southPanel.add(btnEnvoyer);

        add(new JLabel("Création d'un nouveau lot", SwingConstants.CENTER), BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void chargerComboboxBateaux() {
        chargerDonnees("bateau", "nom", comboBateau);
    }

    private void chargerComboboxQualites() {
        chargerDonnees("qualite", "libelle", comboQualite);
    }

    private void chargerComboboxTailles() {
        chargerDonnees("taille", "specification", comboTaille);
    }

    private void chargerComboboxEspeces() {
        chargerDonnees("espece", "nom", comboEspece);
    }

    private void chargerDonnees(String tableName, String columnName, JComboBox<String> comboBox) {
        String sql = "SELECT " + columnName + " FROM " + tableName;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                comboBox.addItem(rs.getString(columnName));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données de " + tableName, "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfaceUtilisateur().setVisible(true));
    }
}
