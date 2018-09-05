package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TurmaDao {
    
    public static JSONArray buscarTurmas() throws SQLException, Exception{
        
        Connection conexao = ConnectionDatabase.getConexao();
        
            if(conexao != null){
                
                String sql = "SELECT t.id, t.nome, t.ano, t.semestre, c.qtPeriodos, c.nivel "
                        + "FROM turma t "
                        + "JOIN matriz_curricular m ON m.id = t.matriz_curricular_id "
                        + "JOIN curso c ON c.id = m.curso_id";
                               
                PreparedStatement preparedStatement;
                preparedStatement = conexao.prepareStatement(sql);
                
                ResultSet resultSet = preparedStatement.executeQuery();
                
                JSONArray array = new JSONArray();
                
                while(resultSet.next()){
                    array.put(gerarJSONTurmas(resultSet));
                }
                
                preparedStatement.close();
                conexao.close();
                
                return array;
        }
            return null;
    }
    
    public static JSONObject gerarJSONTurmas(ResultSet resultSet){
        
        JSONObject json = new JSONObject();
        
        try {
            
            json.put("id", resultSet.getString(1));
            json.put("nome", resultSet.getString(2));
            json.put("ano", resultSet.getString(3));
            json.put("semestre", resultSet.getString(4));
            
            json.put("curso", new JSONObject()
                .put("qtPeriodos", resultSet.getString(5))
                .put("nivel", resultSet.getString(6)));
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (JSONException ex) {
            Logger.getLogger(TurmaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    } 
}
