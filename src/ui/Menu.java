package ui;

import service.ClientService;
import service.CompteService;
import service.OperationService;
import model.Client;
import model.Operation;
import java.util.Scanner;
import java.util.List;
import java.util.Map;

public class Menu {
    
    public static final String CYAN  = "\u001B[36m";
    public static final String GRIS_FONCE = "\u001B[90m";
    public static final String RESET = "\u001B[0m";

    private Scanner sc = new Scanner(System.in);
    private ClientService clientService = new ClientService();
    private CompteService compteService = new CompteService();
    private OperationService operationService = new OperationService();

    public void afficherMenuPrincipal() {
        int choix = 0;
        do {
            System.out.println(CYAN + "\n================================================" + RESET);
            System.out.println(CYAN + "            BIENVENUE SUR SAMA KALPÉ" + RESET);
            System.out.println(CYAN + "================================================" + RESET);
            System.out.println("1 - Ajouter un client");
            System.out.println("2 - Afficher les clients");
            System.out.println("3 - Créer un compte");
            System.out.println("4 - Depôt d'argent");
            System.out.println("5 - Retrait d'argent ");
            System.out.println("6 - Transfert d'argent");
            System.out.println("7 - Paiement marchand");
            System.out.println("8 - Historique des opérations");
            System.out.println("9 - Statistiques des flux"); // Option Bonus ajoutée
            System.out.println("10 - Rechercher par date"); // Option Bonus ajoutée
            System.out.println("0 - Quitter");
            System.out.println(CYAN + "================================================" + RESET);
            System.out.print("Votre Choix : ");

            if (sc.hasNextInt()) {
                choix = sc.nextInt();
                sc.nextLine(); // Nettoyage du buffer
                switch (choix) {
                    case 1: AjouterClient(); break;
                    case 2: AffichageClient(); break;
                    case 3: CreationCompte(); break;
                    case 4: DepotArgent(); break;
                    case 5: RetraitArgent(); break;
                    case 6: transfertArgent(); break;
                    case 7: paiementMarchand(); break;
                    case 8: afficherHistorique(); break;
                    case 9: afficherStatistiques(); break; // Option bonus pour les stats globales
                    case 10: rechercherParDate(); break; // Lien vers le bonus
                    case 0: System.out.println(GRIS_FONCE + "Fermeture de l'application..." + RESET); break;
                    default: System.out.println("Choix invalide !");
                }
            } else {
                System.out.println("Veuillez entrer un nombre valide.");
                sc.nextLine();
            }
        } while (choix != 0);
    }

