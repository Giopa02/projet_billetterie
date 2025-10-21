import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleModel {
    // Attributs correspondant aux colonnes de la table Salle
    private Integer idSalle;
    private String nomSalle;
    private Integer idComplexe;

    // Constructeur vide
    public SalleModel() {}

    // Constructeur avec tous les paramètres
    public SalleModel(Integer idSalle, String nomSalle, Integer idComplexe) {
        this.idSalle = idSalle;
        this.nomSalle = nomSalle;
        this.idComplexe = idComplexe;
    }

    // ===== MÉTHODES CRUD =====

    /**
     * Récupère toutes les salles
     */
    public static List<SalleModel> getAll() {
        String sql = "SELECT * FROM Salle ORDER BY nomSalle";
        List<SalleModel> salles = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                SalleModel salle = new SalleModel();
                salle.setIdSalle(rs.getInt("id_salle"));
                salle.setNomSalle(rs.getString("nomSalle"));
                salle.setIdComplexe(rs.getInt("id_complexe"));
                
                salles.add(salle);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des salles : " + ex.getMessage());
        }

        return salles;
    }

    /**
     * Récupère une salle par son ID
     */
    public static SalleModel getById(Integer id) {
        String sql = "SELECT * FROM Salle WHERE id_salle = ?";
        SalleModel salle = null;

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                salle = new SalleModel();
                salle.setIdSalle(rs.getInt("id_salle"));
                salle.setNomSalle(rs.getString("nomSalle"));
                salle.setIdComplexe(rs.getInt("id_complexe"));
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de la salle : " + ex.getMessage());
        }

        return salle;
    }

    /**
     * Insère une nouvelle salle
     */
    public static boolean insert(String nomSalle, Integer idComplexe) {
        String sql = "INSERT INTO Salle (nomSalle, id_complexe) VALUES (?, ?)";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomSalle);
            pstmt.setInt(2, idComplexe);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'insertion de la salle : " + ex.getMessage());
            return false;
        }
    }

    /**
     * Met à jour une salle existante
     */
    public static boolean update(Integer id, String nomSalle, Integer idComplexe) {
        String sql = "UPDATE Salle SET nomSalle = ?, id_complexe = ? WHERE id_salle = ?";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomSalle);
            pstmt.setInt(2, idComplexe);
            pstmt.setInt(3, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour de la salle : " + ex.getMessage());
            return false;
        }
    }

    /**
     * Supprime une salle par son ID
     */
    public static boolean delete(Integer id) {
        String sql = "DELETE FROM Salle WHERE id_salle = ?";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de la salle : " + ex.getMessage());
            return false;
        }
    }

    /**
     * Récupère les salles d'un complexe culturel spécifique
     */
    public static List<SalleModel> getByComplexe(Integer idComplexe) {
        String sql = "SELECT * FROM Salle WHERE id_complexe = ? ORDER BY nomSalle";
        List<SalleModel> salles = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idComplexe);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SalleModel salle = new SalleModel();
                salle.setIdSalle(rs.getInt("id_salle"));
                salle.setNomSalle(rs.getString("nomSalle"));
                salle.setIdComplexe(rs.getInt("id_complexe"));
                
                salles.add(salle);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des salles du complexe : " + ex.getMessage());
        }

        return salles;
    }

    /**
     * Récupère les salles pour un événement spécifique
     */
    public static List<SalleModel> getByEvenement(Integer idEvenement) {
        String sql = "SELECT s.* FROM Salle s " +
                     "JOIN Evenement_Salle es ON s.id_salle = es.id_salle " +
                     "WHERE es.id_evenement = ? ORDER BY s.nomSalle";
        List<SalleModel> salles = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEvenement);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SalleModel salle = new SalleModel();
                salle.setIdSalle(rs.getInt("id_salle"));
                salle.setNomSalle(rs.getString("nomSalle"));
                salle.setIdComplexe(rs.getInt("id_complexe"));
                
                salles.add(salle);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des salles de l'événement : " + ex.getMessage());
        }

        return salles;
    }

    /**
     * Récupère les salles avec le nom de leur complexe (JOIN)
     */
    public static List<String> getSallesWithComplexe() {
        String sql = "SELECT s.nomSalle, c.nomComplexe, c.adresse " +
                     "FROM Salle s " +
                     "JOIN ComplexeCulturel c ON s.id_complexe = c.id_complexe " +
                     "ORDER BY c.nomComplexe, s.nomSalle";
        List<String> details = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String detail = String.format("%s - %s (%s)",
                    rs.getString("nomSalle"),
                    rs.getString("nomComplexe"),
                    rs.getString("adresse")
                );
                details.add(detail);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des détails : " + ex.getMessage());
        }

        return details;
    }

    /**
     * Compte le nombre d'événements prévus dans une salle
     */
    public static int countEvenementsBySalle(Integer idSalle) {
        String sql = "SELECT COUNT(*) as total FROM Evenement_Salle WHERE id_salle = ?";
        int count = 0;

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idSalle);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("total");
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors du comptage des événements : " + ex.getMessage());
        }

        return count;
    }

    // ===== GETTERS ET SETTERS =====

    public Integer getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(Integer idSalle) {
        this.idSalle = idSalle;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public Integer getIdComplexe() {
        return idComplexe;
    }

    public void setIdComplexe(Integer idComplexe) {
        this.idComplexe = idComplexe;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "id=" + idSalle +
                ", nom='" + nomSalle + '\'' +
                ", complexe=" + idComplexe +
                '}';
    }
}