import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BilletModel {
    // Attributs correspondant aux colonnes de la table Billet
    private Integer idClient;
    private Integer idEvenement;
    private Integer idHoraire;
    private Timestamp dateAchat;
    private Double prix;

    // Constructeur vide
    public BilletModel() {}

    // Constructeur avec tous les paramètres
    public BilletModel(Integer idClient, Integer idEvenement, Integer idHoraire, 
                    Timestamp dateAchat, Double prix) {
        this.idClient = idClient;
        this.idEvenement = idEvenement;
        this.idHoraire = idHoraire;
        this.dateAchat = dateAchat;
        this.prix = prix;
    }

    // ===== MÉTHODES CRUD =====

    /**
     * Récupère tous les billets
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
            System.err.println("Erreur lors de la récupération des billets : " + ex.getMessage());
        }

        return billets;
    }

    /**
     * Récupère un billet spécifique par sa clé primaire composite
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
            System.err.println("Erreur lors de la récupération du billet : " + ex.getMessage());
        }

        return billet;
    }

    /**
     * Insère un nouveau billet
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
     * Met à jour un billet existant
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
            System.err.println("Erreur lors de la mise à jour du billet : " + ex.getMessage());
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
     * Récupère tous les billets d'un client
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
            System.err.println("Erreur lors de la récupération des billets du client : " + ex.getMessage());
        }

        return billets;
    }

    /**
     * Récupère tous les billets pour un événement
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
            System.err.println("Erreur lors de la récupération des billets de l'événement : " + ex.getMessage());
        }

        return billets;
    }

    /**
     * Récupère tous les billets pour un horaire spécifique
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
            System.err.println("Erreur lors de la récupération des billets de l'horaire : " + ex.getMessage());
        }

        return billets;
    }

    /**
     * Compte le nombre de billets vendus pour un événement et un horaire
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
     * Récupère les billets avec détails (JOIN avec Client et Evenement)
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
                String detail = String.format("Client: %s | Événement: %s | Date achat: %s | Prix: %.2f€",
                    rs.getString("nom_client"),
                    rs.getString("nomEvenement"),
                    rs.getTimestamp("dateAchat"),
                    rs.getDouble("prix")
                );
                details.add(detail);
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des détails : " + ex.getMessage());
        }

        return details;
    }

    // ===== GETTERS ET SETTERS =====

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Integer getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(Integer idEvenement) {
        this.idEvenement = idEvenement;
    }

    public Integer getIdHoraire() {
        return idHoraire;
    }

    public void setIdHoraire(Integer idHoraire) {
        this.idHoraire = idHoraire;
    }

    public Timestamp getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(Timestamp dateAchat) {
        this.dateAchat = dateAchat;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Billet{" +
                "client=" + idClient +
                ", événement=" + idEvenement +
                ", horaire=" + idHoraire +
                ", dateAchat=" + dateAchat +
                ", prix=" + prix + "€" +
                '}';
    }
}