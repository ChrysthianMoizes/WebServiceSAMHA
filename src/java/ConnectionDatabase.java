
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionDatabase {
    
    public static String status = "Sem conexão";
    
    /**
     * Cria uma nova conxão com o banco de dados configurado
     * @return Nova conexão caso tenha exito em conectar
     */
    
    public static java.sql.Connection getConexao() throws Exception{
        Connection connection = null;
        try{
            //Carrega JDBC padrão
            String driveName = "com.mysql.jdbc.Driver";
            Class.forName(driveName);
            
            //Configurando conexão com o banco
            String serverName = "localhost"; //caminho para o BD
            String dataBase = "samha";
            String url = "jdbc:mysql://"+serverName+"/"+dataBase;
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
    
    /**
     * Retorna o estado da conexão com o banco de dados
     * @return Status
     */
    public static String statusConexao() {
        return status;
    }
    
    /**
    * Fecha a conexão com o banco de dados
    * @return true caso a conexão tenha sido fechada corretamente
    * @return false caso a conexao não tenha sido fechada
    */
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
    
    /**
     * Reinicia a conexão com o banco de dados chamando fecharConexao() e getConexao()
     * @return Nova Conexão com o banco caso tenha exito em reiniciar
     * @return Null caso a conexão não possa ser estabelecida
     */
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
