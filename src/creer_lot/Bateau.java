package creer_lot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bateau {
    private int id;
    private String nom;
    private float longueur;
    private float largeur;

    public Bateau() {
    }

    public Bateau(int id, String nom, float longueur, float largeur) {
        this.id = id;
        this.nom = nom;
        this.longueur = longueur;
        this.largeur = largeur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getLongueur() {
        return longueur;
    }

    public void setLongueur(float longueur) {
        this.longueur = longueur;
    }

    public float getLargeur() {
        return largeur;
    }

    public void setLargeur(float largeur) {
        this.largeur = largeur;
    }

    @Override
    public String toString() {
        return "Bateau{" +
               "id=" + id +
               ", nom='" + nom + '\'' +
               ", longueur=" + longueur +
               ", largeur=" + largeur +
               '}';
    }

    public boolean enregistrer() {
        String sql = "INSERT INTO bateaux (id, nom, longueur, largeur) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, this.id);
            pstmt.setString(2, this.nom);
            pstmt.setFloat(3, this.longueur);
            pstmt.setFloat(4, this.largeur);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Bateau chercherParId(int id) {
        String sql = "SELECT * FROM bateaux WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Bateau bateau = new Bateau();
                bateau.setId(rs.getInt("id"));
                bateau.setNom(rs.getString("nom"));
                bateau.setLongueur(rs.getFloat("longueur"));
                bateau.setLargeur(rs.getFloat("largeur"));
                return bateau;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
