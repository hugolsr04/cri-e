package creer_lot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class MenuPrincipal extends JFrame implements Serializable {
    private static final long serialVersionUID = 1L;

    public MenuPrincipal() {
        super("Menu Principal");
        initializeComponents();
    }

    private void initializeComponents() {
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JButton btnCreerLot = new JButton("Créer des lots");
        JButton btnSupprimerLot = new JButton("Supprimer des lots");

        btnCreerLot.addActionListener(e -> {
            InterfaceUtilisateur interfaceUtilisateur = new InterfaceUtilisateur();
            interfaceUtilisateur.setVisible(true);
        });

        btnSupprimerLot.addActionListener(e -> {
            SuppressionLot suppressionLot = new SuppressionLot();
            suppressionLot.setVisible(true);
        });

        add(btnCreerLot);
        add(btnSupprimerLot);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
