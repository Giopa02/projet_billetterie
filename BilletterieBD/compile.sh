#!/bin/bash

# Script de compilation pour le projet BilletterieBD
# Ce script compile tous les fichiers Java du dossier src vers le dossier bin

echo "🔨 Compilation du projet BilletterieBD..."

# Créer le dossier bin s'il n'existe pas
mkdir -p bin

# Compiler tous les fichiers Java de src vers bin
javac -cp lib/mysql-connector-j-9.4.0.jar -d bin src/BilletModel.java src/ClientModel.java src/DatabaseConfig.java src/EvenementModel.java src/HoraireModel.java src/Main.java src/Model.java src/MySQLConnection.java src/SalleModel.java

# Vérifier si la compilation a réussi
if [ $? -eq 0 ]; then
    echo "✅ Compilation réussie ! Les fichiers .class sont dans bin/"
    echo "📁 Fichiers compilés :"
    ls -la bin/*.class
else
    echo "❌ Erreur de compilation"
    exit 1
fi
echo "🚀 Vous pouvez maintenant exécuter le programme avec la commande :"
echo "java -cp bin:lib/mysql-connector-j-9.4.0.jar Main"
echo "Bonne utilisation ! 🎉"
