package DAO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Modèle représentant un Événement
 * ✅ VERSION ADAPTÉE avec listes de horaires et salles
 */
public class EvenementModel {
    // Attributs de base
    private Integer idEvenement;
    private String nomEvenement;
    private String affiche;
    private String description;
    private Date date;
    private Integer ageMinimal;
    private Integer duree;
    private Double prix;

    // ✅ Relations N-N
    private List<HoraireModel> horaires;
    private List<SalleModel> salles;

    // Constructeur vide
    public EvenementModel() {
        this.horaires = new ArrayList<>();
        this.salles = new ArrayList<>();
    }

    // Constructeur avec paramètres de base
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
        this.horaires = new ArrayList<>();
        this.salles = new ArrayList<>();
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

    // ✅ Getters/Setters pour les relations

    public List<HoraireModel> getHoraires() {
        return horaires;
    }

    public void setHoraires(List<HoraireModel> horaires) {
        this.horaires = horaires;
    }

    public List<SalleModel> getSalles() {
        return salles;
    }

    public void setSalles(List<SalleModel> salles) {
        this.salles = salles;
    }

    // ===== MÉTHODES UTILITAIRES =====

    /**
     * Retourne une chaîne formatée des horaires (pour affichage dans TableView)
     */
    public String getHorairesFormatted() {
        if (horaires == null || horaires.isEmpty()) {
            return "-";
        }

        if (horaires.size() == 1) {
            HoraireModel h = horaires.get(0);
            return h.getHeureDebut() + " - " + h.getHeureFin();
        }

        // Plusieurs horaires : afficher le nombre
        return horaires.size() + " horaires";
    }

    /**
     * Retourne une chaîne formatée des salles (pour affichage dans TableView)
     */
    public String getSallesFormatted() {
        if (salles == null || salles.isEmpty()) {
            return "-";
        }

        if (salles.size() == 1) {
            return salles.get(0).getNomSalle();
        }

        // Plusieurs salles : afficher les noms séparés par des virgules
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < salles.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(salles.get(i).getNomSalle());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + idEvenement +
                ", nom='" + nomEvenement + '\'' +
                ", date=" + date +
                ", prix=" + prix + "€" +
                ", horaires=" + (horaires != null ? horaires.size() : 0) +
                ", salles=" + (salles != null ? salles.size() : 0) +
                '}';
    }
}