package service;

import java.util.List;
import dao.ClientDAO;
import model.Client;

public class ClientService {

    private ClientDAO clientDAO = new ClientDAO();

    public String ajouterClient(String nom, String prenom, String tel, String adresse) {
        if (nom == null || nom.trim().isEmpty()) {
            return "Erreur : Le nom est obligatoire.";
        }

        if (prenom == null || prenom.trim().isEmpty()) {
            return "Erreur : Le prénom est obligatoire.";
        }
        
        if (tel == null || tel.length() != 9) {
            return "Erreur : Le téléphone doit contenir exactement 9 chiffres.";
        }

        if (clientDAO.existeDeja(tel)) {
            return "Erreur : Ce numéro de téléphone est déjà utilisé par un autre client.";
        }

        try {
            Client nouveauClient = new Client(nom, prenom, tel, adresse);

            boolean estEnregistre = clientDAO.save(nouveauClient);

            if (estEnregistre) {
                return "Succès : Le client " + prenom + " " + nom + " a été créé.";
            } 
            else {
                return "Erreur technique : Impossible d'enregistrer le client en base de données.";
            }
        } 
        catch (Exception e) {
            return "Une erreur inattendue est survenue : " + e.getMessage();
        }
    }

    public List<Client> listerClients() {
        return clientDAO.findAll();
    }

    public Client rechercherClient(String critere) {
        if (critere == null || critere.trim().isEmpty()) {
            throw new IllegalArgumentException("Le critère de recherche ne peut pas être vide.");
        }
        
        return clientDAO.findByNomOrTel(critere);
    }

}