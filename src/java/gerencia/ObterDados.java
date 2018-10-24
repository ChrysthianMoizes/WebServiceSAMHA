package gerencia;

import dao.AulaDao;
import dao.ProfessorDao;
import dao.TurmaDao;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.json.JSONArray;

public class ObterDados {
    
    public static JSONArray getAulasTurma(int ano, int semestre, int idTurma){
            
        try { 
            JSONArray array = AulaDao.buscarAulasTurma(ano, semestre, idTurma);
            return ObterDados.verificarAnoSemestreDisponivel(ano, semestre, array);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public static JSONArray getAulasProfessor(int ano, int semestre, int idProfessor, String nome){
            
        try { 
            JSONArray array = AulaDao.buscarAulasProfessor(ano, semestre, idProfessor, nome);
            return ObterDados.verificarAnoSemestreDisponivel(ano, semestre, array);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public static JSONArray verificarAnoSemestreDisponivel(int ano, int semestre, JSONArray array){
        
        try {
            FileReader file = new FileReader("C:\\Users\\chrys\\Desktop\\arquivo.txt");
            BufferedReader bf = new BufferedReader(file);
            String linha = bf.readLine();

            String[] vet = linha.split("/");

            int anoArquivo = Integer.valueOf(vet[0]);
            int semestreArquivo = Integer.valueOf(vet[1]);
            
            if(anoArquivo >= ano && semestreArquivo >= semestre)
                return array;
            
            return new JSONArray();
                 
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        return null;
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
