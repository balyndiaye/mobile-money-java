# Application Mobile Money - Java Console (PFE)

## Description du Projet
Ce projet consiste en la conception et le développement d'une application de gestion de services **SAMA KALPE** mobile money (Dépôts, Retraits, Transferts, Paiements). Réalisée dans le cadre de notre deuxième année de DUT à l'**ESP Dakar**, cette application simule le fonctionnement d'un système de transactions financières en temps réel avec une base de données MySQL.

L'architecture logicielle suit une approche **multicouche (N-Tier)**, garantissant une séparation stricte entre l'interface utilisateur, la logique métier et l'accès aux données.

## Fonctionnalités implémentées
* **Gestion Clientèle** : Création de comptes et authentification par numéro de téléphone.
* **Opérations Financières** :
    * Dépôts et Retraits avec mise à jour instantanée du solde.
    * Transferts de compte à compte avec vérification des préconditions.
    * **Paiement Marchand** : Processus sécurisé (validation du code marchand et mise à jour des soldes).
* **Fonctionnalités Avancées (Bonus)** :
    * **Statistiques Dynamiques** : Calcul en temps réel des flux par type d'opération (via SQL `SUM` & `GROUP BY`).
    * **Recherche par Date** : Filtrage de l'historique sur une période précise.
    * **Gestion des Exceptions** : Traitement des erreurs (solde insuffisant, format invalide 9 chiffres pour le telephone) via des classes d'exception personnalisées.

##  Instructions d'Installation

### 1. Prérequis
* **Java JDK** 17 ou plus.
* **Serveur MySQL** (via XAMPP, WAMP ou MySQL Installer).
* **MySQL Connector/J 9.0.0** (présent dans le dossier `lib/`).

### 2. Configuration de la Base de Données
1. Lancer **phpMyAdmin**.
2. Créer une base de données nommée `mobile_money`.
3. Importer le script SQL (tables `client`, `compte`, `operation`, `marchand`).
4. Vérifier le fichier `src/database/ConnexionDB.java` pour ajuster vos identifiants (Host, User, Password).
### 3. Lancement via VS Code (Recommandé)
1. Ouvrir le projet dans VS Code.
2. S'assurer que le fichier `lib/mysql-connector-j-9.0.0.jar` est bien ajouté aux **Referenced Libraries** (dans l'onglet Java Projects).
3. Ouvrir le fichier **`src/main/Main.java`**.
4. Cliquer sur le bouton **Run** (ou la flèche de lecture) qui apparaît au-dessus de la méthode `main`.

### Structure du Code Source
src/ : Racine du code source.

database/ : Gestion de la connexion JDBC (ConnexionDB.java).

exception/ : Classes d'exceptions personnalisées pour la gestion d'erreurs.

model/ : Classes d'entités comme Client, Compte, Operation et Marchand.

dao/ : Couche d'accès aux données et requêtes SQL.

service/ : Logique métier, calculs et validations des transactions.

ui/ : Gestion de l'interface console (Menu.java) et point d'entrée principal de l'application (Main.java).

lib/ : Contient le driver mysql-connector-j-9.0.0.jar.
 ### Exemples d'Utilisation
### Cas 1 : Effectuer un Paiement Marchand
Sélectionner l'option Paiement Marchand (7).

Saisir le Code Marchand (ex: MM-1741).

Saisir le Montant.

Le système vérifie le solde via ValidationService, lève une exception si nécessaire, sinon effectue le débit client et le crédit marchand.

### Cas 2 : Consultation des Statistiques
Sélectionner l'option Statistiques des flux (9)

L'application affiche la répartition des flux (ex: TOTAL DEPOT : 500.000 FCFA) et le Volume Global des transactions.

# Équipe de projet


Mame Diarra Sall : Architecte 

Mame Bousso Ndiaye Syll : logiciel

Astou Thiam : Interface Utilisateur 