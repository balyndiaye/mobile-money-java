package model;

public class Marchand {
    private int id;
    private String nom; 
    private String codeMarchand; 
    private int compteId;

    public Marchand() {}

    // Constructeur synchronisé
    public Marchand(int id, String nom, String codeMarchand, int compteId) {
        this.id = id;
        this.nom = nom;
        this.codeMarchand = codeMarchand;
        this.compteId = compteId;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; } // Indispensable pour son Service
    public void setNom(String nom) { this.nom = nom; }

    public String getCodeMarchand() { return codeMarchand; }
    public void setCodeMarchand(String codeMarchand) { this.codeMarchand = codeMarchand; }

    public int getCompteId() { return compteId; }
    public void setCompteId(int compteId) { this.compteId = compteId; }
}