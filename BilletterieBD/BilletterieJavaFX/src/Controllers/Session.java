package Controllers;

import DAO.ClientModel;

/**
 * Classe pour gérer la session utilisateur
 */
public class Session {

    private static ClientModel clientConnecte = null;
    private static boolean isAdmin = false;

    public static void connecterClient(ClientModel client) {
        clientConnecte = client;
        isAdmin = false;
        System.out.println("✓ Client connecté : " + client.getNom());
    }

    public static void connecterAdmin() {
        clientConnecte = null;
        isAdmin = true;
        System.out.println("✓ Session Admin activée");
    }

    public static void deconnecter() {
        clientConnecte = null;
        isAdmin = false;
        System.out.println("✓ Déconnexion réussie");
    }

    public static ClientModel getClientConnecte() {
        return clientConnecte;
    }

    public static boolean isAdmin() {
        return isAdmin;
    }

    public static boolean isClientConnecte() {
        return clientConnecte != null;
    }
}