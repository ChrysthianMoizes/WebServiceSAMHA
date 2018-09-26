package gerencia;

import dao.AulaDao;
import dao.ProfessorDao;
import dao.TurmaDao;
import java.util.List;
import org.json.JSONArray;

public class ObterDados {
    
    public static JSONArray getAulasTurma(int ano, int semestre, int idTurma){
            
        try { 
            return AulaDao.buscarAulasTurma(ano, semestre, idTurma); 
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public static JSONArray getAulasProfessor(int ano, int semestre, int idProfessor, String nome){
            
        try { 
            return AulaDao.buscarAulasProfessor(ano, semestre, idProfessor, nome); 
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public static String[] getProfessorPorEmail(String email){
        
        try {
            return ProfessorDao.identificarProfessor(email);
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return null;
    } 
    
    public static JSONArray getTurmasAtivas(int ano, int semestre){
            
        try { 
            
            JSONArray array = TurmaDao.buscarTurmas();
            List listaTurmas = GTTurma.transformarJSONEmListaTurmas(array);
            List listaTurmasAtivas = GTTurma.filtrarTurmasAtivas(listaTurmas, ano, semestre);
            JSONArray json = GTTurma.transformarListaEmJSONTurmas(listaTurmasAtivas);
            
            return json;
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
