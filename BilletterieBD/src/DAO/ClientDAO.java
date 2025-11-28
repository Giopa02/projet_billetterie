package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object pour la table Client
 * Contient toutes les mÃ©thodes d'accÃ¨s Ã  la base de donnÃ©es
 */
public class ClientDAO {

    /**
     * RÃ©cupÃ¨re tous les clients
     */
    public static List<ClientModel> getAll() {
        String sql = "SELECT * FROM Client";
        List<ClientModel> clients = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ClientModel client = new ClientModel();
                client.setIdClient(rs.getInt("id_client"));
                client.setNom(rs.getString("nom"));
                client.setEmail(rs.getString("email"));
                client.setAdresse(rs.getString("adresse"));
                client.setDateNaissance(rs.getDate("dateNaissance"));
                client.setTelephone(rs.getString("telephone"));
                client.setHistoireAchat(rs.getString("histoireAchat"));

                clients.add(client);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la rÃ©cupÃ©ration des clients : " + ex.getMessage());
        }

        return clients;
    }

    /**
     * RÃ©cupÃ¨re un client par son ID
     */
    public static ClientModel getById(Integer id) {
        String sql = "SELECT * FROM Client WHERE id_client = ?";
        ClientModel client = null;

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                client = new ClientModel();
                client.setIdClient(rs.getInt("id_client"));
                client.setNom(rs.getString("nom"));
                client.setEmail(rs.getString("email"));
                client.setAdresse(rs.getString("adresse"));
                client.setDateNaissance(rs.getDate("dateNaissance"));
                client.setTelephone(rs.getString("telephone"));
                client.setHistoireAchat(rs.getString("histoireAchat"));
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la rÃ©cupÃ©ration du client : " + ex.getMessage());
        }

        return client;
    }

    /**
     * InsÃ¨re un nouveau client
     */
    public static boolean insert(String nom, String email, String adresse,
                                 Date dateNaissance, String telephone, String histoireAchat) {
        String sql = "INSERT INTO Client (nom, email, adresse, dateNaissance, telephone, histoireAchat) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, email);
            pstmt.setString(3, adresse);
            pstmt.setDate(4, dateNaissance);
            pstmt.setString(5, telephone);
            pstmt.setString(6, histoireAchat);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'insertion du client : " + ex.getMessage());
            return false;
        }
    }

    /**
     * Met Ã  jour un client existant
     */
    public static boolean update(Integer id, String nom, String email, String adresse,
                                 Date dateNaissance, String telephone, String histoireAchat) {
        String sql = "UPDATE Client SET nom = ?, email = ?, adresse = ?, " +
                "dateNaissance = ?, telephone = ?, histoireAchat = ? " +
                "WHERE id_client = ?";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, email);
            pstmt.setString(3, adresse);
            pstmt.setDate(4, dateNaissance);
            pstmt.setString(5, telephone);
            pstmt.setString(6, histoireAchat);
            pstmt.setInt(7, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise Ã  jour du client : " + ex.getMessage());
            return false;
        }
    }

    /**
     * Supprime un client par son ID
     */
    public static boolean delete(Integer id) {
        String sql = "DELETE FROM Client WHERE id_client = ?";

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du client : " + ex.getMessage());
            return false;
        }
    }

    /**
     * Recherche des clients par nom
     */
    public static List<ClientModel> searchByName(String nom) {
        String sql = "SELECT * FROM Client WHERE nom LIKE ?";
        List<ClientModel> clients = new ArrayList<>();

        try (Connection conn = MySQLConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nom + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ClientModel client = new ClientModel();
                client.setIdClient(rs.getInt("id_client"));
                client.setNom(rs.getString("nom"));
                client.setEmail(rs.getString("email"));
                client.setAdresse(rs.getString("adresse"));
                client.setDateNaissance(rs.getDate("dateNaissance"));
                client.setTelephone(rs.getString("telephone"));
                client.setHistoireAchat(rs.getString("histoireAchat"));

                clients.add(client);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la recherche : " + ex.getMessage());
        }

        return clients;
    }
}
