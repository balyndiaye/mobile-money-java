package ui;

import service.ClientService;
import service.CompteService;
import service.OperationService;
import model.Client;
import model.Operation;
import java.util.Scanner;
import java.util.List;

public class Menu {
    // Déclarer les variables en attributs pour qu'elles soient accessibles partout
    private Scanner sc = new Scanner(System.in);
    private ClientService clientService = new ClientService();
    private CompteService compteService = new CompteService();
    private OperationService operationService = new OperationService();

    public void afficherMenuPrincipal() {
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
            System.out.print("Votre Choix : ");

            if (sc.hasNextInt()) {
                choix = sc.nextInt();
                sc.nextLine(); // Nettoyer le buffer après nextInt
                switch (choix) {
                    case 1: AjouterClient(); break;
                    case 2: AffichageClient(); break;
                    case 3: CreationCompte(); break;
                    case 4: DepotArgent(); break;
                    case 5: RetraitArgent(); break;
                    case 6: transfertArgent(); break;
                    case 8: afficherHistorique(); break;
                    case 9: break;
                    default: System.out.println("Choix invalide !");
                }
            } else {
                System.out.println("Veuillez entrer un nombre.");
                sc.nextLine();
            }
        } while (choix != 9);
    }

    private void AjouterClient() {
        System.out.println("----Ajout d'un client----");
        try {
            String prenom, nom, adresse, telephone;
            boolean valid = false;
            do {
                System.out.print("Veuillez entrer votre prénom : ");
                prenom = sc.nextLine().trim();
                System.out.print("Veuillez entrer votre nom : ");
                nom = sc.nextLine().trim();
                System.out.print("Veuillez entrer votre numero de téléphone : ");
                telephone = sc.nextLine().trim();
                System.out.print("Veuillez entrer votre adresse : ");
                adresse = sc.nextLine().trim();

                if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || adresse.isEmpty()) {
                    System.out.println("Erreur : Tous les champs sont obligatoires.");
                } else {
                    valid = true;
                }
            } while (!valid);
            
            String reponse = clientService.ajouterClient(nom, prenom, telephone, adresse);
            System.out.println(reponse);
        } catch (Exception e) {
            System.out.println("Erreur lors de la saisie.");
        }
    }

    private void AffichageClient() {
        System.out.println("-------Affichage des clients enregistrés-------");
        List<Client> clients = clientService.listerClients();
        if (clients == null || clients.isEmpty()) {
            System.out.println("Aucun client trouvé.");
        } else {
            System.out.printf("%-5s | %-15s | %-15s | %-15s | %-12s%n", "ID", "PRENOM", "NOM", "ADRESSE", "TELEPHONE");
            System.out.println("-------------------------------------------------------------------------------------");
            for (Client c : clients) {
                System.out.printf("%-5s | %-15s | %-15s | %-15s | %-12s%n", c.getId(), c.getPrenom(), c.getNom(), c.getAdresse(), c.getTelephone());
            }
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println(" Total : " + clients.size() + " client(s) trouvé(s)");
        }
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        sc.nextLine();
    }

    private void CreationCompte() {
        System.out.println("------Création Compte-------");
        try {
            System.out.print("Veuillez entrer le numéro de téléphone : ");
            String telephone = sc.nextLine();
            Client client = clientService.rechercherClient(telephone);
            if (client == null) {
                System.out.println("Aucun client trouvé. Veuillez d'abord créer le client (Option 1).");
                return;
            }
            String messageSucces = compteService.creerCompte(client.getId());
            System.out.println(messageSucces);
            System.out.println("Titulaire : " + client.getPrenom() + " " + client.getNom().toUpperCase());
        } catch (Exception e) {
            System.out.println("ERREUR : " + e.getMessage());
        }
    }

    private void DepotArgent() {
        System.out.println("-------OPERATION : DEPOT-------");
        try {
            System.out.print("Veuillez entrer le numero du compte : ");
            String numero = sc.next();
            double montant;
            do {
                System.out.print("Montant du dépôt (FCFA) : ");
                montant = sc.nextDouble();
                if (montant <= 0) System.out.println("Le montant doit être supérieur à 0.");
            } while (montant <= 0);
            
            String res = operationService.effectuerDepot(numero, montant);
            System.out.println(res);
        } catch (Exception e) {
            System.out.println("Erreur : Saisie invalide.");
            sc.nextLine();
        }
    }

    private void RetraitArgent() {
        System.out.println("------Retrait Argent------");
        try {
            System.out.print("Veuillez entrer le numero du compte : ");
            String numero = sc.next();
            double montant;
            do {
                System.out.print("Montant à retirer (FCFA) : ");
                montant = sc.nextDouble();
                if (montant <= 0) System.out.println("Le montant doit être supérieur à 0.");
            } while (montant <= 0);

            String mess = operationService.effectuerRetrait(numero, montant);
            System.out.println(mess);
        } catch (Exception e) {
            System.out.println("Erreur : Saisie invalide.");
            sc.nextLine();
        }
    }

    private void transfertArgent() {
        System.out.println("--------Transfert Argent--------");
        try {
            System.out.print("Numéro expéditeur : ");
            String numE = sc.next();
            System.out.print("Numéro bénéficiaire : ");
            String numB = sc.next();
            if (numE.equals(numB)) {
                System.out.println("Erreur : Les numéros doivent être différents.");
                return;
            }
            System.out.print("Montant transfert : ");
            double mnt = sc.nextDouble();
            String res = operationService.effectuerTransfert(numE, numB, mnt);
            System.out.println(res);
        } catch (Exception e) {
            System.out.println("Échec : " + e.getMessage());
            sc.nextLine();
        }
    }

    private void afficherHistorique() {
        System.out.println("\n------- HISTORIQUE DES OPÉRATIONS -------");
        try {
            System.out.print("Entrez le numéro de compte : ");
            String num = sc.next();

            // Correction du nom de la méthode : listerOperations au lieu de consulterHistorique
            List<Operation> historique = operationService.listerOperations(num);

            if (historique == null || historique.isEmpty()) {
                System.out.println("Aucune opération enregistrée pour ce compte.");
            } else {
                // Un petit titre pour faire propre
                System.out.printf("\n  %-12s | %-10s | %-15s%n", "TYPE", "MONTANT", "CIBLE");
                System.out.println("  ---------------------------------------------");
                
                for (Operation op : historique) {
                    String cible = "-";
                    if (op.getMarchand() != null) {
                        cible = op.getMarchand();
                    } else if (op.getCompteDest() != null) {
                        cible = op.getCompteDest();
                    }
                    
                    System.out.printf("  %-12s | %-10.2f | %-15s%n", 
                        op.getType(), op.getMontant(), cible);
                }
            }
        } 
        catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
    
}