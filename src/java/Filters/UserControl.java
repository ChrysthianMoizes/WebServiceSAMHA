package Filters;

import DAO.UserDAO;

/**
 *
 * @author Davy Lima
 */
public class UserControl {

    /**
     * 
     * @param email
     * @param password
     * @return 
     */
    static boolean validateLogin(String email, String password) throws Exception {
        return UserDAO.verifyLogin(email, password);
    }

    static String cadastrarNovoUsuario(String name, String email, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
