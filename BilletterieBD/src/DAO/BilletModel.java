package DAO;

import java.sql.Timestamp;

/**
 * ModÃ¨le reprÃ©sentant un Billet
 * Contient uniquement les attributs, constructeurs et getters/setters
 * Les mÃ©thodes d'accÃ¨s Ã  la base de donnÃ©es sont dans BilletDAO
 */
public class BilletModel {
    // Attributs correspondant aux colonnes de la table Billet
    private Integer idClient;
    private Integer idEvenement;
    private Integer idHoraire;
    private Timestamp dateAchat;
    private Double prix;

    // Constructeur vide
    public BilletModel() {}

    // Constructeur avec tous les paramÃ¨tres
    public BilletModel(Integer idClient, Integer idEvenement, Integer idHoraire,
                       Timestamp dateAchat, Double prix) {
        this.idClient = idClient;
        this.idEvenement = idEvenement;
        this.idHoraire = idHoraire;
        this.dateAchat = dateAchat;
        this.prix = prix;
    }

    /**
     * Classe interne pour reprÃ©senter un billet avec dÃ©tails pour l'affichage
     * UtilisÃ©e pour les requÃªtes avec JOIN
     * âœ… TODO #1 : Ajout des IDs pour permettre la modification et suppression
     */
    public static class BilletDetail {
        private Integer idBillet;      // ID d'affichage
        private Integer idClient;      // â† AJOUTÃ‰ pour supprimer
        private Integer idEvenement;   // â† AJOUTÃ‰ pour supprimer
        private Integer idHoraire;     // â† AJOUTÃ‰ pour supprimer
        private String nomClient;
        private String nomEvenement;
        private Double prix;
        private String heure;

        public BilletDetail(Integer idBillet, Integer idClient, Integer idEvenement, Integer idHoraire,
                            String nomClient, String nomEvenement, Double prix, String heure) {
            this.idBillet = idBillet;
            this.idClient = idClient;
            this.idEvenement = idEvenement;
            this.idHoraire = idHoraire;
            this.nomClient = nomClient;
            this.nomEvenement = nomEvenement;
            this.prix = prix;
            this.heure = heure;
        }

        // Getters
        public Integer getIdBillet() { return idBillet; }
        public Integer getIdClient() { return idClient; }         // â† AJOUTÃ‰
        public Integer getIdEvenement() { return idEvenement; }   // â† AJOUTÃ‰
        public Integer getIdHoraire() { return idHoraire; }       // â† AJOUTÃ‰
        public String getNomClient() { return nomClient; }
        public String getNomEvenement() { return nomEvenement; }
        public Double getPrix() { return prix; }
        public String getHeure() { return heure; }
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
                ", Ã©vÃ©nement=" + idEvenement +
                ", horaire=" + idHoraire +
                ", dateAchat=" + dateAchat +
                ", prix=" + prix + "â‚¬" +
                '}';
    }
}