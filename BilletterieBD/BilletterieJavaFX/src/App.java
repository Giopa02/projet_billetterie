import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Point d'entrée de l'application JavaFX
 * Lance l'écran de sélection (Client ou Admin)
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger l'écran de sélection au démarrage
            Parent root = FXMLLoader.load(getClass().getResource("/Tabs/SelectionTab.fxml"));
            Scene scene = new Scene(root);

            // Configuration de la fenêtre
            primaryStage.setTitle("🎟️ Système de Billetterie");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.show();

            System.out.println("✓ Application lancée - Écran de sélection");

        } catch (Exception e) {
            System.err.println("❌ Erreur lors du chargement de l'interface");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
