import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementModel {
    // Attributs correspondant aux colonnes de la table Evenement
    private Integer idEvenement;
    private String nomEvenement;
    private String affiche;
    private String description;
    private Date date;
    private Integer ageMinimal;
    private Integer duree;
    private Double prix;

    // Constructeur vide
    public EvenementModel() {}

    // Constructeur avec tous les paramètres
    public EvenementModel(Integer idEvenement, String nomEvenement, String affiche, 
                        String description, Date date, Integer ageMinimal, 
                        Integer duree, Double prix) {
        this.idEvenement = idEvenement;
        this.nomEvenement = nomEvenement;
        this.affiche = affiche;
        this.description = description;
        this.date = date;
        this.ageMinimal = ageMinimal;
        this.duree = duree;
        this.prix = prix;
    }

    // ===== MÉTHODES CRUD =====

    /**
     * Récupère tous les événements
     */
    public static List<EvenementModel> getAll() {
        String sql = "SELECT * FROM Evenement ORDER BY date";
        List<EvenementModel> evenements = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                EvenementModel event = new EvenementModel();
                event.setIdEvenement(rs.getInt("id_evenement"));
                event.setNomEvenement(rs.getString("nomEvenement"));
                event.setAffiche(rs.getString("affiche"));
                event.setDescription(rs.getString("description"));
                event.setDate(rs.getDate("date"));
                event.setAgeMinimal(rs.getInt("ageMinimal"));
                event.setDuree(rs.getInt("duree"));
                event.setPrix(rs.getDouble("prix"));
                
                evenements.add(event);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des événements : " + ex.getMessage());
        }

        return evenements;
    }

    /**
     * Récupère un événement par son ID
     */
    public static EvenementModel getById(Integer id) {
        String sql = "SELECT * FROM Evenement WHERE id_evenement = ?";
        EvenementModel event = null;

        try (Connection conn = MySQLConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                event = new EvenementModel();
                event.setIdEvenement(rs.getInt("id_evenement"));
                event.setNomEvenement(rs.getString("nomEvenement"));
                event.setAffiche(rs.getString("affiche"));
                event.setDescription(rs.getString("description"));
                event.setDate(rs.getDate("date"));
                event.setAgeMinimal(rs.getInt("ageMinimal"));
                event.setDuree(rs.getInt("duree"));
                event.setPrix(rs.getDouble("prix"));
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de l'événement : " + ex.getMessage());
        }

        return event;
    }

    /**
     * Insère un nouvel événement
     */
    public static boolean insert(String nomEvenement, String affiche, String description,
                                Date date, Integer ageMinimal, Integer duree, Double prix) {
        String sql = "INSERT INTO Evenement (nomEvenement, affiche, description, date, ageMinimal, duree, prix) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomEvenement);
            pstmt.setString(2, affiche);
            pstmt.setString(3, description);
            pstmt.setDate(4, date);
            pstmt.setInt(5, ageMinimal);
            pstmt.setInt(6, duree);
            pstmt.setDouble(7, prix);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'insertion de l'événement : " + ex.getMessage());
            return false;
        }
    }

    /**
     * Met à jour un événement existant
     */
    public static boolean update(Integer id, String nomEvenement, String affiche, String description,
                                Date date, Integer ageMinimal, Integer duree, Double prix) {
        String sql = "UPDATE Evenement SET nomEvenement = ?, affiche = ?, description = ?, " +
                    "date = ?, ageMinimal = ?, duree = ?, prix = ? WHERE id_evenement = ?";

        try (Connection conn = MySQLConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomEvenement);
            pstmt.setString(2, affiche);
            pstmt.setString(3, description);
            pstmt.setDate(4, date);
            pstmt.setInt(5, ageMinimal);
            pstmt.setInt(6, duree);
            pstmt.setDouble(7, prix);
            pstmt.setInt(8, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour de l'événement : " + ex.getMessage());
            return false;
        }
    }

    /**
     * Supprime un événement par son ID
     */
    public static boolean delete(Integer id) {
        String sql = "DELETE FROM Evenement WHERE id_evenement = ?";

        try (Connection conn = MySQLConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de l'événement : " + ex.getMessage());
            return false;
        }
    }

    /**
     * Récupère les événements d'une date spécifique
     */
    public static List<EvenementModel> getByDate(Date date) {
        String sql = "SELECT * FROM Evenement WHERE date = ? ORDER BY nomEvenement";
        List<EvenementModel> evenements = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, date);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                EvenementModel event = new EvenementModel();
                event.setIdEvenement(rs.getInt("id_evenement"));
                event.setNomEvenement(rs.getString("nomEvenement"));
                event.setAffiche(rs.getString("affiche"));
                event.setDescription(rs.getString("description"));
                event.setDate(rs.getDate("date"));
                event.setAgeMinimal(rs.getInt("ageMinimal"));
                event.setDuree(rs.getInt("duree"));
                event.setPrix(rs.getDouble("prix"));
                
                evenements.add(event);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la recherche par date : " + ex.getMessage());
        }

        return evenements;
    }

    /**
     * Récupère les événements à venir
     */
    public static List<EvenementModel> getUpcoming() {
        String sql = "SELECT * FROM Evenement WHERE date >= CURDATE() ORDER BY date";
        List<EvenementModel> evenements = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                EvenementModel event = new EvenementModel();
                event.setIdEvenement(rs.getInt("id_evenement"));
                event.setNomEvenement(rs.getString("nomEvenement"));
                event.setAffiche(rs.getString("affiche"));
                event.setDescription(rs.getString("description"));
                event.setDate(rs.getDate("date"));
                event.setAgeMinimal(rs.getInt("ageMinimal"));
                event.setDuree(rs.getInt("duree"));
                event.setPrix(rs.getDouble("prix"));
                
                evenements.add(event);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des événements à venir : " + ex.getMessage());
        }

        return evenements;
    }

    /**
     * Recherche des événements par nom
     */
    public static List<EvenementModel> searchByName(String nom) {
        String sql = "SELECT * FROM Evenement WHERE nomEvenement LIKE ? ORDER BY date";
        List<EvenementModel> evenements = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nom + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                EvenementModel event = new EvenementModel();
                event.setIdEvenement(rs.getInt("id_evenement"));
                event.setNomEvenement(rs.getString("nomEvenement"));
                event.setAffiche(rs.getString("affiche"));
                event.setDescription(rs.getString("description"));
                event.setDate(rs.getDate("date"));
                event.setAgeMinimal(rs.getInt("ageMinimal"));
                event.setDuree(rs.getInt("duree"));
                event.setPrix(rs.getDouble("prix"));
                
                evenements.add(event);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la recherche : " + ex.getMessage());
        }

        return evenements;
    }

    // ===== GETTERS ET SETTERS =====

    public Integer getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(Integer idEvenement) {
        this.idEvenement = idEvenement;
    }

    public String getNomEvenement() {
        return nomEvenement;
    }

    public void setNomEvenement(String nomEvenement) {
        this.nomEvenement = nomEvenement;
    }

    public String getAffiche() {
        return affiche;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAgeMinimal() {
        return ageMinimal;
    }

    public void setAgeMinimal(Integer ageMinimal) {
        this.ageMinimal = ageMinimal;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + idEvenement +
                ", nom='" + nomEvenement + '\'' +
                ", date=" + date +
                ", prix=" + prix + "€" +
                ", âge minimum=" + ageMinimal +
                '}';
    }
}