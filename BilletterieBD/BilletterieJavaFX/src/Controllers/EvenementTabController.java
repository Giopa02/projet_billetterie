package Controllers;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * Contrôleur pour la gestion des événements
 * ✅ VERSION CORRIGÉE - Affichage des horaires et salles dans ListView
 */
public class EvenementTabController {

    // ===== COMPOSANTS FXML =====
    @FXML private TextField tfNomEvenement;
    @FXML private DatePicker dpDate;
    @FXML private TextField tfPrix;
    @FXML private TextField tfAgeMinimal;
    @FXML private TextArea taDescription;
    @FXML private TextField tfRecherche;

    // Horaires
    @FXML private TextField tfHeureDebut;
    @FXML private TextField tfHeureFin;
    @FXML private ListView<HoraireModel> lvHoraires;
    @FXML private Button btnAjouterHoraire;
    @FXML private Button btnSupprimerHoraire;

    // Salles
    @FXML private ComboBox<SalleModel> cbSalleDisponible;
    @FXML private ListView<SalleModel> lvSalles;
    @FXML private Button btnAjouterSalle;
    @FXML private Button btnSupprimerSalle;

    // Boutons principaux
    @FXML private Button btnCreer;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private Button btnVider;

    // Tableau
    @FXML private TableView<EvenementModel> tableEvenements;
    @FXML private TableColumn<EvenementModel, Integer> colId;
    @FXML private TableColumn<EvenementModel, String> colNom;
    @FXML private TableColumn<EvenementModel, Date> colDate;
    @FXML private TableColumn<EvenementModel, String> colHoraires;
    @FXML private TableColumn<EvenementModel, String> colSalles;
    @FXML private TableColumn<EvenementModel, Double> colPrix;
    @FXML private TableColumn<EvenementModel, Integer> colAge;

    private ObservableList<EvenementModel> evenementsList = FXCollections.observableArrayList();
    private ObservableList<HoraireModel> horairesTemp = FXCollections.observableArrayList();
    private ObservableList<SalleModel> sallesTemp = FXCollections.observableArrayList();
    private EvenementModel evenementSelectionne = null;

