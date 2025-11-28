package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des événements
 * ✅ VERSION ADAPTÉE à votre structure avec tables de liaison
 */
public class EvenementDAO {

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

                // ✅ Récupérer les horaires de cet événement
                List<HoraireModel> horaires = HoraireDAO.getByEvenement(event.getIdEvenement());
                if (!horaires.isEmpty()) {
                    event.setHoraires(horaires);
                }

                // ✅ Récupérer les salles de cet événement
                List<SalleModel> salles = SalleDAO.getByEvenement(event.getIdEvenement());
                if (!salles.isEmpty()) {
                    event.setSalles(salles);
                }

                evenements.add(event);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des événements : " + ex.getMessage());
        }

        return evenements;
    }

    /**
     * Récupère un événement par son ID avec ses horaires et salles
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

                // ✅ Récupérer les horaires
                List<HoraireModel> horaires = HoraireDAO.getByEvenement(id);
                event.setHoraires(horaires);

                // ✅ Récupérer les salles
                List<SalleModel> salles = SalleDAO.getByEvenement(id);
                event.setSalles(salles);
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
     * ✅ NOUVELLE : Insère un événement et retourne son ID
     */
    public static Integer insertAndGetId(String nomEvenement, String affiche, String description,
                                         Date date, Integer ageMinimal, Integer duree, Double prix) {
        String sql = "INSERT INTO Evenement (nomEvenement, affiche, description, date, ageMinimal, duree, prix) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, nomEvenement);
            pstmt.setString(2, affiche);
            pstmt.setString(3, description);
            pstmt.setDate(4, date);
            pstmt.setInt(5, ageMinimal);
            pstmt.setInt(6, duree);
            pstmt.setDouble(7, prix);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'insertion de l'événement : " + ex.getMessage());
        }

        return null;
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

    /**
     * ✅ NOUVELLE : Lie un événement à une salle
     */
    public static boolean addSalle(Integer idEvenement, Integer idSalle) {
        String sql = "INSERT INTO Evenement_Salle (id_evenement, id_salle) VALUES (?, ?)";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEvenement);
            pstmt.setInt(2, idSalle);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout de la salle : " + ex.getMessage());
            return false;
        }
    }

    /**
     * ✅ NOUVELLE : Lie un événement à un horaire
     */
    public static boolean addHoraire(Integer idEvenement, Integer idHoraire) {
        String sql = "INSERT INTO Evenement_Horaire (id_evenement, id_horaire) VALUES (?, ?)";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEvenement);
            pstmt.setInt(2, idHoraire);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout de l'horaire : " + ex.getMessage());
            return false;
        }
    }

    /**
     * ✅ NOUVELLE : Supprime toutes les salles d'un événement
     */
    public static boolean clearSalles(Integer idEvenement) {
        String sql = "DELETE FROM Evenement_Salle WHERE id_evenement = ?";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEvenement);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression des salles : " + ex.getMessage());
            return false;
        }
    }

    /**
     * ✅ NOUVELLE : Supprime tous les horaires d'un événement
     */
    public static boolean clearHoraires(Integer idEvenement) {
        String sql = "DELETE FROM Evenement_Horaire WHERE id_evenement = ?";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEvenement);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression des horaires : " + ex.getMessage());
            return false;
        }
    }
}