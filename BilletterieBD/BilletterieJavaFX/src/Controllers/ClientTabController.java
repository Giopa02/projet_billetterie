package Controllers;

import DAO.ClientModel;
import DAO.ClientDAO;
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
import java.util.List;
import java.util.Objects;

/**
 * Contrôleur pour la gestion des clients
 */
public class ClientTabController {

    @FXML private TextField tfNom;
    @FXML private TextField tfPrenom;
    @FXML private TextField tfEmail;
    @FXML private TextField tfTelephone;
    @FXML private TextField tfAdresse;
    @FXML private TextField tfRecherche;

    @FXML private TableView<ClientModel> tableClients;
    @FXML private TableColumn<ClientModel, Integer> colId;
    @FXML private TableColumn<ClientModel, String> colNom;
    @FXML private TableColumn<ClientModel, String> colPrenom;
    @FXML private TableColumn<ClientModel, String> colEmail;
    @FXML private TableColumn<ClientModel, String> colTelephone;

    private ObservableList<ClientModel> clientsList = FXCollections.observableArrayList();
    private ClientModel clientSelectionne = null;

    @FXML
    public void initialize() {
        System.out.println("✓ ClientController initialisé");

        colId.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(cellData -> {
            return new javafx.beans.property.SimpleStringProperty("");
        });
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        chargerClients();

        tableClients.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        afficherClientSelectionne(newSelection);
                    }
                }
        );
    }

    @FXML
    private void btnAjouter(ActionEvent event) {
        System.out.println("→ Ajout d'un client...");

        if (!validerChamps()) {
            return;
        }

        String nom = tfNom.getText().trim();
        String prenom = tfPrenom.getText().trim();
        if (!prenom.isEmpty()) {
            nom = nom + " " + prenom;
        }
        String email = tfEmail.getText().trim();
        String telephone = tfTelephone.getText().trim();
        String adresse = tfAdresse.getText().trim();

        Date dateNaissance = null;
        String histoireAchat = null;

        boolean succes = ClientDAO.insert(nom, email, adresse, dateNaissance, telephone, histoireAchat);

        if (succes) {
            afficherSucces("Client ajouté avec succès !");
            viderChamps();
            chargerClients();
        } else {
            afficherErreur("Erreur lors de l'ajout du client");
        }
    }

    @FXML
    private void btnModifier(ActionEvent event) {
        System.out.println("→ Modification d'un client...");

        if (clientSelectionne == null) {
            afficherErreur("Veuillez sélectionner un client dans le tableau");
            return;
        }

        if (!validerChamps()) {
            return;
        }

        String nom = tfNom.getText().trim();
        String prenom = tfPrenom.getText().trim();
        if (!prenom.isEmpty()) {
            nom = nom + " " + prenom;
        }
        String email = tfEmail.getText().trim();
        String telephone = tfTelephone.getText().trim();
        String adresse = tfAdresse.getText().trim();

        Date dateNaissance = null;
        String histoireAchat = null;

        boolean succes = ClientDAO.update(
                clientSelectionne.getIdClient(),
                nom, email, adresse, dateNaissance, telephone, histoireAchat
        );

        if (succes) {
            afficherSucces("Client modifié avec succès !");
            viderChamps();
            chargerClients();
            clientSelectionne = null;
        } else {
            afficherErreur("Erreur lors de la modification");
        }
    }

    @FXML
    private void btnSupprimer(ActionEvent event) {
        System.out.println("→ Suppression d'un client...");

        if (clientSelectionne == null) {
            afficherErreur("Veuillez sélectionner un client à supprimer");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.initOwner(getStage());
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Supprimer le client ?");
        confirmation.setContentText(
                "Voulez-vous vraiment supprimer " +
                        clientSelectionne.getNom() + " ?"
        );

        if (confirmation.showAndWait().get() != ButtonType.OK) {
            return;
        }

        boolean succes = ClientDAO.delete(clientSelectionne.getIdClient());

        if (succes) {
            afficherSucces("Client supprimé avec succès !");
            viderChamps();
            chargerClients();
            clientSelectionne = null;
        } else {
            afficherErreur("Erreur lors de la suppression");
        }
    }

    @FXML
    private void btnRechercher(ActionEvent event) {
        String recherche = tfRecherche.getText().trim();

        if (recherche.isEmpty()) {
            chargerClients();
            return;
        }

        System.out.println("→ Recherche : " + recherche);

        List<ClientModel> resultats = ClientDAO.searchByName(recherche);

        clientsList.clear();
        clientsList.addAll(resultats);
        tableClients.setItems(clientsList);

        System.out.println("✓ " + resultats.size() + " résultat(s)");
    }

    // ===== NAVIGATION =====

    /**
     * Naviguer vers Gestion Événements
     */
    @FXML
    private void btnNavEvenements(ActionEvent event) {
        naviguerVers("/Tabs/EvenementTab.fxml", "Gestion des Événements", event);
    }

    /**
     * Naviguer vers Vente de Billets
     */
    @FXML
    private void btnNavBillets(ActionEvent event) {
        naviguerVers("/Tabs/BilletTab.fxml", "Vente de Billets", event);
    }

    /**
     * Déconnexion (retour écran sélection)
     */
    @FXML
    private void btnDeconnexion(ActionEvent event) {
        Controllers.Session.deconnecter();
        naviguerVers("/Tabs/SelectionTab.fxml", "Système de Billetterie", event);
    }

    /**
     * Méthode utilitaire pour naviguer
     */
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

    private void chargerClients() {
        System.out.println("→ Chargement des clients...");

        List<ClientModel> clients = ClientDAO.getAll();

        clientsList.clear();
        clientsList.addAll(clients);
        tableClients.setItems(clientsList);

        System.out.println("✓ " + clients.size() + " client(s) chargé(s)");
    }

    private void afficherClientSelectionne(ClientModel client) {
        clientSelectionne = client;

        String nomComplet = client.getNom() != null ? client.getNom() : "";
        String[] parties = nomComplet.split(" ", 2);
        tfNom.setText(parties[0]);
        tfPrenom.setText(parties.length > 1 ? parties[1] : "");
        tfEmail.setText(client.getEmail() != null ? client.getEmail() : "");
        tfTelephone.setText(client.getTelephone() != null ? client.getTelephone() : "");
        tfAdresse.setText(client.getAdresse() != null ? client.getAdresse() : "");

        System.out.println("✓ Client sélectionné : " + client.getNom());
    }

    private boolean validerChamps() {
        if (tfNom.getText().trim().isEmpty()) {
            afficherErreur("Le nom est obligatoire");
            return false;
        }

        if (tfPrenom.getText().trim().isEmpty()) {
            afficherErreur("Le prénom est obligatoire");
            return false;
        }

        if (tfEmail.getText().trim().isEmpty()) {
            afficherErreur("L'email est obligatoire");
            return false;
        }

        if (!tfEmail.getText().contains("@")) {
            afficherErreur("L'email doit contenir un @");
            return false;
        }

        return true;
    }

    private void viderChamps() {
        tfNom.clear();
        tfPrenom.clear();
        tfEmail.clear();
        tfTelephone.clear();
        tfAdresse.clear();
        tfRecherche.clear();
        clientSelectionne = null;
        tableClients.getSelectionModel().clearSelection();
    }

    private Stage getStage() {
        return (Stage) tableClients.getScene().getWindow();
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