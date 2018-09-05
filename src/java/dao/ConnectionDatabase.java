package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionDatabase {
    
    public static String status = "Sem conexão";
    
    public static java.sql.Connection getConexao() throws Exception{
        
        Connection connection = null;
        
        try{
            
            String driveName = "com.mysql.jdbc.Driver";
            Class.forName(driveName);

            String serverName = "localhost";
            String dataBase = "samha";
            String url = "jdbc:mysql://" + serverName + "/" + dataBase;
            String username = "root";
            String password = "";
            
            Level l = null;
            
            connection = DriverManager.getConnection(url, username, password);
            
            if(connection != null){
                status = "STATUS --- Conectado com sucesso!";
                l = Level.INFO;
                connection.setAutoCommit(false);
            }else{
                status = "STATUS --- Não foi possivel conectar";
                l = Level.SEVERE;
                throw new Exception("Não foi possivel conectar");
            }            
            
            Logger.getLogger(ConnectionDatabase.class.getName()).log(l, "DB Status "+status);
            return connection;
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ConnectionDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }
    
    public static String statusConexao() {
        return status;
    }
    
    public static boolean fecharConexao() {
        try {
            ConnectionDatabase.getConexao().close();
            return true;
        } catch (SQLException e) {
            return false;
        } catch (Exception ex) {
            Logger.getLogger(ConnectionDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static java.sql.Connection reiniciarConexao() {
        fecharConexao();
        try {
            return ConnectionDatabase.getConexao();
        } catch (Exception ex) {
            Logger.getLogger(ConnectionDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
