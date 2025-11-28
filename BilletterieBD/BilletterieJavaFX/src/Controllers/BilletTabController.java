package Controllers;

import DAO.ClientModel;
import DAO.ClientDAO;
import DAO.EvenementModel;
import DAO.EvenementDAO;
import DAO.BilletModel;
import DAO.BilletDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Contrôleur pour la vente de billets
 */
public class BilletTabController {

    // ===== ÉTAPE 1 : CLIENT =====
    @FXML private TextField tfRechercheClient;
    @FXML private VBox vboxClients;
    @FXML private Label lblClientSelectionne;

    // ===== ÉTAPE 2 : ÉVÉNEMENT =====
    @FXML private TextField tfRechercheEvenement;
    @FXML private VBox vboxEvenements;
    @FXML private Label lblEvenementSelectionne;

    // ===== ÉTAPE 3 : CONFIRMATION =====
    @FXML private Label lblRecapClient;
    @FXML private Label lblRecapEvenement;
    @FXML private Label lblRecapDate;
    @FXML private Label lblRecapHoraire;
    @FXML private Label lblRecapPrix;
    @FXML private ComboBox<Integer> cbNombreBillets;
    @FXML private Label lblTotal;

    // ===== HISTORIQUE =====
    @FXML private TableView<BilletModel.BilletDetail> tableHistorique;
    @FXML private TableColumn<BilletModel.BilletDetail, Integer> colHistoId;
    @FXML private TableColumn<BilletModel.BilletDetail, String> colHistoClient;
    @FXML private TableColumn<BilletModel.BilletDetail, String> colHistoEvenement;
    @FXML private TableColumn<BilletModel.BilletDetail, Double> colHistoPrix;
    @FXML private TableColumn<BilletModel.BilletDetail, String> colHistoHeure;
    @FXML private TableColumn<BilletModel.BilletDetail, Void> colHistoActions;

    // Variables de sélection
    private ToggleGroup clientsGroup = new ToggleGroup();
    private ToggleGroup evenementsGroup = new ToggleGroup();
    private ClientModel clientSelectionne = null;
    private EvenementModel evenementSelectionne = null;
    private BilletModel.BilletDetail billetSelectionne = null;

