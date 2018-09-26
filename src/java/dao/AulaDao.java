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

public class AulaDao {
    
    public static JSONArray buscarAulasTurma(int ano, int semestre, int idTurma) throws SQLException, Exception{
        
        Connection conexao = ConnectionDatabase.getConexao();
        
            if(conexao != null){
                
                String sql = "SELECT a.id, a.dia, a.numero, a.turno, al.id, d.id, d.sigla, s.id, s.nome, s2.id, s2.nome FROM aula a "
                        + "JOIN oferta o ON o.id = a.oferta_id "
                        + "JOIN turma t ON o.turma_id = t.id "
                        + "JOIN alocacao al ON al.id = a.alocacao_id "
                        + "JOIN disciplina d ON d.id = al.disciplina_id "
                        + "JOIN servidor s ON s.id = al.professor1_id "
                        + "LEFT JOIN servidor s2 on s2.id = al.professor2_id "
                        + "WHERE o.ano = ? AND o.semestre = ? AND t.id = ?";
                               
                PreparedStatement preparedStatement;
                preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setInt(1, ano);
                preparedStatement.setInt(2, semestre);
                preparedStatement.setInt(3, idTurma);
                
                ResultSet resultSet = preparedStatement.executeQuery();
                
                JSONArray array = new JSONArray();
                
                while(resultSet.next()){
                    array.put(gerarJSONAulasTurma(resultSet));
                }
                
                preparedStatement.close();
                conexao.close();
                
                return array;
        }
            return null;
    }
    
    public static JSONObject gerarJSONAulasTurma(ResultSet resultSet){
        
        JSONObject json = new JSONObject();
        
        try {
            
            json.put("id_aula", resultSet.getString(1))
                .put("dia", resultSet.getString(2))
                .put("numero", resultSet.getString(3))
                .put("turno", resultSet.getString(4))
                    
                .put("alocacao", new JSONObject()
                    .put("id_alocacao", resultSet.getString(5))

                    .put("disciplina", new JSONObject()
                        .put("id_disciplina", resultSet.getString(6))
                        .put("sigla", resultSet.getString(7))  
                        )

                    .put("professor1", new JSONObject()
                        .put("id_professor1", resultSet.getString(8))
                        .put("nome_professor1", resultSet.getString(9))  
                        )

                    .put("professor2", new JSONObject()                       
                        .put("id_professor2", resultSet.getString(10))
                        .put("nome_professor2", resultSet.getString(11))  
                    )
                );        
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (JSONException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
    public static JSONArray buscarAulasProfessor(int ano, int semestre, int idProfessor, String nome) throws SQLException, Exception{
        
        Connection conexao = ConnectionDatabase.getConexao();
        
            if(conexao != null){
                
                String sql = "SELECT a.dia, a.numero, a.turno, d.sigla, t.nome FROM aula a "
                        + "JOIN oferta o ON o.id = a.oferta_id "
                        + "JOIN turma t ON o.turma_id = t.id "
                        + "JOIN alocacao al ON al.id = a.alocacao_id "
                        + "JOIN disciplina d ON d.id = al.disciplina_id "
                        + "JOIN servidor s ON s.id = al.professor1_id or s.id = al.professor2_id "
                        + "WHERE o.ano = ? AND o.semestre = ? AND s.id = ?";
                               
                PreparedStatement preparedStatement;
                preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setInt(1, ano);
                preparedStatement.setInt(2, semestre);
                preparedStatement.setInt(3, idProfessor);
                
                ResultSet resultSet = preparedStatement.executeQuery();
                
                JSONArray array = new JSONArray();
                JSONObject professor = new JSONObject().put("nome", nome);
                array.put(professor);
                
                while(resultSet.next()){
                    array.put(gerarJSONAulasProfessor(resultSet));
                }
                
                preparedStatement.close();
                conexao.close();
                
                return array;
        }
            return null;
    }
    
    public static JSONObject gerarJSONAulasProfessor(ResultSet resultSet){
        
        JSONObject json = new JSONObject();
        
        try {
            
            json.put("dia", resultSet.getString(1))
                .put("numero", resultSet.getString(2))
                .put("turno", resultSet.getString(3))
                    
                .put("alocacao", new JSONObject()

                    .put("disciplina", new JSONObject()
                        .put("sigla", resultSet.getString(4))  
                        )
                ).put("oferta", new JSONObject()
                    .put("turma", new JSONObject()
                        .put("nome", resultSet.getString(5))
                    )
                );        
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (JSONException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
}
