package model;

import java.sql.Timestamp;

public class Operation {
    private int id;
    private String typeOp; // Ex: 'DEPOT', 'RETRAIT', 'TRANSFERT'
    private double montant;
    private Timestamp dateOp;
    private int compteId;
    private Integer compteDestId; // Integer car il peut être null (si pas un transfert)

    public Operation() {}

    public Operation(int id, String typeOp, double montant, Timestamp dateOp, int compteId, Integer compteDestId) {
        this.id = id;
        this.typeOp = typeOp;
        this.montant = montant;
        this.dateOp = dateOp;
        this.compteId = compteId;
        this.compteDestId = compteDestId;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTypeOp() { return typeOp; }
    public void setTypeOp(String typeOp) { this.typeOp = typeOp; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public Timestamp getDateOp() { return dateOp; }
    public void setDateOp(Timestamp dateOp) { this.dateOp = dateOp; }

    public int getCompteId() { return compteId; }
    public void setCompteId(int compteId) { this.compteId = compteId; }

    public Integer getCompteDestId() { return compteDestId; }
    public void setCompteDestId(Integer compteDestId) { this.compteDestId = compteDestId; }
}