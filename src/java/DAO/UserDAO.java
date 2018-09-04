package DAO;

import Persistence.Conection;
import acessControl.AcessTokenControl;
import com.sun.istack.logging.Logger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * @author Davy Lima
 */
public class UserDAO {

    public static boolean verifyLogin(String email, String password) throws SQLException, Exception {
        Connection conexao = Conection.getConexao();
        String sql = "SELECT EXISTS ( SELECT u.email, u.password FROM user u "
                + "WHERE u.email like ? and u.password = ? ) as 'validacao'";
        PreparedStatement ps;
        ps = conexao.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        rs.first();
        boolean res = rs.getString("validacao").equals("1");
        conexao.close();
        Logger.getLogger(UserDAO.class).log(Level.INFO, "verifyLogin "+Boolean.toString(res));
        return res;
    }
  
    public static String createAccout(String name, String email, String password) throws Exception {
        Connection conexao = Conection.getConexao();
        conexao.setAutoCommit(false);
        String sql = "INSERT INTO user(email, name, password) VALUES(?,?,?)";
        PreparedStatement ps;
        ps = conexao.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, name);
        ps.setString(3, password);
        int executeUpdate = ps.executeUpdate();
        conexao.commit();
        conexao.setAutoCommit(true);
        conexao.close();
        if(executeUpdate == 1){
            return "Cadastrado";
        }else
            return "Não foi possivel realizar o cadastro";
    }
    
}
