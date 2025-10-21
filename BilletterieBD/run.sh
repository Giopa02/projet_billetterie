#!/bin/bash

# Script d'exécution pour le projet BilletterieBD
# Ce script exécute le programme principal

echo "🚀 Lancement du programme BilletterieBD..."

# Exécuter le programme principal
java -cp bin:lib/mysql-connector-j-9.4.0.jar:src Main

echo "🏁 Programme terminé"
