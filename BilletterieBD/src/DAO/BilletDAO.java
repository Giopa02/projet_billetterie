package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object pour la table Billet
 * Contient toutes les mÃ©thodes d'accÃ¨s Ã  la base de donnÃ©es
 */
public class BilletDAO {

    /**
     * RÃ©cupÃ¨re tous les billets
     */
    public static List<BilletModel> getAll() {
        String sql = "SELECT * FROM Billet ORDER BY dateAchat DESC";
        List<BilletModel> billets = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BilletModel billet = new BilletModel();
                billet.setIdClient(rs.getInt("id_client"));
                billet.setIdEvenement(rs.getInt("id_evenement"));
                billet.setIdHoraire(rs.getInt("id_horaire"));
                billet.setDateAchat(rs.getTimestamp("dateAchat"));
                billet.setPrix(rs.getDouble("prix"));

                billets.add(billet);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la rÃ©cupÃ©ration des billets : " + ex.getMessage());
        }

        return billets;
    }

    /**
     * RÃ©cupÃ¨re un billet spÃ©cifique par sa clÃ© primaire composite
     */
    public static BilletModel getById(Integer idClient, Integer idEvenement, Integer idHoraire) {
        String sql = "SELECT * FROM Billet WHERE id_client = ? AND id_evenement = ? AND id_horaire = ?";
        BilletModel billet = null;

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idClient);
            pstmt.setInt(2, idEvenement);
            pstmt.setInt(3, idHoraire);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                billet = new BilletModel();
                billet.setIdClient(rs.getInt("id_client"));
                billet.setIdEvenement(rs.getInt("id_evenement"));
                billet.setIdHoraire(rs.getInt("id_horaire"));
                billet.setDateAchat(rs.getTimestamp("dateAchat"));
                billet.setPrix(rs.getDouble("prix"));
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la rÃ©cupÃ©ration du billet : " + ex.getMessage());
        }

        return billet;
    }

    /**
     * InsÃ¨re un nouveau billet
     */
    public static boolean insert(Integer idClient, Integer idEvenement, Integer idHoraire,
                                 Timestamp dateAchat, Double prix) {
        String sql = "INSERT INTO Billet (id_client, id_evenement, id_horaire, dateAchat, prix) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idClient);
            pstmt.setInt(2, idEvenement);
            pstmt.setInt(3, idHoraire);
            pstmt.setTimestamp(4, dateAchat);
            pstmt.setDouble(5, prix);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'insertion du billet : " + ex.getMessage());
            return false;
        }
    }

    /**
     * Met Ã  jour un billet existant
     */
    public static boolean update(Integer idClient, Integer idEvenement, Integer idHoraire,
                                 Timestamp dateAchat, Double prix) {
        String sql = "UPDATE Billet SET dateAchat = ?, prix = ? " +
                "WHERE id_client = ? AND id_evenement = ? AND id_horaire = ?";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTimestamp(1, dateAchat);
            pstmt.setDouble(2, prix);
            pstmt.setInt(3, idClient);
            pstmt.setInt(4, idEvenement);
            pstmt.setInt(5, idHoraire);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise Ã  jour du billet : " + ex.getMessage());
            return false;
        }
    }

    /**
     * âœ… TODO #1 : Met Ã  jour SEULEMENT le prix d'un billet
     * MÃ©thode simplifiÃ©e pour la modification depuis l'interface
     */
    public static boolean updatePrix(Integer idClient, Integer idEvenement, Integer idHoraire, Double nouveauPrix) {
        String sql = "UPDATE Billet SET prix = ? " +
                "WHERE id_client = ? AND id_evenement = ? AND id_horaire = ?";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, nouveauPrix);
            pstmt.setInt(2, idClient);
            pstmt.setInt(3, idEvenement);
            pstmt.setInt(4, idHoraire);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("âœ“ Prix du billet modifiÃ© : " + rowsAffected + " ligne(s)");
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la modification du prix : " + ex.getMessage());
            return false;
        }
    }

    /**
     * Supprime un billet
     */
    public static boolean delete(Integer idClient, Integer idEvenement, Integer idHoraire) {
        String sql = "DELETE FROM Billet WHERE id_client = ? AND id_evenement = ? AND id_horaire = ?";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idClient);
            pstmt.setInt(2, idEvenement);
            pstmt.setInt(3, idHoraire);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du billet : " + ex.getMessage());
            return false;
        }
    }

    /**
     * RÃ©cupÃ¨re tous les billets d'un client
     */
    public static List<BilletModel> getByClient(Integer idClient) {
        String sql = "SELECT * FROM Billet WHERE id_client = ? ORDER BY dateAchat DESC";
        List<BilletModel> billets = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idClient);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                BilletModel billet = new BilletModel();
                billet.setIdClient(rs.getInt("id_client"));
                billet.setIdEvenement(rs.getInt("id_evenement"));
                billet.setIdHoraire(rs.getInt("id_horaire"));
                billet.setDateAchat(rs.getTimestamp("dateAchat"));
                billet.setPrix(rs.getDouble("prix"));

                billets.add(billet);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la rÃ©cupÃ©ration des billets du client : " + ex.getMessage());
        }

        return billets;
    }

    /**
     * RÃ©cupÃ¨re tous les billets pour un Ã©vÃ©nement
     */
    public static List<BilletModel> getByEvenement(Integer idEvenement) {
        String sql = "SELECT * FROM Billet WHERE id_evenement = ? ORDER BY dateAchat DESC";
        List<BilletModel> billets = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEvenement);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                BilletModel billet = new BilletModel();
                billet.setIdClient(rs.getInt("id_client"));
                billet.setIdEvenement(rs.getInt("id_evenement"));
                billet.setIdHoraire(rs.getInt("id_horaire"));
                billet.setDateAchat(rs.getTimestamp("dateAchat"));
                billet.setPrix(rs.getDouble("prix"));

                billets.add(billet);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la rÃ©cupÃ©ration des billets de l'Ã©vÃ©nement : " + ex.getMessage());
        }

        return billets;
    }

    /**
     * RÃ©cupÃ¨re tous les billets pour un horaire spÃ©cifique
     */
    public static List<BilletModel> getByHoraire(Integer idHoraire) {
        String sql = "SELECT * FROM Billet WHERE id_horaire = ? ORDER BY dateAchat DESC";
        List<BilletModel> billets = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idHoraire);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                BilletModel billet = new BilletModel();
                billet.setIdClient(rs.getInt("id_client"));
                billet.setIdEvenement(rs.getInt("id_evenement"));
                billet.setIdHoraire(rs.getInt("id_horaire"));
                billet.setDateAchat(rs.getTimestamp("dateAchat"));
                billet.setPrix(rs.getDouble("prix"));

                billets.add(billet);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la rÃ©cupÃ©ration des billets de l'horaire : " + ex.getMessage());
        }

        return billets;
    }

    /**
     * Compte le nombre de billets vendus pour un Ã©vÃ©nement et un horaire
     */
    public static int countByEvenementHoraire(Integer idEvenement, Integer idHoraire) {
        String sql = "SELECT COUNT(*) as total FROM Billet WHERE id_evenement = ? AND id_horaire = ?";
        int count = 0;

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEvenement);
            pstmt.setInt(2, idHoraire);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("total");
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors du comptage des billets : " + ex.getMessage());
        }

        return count;
    }

    /**
     * RÃ©cupÃ¨re les billets avec dÃ©tails (JOIN avec Client et Evenement)
     */
    public static List<String> getBilletsWithDetails() {
        String sql = "SELECT b.*, c.nom as nom_client, e.nomEvenement " +
                "FROM Billet b " +
                "JOIN Client c ON b.id_client = c.id_client " +
                "JOIN Evenement e ON b.id_evenement = e.id_evenement " +
                "ORDER BY b.dateAchat DESC";
        List<String> details = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String detail = String.format("Client: %s | Ã‰vÃ©nement: %s | Date achat: %s | Prix: %.2fâ‚¬",
                        rs.getString("nom_client"),
                        rs.getString("nomEvenement"),
                        rs.getTimestamp("dateAchat"),
                        rs.getDouble("prix")
                );
                details.add(detail);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la rÃ©cupÃ©ration des dÃ©tails : " + ex.getMessage());
        }

        return details;
    }

    /**
     * RÃ©cupÃ¨re les billets d'aujourd'hui avec dÃ©tails (nom client, nom Ã©vÃ©nement)
     * âœ… TODO #1 : ModifiÃ© pour retourner TOUS les IDs (idClient, idEvenement, idHoraire)
     */
    public static List<BilletModel.BilletDetail> getBilletsAujourdhui() {
        String sql = "SELECT b.id_client, b.id_evenement, b.id_horaire, " +
                "c.nom as nom_client, e.nomEvenement, b.prix, b.dateAchat " +
                "FROM Billet b " +
                "JOIN Client c ON b.id_client = c.id_client " +
                "JOIN Evenement e ON b.id_evenement = e.id_evenement " +
                "WHERE DATE(b.dateAchat) = CURDATE() " +
                "ORDER BY b.dateAchat DESC";
        List<BilletModel.BilletDetail> billets = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int idCounter = 1; // Simple counter for display ID
            while (rs.next()) {
                Integer idClient = rs.getInt("id_client");           // â† AJOUTÃ‰
                Integer idEvenement = rs.getInt("id_evenement");     // â† AJOUTÃ‰
                Integer idHoraire = rs.getInt("id_horaire");         // â† AJOUTÃ‰
                String nomClient = rs.getString("nom_client");
                String nomEvenement = rs.getString("nomEvenement");
                Double prix = rs.getDouble("prix");
                Timestamp dateAchat = rs.getTimestamp("dateAchat");
                String heure = new java.text.SimpleDateFormat("HH:mm:ss").format(dateAchat);

                BilletModel.BilletDetail billet = new BilletModel.BilletDetail(
                        idCounter++,
                        idClient,      // â† AJOUTÃ‰
                        idEvenement,   // â† AJOUTÃ‰
                        idHoraire,     // â† AJOUTÃ‰
                        nomClient,
                        nomEvenement,
                        prix,
                        heure
                );
                billets.add(billet);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la rÃ©cupÃ©ration des billets d'aujourd'hui : " + ex.getMessage());
        }

        return billets;
    }
}