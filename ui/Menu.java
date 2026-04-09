package ui;
import service.*;
import java.util.Scanner;
// import service.*;

public class Menu {
    public void afficherMenuPrincipal(){
    private Scanner sc = new Scanner(System.in);
    private ClientService clientService = new ClientService();
    private CompteService compteService = new CompteService();
    private OperationService operationService = new OperationService();
    int choix = 0;
    do {
        System.out.println("=============================");
        System.out.println("BIENVENUE SUR MOBILE MONEY");
        System.out.println("=============================");
        System.out.println("1 - Ajouter un client");
        System.out.println("2 - Afficher les clients");
        System.out.println("3 - Créer un compte");
        System.out.println("4 - Depôt d'argent");
        System.out.println("5 - Retrait d'argent ");
        System.out.println("6 - Transfert d'argent");
        System.out.println("7 - Paiement marchand");
        System.out.println("8 - Historique des opérations");
        System.out.println("9 - Quitter");
        System.out.println("=============================");
        System.out.print("Votre Choix :");
        choix = sc.nextInt();
        switch (choix) {
            case 1 :
                System.out.println("Ajout client");
                break;
            case 2 :
                System.out.println("Affichage des clients");
                break;
            case 3 :
                System.out.println("Création de compte");
                break;
            case 4 :
                System.out.println("Depôt d'argent"); 
                break;
            case 5 : 
                System.out.println("Retrait d'argent");
                break;
            case 6 : 
                System.out.println("Transfert d'argent");
                break;
            case 7 : 
                System.out.println("Paiement marchand");
                break;
            case 8 : 
                System.out.println("Historique");
                break;
            default : 
              System.out.println("Choix invalide! Veuillez recommencer");              

        }

    }while (choix != 9);
    }

    private void AjouterClient() {
        sc.nextLine();

        System.out.println("----Ajout d'un client----");
        try {
            String prenom ="", nom ="", adresse="", telephone="";
            boolean valid = false;

        do {    
        System.out.print("Veuillez entrer votre prénom : ");
        prenom = sc.nextLine().trim();
        System.out.println("Veuillez entrer votre nom");
        nom = sc.nextLine().trim();
        System.out.println("Veuillez entrer votre numero de téléphone");
        telephone = sc.nextLine().trim();
        System.out.println("Veuillez entrer votre adresse");
        adresse = sc.nextLine().trim();
        
        if(nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || adresse.isEmpty()){
            System.out.println("Le nom, le prénom, le numéro de téléphone et l'adresse sont obligatoires");
        } else {
            valid = true;
        }

        } while (!valid);
        String reponse = clientService.ajouterClient(nom, prenom, telephone, adresse);
        System.out.println(reponse);
        } catch(Exception e) {
            System.out.println("Erreur lors de la saisie");
            sc.nextLine();
        }
    }

    private void AffichageClient(){
        System.out.println("-------Affichage des clients enregistrés-------");
       
        List<Client> clients = clientService.listerClients();
        if (clients == null || clients.isEmpty()){
            System.out.println("Aucun client trouvé");
        } else {
            System.out.printf(" %-5s | %-15s | %-15s | %-15s | %-12s%n", "ID", "PRENOM", "NOM", "ADRESSE", "TELEPHONE");
            System.out.println("-------------------------------------------------------------------------------------");
            for (Client c : clients){
                System.out.println("%-5s | %-15s | %-15s | %-15s | %-12s%n", c.getId(), c.getPrenom(), c.getNom(), c.getAdresse(), c.getTelephone());

            }
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println(" Total : "+clients.size()+ " client(s) trouvés" );
        }
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        sc.nextLine();
        if(sc.hasNextLine()) sc.nextLine(); 
    }

    private void CreationCompte(){
        System.out.println("------Création Compte-------");

        try {
       
        System.out.println("Veuillez le numéro pour lequel vous voulez créer un compte");
        String telephone= sc.nextLine();
        Client client = clientService.rechercherClient(tel);
        if(client == null){
            System.out.println("Aucun client trouvé pour ce numéro saisi");
            System.out.println("Veuillez d'abord créer le client (Option 1)");
        }
        String messageSucces = compteService.creerCompte(client.getId());
        System.out.println(messageSucces);
        System.out.println("Titulaire : " + client.getPrenom() + " " + client.getNom().toUpperCase());
        } catch (Exception e) {
            System.out.println("ERREUR "+e.getMessage());
        }  
    }

    private void DepotArgent(){
        System.out.println("-------OPERATION : DEPOT-------");
        try {
        System.out.println("Veuillez entrer le numero du compte");
        String numero = sc.next();
        double montant=0;
        boolean montValide=false;
        do {
            System.out.println("Veuillez fournir le montant du dépôt(FCFA) qui doit être supérieur à 0");
            montant = sc.nextDouble();
            if (montant <=0){
                System.out.println("Le montant saisi est incorrect. Veuillez recommencer");
            } else {
                montValide=true;
            }
        } while(!montValide);
        String messageSuccess = operationService.effectuerDepot(numero, montant);

        System.out.println(messageSuccess);
        }catch (java.util.InputMismatchException e) {
        System.out.println("Erreur : Veuillez utiliser uniquement des chiffres pour le montant.");
        sc.nextLine(); 
        } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    }

    private void RetraitArgent(){
        System.out.println("------Retrait Argent------");
        try {
        System.out.println("Veuillez entrer le numero du compte");
        String numero = sc.next();
        double montant=0;
        boolean montValide=false;
        do {
            System.out.println("Veuillez fournir le montant à retirer (FCFA) qui doit être supérieur à 0");
            montant = sc.nextDouble();
            if (montant <=0){
                System.out.println("Le montant saisi est incorrect. Veuillez recommencer");
            } else {
                montValide=true;
            }
        } while(!montValide);
        String mess = OperationService.effectuerRetrait(numero, montant);
        System.out.println(mess);
        } catch (java.util.InputMismatchException e) {
        System.out.println("Erreur : Veuillez saisir un nombre valide pour le montant.");
        sc.nextLine(); // Nettoyage buffer
        } catch (Exception e) {
        System.out.println(e.getMessage());
    }
        
    }
    private void transfertArgent() {
    System.out.println("--------Transfert Argent--------");

    try {
        // 1. Saisie des comptes
        System.out.print("Veuillez entrer le numéro de l'éexpediteur");
        String numEmetteur = sc.next();
        System.out.print("Veuillez entrer le numéro du bénéficiaire");
        String numBeneficiaire = sc.next();

        if (numEmetteur.equals(numBeneficiaire)) {
            System.out.println("Erreur : Les deux numéros de compte doivent être différents.");
            return;
        }
        // 2. Saisie du montant avec ta boucle de validation habituelle
        double montant = 0;
        boolean montValide = false;
        do {
            System.out.print("Entrez le montant du transfert");
            montant = sc.nextDouble();
            if (montant <= 0) {
                System.out.println("Le montant doit être supérieur à 0.");
            } else {
                montValide = true;
            }
        } while (!montValide);
        String message = operationService.effectuerTransfert(numEmetteur, numBeneficiaire, montant);

        // 4. Affichage du succès
        System.out.println(message);

    } catch (java.util.InputMismatchException e) {
        System.out.println("Erreur : Format de montant invalide.");
        sc.nextLine(); 
    } catch (Exception e) {
        // Attrape les erreurs du Dev 1 : "Solde insuffisant", "Compte inexistant", etc.
        System.out.println("Échec du transfert : " + e.getMessage());
    }
}
   
    // private void PaiementMarchand(){
        
    // }

    // private void Historique(){
        
    // }
}