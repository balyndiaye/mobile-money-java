DROP DATABASE IF EXISTS mobile_money_db;
CREATE DATABASE mobile_money_db;
USE mobile_money_db;

-- 1. Table des Clients
CREATE TABLE Client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    telephone VARCHAR(15) UNIQUE NOT NULL,
    adresse VARCHAR(100)
);

-- 2. Table des Comptes
CREATE TABLE Compte (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(20) UNIQUE NOT NULL,
    solde DOUBLE DEFAULT 0.0,
    client_id INT,
    FOREIGN KEY (client_id) REFERENCES Client(id) ON DELETE CASCADE
);

-- 3. Table des Marchands 
CREATE TABLE Marchand (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    code_marchand VARCHAR(20) UNIQUE NOT NULL,
    compte_id INT,
    FOREIGN KEY (compte_id) REFERENCES Compte(id) ON DELETE CASCADE
);

-- 4. Table des Opérations
CREATE TABLE Operation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type_op ENUM('DEPOT', 'RETRAIT', 'TRANSFERT', 'PAIEMENT') NOT NULL,
    montant DOUBLE NOT NULL,
    date_op TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    compte_id INT NOT NULL,
    compte_dest_id INT DEFAULT NULL,
    FOREIGN KEY (compte_id) REFERENCES Compte(id),
    FOREIGN KEY (compte_dest_id) REFERENCES Compte(id)
);

-- ---------------
-- DONNÉES DE TEST 
-- ---------------

-- Ajout d'un client
INSERT INTO Client (nom, prenom, telephone, adresse) 
VALUES ('Diarra', 'Etudiant', '771234567', 'Dakar ESP');

-- Ajout d'un compte pour ce client (Numéro MM-0001)
INSERT INTO Compte (numero, solde, client_id) 
VALUES ('MM-0001', 50000.0, 1);

-- Ajout des marchands 
INSERT INTO Marchand (nom, code_marchand, compte_id) VALUES 
('Cafeteria ESP', 'MARCH001', 1),
('MagaScent', 'MARCH002', 1);