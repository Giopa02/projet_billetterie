package DAO;

/**
 * Modèle représentant une Salle
 * ✅ VERSION COMPLÈTE avec toString, equals, hashCode
 */
public class SalleModel {
    private Integer idSalle;
    private String nomSalle;
    private Integer idComplexe;

    // Constructeur vide
    public SalleModel() {}

    // Constructeur avec tous les paramètres
    public SalleModel(Integer idSalle, String nomSalle, Integer idComplexe) {
        this.idSalle = idSalle;
        this.nomSalle = nomSalle;
        this.idComplexe = idComplexe;
    }

    // ===== GETTERS ET SETTERS =====

    public Integer getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(Integer idSalle) {
        this.idSalle = idSalle;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public Integer getIdComplexe() {
        return idComplexe;
    }

    public void setIdComplexe(Integer idComplexe) {
        this.idComplexe = idComplexe;
    }

    /**
     * ✅ IMPORTANT : Pour affichage dans ComboBox et ListView
     */
    @Override
    public String toString() {
        return nomSalle;  // Affiche seulement le nom dans les listes
    }

    /**
     * ✅ IMPORTANT : Pour comparaison (éviter doublons dans ListView)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SalleModel that = (SalleModel) obj;
        return idSalle != null && idSalle.equals(that.idSalle);
    }

    @Override
    public int hashCode() {
        return idSalle != null ? idSalle.hashCode() : 0;
    }
}