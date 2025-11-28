package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * Contrôleur pour l'écran de sélection (Client ou Admin)
 */
public class SelectionTabController {

    /**
     * Bouton Espace Client
     */
    @FXML
    private void btnEspaceClient(ActionEvent event) {
        System.out.println("→ Accès Espace Client demandé");

        // Pour l'instant, afficher un message
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        info.initOwner(stage);
        info.setTitle("Espace Client");
        info.setHeaderText("Fonctionnalité en développement");
        info.setContentText(
                "L'espace client comprendra :\n\n" +
                        "• Connexion / Création de compte\n" +
                        "• Catalogue des événements\n" +
                        "• Panier d'achat\n" +
                        "• Historique de mes billets\n\n" +
                        "Cette partie sera implémentée dans la prochaine version."
        );
        info.showAndWait();
    }

    /**
     * Bouton Espace Admin
     * CHARGE DIRECTEMENT L'INTERFACE CLIENTS
     */
    @FXML
    private void btnEspaceAdmin(ActionEvent event) {
        System.out.println("→ Accès Espace Administration");

        try {
            // Marquer la session comme Admin
            Session.connecterAdmin();

            // Charger directement l'interface Gestion Clients
            Parent root = FXMLLoader.load(
                    getClass().getResource("/Tabs/ClientTab.fxml")
            );

            // Obtenir la fenêtre actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene currentScene = stage.getScene();

            // Changer uniquement le root de la scène existante
            // Cela préserve le plein écran automatiquement
            currentScene.setRoot(root);
            stage.setTitle("🎟️ Administration - Gestion des Clients");

            System.out.println("✓ Interface Gestion Clients chargée");

        } catch (Exception e) {
            System.err.println("❌ Erreur chargement interface");
            e.printStackTrace();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Alert erreur = new Alert(Alert.AlertType.ERROR);
            erreur.initOwner(stage);
            erreur.setTitle("Erreur");
            erreur.setHeaderText("Impossible de charger l'interface");
            erreur.setContentText(e.getMessage());
            erreur.showAndWait();
        }
    }
}