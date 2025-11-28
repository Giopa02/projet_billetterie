#!/bin/bash

# Script de compilation et d'exécution pour le projet BilletterieBD
# Ce script compile tous les fichiers Java du dossier src vers src/bin, puis exécute le programme

echo "🔨 Compilation du projet BilletterieBD..."

# Créer le dossier src/bin s'il n'existe pas
mkdir -p src/bin

# Compiler tous les fichiers Java de src vers src/bin
javac -cp lib/mysql-connector-j-9.4.0.jar -d src/bin src/*.java

# Vérifier si la compilation a réussi
if [ $? -eq 0 ]; then
    echo "✅ Compilation réussie !"
    echo "📁 Fichiers compilés :"
    ls -la src/bin/*.class
    echo ""
    echo "🚀 Lancement du programme BilletterieBD..."
    echo ""
    
    # Exécuter le programme principal
    java -cp src/bin:lib/mysql-connector-j-9.4.0.jar:src Main
    
    echo ""
    echo "🏁 Programme terminé"
else
    echo "❌ Erreur de compilation"
    exit 1
fi

