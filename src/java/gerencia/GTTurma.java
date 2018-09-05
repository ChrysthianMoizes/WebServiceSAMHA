package gerencia;

import dominio.Curso;
import dominio.MatrizCurricular;
import dominio.Turma;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GTTurma {
    
    public static List transformarJSONEmListaTurmas(JSONArray array) throws JSONException{
        
        List turmas = new ArrayList();
        
        for(int i = 0; i < array.length(); i++){
            
            Turma turma = new Turma();
            
            JSONObject t = array.getJSONObject(i);
            
            turma.setId(t.getInt("id"));
            turma.setNome(t.getString("nome"));
            turma.setAno(t.getInt("ano"));
            turma.setSemestre(t.getInt("semestre"));
            
            Curso curso = new Curso();
            
            JSONObject c = t.getJSONObject("curso");
            curso.setQtPeriodos(c.getInt("qtPeriodos"));
            curso.setNivel(c.getString("nivel"));
            
            MatrizCurricular matriz = new MatrizCurricular();
            
            matriz.setCurso(curso);
            turma.setMatriz(matriz);
            
            turmas.add(turma);
        }
        
        return turmas;
    }
    
    public static JSONArray transformarListaEmJSONTurmas(List<Turma> lista) throws JSONException{
        
        JSONArray array = new JSONArray();
        
        for(Turma turma : lista){
            
            JSONObject json = new JSONObject();
            
            json.put("id", turma.getId());
            json.put("nome", turma.getNome());
            
            array.put(json);
        }

        return array;
    }
    
    public static List filtrarTurmasAtivas(List<Turma> listaTurmas, int ano, int semestre){
        
        List turmasAtivas = new ArrayList();
        
        for(Turma turma : listaTurmas){
            if(verificarTurmaAtiva(turma, ano, semestre)){
                turmasAtivas.add(turma);
            }
        }
        return turmasAtivas;
    }
    
    public static boolean verificarTurmaAtiva(Turma turma, int anoAtual, int semestreAtual){
        
        int anoInicial = turma.getAno();
        int semestreInicial = turma.getSemestre();
        String p = null;
        
        if(turma.getMatriz().getCurso().getNivel().equals("ENSINO MÉDIO INTEGRADO")){
         
            p = calcularAnoAtual(anoAtual, anoInicial);
        }else{

            p = calcularPeriodoAtual(anoAtual, semestreAtual, anoInicial, semestreInicial);
        }
        
        int anoPeriodo = Integer.valueOf(p);
        
        if(anoPeriodo > turma.getMatriz().getCurso().getQtPeriodos() || anoPeriodo < 1)
            return false;
        
        return true;
    }

    public static String calcularAnoAtual(int anoAtual, int anoInicial){
        
        int qtAnos = (anoAtual - anoInicial) + 1;
        String ano = String.valueOf(qtAnos);
        
        return ano;
    }
    
    public static String calcularPeriodoAtual(int anoAtual, int semestreAtual, int anoInicial, int semestreInicial){
        
        int qtAnos = ((anoAtual - anoInicial) * 2);
        
        if(semestreAtual == semestreInicial){
            qtAnos+=1;
        }else if(semestreAtual == 2 && semestreInicial == 1){
            qtAnos+=2;
        }
        
        String periodo = String.valueOf(qtAnos);
        
        return periodo;
    }
    
}
