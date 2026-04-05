package model;

public class Compte {
    private int id;
    private String numeroCompte;
    private double solde;
    private int clientId; // La clé étrangère vers le Client

    public Compte() {}

    public Compte(int id, String numeroCompte, double solde, int clientId) {
        this.id = id;
        this.numeroCompte = numeroCompte;
        this.solde = solde;
        this.clientId = clientId;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumeroCompte() { return numeroCompte; }
    public void setNumeroCompte(String numeroCompte) { this.numeroCompte = numeroCompte; }

    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
}