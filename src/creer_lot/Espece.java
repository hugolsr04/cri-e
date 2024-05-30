package creer_lot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Espece {
    private int id;
    private String nom;
    private String description;

    public Espece() {
    }

    public Espece(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Espece{" +
               "id=" + id +
               ", nom='" + nom + '\'' +
               ", description='" + description + '\'' +
               '}';
    }

    public boolean enregistrer() {
        String sql = "INSERT INTO especes (id, nom, description) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, this.id);
            pstmt.setString(2, this.nom);
            pstmt.setString(3, this.description);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Espece chercherParId(int id) {
        String sql = "SELECT * FROM especes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Espece espece = new Espece();
                espece.setId(rs.getInt("id"));
                espece.setNom(rs.getString("nom"));
                espece.setDescription(rs.getString("description"));
                return espece;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
