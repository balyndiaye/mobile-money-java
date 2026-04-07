package service;

import dao.CompteDAO;
import dao.ClientDAO;
import model.Compte;
import model.Client;

public class CompteService {

    private CompteDAO compteDAO = new CompteDAO();
    private ClientDAO clientDAO = new ClientDAO();

    public String creerCompte(int clientId) {
        Client client = clientDAO.findById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Erreur : Le client avec l'ID " + clientId + " n'existe pas.");
        }

        Compte nouveauCompte = new Compte();
        nouveauCompte.setNumero(numeroCompte);
        nouveauCompte.setSolde(0.0);
        nouveauCompte.setClient(client);

        compteDAO.save(nouveauCompte);

        return "Compte créé avec succès. Numéro : " + numeroCompte;
    }

    public Compte consulterCompte(String numeroCompte) {
        if (numeroCompte == null || numeroCompte.trim().isEmpty()) {
            throw new IllegalArgumentException("Le numéro de compte est obligatoire.");
        }
        
        Compte compte = compteDAO.findByNumero(numeroCompte);
        if (compte == null) {
            throw new RuntimeException("Compte introuvable.");
        }
        return compte;
    }

    public double afficherSolde(String numeroCompte) {
        Compte compte = consulterCompte(numeroCompte);
        return compte.getSolde();
    }
    
}