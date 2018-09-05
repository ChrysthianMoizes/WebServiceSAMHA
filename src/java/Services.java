import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class Services extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        String tipoReq = request.getHeader("tipo");
        System.out.println("=============================== tipo : "+tipoReq);
        
        PrintWriter out = response.getWriter();
        
        try {
            
            switch(tipoReq){
                case "aulas_turma":
                    buscarAulasTurma(request, response, out);
                    break;
                    
                case "turmas_ativas":
                    buscarTurmasAtivas(request, response, out);
                    break;
            }
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    
    private void buscarTurmasAtivas(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws JSONException, IOException {
        
        int ano = Integer.parseInt(request.getParameter("ano"));
        int semestre = Integer.parseInt(request.getParameter("semestre"));
        
        JSONObject jsonTipoProva = MontaJSON.getAulas();
        out.print(jsonTipoProva);
    }

    private void buscarAulasTurma(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException, JSONException {
        
//        int ano = Integer.parseInt(request.getParameter("ano"));
//        int semestre = Integer.parseInt(request.getParameter("semestre"));
//        int idTurma = Integer.parseInt(request.getParameter("turma_id"));
//        
//        System.out.println("=============================== ano : " + ano);

//        String js = IOUtils.toString(request.getInputStream(), "UTF-8");
//        JSONObject json = new JSONObject(js);
//        String tipoProva = json.getString("tipoProva");
//        JSONObject jsonProva = JsonProvaFactory.getProva(tipoProva);
//        out.print(jsonProva);

        JSONObject jsonTipoProva = MontaJSON.getAulas();
        out.print(jsonTipoProva);
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
