# 🎟️ Système de Billetterie - JavaFX

Application de gestion de billetterie développée en JavaFX avec MySQL, permettant la gestion complète des clients, événements et billets avec une interface d'administration professionnelle.

![Java](https://img.shields.io/badge/Java-17+-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Status](https://img.shields.io/badge/Status-Production-green)

---

## 📋 Table des matières

- [Fonctionnalités](#-fonctionnalités)
- [Architecture](#-architecture)
- [Installation](#-installation)
- [Difficultés rencontrées](#-difficultés-rencontrées-et-solutions)
- [Améliorations apportées](#-améliorations-apportées)
- [Technologies](#-technologies-utilisées)
- [Structure du projet](#-structure-du-projet)

---

## ✨ Fonctionnalités

### 👥 Gestion des Clients
- ✅ CRUD complet (Création, Lecture, Modification, Suppression)
- ✅ Séparation Nom/Prénom dans l'interface
- ✅ Recherche par nom
- ✅ Validation des champs (email, téléphone)
- ✅ Gestion des adresses et historique d'achat

### 📅 Gestion des Événements (Interface Avancée)
- ✅ **Multi-horaires** : Ajout/suppression de plusieurs horaires par événement
- ✅ **Multi-salles** : Sélection de plusieurs salles via ComboBox
- ✅ Tables de liaison N-N (Evenement_Horaire, Evenement_Salle)
- ✅ Affichage intelligent dans le tableau :
  - 1 horaire : "20:00 - 22:00"
  - Plusieurs : "3 horaires"
- ✅ Validation complète des données
- ✅ Recherche par nom d'événement

### 🎫 Gestion des Billets
- ✅ Vente de billets avec clé composite (id_client, id_evenement, id_horaire)
- ✅ Modification du prix
- ✅ Suppression de billets
- ✅ Affichage des billets du jour avec détails (nom client, événement, prix)
- ✅ ScrollPane pour interface fluide

### 🎨 Interface Utilisateur
- ✅ Navigation fluide entre toutes les pages
- ✅ Barre de navigation cohérente (Clients | Événements | Billets | Déconnexion)
- ✅ Style CSS unifié (couleur violette #667eea)
- ✅ Bouton actif surligné avec border blanc
- ✅ Messages de confirmation et d'erreur modaux
- ✅ Plein écran automatique

---

## 🏗️ Architecture

### Schéma de l'architecture MVC

```
┌─────────────────────────────────────────────────────────────────┐
│                        APPLICATION                              │
│                         (App.java)                              │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                          VIEWS (FXML)                           │
├─────────────────────────────────────────────────────────────────┤
│  SelectionTab.fxml  │  ClientTab.fxml  │  EvenementTab.fxml    │
│        │                    │                    │               │
│  BilletTab.fxml            │                    │               │
└────────────────────────────┼────────────────────┼───────────────┘
                             │                    │
                             ▼                    ▼
┌─────────────────────────────────────────────────────────────────┐
│                      CONTROLLERS                                │
├─────────────────────────────────────────────────────────────────┤
│  SelectionTabController  │  ClientTabController                 │
│  EvenementTabController  │  BilletTabController                 │
│                                                                  │
│  + Session.java (Gestion session utilisateur)                   │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                       MODEL + DAO                               │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ClientModel.java    ←→    ClientDAO.java                       │
│  EvenementModel.java ←→    EvenementDAO.java                    │
│  BilletModel.java    ←→    BilletDAO.java                       │
│  HoraireModel.java   ←→    HoraireDAO.java                      │
│  SalleModel.java     ←→    SalleDAO.java                        │
│                                                                  │
│  Pattern DAO :                                                   │
│  • Model = Attributs + Getters/Setters                          │
│  • DAO = Méthodes CRUD (getAll, getById, insert, update, etc.)  │
│                                                                  │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                      MYSQL DATABASE                             │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  Tables principales :                                            │
│  • Client (id_client, nom, email, telephone, adresse...)        │
│  • Evenement (id_evenement, nomEvenement, date, prix...)        │
│  • Horaire (id_horaire, heureDebut, heureFin)                   │
│  • Salle (id_salle, nomSalle, id_complexe)                      │
│  • Billet (id_client, id_evenement, id_horaire, prix...)        │
│                                                                  │
│  Tables de liaison N-N :                                         │
│  • Evenement_Horaire (id_evenement, id_horaire)                 │
│  • Evenement_Salle (id_evenement, id_salle)                     │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
```

### Flux de données

```
┌──────────────┐
│    USER      │
│   (Admin)    │
└──────┬───────┘
       │ Clic bouton
       ▼
┌──────────────────────┐
│   FXML View          │
│   (EvenementTab)     │
└──────┬───────────────┘
       │ Event
       ▼
┌──────────────────────────────┐
│   Controller                 │
│   (EvenementTabController)   │
│   • Validation               │
│   • Logique métier           │
└──────┬───────────────────────┘
       │ Appel méthode
       ▼
┌──────────────────────────┐
│   DAO Layer              │
│   (EvenementDAO)         │
│   • Requêtes SQL         │
│   • Connexion BDD        │
└──────┬───────────────────┘
       │ SQL Query
       ▼
┌──────────────────────────┐
│   MySQL Database         │
│   • Evenement            │
│   • Evenement_Horaire    │
│   • Evenement_Salle      │
└──────┬───────────────────┘
       │ ResultSet
       ▼
┌──────────────────────────┐
│   Model                  │
│   (EvenementModel)       │
│   • Liste horaires       │
│   • Liste salles         │
└──────┬───────────────────┘
       │ ObservableList
       ▼
┌──────────────────────────┐
│   TableView/ListView     │
│   • Affichage données    │
└──────────────────────────┘
```

---

## 🚀 Installation

### Prérequis

- **Java 17+** (JDK)
- **JavaFX SDK 21**
- **MySQL 8.0+**
- **IntelliJ IDEA** (recommandé) ou Eclipse
- **Maven** (optionnel)

### Étapes d'installation

1. **Configurer la connexion MySQL**

Modifiez `src/DAO/MySQLConnection.java` :
```java
private static final String URL = "jdbc:mysql://localhost:.../nom_de_bdd";
private static final String USER = "votre_username";
private static final String PASSWORD = "votre_password";
```

2. **Ajouter JavaFX au projet**

Dans IntelliJ IDEA :
- File → Project Structure → Libraries
- Add → Java → Sélectionner le dossier `javafx-sdk-21/lib`
- Apply

3. **Configurer VM Options**

Run → Edit Configurations → Add VM options :
```
--module-path /chemin/vers/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml
```

4. **Lancer l'application**
```
Depuis IntelliJ : Run → Run 'App'

Ou depuis le terminal :
```bash
# Utiliser le script de compilation fourni
./build.sh

# Ou manuellement
javac -d bin BilletterieJavaFX/src/App.java
java -cp "bin:lib/*" --module-path /chemin/vers/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml App
```

---

## 🐛 Difficultés rencontrées et solutions

### 1. **Plein écran non préservé lors de la navigation**

**Problème** : Lors du changement de page, la fenêtre perdait le mode plein écran.

**Cause** : Utilisation de `new Scene()` qui créait une nouvelle scène à chaque navigation.

**Solution** :
```java
// ❌ AVANT
Scene scene = new Scene(root);
stage.setScene(scene);

// ✅ APRÈS
Scene currentScene = stage.getScene();
currentScene.setRoot(root);
```

---

### 2. **Alertes modales non affichées correctement**

**Problème** : Les boîtes de dialogue `Alert` apparaissaient derrière la fenêtre principale.

**Cause** : Absence de `initOwner()`.

**Solution** :
```java
// ❌ AVANT
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.showAndWait();

// ✅ APRÈS
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.initOwner(getStage());  // Définir la fenêtre parente
alert.showAndWait();
```

---

### 3. **Confusion sur la structure de la base de données**

**Problème** : Tentative d'ajout de colonnes `heureDebut`, `heureFin`, `lieu` directement dans la table `Evenement`.

**Cause** : Méconnaissance de la structure réelle qui utilisait des tables de liaison N-N.

**Solution** : 
- Utilisation des tables de liaison existantes :
  - `Evenement_Horaire` (id_evenement, id_horaire)
  - `Evenement_Salle` (id_evenement, id_salle)
- Création de méthodes DAO spécifiques :
  - `EvenementDAO.addHoraire()`
  - `EvenementDAO.addSalle()`
  - `HoraireDAO.getByEvenement()`
  - `SalleDAO.getByEvenement()`

---

### 4. **Erreurs de compilation après migration vers pattern DAO**

**Problème** : `HoraireModel.getAll()` et `HoraireModel.getByEvenement()` introuvables.

**Cause** : Migration du pattern Model-CRUD vers pattern Model/DAO - les méthodes statiques ont été déplacées de `HoraireModel` vers `HoraireDAO`.

**Solution** :
```java
// ❌ AVANT (Model-CRUD)
List<HoraireModel> horaires = HoraireModel.getAll();

// ✅ APRÈS (Pattern DAO)
List<HoraireModel> horaires = HoraireDAO.getAll();
```

Fichiers corrigés : `Main.java`, `EvenementDAO.java`

---

### 5. **Navigation cassée dans ClientTabController**

**Problème** : L'application plantait lors du clic sur les boutons de navigation.

**Cause** : Les méthodes de navigation (`btnNavEvenements()`, `btnNavBillets()`, `btnDeconnexion()`) étaient absentes du contrôleur.

**Solution** : Ajout des méthodes manquantes avec import des classes nécessaires :
```java
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

@FXML
private void btnNavEvenements(ActionEvent event) {
    naviguerVers("/Tabs/EvenementTab.fxml", "Gestion des Événements", event);
}

private void naviguerVers(String cheminFxml, String titre, ActionEvent event) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(cheminFxml));
    Parent root = loader.load();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("🎟️ Admin - " + titre);
    stage.show();
}
```

---

### 6. **ComboBox des salles affichait `Salle{id=1, nom='Salle A'}`**

**Problème** : La ComboBox n'affichait pas correctement les noms des salles, mais leur représentation `toString()` par défaut.

**Cause** : Méthode `toString()` manquante ou incorrecte dans `SalleModel`.

**Solution** :
```java
// SalleModel.java
@Override
public String toString() {
    return nomSalle;  // Retourne seulement le nom
}

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
```

---

### 7. **ScrollPane trop court dans BilletTab**

**Problème** : Le formulaire était coupé et nécessitait un scroll vertical inconfortable.

**Cause** : `prefHeight="300"` sur le ScrollPane.

**Solution** :
```xml
<!-- ❌ AVANT -->
<ScrollPane fitToWidth="true" prefHeight="300.0">

<!-- ✅ APRÈS -->
<ScrollPane fitToWidth="true" prefHeight="400.0">
```

---

### 8. **Interface Événements sans navigation**

**Problème** : Impossible de revenir aux autres pages depuis l'interface Événements.

**Cause** : Le FXML initial ne contenait pas de barre de navigation.

**Solution** : Création d'un nouveau FXML avec :
- Structure `BorderPane` identique aux autres interfaces
- Barre de navigation en `<top>`
- Formulaire en `<center>` avec `ScrollPane`
- Tableau en `<bottom>`
- Réutilisation des classes CSS existantes (`form-row`, `button-bar`, etc.)

---

## 🎯 Améliorations apportées

### Architecture et Code

1. **Pattern DAO complet**
   - Séparation claire Model (données) / DAO (accès BDD)
   - Réutilisabilité du code
   - Maintenance facilitée

2. **Gestion des relations N-N**
   - Tables de liaison `Evenement_Horaire` et `Evenement_Salle`
   - Méthodes DAO spécialisées (`addHoraire()`, `clearHoraires()`)
   - Support multi-horaires et multi-salles par événement

3. **Méthodes `insertAndGetId()`**
   - Utilisation de `Statement.RETURN_GENERATED_KEYS`
   - Récupération de l'ID après insertion
   - Essentiel pour les tables de liaison

4. **Clé composite pour Billets**
   - `(id_client, id_evenement, id_horaire)`
   - Méthode `updatePrix()` pour modification simplifiée
   - Méthode `delete()` avec clé composite

### Interface utilisateur

5. **Navigation cohérente**
   - Barre de navigation sur toutes les pages
   - Méthode `naviguerVers()` réutilisable
   - Gestion de la session (Admin/Client)

6. **Style CSS unifié**
   - Couleur principale : `#667eea` (violet)
   - Classes réutilisables (`form-row`, `button-bar`, `search-container`)
   - Bouton actif surligné avec border blanc

7. **Validation des données**
   - Vérification des champs obligatoires
   - Format HH:MM pour les horaires
   - Validation email (@)
   - Messages d'erreur clairs

8. **Affichage intelligent**
   - Méthodes `getHorairesFormatted()` et `getSallesFormatted()`
   - Affichage adaptatif : "20:00-22:00" ou "3 horaires"
   - `toString()` optimisé pour ListView et ComboBox

### Gestion de billets

9. **Interface Billets améliorée**
   - ScrollPane 400px pour confort
   - Séparation Nom/Prénom
   - Tableau centré avec `CONSTRAINED_RESIZE_POLICY`
   - Affichage des billets du jour avec JOIN

10. **CRUD Billets complet**
    - Création avec clé composite
    - Modification du prix uniquement (`updatePrix()`)
    - Suppression avec confirmation modale
---

## 🛠️ Technologies utilisées

- **Java 17** - Langage de programmation
- **JavaFX 21** - Framework d'interface graphique
- **MySQL 8.0** - Base de données relationnelle
- **JDBC** - Connecteur Java-MySQL
- **CSS** - Stylisation de l'interface
- **FXML** - Déclaration des interfaces
- **Git** - Gestion de version

---

## 📁 Structure du projet

```
BilletterieBD/
│
├── BilletterieJavaFX/
│   └── src/
│       ├── App.java                      					# Point d'entrée
│       │
│       ├── Controllers/
│       │   ├── ClientTabController.java  					# Contrôleur Clients
│       │   ├── EvenementTabController.java 				# Contrôleur Événements
│       │   ├── BilletTabController.java  					# Contrôleur Billets
│       │   ├── SelectionTabController.java 				# Écran sélection
│       │   └── Session.java              					# Gestion session
│       │
│       ├── Tabs/
│       │   ├── SelectionTab.fxml         					# Interface sélection
│       │   ├── ClientTab.fxml            					# Interface clients
│       │   ├── EvenementTab.fxml         					# Interface événements
│       │   └── BilletTab.fxml            					# Interface billets
│       │
│       └── css/
│           └── style.css                 					# Styles CSS
│
├── src/
│   ├── DAO/
│   │   ├── MySQLConnection.java          					# Connexion BDD
│   │   ├── DatabaseConfig.java           					# Configuration BDD
│   │   │
│   │   ├── ClientModel.java              					# Model Client
│   │   ├── ClientDAO.java                					# DAO Client
│   │   │
│   │   ├── EvenementModel.java           					# Model Événement
│   │   ├── EvenementDAO.java             					# DAO Événement
│   │   │
│   │   ├── HoraireModel.java             					# Model Horaire
│   │   ├── HoraireDAO.java               					# DAO Horaire
│   │   │
│   │   ├── SalleModel.java               					# Model Salle
│   │   ├── SalleDAO.java                 					# DAO Salle
│   │   │
│   │   ├── BilletModel.java              					# Model Billet
│   │   ├── BilletDAO.java                					# DAO Billet
│   │   │
│   │   ├── Model.java                    					# Interface Model
│   │   └── Main.java                     					# Tests DAO
│   │
│   └── resources/
│       └── config.properties             					# Configuration
│
├── data/
│   ├── billetterie_db.sql                # Structure BDD
│   └── data.sql                          # Données de test
│
├── lib/
│   └── mysql-connector-j-9.4.0.jar       # Driver MySQL
│
├── build.sh                              # Script de compilation
├── README.md                             # Ce fichier
├── CHANGELOG.md                          # Historique versions
└── .gitignore                            # Fichiers ignorés
```

---

## 🔮 Améliorations futures

- [ ] Espace client avec authentification
- [ ] Panier d'achat et paiement en ligne
- [ ] Export des billets en PDF
- [ ] Statistiques et tableaux de bord
- [ ] Envoi de billets par email
- [ ] Gestion des places disponibles par salle
- [ ] Système de réservation temporaire
- [ ] Multi-langue (FR/EN)
- [ ] Mode sombre
- [ ] API REST pour intégration externe

---

**⭐ Si ce projet vous a été utile, n'hésitez pas à lui donner une étoile !**
