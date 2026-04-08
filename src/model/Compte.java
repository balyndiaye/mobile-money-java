package model;

public class Compte {
    private int id;
    private String numero; 
    private double solde;
    private Client client; 

    public Compte() {}

    // Constructeur complet
    public Compte(int id, String numero, double solde, Client client) {
        this.id = id;
        this.numero = numero;
        this.solde = solde;
        this.client = client;
    }

    // Getters et Setters synchronisés avec le Service et le DAO
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumero() { return numero; } // Nom exact attendu
    public void setNumero(String numero) { this.numero = numero; }

    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }

    public Client getClient() { return client; } // Permet de faire getClient().getId()
    public void setClient(Client client) { this.client = client; }
}