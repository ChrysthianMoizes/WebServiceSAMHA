package visao;

import gerencia.ObterDados;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Services extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        String tipoReq = request.getHeader("tipo");
        identificarRequisicao(tipoReq, request, response);
    }
    
    public void identificarRequisicao(String requisicao, HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        PrintWriter out = response.getWriter();
        
        try {
            
            switch(requisicao){
                
                case "aulas_turma":
                    buscarAulasTurma(request, response, out);
                    break;
                    
                case "turmas_ativas":
                    buscarTurmasAtivas(request, response, out);
                    break;
                    
                case "aulas_professor":
                    buscarAulasProfessor(request, response, out);
                    break;
            }
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private void buscarAulasTurma(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException, JSONException {
 
        String body = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONObject js = new JSONObject(body);
        
        int ano = Integer.parseInt(js.getString("ano"));
        int semestre = Integer.parseInt(js.getString("semestre"));
        int idTurma = Integer.parseInt(js.getString("turma_id"));
        
        JSONArray array = ObterDados.getAulasTurma(ano, semestre, idTurma);
        out.print(array);
    }
    
    private void buscarAulasProfessor(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException, JSONException {
 
        String body = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONObject js = new JSONObject(body);
        
        int ano = Integer.parseInt(js.getString("ano"));
        int semestre = Integer.parseInt(js.getString("semestre"));
        
        String email = js.getString("email");
        String[] dados = ObterDados.getProfessorPorEmail(email);
        
        int idProfessor = Integer.parseInt(dados[0]);
        String nome = dados[1];
        
        if(dados != null){
            JSONArray array = ObterDados.getAulasProfessor(ano, semestre, idProfessor, nome);
            out.print(array);
        }
    }
    
    private void buscarTurmasAtivas(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws JSONException, IOException {
        
        String body = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONObject js = new JSONObject(body);
        
        int ano = Integer.parseInt(js.getString("ano"));
        int semestre = Integer.parseInt(js.getString("semestre"));
        
        JSONArray array = ObterDados.getTurmasAtivas(ano, semestre);
        out.print(array);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
