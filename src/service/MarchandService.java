package service;

import dao.MarchandDAO;
import model.Marchand;

public class MarchandService {
    
    private MarchandDAO marchandDAO = new MarchandDAO();

    public boolean marchandExiste(String nomMarchand) {
        return marchandDAO.findByNom(nomMarchand) != null;
    }
    
}