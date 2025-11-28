package DAO;

import java.sql.Time;

/**
 * Modèle représentant un Horaire
 * ✅ VERSION AMÉLIORÉE pour affichage dans ListView
 */
public class HoraireModel {
    private Integer idHoraire;
    private Time heureDebut;
    private Time heureFin;

    // Constructeur vide
    public HoraireModel() {}

    // Constructeur avec tous les paramètres
    public HoraireModel(Integer idHoraire, Time heureDebut, Time heureFin) {
        this.idHoraire = idHoraire;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    // Getters et Setters
    public Integer getIdHoraire() {
        return idHoraire;
    }

    public void setIdHoraire(Integer idHoraire) {
        this.idHoraire = idHoraire;
    }

    public Time getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Time getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }

    /**
     * Calcule la durée en minutes
     */
    public int getDureeMinutes() {
        if (heureDebut != null && heureFin != null) {
            long diff = heureFin.getTime() - heureDebut.getTime();
            return (int) (diff / (1000 * 60));
        }
        return 0;
    }

    /**
     * Retourne une représentation formatée pour affichage
     * Utilisé par ListView et ComboBox
     */
    @Override
    public String toString() {
        if (heureDebut != null && heureFin != null) {
            return String.format("%s - %s (%d min)",
                    heureDebut.toString().substring(0, 5),  // HH:mm
                    heureFin.toString().substring(0, 5),    // HH:mm
                    getDureeMinutes()
            );
        }
        return "Horaire non défini";
    }

    /**
     * Pour comparaison
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HoraireModel that = (HoraireModel) obj;
        return idHoraire != null && idHoraire.equals(that.idHoraire);
    }

    @Override
    public int hashCode() {
        return idHoraire != null ? idHoraire.hashCode() : 0;
    }
}