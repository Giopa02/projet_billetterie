package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des horaires
 * ✅ VERSION ADAPTÉE avec insertAndGetId
 */
public class HoraireDAO {

    /**
     * Récupère tous les horaires
     */
    public static List<HoraireModel> getAll() {
        String sql = "SELECT * FROM Horaire ORDER BY heureDebut";
        List<HoraireModel> horaires = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HoraireModel horaire = new HoraireModel();
                horaire.setIdHoraire(rs.getInt("id_horaire"));
                horaire.setHeureDebut(rs.getTime("heureDebut"));
                horaire.setHeureFin(rs.getTime("heureFin"));

                horaires.add(horaire);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des horaires : " + ex.getMessage());
        }

        return horaires;
    }

    /**
     * Récupère un horaire par son ID
     */
    public static HoraireModel getById(Integer id) {
        String sql = "SELECT * FROM Horaire WHERE id_horaire = ?";
        HoraireModel horaire = null;

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                horaire = new HoraireModel();
                horaire.setIdHoraire(rs.getInt("id_horaire"));
                horaire.setHeureDebut(rs.getTime("heureDebut"));
                horaire.setHeureFin(rs.getTime("heureFin"));
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de l'horaire : " + ex.getMessage());
        }

        return horaire;
    }

    /**
     * Insère un nouvel horaire
     */
    public static boolean insert(Time heureDebut, Time heureFin) {
        String sql = "INSERT INTO Horaire (heureDebut, heureFin) VALUES (?, ?)";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTime(1, heureDebut);
            pstmt.setTime(2, heureFin);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'insertion de l'horaire : " + ex.getMessage());
            return false;
        }
    }

    /**
     * ✅ NOUVEAU : Insère un horaire et retourne son ID
     */
    public static Integer insertAndGetId(Time heureDebut, Time heureFin) {
        String sql = "INSERT INTO Horaire (heureDebut, heureFin) VALUES (?, ?)";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setTime(1, heureDebut);
            pstmt.setTime(2, heureFin);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'insertion de l'horaire : " + ex.getMessage());
        }

        return null;
    }

    /**
     * Met à jour un horaire existant
     */
    public static boolean update(Integer id, Time heureDebut, Time heureFin) {
        String sql = "UPDATE Horaire SET heureDebut = ?, heureFin = ? WHERE id_horaire = ?";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTime(1, heureDebut);
            pstmt.setTime(2, heureFin);
            pstmt.setInt(3, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour de l'horaire : " + ex.getMessage());
            return false;
        }
    }

    /**
     * Supprime un horaire par son ID
     */
    public static boolean delete(Integer id) {
        String sql = "DELETE FROM Horaire WHERE id_horaire = ?";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de l'horaire : " + ex.getMessage());
            return false;
        }
    }

    /**
     * Récupère les horaires pour un événement spécifique
     */
    public static List<HoraireModel> getByEvenement(Integer idEvenement) {
        String sql = "SELECT h.* FROM Horaire h " +
                "JOIN Evenement_Horaire eh ON h.id_horaire = eh.id_horaire " +
                "WHERE eh.id_evenement = ? ORDER BY h.heureDebut";
        List<HoraireModel> horaires = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEvenement);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                HoraireModel horaire = new HoraireModel();
                horaire.setIdHoraire(rs.getInt("id_horaire"));
                horaire.setHeureDebut(rs.getTime("heureDebut"));
                horaire.setHeureFin(rs.getTime("heureFin"));

                horaires.add(horaire);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des horaires de l'événement : " + ex.getMessage());
        }

        return horaires;
    }
}