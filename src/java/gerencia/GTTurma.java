package gerencia;

import dominio.Turma;
import java.util.ArrayList;
import java.util.List;

public class GTTurma {
    
    public List filtrarTurmasAtivas(List<Turma> listaTurmas, int ano, int semestre){
        
        List turmasAtivas = new ArrayList();
        
        for(Turma turma : listaTurmas){
            if(verificarTurmaAtiva(turma, ano, semestre)){
                turmasAtivas.add(turma);
            }
        }
        return turmasAtivas;
    }
    
    public boolean verificarTurmaAtiva(Turma turma, int anoAtual, int semestreAtual){
        
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
    
    public String obterStringAnoPeriodoAtual(int anoAtual, int semestreAtual, Turma turma){
        
        int anoInicial = turma.getAno();
        int semestreInicial = turma.getSemestre();
        
        String nivel = null;
        String periodoAtual = null;
        
        if(turma.getMatriz().getCurso().getNivel().equals("ENSINO MÉDIO INTEGRADO")){
            nivel = "º ANO";
            periodoAtual = calcularAnoAtual(anoAtual, anoInicial);
        }else{
            nivel = "º PERÍODO";
            periodoAtual = calcularPeriodoAtual(anoAtual, semestreAtual, anoInicial, semestreInicial);
        }

        return periodoAtual + nivel;
    }
    
    public int obterInteiroAnoSemestreAtual(int anoAtual, int semestreAtual, Turma turma){
        
        int anoInicial = turma.getAno();
        int semestreInicial = turma.getSemestre();
        
        String p = null;
        
        if(turma.getMatriz().getCurso().getNivel().equals("ENSINO MÉDIO INTEGRADO"))
            p = calcularAnoAtual(anoAtual, anoInicial);
        else
            p = calcularPeriodoAtual(anoAtual, semestreAtual, anoInicial, semestreInicial);
         
        return Integer.valueOf(p);
    }
    
    public String calcularAnoAtual(int anoAtual, int anoInicial){
        
        int qtAnos = (anoAtual - anoInicial) + 1;
        String ano = String.valueOf(qtAnos);
        
        return ano;
    }
    
    public String calcularPeriodoAtual(int anoAtual, int semestreAtual, int anoInicial, int semestreInicial){
        
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
