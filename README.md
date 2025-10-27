# 🎟️ Projet de billetterie – Application client lourd

## 📚 Contexte

Ce projet est réalisé dans le cadre de mon **BTS SIO (Services Informatiques aux Organisations)**, option **SLAM (Solutions Logicielles et Applications Métiers)**.  
Il s’agit d’une application de billetterie permettant de gérer des événements, des réservations et la vente de billets.

⸻

## 💻 Type d’application

Ce projet est un **client lourd**, c’est-à-dire une application installée directement sur le poste de l’utilisateur.  
Contrairement à une application web (client léger), un client lourd exécute la majorité des traitements en local, ce qui permet d’améliorer les performances et de fonctionner même sans connexion Internet.


⸻

## 🧠 Objectifs du projet

- Concevoir et modéliser la structure de l’application (**diagrammes UML, MCD/MLD**).  
- Créer la **base de données** nécessaire à la gestion des événements et des billets.  
- Développer l’**interface et la logique applicative en Java**.  
- Mettre en place une **gestion des utilisateurs et des réservations**.  
- Assurer la **cohérence des données** entre l’application et la base.

⸻

## 🛠️ Technologies utilisées

- **Langage :** Java (JDK 11+)  
- **Base de données :** MySQL / MariaDB (8.0+)  
- **Outils :** IntelliJ IDEA / VS Code / DBeaver  
- **Modélisation :** UML (cas d’utilisation, classes, MCD/MLD)  
- **Scripts d’automatisation :** Bash (4.0+)

⸻

## ⚙️ Compilation et exécution

Le script `build.sh` compile et exécute automatiquement l'application en une seule commande :

```bash
# Rendre le script exécutable (première utilisation uniquement)
chmod +x build.sh

# Compiler et lancer l'application
./build.sh

```
⸻

## 🔧 Ce que fait le script
	•	Crée automatiquement le dossier src/bin/ s’il n’existe pas.
	•	Compile tous les fichiers .java du dossier src/.
	•	Inclut le driver MySQL dans le classpath.
	•	Génère les fichiers .class dans le dossier src/bin/.
	•	Si la compilation réussit, lance automatiquement l’application.
	•	Affiche des messages de confirmation à chaque étape.

⸻

## 🚀 Objectif final

Offrir un logiciel complet et ergonomique de billetterie, permettant la gestion des événements, des billets et des réservations clients, tout en mettant en pratique les compétences acquises en conception et développement d’applications.
