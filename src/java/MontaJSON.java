import dominio.Aula;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MontaJSON {
    
    public static JSONObject getAulas(){
        
        JSONObject json = new JSONObject();
        
        try {
            
            JSONArray array = BuscaObjetos.buscarAulasTurma(2018, 2, 23); 
            json.put("aulas", array);
            
        } catch (SQLException ex) {
            Logger.getLogger(MontaJSON.class.getName()).log(Level.SEVERE, null, ex);
            try {
                json.put("ERRO", "Erro ao recuperar tipos de provas");
            } catch (JSONException ex1) {
                Logger.getLogger(MontaJSON.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch ( JSONException ex){
            Logger.getLogger(MontaJSON.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(json.length() == 0)
                try {
                    json.put("ERRO", "Erro ao recuperar tipos de provas");
            } catch (JSONException ex) {
                Logger.getLogger(MontaJSON.class.getName()).log(Level.SEVERE, null, ex);
            }
            return json;
        }
    }
}
