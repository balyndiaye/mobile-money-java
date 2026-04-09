package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.CompteDAO;
import dao.OperationDAO;
import model.Compte;
import model.Operation;
import exception.SoldeInsuffisantException;

public class OperationService {

    private CompteDAO compteDAO = new CompteDAO();
    private OperationDAO operationDAO = new OperationDAO();

    public String effectuerDepot(String numeroCompte, double montant) {
        Compte compte = compteDAO.findByNumero(numeroCompte);
        if (compte == null) throw new RuntimeException("Compte inexistant.");
        if (montant <= 0) throw new IllegalArgumentException("Le montant doit être positif.");

        double nouveauSolde = compte.getSolde() + montant;
        compteDAO.mettreAJourSolde(numeroCompte, nouveauSolde);
      
        Operation op = new Operation("DEPOT", montant, numeroCompte);
        operationDAO.enregistrerOperation(op);

        return "Dépôt réussi. Nouveau solde : " + nouveauSolde;
    }

    public String effectuerRetrait(String numeroCompte, double montant) throws SoldeInsuffisantException {
        Compte compte = compteDAO.findByNumero(numeroCompte);
        if (compte == null) throw new RuntimeException("Compte inexistant.");
        if (montant <= 0) throw new IllegalArgumentException("Le montant doit être > 0.");
        if (compte.getSolde() < montant) throw new SoldeInsuffisantException("Solde insuffisant.");

        double nouveauSolde = compte.getSolde() - montant;
        compteDAO.mettreAJourSolde(numeroCompte, nouveauSolde);

        Operation op = new Operation("RETRAIT", montant, numeroCompte);
        operationDAO.enregistrerOperation(op);

        return "Retrait réussi. Nouveau solde : " + nouveauSolde;
    }

    public String effectuerTransfert(String compteSource, String compteDest, double montant) throws Exception {
        if (compteSource.equals(compteDest)) return "Erreur : Comptes identiques.";
        
        Compte dest = compteDAO.findByNumero(compteDest);
        if (dest == null) throw new RuntimeException("Compte destination introuvable.");

        try {
            effectuerRetrait(compteSource, montant);
            double nouveauSoldeDest = dest.getSolde() + montant;
            compteDAO.mettreAJourSolde(compteDest, nouveauSoldeDest);

            Operation op = new Operation("TRANSFERT", montant, compteSource, compteDest);
            operationDAO.enregistrerOperation(op);

            return "Succès : Transfert effectué.";
        } catch (Exception e) {
            throw new Exception("Échec du transfert : " + e.getMessage());
        }
    }

    public String effectuerPaiement(String numeroCompte, String nomMarchand, double montant) throws SoldeInsuffisantException {
        Compte compte = compteDAO.findByNumero(numeroCompte);
        if (compte == null) throw new RuntimeException("Compte inexistant.");
        if (compte.getSolde() < montant) throw new SoldeInsuffisantException("Solde insuffisant.");

        double nouveauSolde = compte.getSolde() - montant;
        compteDAO.mettreAJourSolde(numeroCompte, nouveauSolde);

        Operation op = new Operation("PAIEMENT", montant, numeroCompte);
        op.setMarchand(nomMarchand);
        operationDAO.enregistrerOperation(op);

        return "Paiement confirmé.";
    }

    public List<Operation> listerOperations(String numeroCompte) {
        Compte compte = compteDAO.findByNumero(numeroCompte);
        if (compte != null) return operationDAO.findByCompteId(compte.getId());
        return new ArrayList<>();
    }

    /**
     * MÉTHODE BONUS : Recherche par plage de dates
     */
    public List<Operation> listerEntreDates(String numeroCompte, String debut, String fin) {
        Compte compte = compteDAO.findByNumero(numeroCompte);
        if (compte != null) {
            return operationDAO.findBetweenDates(compte.getId(), debut, fin);
        }
        return new ArrayList<>();
    }

    public Map<String, Double> consulterStatsGlobales() {
    return operationDAO.obtenirSommesParType();
}
}