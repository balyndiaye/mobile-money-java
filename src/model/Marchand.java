package model;

public class Marchand {
    private int id;
    private String nomCommerce;
    private String codeMarchand; // Ex: 123456
    private int compteId;

    public Marchand() {}

    public Marchand(int id, String nomCommerce, String codeMarchand, int compteId) {
        this.id = id;
        this.nomCommerce = nomCommerce;
        this.codeMarchand = codeMarchand;
        this.compteId = compteId;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomCommerce() { return nomCommerce; }
    public void setNomCommerce(String nomCommerce) { this.nomCommerce = nomCommerce; }
    public String getCodeMarchand() { return codeMarchand; }
    public void setCodeMarchand(String codeMarchand) { this.codeMarchand = codeMarchand; }
    public int getCompteId() { return compteId; }
    public void setCompteId(int compteId) { this.compteId = compteId; }
}