    // ===== INITIALISATION =====
    @FXML
    public void initialize() {
        System.out.println("✓ BilletController initialisé");

        // Remplir le ComboBox du nombre de billets
        cbNombreBillets.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        cbNombreBillets.setValue(1);

        // Écouter les changements de nombre
        cbNombreBillets.valueProperty().addListener((obs, oldVal, newVal) -> {
            calculerTotal();
        });

        // Configurer les colonnes du tableau
        colHistoId.setCellValueFactory(new PropertyValueFactory<>("idBillet"));
        colHistoClient.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        colHistoEvenement.setCellValueFactory(new PropertyValueFactory<>("nomEvenement"));
        colHistoPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colHistoHeure.setCellValueFactory(new PropertyValueFactory<>("heure"));

        // Ajouter les boutons Modifier/Supprimer dans chaque ligne
        ajouterBoutonsActions();

        // Charger l'historique
        chargerHistorique();

        // Écouter la sélection dans le tableau
        tableHistorique.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        billetSelectionne = newSelection;
                        System.out.println("✓ Billet sélectionné : " + newSelection.getNomClient());
                    }
                }
        );

        // Charger tous les clients et événements au démarrage
        chargerClients();
        chargerEvenements();
    }

    // ===== ÉTAPE 1 : MÉTHODES CLIENT =====

    @FXML
    private void btnRechercherClient(ActionEvent event) {
        String recherche = tfRechercheClient.getText().trim();

        if (recherche.isEmpty()) {
            chargerClients();
            return;
        }

        System.out.println("→ Recherche client : " + recherche);
        List<ClientModel> resultats = ClientDAO.searchByName(recherche);
        afficherClients(resultats);
    }

    private void chargerClients() {
        System.out.println("→ Chargement des clients...");
        List<ClientModel> clients = ClientDAO.getAll();
        afficherClients(clients);
    }

    private void afficherClients(List<ClientModel> clients) {
        vboxClients.getChildren().clear();

        if (clients.isEmpty()) {
            Label aucun = new Label("Aucun client trouvé");
            aucun.setStyle("-fx-text-fill: #6c757d;");
            vboxClients.getChildren().add(aucun);
            return;
        }

        // Afficher maximum 5 clients pour ne pas surcharger
        int max = Math.min(5, clients.size());

        for (int i = 0; i < max; i++) {
            ClientModel client = clients.get(i);

            RadioButton rb = new RadioButton(
                    client.getNom() + " (" + client.getEmail() + ")"
            );
            rb.setToggleGroup(clientsGroup);
            rb.setStyle("-fx-font-size: 13px;");

            // Stocker le client dans les propriétés du RadioButton
            rb.setUserData(client);

            // Écouter la sélection
            rb.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    clientSelectionne = (ClientModel) rb.getUserData();
                    lblClientSelectionne.setText(clientSelectionne.getNom());
                    mettreAJourRecapitulatif();
                }
            });

            vboxClients.getChildren().add(rb);
        }

        if (clients.size() > 5) {
            Label plus = new Label("... et " + (clients.size() - 5) + " autres clients");
            plus.setStyle("-fx-text-fill: #6c757d; -fx-font-style: italic;");
            vboxClients.getChildren().add(plus);
        }

        System.out.println("✓ " + clients.size() + " client(s) trouvé(s)");
    }

    // ===== ÉTAPE 2 : MÉTHODES ÉVÉNEMENT =====

    @FXML
    private void btnRechercherEvenement(ActionEvent event) {
        String recherche = tfRechercheEvenement.getText().trim();

        if (recherche.isEmpty()) {
            chargerEvenements();
            return;
        }

        System.out.println("→ Recherche événement : " + recherche);
        List<EvenementModel> resultats = EvenementDAO.searchByName(recherche);
        afficherEvenements(resultats);
    }

    @FXML
    private void btnTousEvenements(ActionEvent event) {
        tfRechercheEvenement.clear();
        chargerEvenements();
    }

    private void chargerEvenements() {
        System.out.println("→ Chargement des événements...");
        List<EvenementModel> evenements = EvenementDAO.getUpcoming();
        afficherEvenements(evenements);
    }

    private void afficherEvenements(List<EvenementModel> evenements) {
        vboxEvenements.getChildren().clear();

        if (evenements.isEmpty()) {
            Label aucun = new Label("Aucun événement trouvé");
            aucun.setStyle("-fx-text-fill: #6c757d; -fx-font-size: 14px;");
            vboxEvenements.getChildren().add(aucun);
            return;
        }

        for (EvenementModel evenement : evenements) {
            // Créer une "carte" pour chaque événement
            HBox carte = new HBox(15);
            carte.setStyle(
                    "-fx-background-color: white; " +
                            "-fx-padding: 15px; " +
                            "-fx-border-color: #e0e0e0; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 8px; " +
                            "-fx-background-radius: 8px;"
            );

            // Informations de l'événement
            VBox infos = new VBox(5);
            Label nom = new Label("📅 " + evenement.getNomEvenement());
            nom.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Label date = new Label("Date : " + evenement.getDate());
            Label prix = new Label(String.format("Prix : %.2f€", evenement.getPrix()));

            infos.getChildren().addAll(nom, date, prix);

            // RadioButton pour sélectionner
            RadioButton rb = new RadioButton("Sélectionner");
            rb.setToggleGroup(evenementsGroup);
            rb.setUserData(evenement);

            rb.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    evenementSelectionne = (EvenementModel) rb.getUserData();
                    lblEvenementSelectionne.setText(evenementSelectionne.getNomEvenement());
                    mettreAJourRecapitulatif();

                    // Effet visuel sur la carte sélectionnée
                    carte.setStyle(
                            "-fx-background-color: #e3f2fd; " +
                                    "-fx-padding: 15px; " +
                                    "-fx-border-color: #2196F3; " +
                                    "-fx-border-width: 2px; " +
                                    "-fx-border-radius: 8px; " +
                                    "-fx-background-radius: 8px;"
                    );
                }
            });

            carte.getChildren().addAll(infos, rb);
            HBox.setMargin(rb, new Insets(0, 0, 0, 20));

            vboxEvenements.getChildren().add(carte);
        }

        System.out.println("✓ " + evenements.size() + " événement(s) affiché(s)");
    }

    // ===== ÉTAPE 3 : RÉCAPITULATIF ET VALIDATION =====

    private void mettreAJourRecapitulatif() {
        if (clientSelectionne != null) {
            lblRecapClient.setText("• Client : " + clientSelectionne.getNom());
        }

        if (evenementSelectionne != null) {
            lblRecapEvenement.setText("• Événement : " + evenementSelectionne.getNomEvenement());
            lblRecapDate.setText("• Date : " + evenementSelectionne.getDate());
            lblRecapHoraire.setText("• Horaire : À définir");
            lblRecapPrix.setText(String.format("• Prix unitaire : %.2f€", evenementSelectionne.getPrix()));

            calculerTotal();
        }
    }

    private void calculerTotal() {
        if (evenementSelectionne != null && cbNombreBillets.getValue() != null) {
            double prixUnitaire = evenementSelectionne.getPrix();
            int nombre = cbNombreBillets.getValue();
            double total = prixUnitaire * nombre;

            lblTotal.setText(String.format("%.2f€", total));
        }
    }

    @FXML
    private void btnValider(ActionEvent event) {
        System.out.println("→ Validation de la vente...");

        // Vérifications
        if (clientSelectionne == null) {
            afficherErreur("Veuillez sélectionner un client");
            return;
        }

        if (evenementSelectionne == null) {
            afficherErreur("Veuillez sélectionner un événement");
            return;
        }

        // Confirmation
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.initOwner(getStage());
        confirmation.setTitle("Confirmation de vente");
        confirmation.setHeaderText("Confirmer la vente de billet ?");
        confirmation.setContentText(
                "Client : " + clientSelectionne.getNom() + "\n" +
                        "Événement : " + evenementSelectionne.getNomEvenement() + "\n" +
                        "Total : " + lblTotal.getText()
        );

        if (confirmation.showAndWait().get() != ButtonType.OK) {
            return;
        }

        // Insertion dans la base
        int nombre = cbNombreBillets.getValue();
        double prixUnitaire = evenementSelectionne.getPrix();

        for (int i = 0; i < nombre; i++) {
            boolean succes = BilletDAO.insert(
                    clientSelectionne.getIdClient(),
                    evenementSelectionne.getIdEvenement(),
                    1, // TODO: Récupérer le vrai ID d'horaire
                    new Timestamp(System.currentTimeMillis()),
                    prixUnitaire
            );

            if (!succes) {
                afficherErreur("Erreur lors de la création du billet " + (i + 1));
                return;
            }
        }

        afficherSucces("Vente réalisée avec succès !\n" + nombre + " billet(s) créé(s)");
        reinitialiser();
        chargerHistorique();
    }

    @FXML
    private void btnAnnuler(ActionEvent event) {
        reinitialiser();
    }

    private void reinitialiser() {
        // Réinitialiser les sélections
        clientsGroup.selectToggle(null);
        evenementsGroup.selectToggle(null);
        clientSelectionne = null;
        evenementSelectionne = null;

        // Réinitialiser les labels
        lblClientSelectionne.setText("Aucun");
        lblEvenementSelectionne.setText("Aucun");
        lblRecapClient.setText("• Client : -");
        lblRecapEvenement.setText("• Événement : -");
        lblRecapDate.setText("• Date : -");
        lblRecapHoraire.setText("• Horaire : -");
        lblRecapPrix.setText("• Prix unitaire : -");
        lblTotal.setText("0,00€");

        // Réinitialiser les champs
        tfRechercheClient.clear();
        tfRechercheEvenement.clear();
        cbNombreBillets.setValue(1);

        System.out.println("✓ Formulaire réinitialisé");
    }

    // ===== GESTION DE L'HISTORIQUE =====

    /**
     * Ajouter les boutons Modifier/Supprimer dans le tableau
     */
    private void ajouterBoutonsActions() {
        Callback<TableColumn<BilletModel.BilletDetail, Void>, TableCell<BilletModel.BilletDetail, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<BilletModel.BilletDetail, Void> call(final TableColumn<BilletModel.BilletDetail, Void> param) {
                        final TableCell<BilletModel.BilletDetail, Void> cell = new TableCell<>() {

                            private final Button btnModifier = new Button("✏️");
                            private final Button btnSupprimer = new Button("🗑️");

                            {
                                btnModifier.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 3px 8px; -fx-background-radius: 3px;");
                                btnSupprimer.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 10px; -fx-padding: 3px 8px; -fx-background-radius: 3px;");

                                btnModifier.setOnAction((ActionEvent event) -> {
                                    BilletModel.BilletDetail billet = getTableView().getItems().get(getIndex());
                                    modifierBillet(billet);
                                });

                                btnSupprimer.setOnAction((ActionEvent event) -> {
                                    BilletModel.BilletDetail billet = getTableView().getItems().get(getIndex());
                                    supprimerBillet(billet);
                                });
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    HBox boutons = new HBox(5);
                                    boutons.getChildren().addAll(btnModifier, btnSupprimer);
                                    setGraphic(boutons);
                                }
                            }
                        };
                        return cell;
                    }
                };

        colHistoActions.setCellFactory(cellFactory);
    }

    /**
     * Charger l'historique des ventes d'aujourd'hui
     */
    private void chargerHistorique() {
        System.out.println("→ Chargement de l'historique...");

        List<BilletModel.BilletDetail> billets = BilletDAO.getBilletsAujourdhui();

        ObservableList<BilletModel.BilletDetail> billetsList = FXCollections.observableArrayList(billets);
        tableHistorique.setItems(billetsList);

        System.out.println("✓ " + billets.size() + " billet(s) dans l'historique");
    }

    /**
     * Actualiser l'historique
     */
    @FXML
    private void btnActualiserHistorique(ActionEvent event) {
        chargerHistorique();
        afficherSucces("Historique actualisé !");
    }

    /**
     * Modifier un billet
     * ✅ TODO #1 : IMPLÉMENTÉ - Modification du prix dans la BDD
     */
    private void modifierBillet(BilletModel.BilletDetail billet) {
        System.out.println("→ Modification du billet #" + billet.getIdBillet() + " pour " + billet.getNomClient());

        // Créer une boîte de dialogue pour modifier le prix
        TextInputDialog dialog = new TextInputDialog(String.valueOf(billet.getPrix()));
        dialog.initOwner(getStage());
        dialog.setTitle("Modifier le billet");
        dialog.setHeaderText("Modifier le prix du billet");
        dialog.setContentText("Nouveau prix :");

        dialog.showAndWait().ifPresent(nouveauPrixStr -> {
            try {
                double nouveauPrix = Double.parseDouble(nouveauPrixStr);

                if (nouveauPrix <= 0) {
                    afficherErreur("Le prix doit être supérieur à 0");
                    return;
                }

                // ✅ Mise à jour dans la base de données avec la clé composite
                boolean succes = BilletDAO.updatePrix(
                        billet.getIdClient(),
                        billet.getIdEvenement(),
                        billet.getIdHoraire(),
                        nouveauPrix
                );

                if (succes) {
                    afficherSucces("Prix modifié avec succès !\nNouveau prix : " + nouveauPrix + "€");
                    chargerHistorique();
                } else {
                    afficherErreur("Erreur lors de la modification du prix");
                }

            } catch (NumberFormatException e) {
                afficherErreur("Le prix doit être un nombre valide");
            }
        });
    }

    /**
     * Supprimer un billet
     * ✅ TODO #2 : IMPLÉMENTÉ - Suppression dans la BDD avec la clé composite
     */
    private void supprimerBillet(BilletModel.BilletDetail billet) {
        System.out.println("→ Suppression du billet #" + billet.getIdBillet() + " pour " + billet.getNomClient());

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.initOwner(getStage());
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Supprimer ce billet ?");
        confirmation.setContentText(
                "Billet #" + billet.getIdBillet() + "\n" +
                        "Client : " + billet.getNomClient() + "\n" +
                        "Événement : " + billet.getNomEvenement() + "\n" +
                        "Prix : " + billet.getPrix() + "€"
        );

        if (confirmation.showAndWait().get() != ButtonType.OK) {
            return;
        }

        // ✅ Suppression dans la base de données avec la clé composite
        boolean succes = BilletDAO.delete(
                billet.getIdClient(),
                billet.getIdEvenement(),
                billet.getIdHoraire()
        );

        if (succes) {
            afficherSucces("Billet #" + billet.getIdBillet() + " supprimé avec succès !");
            chargerHistorique();
        } else {
            afficherErreur("Erreur lors de la suppression du billet");
        }
    }

    // ===== NAVIGATION =====

    /**
     * Naviguer vers Gestion Clients
     */
    @FXML
    private void btnNavClients(ActionEvent event) {
        naviguerVers("/Tabs/ClientTab.fxml", "Gestion des Clients", event);
    }

    /**
     * Naviguer vers Gestion Événements
     */
    @FXML
    private void btnNavEvenements(ActionEvent event) {
        naviguerVers("/Tabs/EvenementTab.fxml", "Gestion des Événements", event);
    }

    /**
     * Déconnexion (retour écran sélection)
     */
    @FXML
    private void btnDeconnexion(ActionEvent event) {
        Session.deconnecter();
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

    // ===== UTILITAIRES =====

    /**
     * Obtenir la fenêtre (Stage) actuelle
     */
    private Stage getStage() {
        return (Stage) tableHistorique.getScene().getWindow();
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