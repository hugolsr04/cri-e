package creer_lot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Lot {
    private int idBateau;
    private Date datePeche;
    private int idEspece;
    private int idTaille;
    private String idPresentation;
    private String idQualite;
    private Float poidsBrutLot;
    private Float prixPlancher;
    private Float prixDepart;
    private String codeEtat;
    private String statutLot;

    public Lot(int idBateau, Date datePeche, int idEspece, int idTaille, String idPresentation,
            String idQualite, Float poidsBrutLot, Float prixPlancher, Float prixDepart,
            String codeEtat, String statutLot) {
     this.idBateau = idBateau;
     this.datePeche = datePeche;
     this.idEspece = idEspece;
     this.idTaille = idTaille;
     this.idPresentation = idPresentation;
     this.idQualite = idQualite;
     this.poidsBrutLot = poidsBrutLot;
     this.prixPlancher = prixPlancher;
     this.prixDepart = prixDepart;
     this.codeEtat = codeEtat;
     this.statutLot = statutLot;
 }

    public int getIdBateau() {
        return idBateau;
    }

    public Date getDatePeche() {
        return datePeche;
    }

    public int getIdEspece() {
        return idEspece;
    }

    public int getIdTaille() {
        return idTaille;
    }

    public String getIdQualite() {
        return idQualite;
    }

    public Float getPoidsBrutLot() {
        return poidsBrutLot;
    }

    public Float getPrixPlancher() {
        return prixPlancher;
    }

    public Float getPrixDepart() {
        return prixDepart;
    }

    public String getCodeEtat() {
        return codeEtat;
    }

    public String getStatutLot() {
        return statutLot;
    }

    public void setIdBateau(int idBateau) {
        this.idBateau = idBateau;
    }

    public void setDatePeche(Date datePeche) {
        this.datePeche = datePeche;
    }

    public void setIdEspece(int idEspece) {
        this.idEspece = idEspece;
    }

    public void setIdTaille(int idTaille) {
        this.idTaille = idTaille;
    }

    public void setIdQualite(String idQualite) {
        this.idQualite = idQualite;
    }

    public void setPoidsBrutLot(Float poidsBrutLot) {
        this.poidsBrutLot = poidsBrutLot;
    }

    public void setPrixPlancher(Float prixPlancher) {
        this.prixPlancher = prixPlancher;
    }

    public void setPrixDepart(Float prixDepart) {
        this.prixDepart = prixDepart;
    }

    public void setCodeEtat(String codeEtat) {
        if (codeEtat != null && codeEtat.length() > 1) {
            codeEtat = codeEtat.substring(0, 1);
        }
        this.codeEtat = codeEtat;
    }

    public void setStatutLot(String statutLot) {
        this.statutLot = statutLot;
    }

    public boolean enregistrer() {
        String sql = "INSERT INTO lot (idBateau, datePeche, idEspece, idTaille, idPresentation, idQualite, poidsBrutLot, prixPlancher, prixDepart, codeEtat, statutLot) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.idBateau);
            pstmt.setDate(2, new java.sql.Date(this.datePeche.getTime()));
            pstmt.setInt(3, this.idEspece);
            pstmt.setInt(4, this.idTaille);
            pstmt.setString(5, this.idPresentation);
            pstmt.setString(6, this.idQualite);
            pstmt.setFloat(7, this.poidsBrutLot);
            pstmt.setFloat(8, this.prixPlancher);
            pstmt.setFloat(9, this.prixDepart);
            pstmt.setString(10, this.codeEtat);
            pstmt.setString(11, this.statutLot);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
