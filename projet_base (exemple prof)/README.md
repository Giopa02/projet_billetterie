Dans ce zip, vous avez la base de votre projet.

Il y a 6 fichiers :
– config. properties sorte. env c’est ici que vous mettez les informations pour se connecter à la base de données
– DatabaseConfig.java ce fichier va aller chercher les informations dans le fichier config. properties
– MySQLConnection.java va utiliser la classe DatabaseConfig pour créer une connexion à la base de données
– Model.java est une interface qui sera implémentée par nos classes Model, celles qui accèderont aux tables, vous créez des fichiers du type : TableModel.java
– ExampleTableModel.java est un exemple qui implémente l’interface Model, vous pouvez le supprimer
– Main.java c’est ici que vous instancierez vos classes TableModel.java. Par la suite, cela sera le rôle du contrôleur.