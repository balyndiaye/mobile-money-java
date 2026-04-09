package service;

import dao.MarchandDAO;

public class MarchandService {
    
    private MarchandDAO marchandDAO = new MarchandDAO();

    public boolean marchandExiste(String nomMarchand) {
        return marchandDAO.findByNom(nomMarchand) != null;
    }
    
}