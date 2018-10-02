package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorDao {
    
    public static String[] identificarProfessor(String email) throws SQLException, Exception{
        
        Connection conexao = ConnectionDatabase.getConexao();
        
            if(conexao != null){
                
                String sql = "SELECT s.id, s.nome FROM servidor s WHERE s.email = ?";
                               
                PreparedStatement preparedStatement;
                preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setString(1, email);
                
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.first();
                String idProfessor = resultSet.getString(1);
                String nome = resultSet.getString(2);
                preparedStatement.close();
                conexao.close();
                
                String[] dados = new String[2];
                dados[0] = idProfessor;
                dados[1] = nome;
                        
                return dados;
        }
            return null;
    }
}