    // ===== INITIALISATION =====
    @FXML
    public void initialize() {
        System.out.println("✓ EvenementController initialisé (Version corrigée affichage)");

        // Configurer les colonnes du tableau
        colId.setCellValueFactory(new PropertyValueFactory<>("idEvenement"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomEvenement"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("ageMinimal"));

        // Colonnes avec formatage personnalisé
        colHoraires.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getHorairesFormatted())
        );
        colSalles.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSallesFormatted())
        );

        // Lier les ListView aux listes temporaires
        lvHoraires.setItems(horairesTemp);
        lvSalles.setItems(sallesTemp);

        // ✅ CORRECTION : Configurer l'affichage des horaires dans la ListView
        lvHoraires.setCellFactory(param -> new ListCell<HoraireModel>() {
            @Override
            protected void updateItem(HoraireModel horaire, boolean empty) {
                super.updateItem(horaire, empty);
                if (empty || horaire == null) {
                    setText(null);
                } else {
                    // Afficher l'horaire au format "HH:MM - HH:MM"
                    setText(horaire.getHeureDebut() + " - " + horaire.getHeureFin());
                }
            }
        });

        // ✅ CORRECTION : Configurer l'affichage des salles dans la ListView
        lvSalles.setCellFactory(param -> new ListCell<SalleModel>() {
            @Override
            protected void updateItem(SalleModel salle, boolean empty) {
                super.updateItem(salle, empty);
                if (empty || salle == null) {
                    setText(null);
                } else {
                    setText(salle.getNomSalle());
                }
            }
        });

        // Charger les salles disponibles dans la ComboBox
        chargerSallesDisponibles();

        // Charger les événements
        chargerEvenements();

        // Écouter la sélection dans le tableau
        tableEvenements.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        afficherEvenementSelectionne(newSelection);
                    }
                }
        );
    }

    // ===== GESTION DES HORAIRES =====

    @FXML
    void ajouterHoraire(ActionEvent event) {
        String debut = tfHeureDebut.getText().trim();
        String fin = tfHeureFin.getText().trim();

        if (debut.isEmpty() || fin.isEmpty()) {
            afficherErreur("Veuillez remplir les deux heures");
            return;
        }

        // Valider le format HH:MM
        if (!debut.matches("\\d{2}:\\d{2}") || !fin.matches("\\d{2}:\\d{2}")) {
            afficherErreur("Format invalide. Utilisez HH:MM (ex: 20:00)");
            return;
        }

        try {
            LocalTime heureDebut = LocalTime.parse(debut);
            LocalTime heureFin = LocalTime.parse(fin);

            if (heureFin.isBefore(heureDebut)) {
                afficherErreur("L'heure de fin doit être après l'heure de début");
                return;
            }

            // Créer un HoraireModel temporaire (sans ID pour l'instant)
            HoraireModel horaire = new HoraireModel();
            horaire.setHeureDebut(Time.valueOf(heureDebut));
            horaire.setHeureFin(Time.valueOf(heureFin));

            horairesTemp.add(horaire);
            tfHeureDebut.clear();
            tfHeureFin.clear();

            System.out.println("✓ Horaire ajouté : " + horaire.getHeureDebut() + " - " + horaire.getHeureFin());

        } catch (Exception e) {
            afficherErreur("Erreur lors de l'ajout de l'horaire : " + e.getMessage());
        }
    }

    @FXML
    void supprimerHoraire(ActionEvent event) {
        HoraireModel selected = lvHoraires.getSelectionModel().getSelectedItem();
        if (selected != null) {
            horairesTemp.remove(selected);
            System.out.println("✓ Horaire supprimé");
        } else {
            afficherErreur("Veuillez sélectionner un horaire à supprimer");
        }
    }

    // ===== GESTION DES SALLES =====

    @FXML
    void ajouterSalle(ActionEvent event) {
        SalleModel salle = cbSalleDisponible.getValue();
        if (salle == null) {
            afficherErreur("Veuillez sélectionner une salle");
            return;
        }

        // Vérifier si la salle n'est pas déjà ajoutée
        if (sallesTemp.contains(salle)) {
            afficherErreur("Cette salle est déjà ajoutée");
            return;
        }

        sallesTemp.add(salle);
        System.out.println("✓ Salle ajoutée : " + salle.getNomSalle());
    }

    @FXML
    void supprimerSalle(ActionEvent event) {
        SalleModel selected = lvSalles.getSelectionModel().getSelectedItem();
        if (selected != null) {
            sallesTemp.remove(selected);
            System.out.println("✓ Salle supprimée");
        } else {
            afficherErreur("Veuillez sélectionner une salle à supprimer");
        }
    }

    // ===== CRUD ÉVÉNEMENTS =====

    @FXML
    void btnCreer(ActionEvent event) {
        System.out.println("→ Création d'un événement...");

        if (!validerChamps()) {
            return;
        }

        // 1. Insérer l'événement et récupérer son ID
        String nom = tfNomEvenement.getText().trim();
        LocalDate localDate = dpDate.getValue();
        Date sqlDate = Date.valueOf(localDate);
        String description = taDescription.getText().trim();
        Double prix = Double.parseDouble(tfPrix.getText().trim());
        Integer ageMinimal = Integer.parseInt(tfAgeMinimal.getText().trim());

        Integer idEvenement = EvenementDAO.insertAndGetId(nom, null, description, sqlDate, ageMinimal, 0, prix);

        if (idEvenement == null) {
            afficherErreur("Erreur lors de la création de l'événement");
            return;
        }

        System.out.println("✓ Événement créé avec ID : " + idEvenement);

        // 2. Insérer les horaires et les lier
        for (HoraireModel horaire : horairesTemp) {
            Integer idHoraire = HoraireDAO.insertAndGetId(horaire.getHeureDebut(), horaire.getHeureFin());
            if (idHoraire != null) {
                EvenementDAO.addHoraire(idEvenement, idHoraire);
                System.out.println("✓ Horaire " + idHoraire + " lié");
            }
        }

        // 3. Lier les salles
        for (SalleModel salle : sallesTemp) {
            EvenementDAO.addSalle(idEvenement, salle.getIdSalle());
            System.out.println("✓ Salle " + salle.getNomSalle() + " liée");
        }

        afficherSucces("Événement créé avec succès !");
        viderChamps();
        chargerEvenements();
    }

    @FXML
    void btnModifier(ActionEvent event) {
        System.out.println("→ Modification d'un événement...");

        if (evenementSelectionne == null) {
            afficherErreur("Veuillez sélectionner un événement");
            return;
        }

        if (!validerChamps()) {
            return;
        }

        // 1. Mettre à jour l'événement
        String nom = tfNomEvenement.getText().trim();
        LocalDate localDate = dpDate.getValue();
        Date sqlDate = Date.valueOf(localDate);
        String description = taDescription.getText().trim();
        Double prix = Double.parseDouble(tfPrix.getText().trim());
        Integer ageMinimal = Integer.parseInt(tfAgeMinimal.getText().trim());

        boolean succes = EvenementDAO.update(
                evenementSelectionne.getIdEvenement(),
                nom, null, description, sqlDate, ageMinimal, 0, prix
        );

        if (!succes) {
            afficherErreur("Erreur lors de la modification");
            return;
        }

        Integer idEvenement = evenementSelectionne.getIdEvenement();

        // 2. Supprimer les anciennes liaisons
        EvenementDAO.clearHoraires(idEvenement);
        EvenementDAO.clearSalles(idEvenement);

        // 3. Ajouter les nouveaux horaires
        for (HoraireModel horaire : horairesTemp) {
            Integer idHoraire = HoraireDAO.insertAndGetId(horaire.getHeureDebut(), horaire.getHeureFin());
            if (idHoraire != null) {
                EvenementDAO.addHoraire(idEvenement, idHoraire);
            }
        }

        // 4. Ajouter les nouvelles salles
        for (SalleModel salle : sallesTemp) {
            EvenementDAO.addSalle(idEvenement, salle.getIdSalle());
        }

        afficherSucces("Événement modifié !");
        viderChamps();
        chargerEvenements();
        evenementSelectionne = null;
    }

    @FXML
    void btnSupprimer(ActionEvent event) {
        if (evenementSelectionne == null) {
            afficherErreur("Veuillez sélectionner un événement");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.initOwner(getStage());
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Supprimer cet événement ?");
        confirmation.setContentText(evenementSelectionne.getNomEvenement());

        if (confirmation.showAndWait().get() != ButtonType.OK) {
            return;
        }

        boolean succes = EvenementDAO.delete(evenementSelectionne.getIdEvenement());

        if (succes) {
            afficherSucces("Événement supprimé !");
            viderChamps();
            chargerEvenements();
            evenementSelectionne = null;
        } else {
            afficherErreur("Erreur lors de la suppression");
        }
    }

    @FXML
    void btnVider(ActionEvent event) {
        viderChamps();
    }

    @FXML
    void rechercher() {
        String terme = tfRecherche.getText().trim();
        if (terme.isEmpty()) {
            chargerEvenements();
        } else {
            List<EvenementModel> resultats = EvenementDAO.searchByName(terme);
            evenementsList.clear();
            evenementsList.addAll(resultats);
            tableEvenements.setItems(evenementsList);
        }
    }

    // ===== NAVIGATION =====

    @FXML
    private void btnNavClients(ActionEvent event) {
        naviguerVers("/Tabs/ClientTab.fxml", "Gestion des Clients", event);
    }

    @FXML
    private void btnNavBillets(ActionEvent event) {
        naviguerVers("/Tabs/BilletTab.fxml", "Vente de Billets", event);
    }

    @FXML
    private void btnDeconnexion(ActionEvent event) {
        Controllers.Session.deconnecter();
        naviguerVers("/Tabs/SelectionTab.fxml", "Système de Billetterie", event);
    }

    private void naviguerVers(String cheminFxml, String titre, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(cheminFxml)));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene currentScene = stage.getScene();

            // Changer uniquement le root de la scène existante
            // Cela préserve le plein écran automatiquement
            currentScene.setRoot(root);
            stage.setTitle("🎟️ Admin - " + titre);

            System.out.println("✓ Navigation vers : " + titre);

        } catch (Exception e) {
            System.err.println("❌ Erreur navigation");
            e.printStackTrace();
        }
    }

    // ===== MÉTHODES UTILITAIRES =====

    private void chargerEvenements() {
        List<EvenementModel> evenements = EvenementDAO.getAll();
        evenementsList.clear();
        evenementsList.addAll(evenements);
        tableEvenements.setItems(evenementsList);
        System.out.println("✓ " + evenements.size() + " événement(s) chargé(s)");
    }

    private void chargerSallesDisponibles() {
        try {
            List<SalleModel> salles = SalleDAO.getAll();
            cbSalleDisponible.setItems(FXCollections.observableArrayList(salles));
            System.out.println("✓ " + salles.size() + " salle(s) disponible(s)");
        } catch (Exception e) {
            System.err.println("Erreur chargement salles : " + e.getMessage());
        }
    }

    private void afficherEvenementSelectionne(EvenementModel evenement) {
        evenementSelectionne = evenement;

        tfNomEvenement.setText(evenement.getNomEvenement());
        dpDate.setValue(evenement.getDate().toLocalDate());
        tfPrix.setText(String.valueOf(evenement.getPrix()));
        tfAgeMinimal.setText(String.valueOf(evenement.getAgeMinimal()));
        taDescription.setText(evenement.getDescription() != null ? evenement.getDescription() : "");

        // Charger les horaires
        horairesTemp.clear();
        if (evenement.getHoraires() != null) {
            horairesTemp.addAll(evenement.getHoraires());
        }

        // Charger les salles
        sallesTemp.clear();
        if (evenement.getSalles() != null) {
            sallesTemp.addAll(evenement.getSalles());
        }

        System.out.println("✓ Événement sélectionné : " + evenement.getNomEvenement());
    }

    private boolean validerChamps() {
        if (tfNomEvenement.getText().trim().isEmpty()) {
            afficherErreur("Le nom est obligatoire");
            return false;
        }

        if (dpDate.getValue() == null) {
            afficherErreur("La date est obligatoire");
            return false;
        }

        if (tfPrix.getText().trim().isEmpty()) {
            afficherErreur("Le prix est obligatoire");
            return false;
        }

        try {
            Double.parseDouble(tfPrix.getText().trim());
        } catch (NumberFormatException e) {
            afficherErreur("Le prix doit être un nombre valide");
            return false;
        }

        if (tfAgeMinimal.getText().trim().isEmpty()) {
            afficherErreur("L'âge minimal est obligatoire");
            return false;
        }

        try {
            Integer.parseInt(tfAgeMinimal.getText().trim());
        } catch (NumberFormatException e) {
            afficherErreur("L'âge minimal doit être un nombre entier");
            return false;
        }

        if (horairesTemp.isEmpty()) {
            afficherErreur("Veuillez ajouter au moins un horaire");
            return false;
        }

        if (sallesTemp.isEmpty()) {
            afficherErreur("Veuillez ajouter au moins une salle");
            return false;
        }

        return true;
    }

    private void viderChamps() {
        tfNomEvenement.clear();
        dpDate.setValue(null);
        tfPrix.clear();
        tfAgeMinimal.clear();
        taDescription.clear();
        tfHeureDebut.clear();
        tfHeureFin.clear();
        horairesTemp.clear();
        sallesTemp.clear();
        evenementSelectionne = null;
        tableEvenements.getSelectionModel().clearSelection();
    }

    private Stage getStage() {
        return (Stage) tableEvenements.getScene().getWindow();
    }

    private void afficherSucces(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(getStage());
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(getStage());
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
