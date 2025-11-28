package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientModel {
    // Attributs correspondant aux colonnes de la table Client
    private Integer idClient;
    private String nom;
    private String email;
    private String adresse;
    private Date dateNaissance;
    private String telephone;
    private String histoireAchat;

    // Constructeur vide
    public ClientModel() {}

    // Constructeur avec tous les paramètres
    public ClientModel(Integer idClient, String nom, String email, String adresse, 
                    Date dateNaissance, String telephone, String histoireAchat) {
        this.idClient = idClient;
        this.nom = nom;
        this.email = email;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.histoireAchat = histoireAchat;
    }

    // ===== MÉTHODES CRUD =====

    /**
     * Récupère tous les clients
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
            System.err.println("Erreur lors de la récupération des clients : " + ex.getMessage());
        }

        return clients;
    }

    /**
     * Récupère un client par son ID
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
            System.err.println("Erreur lors de la récupération du client : " + ex.getMessage());
        }

        return client;
    }

    /**
     * Insère un nouveau client
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
     * Met à jour un client existant
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
            System.err.println("Erreur lors de la mise à jour du client : " + ex.getMessage());
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

    // ===== GETTERS ET SETTERS =====

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getHistoireAchat() {
        return histoireAchat;
    }

    public void setHistoireAchat(String histoireAchat) {
        this.histoireAchat = histoireAchat;
    }

    // Méthode toString pour afficher facilement un client
    @Override
    public String toString() {
        return "Client{" +
                "id=" + idClient +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }

    public String getPrenom() {
        return "";
    }
}