package model;

import java.sql.Timestamp;

public class Operation {
    private int id;
    private String type;         
    private double montant;
    private Timestamp dateOp;
    private String compteSource; 
    private String compteDest;   
    private String marchand;     

    // Constructeur Vide
    public Operation() {
        this.dateOp = new Timestamp(System.currentTimeMillis());
    }

    // Constructeur pour DEPOT, RETRAIT, PAIEMENT 

    public Operation(String type, double montant, String compteSource) {
        this();
        this.type = type;
        this.montant = montant;
        this.compteSource = compteSource;
    }

    //  Constructeur pour TRANSFERT 
    
    public Operation(String type, double montant, String compteSource, String compteDest) {
        this(type, montant, compteSource);
        this.compteDest = compteDest;
    }

    // --- Getters et Setters synchronisés ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType() { return type; } 
    public void setType(String type) { this.type = type; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public Timestamp getDateOp() { return dateOp; }
    public void setDateOp(Timestamp dateOp) { this.dateOp = dateOp; }

    public String getCompteSource() { return compteSource; }
    public void setCompteSource(String compteSource) { this.compteSource = compteSource; }

    public String getCompteDest() { return compteDest; }
    public void setCompteDest(String compteDest) { this.compteDest = compteDest; }

    public String getMarchand() { return marchand; }
    public void setMarchand(String marchand) { this.marchand = marchand; }
}