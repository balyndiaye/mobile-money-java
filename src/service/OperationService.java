package service;

import dao.CompteDAO;
import dao.OperationDAO;
import model.Compte;
import model.Operation;
import exception.SoldeInsuffisantException;
import java.sql.Connection;
import java.sql.SQLException;

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
        if (compte.getSolde() < montant) throw new SoldeInsuffisantException("Solde insuffisant pour ce retrait.");

        double nouveauSolde = compte.getSolde() - montant;
        compteDAO.mettreAJourSolde(numeroCompte, nouveauSolde);

        Operation op = new Operation("RETRAIT", montant, numeroCompte);
        operationDAO.enregistrerOperation(op);

        return "Retrait réussi. Nouveau solde : " + nouveauSolde;
    }

    public String effectuerTransfert(String compteSource, String compteDest, double montant) throws Exception {
        if (compteSource.equals(compteDest)) {
            return "Erreur : Les comptes source et destination doivent être différents.";
        }

        Connection conn = database.DatabaseConnection.getConnection();

        try {
            conn.setAutoCommit(false);

            effectuerRetrait(compteSource, montant);
            
            Compte dest = compteDAO.findByNumero(compteDest);

            if (dest == null) {
                throw new RuntimeException("Compte destination introuvable.");
            }

            compteDAO.mettreAJourSolde(compteDest, dest.getSolde() + montant);

            Operation op = new Operation("TRANSFERT", montant, compteSource, compteDest);
            operationDAO.enregistrerOperation(op);

            conn.commit();

            return "Succès : Transfert de " + montant + " effectué avec succès.";
            
        } 
        catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } 
        finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close(); 
            }
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

        return "Paiement de " + montant + " à " + nomMarchand + " confirmé.";
    }

    public List<Operation> listerOperations(int compteId) {
        return operationDAO.findByCompteId(compteId);
    }

}