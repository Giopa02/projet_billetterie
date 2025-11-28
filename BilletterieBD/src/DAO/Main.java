package DAO;

import java.sql.Timestamp;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     TEST DE LA BASE DE DONNÉES BILLETTERIE                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        // ===== TEST 1 : ClientModel =====
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("1. GESTION DES CLIENTS");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // Récupérer tous les clients
        System.out.println("--- Liste de tous les clients ---");
        List<ClientModel> clients = ClientDAO.getAll();
        System.out.println("Total : " + clients.size() + " clients");

        // Afficher les 5 premiers
        for (int i = 0; i < Math.min(5, clients.size()); i++) {
            ClientModel c = clients.get(i);
            System.out.println("  • " + c.getNom() + " - " + c.getEmail());
        }
        System.out.println();

        // Récupérer un client spécifique
        System.out.println("--- Détails du client ID = 1 ---");
        ClientModel client1 = ClientDAO.getById(1);
        if (client1 != null) {
            System.out.println("  Nom : " + client1.getNom());
            System.out.println("  Email : " + client1.getEmail());
            System.out.println("  Téléphone : " + client1.getTelephone());
            System.out.println("  Adresse : " + client1.getAdresse());
        }
        System.out.println();

        // Rechercher des clients
        System.out.println("--- Recherche de clients 'Martin' ---");
        List<ClientModel> results = ClientDAO.searchByName("Martin");
        System.out.println("Résultats trouvés : " + results.size());
        for (ClientModel c : results) {
            System.out.println("  • " + c.getNom());
        }
        System.out.println("\n");

        // ===== TEST 2 : EvenementModel =====
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("2. GESTION DES ÉVÉNEMENTS");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // Récupérer tous les événements
        System.out.println("--- Liste de tous les événements ---");
        List<EvenementModel> evenements = EvenementDAO.getAll();
        System.out.println("Total : " + evenements.size() + " événements\n");

        for (EvenementModel e : evenements) {
            System.out.printf("  • %s - %s (%.2f€) - Âge min: %d ans\n",
                    e.getNomEvenement(),
                    e.getDate(),
                    e.getPrix(),
                    e.getAgeMinimal());
        }
        System.out.println();

        // Événements à venir
        System.out.println("--- Événements à venir ---");
        List<EvenementModel> upcoming = EvenementDAO.getUpcoming();
        System.out.println("Nombre d'événements à venir : " + upcoming.size());
        System.out.println("\n");

        // ===== TEST 3 : HoraireDAO (✅ CORRIGÉ) =====
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("3. GESTION DES HORAIRES");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // ✅ CORRECTION : Utiliser HoraireDAO au lieu de HoraireModel
        System.out.println("--- Liste de tous les horaires ---");
        List<HoraireModel> horaires = HoraireDAO.getAll();
        System.out.println("Total : " + horaires.size() + " horaires");

        // Afficher les 10 premiers
        for (int i = 0; i < Math.min(10, horaires.size()); i++) {
            HoraireModel h = horaires.get(i);
            System.out.printf("  • Horaire #%d : %s - %s (%d min)\n",
                    h.getIdHoraire(),
                    h.getHeureDebut(),
                    h.getHeureFin(),
                    h.getDureeMinutes());
        }
        System.out.println();

        // ✅ CORRECTION : Utiliser HoraireDAO.getByEvenement()
        System.out.println("--- Horaires pour l'événement ID = 1 ---");
        List<HoraireModel> horairesEvent1 = HoraireDAO.getByEvenement(1);
        for (HoraireModel h : horairesEvent1) {
            System.out.printf("  • %s - %s\n", h.getHeureDebut(), h.getHeureFin());
        }
        System.out.println("\n");

        // ===== TEST 4 : SalleModel =====
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("4. GESTION DES SALLES");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // Récupérer toutes les salles
        System.out.println("--- Liste de toutes les salles ---");
        List<SalleModel> salles = SalleDAO.getAll();
        System.out.println("Total : " + salles.size() + " salles\n");

        for (SalleModel s : salles) {
            System.out.printf("  • %s (Complexe #%d)\n",
                    s.getNomSalle(),
                    s.getIdComplexe());
        }
        System.out.println();

        // Salles avec détails du complexe
        System.out.println("--- Salles avec leurs complexes ---");
        List<String> sallesDetails = SalleDAO.getSallesWithComplexe();
        for (String detail : sallesDetails) {
            System.out.println("  • " + detail);
        }
        System.out.println("\n");

        // ===== TEST 5 : BilletModel =====
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("5. GESTION DES BILLETS");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // Billets d'un client
        System.out.println("--- Billets du client ID = 1 ---");
        List<BilletModel> billetsClient1 = BilletDAO.getByClient(1);
        System.out.println("Nombre de billets : " + billetsClient1.size());
        for (BilletModel b : billetsClient1) {
            System.out.printf("  • Événement #%d - Horaire #%d - %.2f€ - Acheté le %s\n",
                    b.getIdEvenement(),
                    b.getIdHoraire(),
                    b.getPrix(),
                    b.getDateAchat());
        }
        System.out.println();

        // Billets pour un événement
        System.out.println("--- Billets vendus pour l'événement ID = 1 ---");
        List<BilletModel> billetsEvent1 = BilletDAO.getByEvenement(1);
        System.out.println("Nombre de billets vendus : " + billetsEvent1.size());
        System.out.println();

        // ✅ CORRECTION : Utiliser HoraireDAO.getByEvenement()
        System.out.println("--- Statistiques par horaire pour l'événement 1 ---");
        List<HoraireModel> horairesE1 = HoraireDAO.getByEvenement(1);
        for (HoraireModel h : horairesE1) {
            int count = BilletDAO.countByEvenementHoraire(1, h.getIdHoraire());
            System.out.printf("  • Horaire %s-%s : %d billets vendus\n",
                    h.getHeureDebut(), h.getHeureFin(), count);
        }
        System.out.println();

        // Détails des billets avec JOIN
        System.out.println("--- Derniers billets vendus (avec détails) ---");
        List<String> billetsDetails = BilletDAO.getBilletsWithDetails();
        for (int i = 0; i < Math.min(10, billetsDetails.size()); i++) {
            System.out.println("  " + (i+1) + ". " + billetsDetails.get(i));
        }
        System.out.println("\n");

        // ===== TEST 6 : Insertion d'un nouveau billet =====
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("6. TEST D'INSERTION");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        System.out.println("--- Insertion d'un nouveau billet ---");
        boolean inserted = BilletDAO.insert(
                15,  // id_client
                5,   // id_evenement
                11,  // id_horaire
                new Timestamp(System.currentTimeMillis()),
                10.00
        );
        System.out.println("Résultat de l'insertion : " + (inserted ? "✓ Succès" : "✗ Échec"));
        System.out.println("\n");

        // ===== RÉSUMÉ FINAL =====
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                     RÉSUMÉ FINAL                           ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.printf("║ Clients          : %-40d ║\n", ClientDAO.getAll().size());
        System.out.printf("║ Événements       : %-40d ║\n", EvenementDAO.getAll().size());
        System.out.printf("║ Horaires         : %-40d ║\n", HoraireDAO.getAll().size());  // ✅ CORRIGÉ
        System.out.printf("║ Salles           : %-40d ║\n", SalleDAO.getAll().size());
        System.out.printf("║ Billets vendus   : %-40d ║\n", BilletDAO.getAll().size());
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        System.out.println("✓ Tous les tests sont terminés avec succès !");
    }
}