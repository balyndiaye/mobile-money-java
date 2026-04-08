package model;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;

    //  Constructeur vide 
    public Client() {}

    //  Constructeur pour la CRÉATION (SANS ID)
    
    public Client(String nom, String prenom, String telephone, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
    }

    // Constructeur pour la RÉCUPÉRATION (AVEC ID)
    public Client(int id, String nom, String prenom, String telephone, String adresse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
    }

    // --- Getters et Setters ---
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    // Méthode pour afficher proprement les infos du client 
    @Override
    public String toString() {
        return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", tel=" + telephone + "]";
    }
}