    private void AjouterClient() {
        System.out.println("======= AJOUT D'UN CLIENT =======");
        try {
            System.out.print("Veuillez entrer votre prénom : ");
            String prenom = sc.nextLine().trim();
            System.out.print("Veuillez entrer votre nom : ");
            String nom = sc.nextLine().trim();
            System.out.print("Veuillez entrer votre numero de téléphone : ");
            String telephone = sc.nextLine().trim();
            System.out.print("Veuillez entrer votre adresse : ");
            String adresse = sc.nextLine().trim();

            if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || adresse.isEmpty()) {
                System.out.println("Erreur : Tous les champs sont obligatoires.");
                return;
            }
            
            String reponse = clientService.ajouterClient(nom, prenom, telephone, adresse);
            System.out.println(reponse);
        } catch (Exception e) {
            System.out.println("Erreur lors de la saisie.");
        }
        System.out.println(GRIS_FONCE + "\nAppuyez sur Entrée pour retourner au menu principal..." + RESET);
        sc.nextLine();
        if(sc.hasNextLine()) {
            sc.nextLine();
        }
    }

    private void AffichageClient() {
        System.out.println("======= AFFICHAGE DES CLIENTS ENREGISTRÉS =======");
        List<Client> clients = clientService.listerClients();
        if (clients == null || clients.isEmpty()) {
            System.out.println("Aucun client trouvé.");
        } else {
            System.out.printf("%-5s | %-15s | %-15s | %-15s | %-12s%n", "ID", "PRENOM", "NOM", "ADRESSE", "TELEPHONE");
            System.out.println("-------------------------------------------------------------------------------------");
            for (Client c : clients) {
                System.out.printf("%-5s | %-15s | %-15s | %-15s | %-12s%n", c.getId(), c.getPrenom(), c.getNom(), c.getAdresse(), c.getTelephone());
            }
        }
        System.out.println(GRIS_FONCE + "\nAppuyez sur Entrée pour retourner au menu principal..." + RESET);
        sc.nextLine();
        if(sc.hasNextLine()) {
            sc.nextLine();
        }
    }

    private void CreationCompte() {
        System.out.println("======= CRÉATION DE COMPTE =======");
        try {
            System.out.print("Veuillez entrer le numéro de téléphone du client : ");
            String telephone = sc.nextLine();
            Client client = clientService.rechercherClient(telephone);
            if (client == null) {
                System.out.println("Aucun client trouvé. Créez d'abord le client.");
                return;
            }
            String message = compteService.creerCompte(client.getId());
            System.out.println(message);
        } catch (Exception e) {
            System.out.println("ERREUR : " + e.getMessage());
        }
        System.out.println(GRIS_FONCE + "\nAppuyez sur Entrée pour retourner au menu principal..." + RESET);
        sc.nextLine();
        if(sc.hasNextLine()) {
            sc.nextLine();
        }
    }

    private void DepotArgent() {
        System.out.println("======= DEPOT D'ARGENT =======");
        try {
            System.out.print("Numéro du compte : ");
            String numero = sc.next();
            System.out.print("Montant (FCFA) : ");
            double montant = sc.nextDouble();
            String res = operationService.effectuerDepot(numero, montant);
            System.out.println(res);
        } catch (Exception e) {
            System.out.println("Erreur : Saisie invalide.");
            sc.nextLine();
        }
        System.out.println(GRIS_FONCE + "\nAppuyez sur Entrée pour retourner au menu principal..." + RESET);
        sc.nextLine();
        if(sc.hasNextLine()) {
            sc.nextLine();
        }
    }

    private void RetraitArgent() {
        System.out.println("======= RETRAIT D'ARGENT =======");
        try {
            System.out.print("Numéro du compte : ");
            String numero = sc.next();
            System.out.print("Montant (FCFA) : ");
            double montant = sc.nextDouble();
            String mess = operationService.effectuerRetrait(numero, montant);
            System.out.println(mess);
        } catch (Exception e) {
            System.out.println("Échec : " + e.getMessage());
            sc.nextLine();
        }
        System.out.println(GRIS_FONCE + "\nAppuyez sur Entrée pour retourner au menu principal..." + RESET);
        sc.nextLine();
        if(sc.hasNextLine()) {
            sc.nextLine();
        }
    }

    private void transfertArgent() {
        System.out.println("======= TRANSFERT D'ARGENT =======");
        try {
            System.out.print("Numéro expéditeur : ");
            String numE = sc.next();
            System.out.print("Numéro bénéficiaire : ");
            String numB = sc.next();
            System.out.print("Montant transfert : ");
            double mnt = sc.nextDouble();
            String res = operationService.effectuerTransfert(numE, numB, mnt);
            System.out.println(res);
        } catch (Exception e) {
            System.out.println("Échec : " + e.getMessage());
            sc.nextLine();
        }
        System.out.println(GRIS_FONCE + "\nAppuyez sur Entrée pour retourner au menu principal..." + RESET);
        sc.nextLine();
        if(sc.hasNextLine()) {
            sc.nextLine();
        }
    }

    private void paiementMarchand() {
        System.out.println("======= PAIEMENT MARCHAND =======");
        try {
            System.out.print("Numéro de compte client : ");
            String num = sc.next(); 
            sc.nextLine(); // Vider le buffer
            
            System.out.print("Nom du marchand : ");
            String marchand = sc.nextLine();
            
            System.out.print("Montant : ");
            double mnt = sc.nextDouble();
            
            String res = operationService.effectuerPaiement(num, marchand, mnt);
            System.out.println(res);
        } catch (Exception e) {
            System.out.println("Erreur paiement : " + e.getMessage());
        }
        System.out.println(GRIS_FONCE + "\nAppuyez sur Entrée pour retourner au menu principal..." + RESET);
        sc.nextLine();
        if(sc.hasNextLine()) {
            sc.nextLine();
        }
    }
    
    private void afficherHistorique() {
        System.out.println("\n======= HISTORIQUE DES OPÉRATIONS =======");
        try {
            System.out.print("Entrez le numéro de compte : ");
            String num = sc.next(); 
            List<Operation> historique = operationService.listerOperations(num);

            if (historique.isEmpty()) {
                System.out.println("Aucune opération enregistrée pour ce compte.");
            } else {
                System.out.printf("\n  %-12s | %-10s | %-20s%n", "TYPE", "MONTANT", "DATE");
                System.out.println("  ----------------------------------------------------------");
                for (Operation op : historique) {
                    System.out.printf("  %-12s | %-10.2f | %-20s%n", 
                        op.getType(), op.getMontant(), op.getDateOp());
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        System.out.println(GRIS_FONCE + "\nAppuyez sur Entrée pour retourner au menu principal..." + RESET);
        sc.nextLine();
        if(sc.hasNextLine()) {
            sc.nextLine();
        }
    }

    // MÉTHODE BONUS : Recherche par plage de dates
    private void rechercherParDate() {
        System.out.println("\n======= RECHERCHE PAR PLAGE DE DATES =======");
        try {
            System.out.print("Entrez le numéro de compte : ");
            String num = sc.next();
            sc.nextLine(); // Nettoyage buffer
            
            System.out.print("Date de début (AAAA-MM-JJ) : ");
            String debut = sc.nextLine();
            
            System.out.print("Date de fin (AAAA-MM-JJ) : ");
            String fin = sc.nextLine();

            List<Operation> ops = operationService.listerEntreDates(num, debut, fin);

            if (ops.isEmpty()) {
                System.out.println("Aucune opération trouvée pour cette période.");
            } else {
                System.out.printf("\n  %-12s | %-10s | %-20s%n", "TYPE", "MONTANT", "DATE");
                System.out.println("  ----------------------------------------------------------");
                for (Operation op : ops) {
                    System.out.printf("  %-12s | %-10.2f | %-20s%n", 
                        op.getType(), op.getMontant(), op.getDateOp());
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        System.out.println(GRIS_FONCE + "\nAppuyez sur Entrée pour retourner au menu principal..." + RESET);
        sc.nextLine();
        if(sc.hasNextLine()) {
            sc.nextLine();
        }
    }

    private void afficherStatistiques() {
    System.out.println("\n======= STATISTIQUES DES FLUX =======");
    Map<String, Double> stats = operationService.consulterStatsGlobales();
    
    if (stats.isEmpty()) {
        System.out.println("Aucune donnée disponible.");
    } else {
        // On parcourt la Map pour afficher chaque type et son total
        stats.forEach((type, total) -> {
            System.out.printf("%-15s : %.2f FCFA%n", type, total);
        });
        
        // Petit calcul bonus pour le volume total
        double global = stats.values().stream().mapToDouble(Double::doubleValue).sum();
        System.out.println("-------------------------------------");
        System.out.printf("VOLUME TOTAL    : %.2f FCFA%n", global);
    }
    System.out.println(GRIS_FONCE + "\nAppuyez sur Entrée pour retourner au menu principal..." + RESET);
        sc.nextLine();
        if(sc.hasNextLine()) {
            sc.nextLine();
        }
    }

}