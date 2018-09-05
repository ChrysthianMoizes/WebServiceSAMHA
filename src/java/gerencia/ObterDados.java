package gerencia;

import dao.AulaDao;
import dao.TurmaDao;
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
    
    public static JSONArray getTurmasAtivas(int ano, int semestre){
            
        try { 
            JSONArray array = TurmaDao.buscarTurmas(); 
            return null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
