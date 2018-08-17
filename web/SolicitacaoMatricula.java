/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */
@WebServlet(name = "SolicitacaoMatricula", urlPatterns = {"/SolicitacaoMatricula"})
public class SolicitacaoMatricula extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String str="";
        Enumeration<String> campos = request.getParameterNames();
        HttpSession session = request.getSession();
        ArrayList<Turma> lista = (ArrayList<Turma>)session.getAttribute("list");
        ArrayList<String> aluno = (ArrayList<String>)session.getAttribute("aluno");
        ArrayList<Turma> listaAux = new ArrayList<>();
        int i = 0;
        for (Enumeration<String> e = campos; e.hasMoreElements();){
            str = (String)e.nextElement();
            for (Turma tur: lista){
                String ok = request.getParameter("checkbox[" + i + "]");
                if (ok != null){
                   listaAux.add(tur);
                }
                i++;
            }
        }
        lista.clear();
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Solicitacao de Matricula</title>");
            out.println( "<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>");
            out.print("<link rel=\"stylesheet\" href=\"styleTable.css\" type=\"text/css\">");
            out.println("<style type=\"text/css\">" +
            "<!-- " +
            "body {background-image:url(http://eppora.com/wp-content/uploads/2015/05/plain-light-color-for-guest-background.jpg);"
                + "background-repeat: no-repeat; background-size: cover;"
                + " color:black; font-size:150%}"+
            
            "//--></style>");
            out.println("</head>");
            out.println("<body>");
            /*out.println("<img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Antu_task-complete.svg/2000px-Antu_task-complete.svg.png\" "
                    + "style=\"width:50px;height:60px;\" class=\"center-block img-responsive foto\" align=\"left\">"
                    + "<center><h4>Operação bem-sucedida!</h4><center>");*/
            out.println("<table align=\"center\"><tr><td>");
            out.println("<img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Antu_task-complete.svg/2000px-Antu_task-complete.svg.png\" "
                    + "style=\"width:50px;height:60px;\"");
            out.println("</td><td valign=\"middle\">Operação bem-sucedida!</td></tr></table>");
            
            out.println("<center><h2>Seus Dados:</h2></center><br/>");
            out.println("<div align=\"center\"><h4>");
            out.println("<b>Aluno: "+aluno.get(1)+"</b><br/>");
            out.println("<b>Matrícula: "+aluno.get(0)+"</b><br/>");
            out.println("<b>Codigo do Curso: "+aluno.get(2)+"</b><br/>");
            out.println("</h4></div>");
            
            out.println("<center><br><h2>Disciplinas Solicitadas</h2></center>");
            out.println("<table align=\"center\" class = \"table table-bordered\"><tr>");
            out.println("<th><b>Código da disciplina</b></th>"
                    +"<th><b>Nome da disciplina</b></th>"
                    +"<th><b>Carga horária semanal</b></th>"
                    +"<th><b>Código da turma</b></th>"
                    +"<th><b>Horários da turma</b></th></tr>");
            for (Turma tur: listaAux){
                out.println("<tr><td>"+ tur.getDiscCod() + "</td>"
                        + "<td>"+tur.getDiscNome()+ "</td>"
                        + "<td>"+tur.getDiscCarga()+ "</td>" 
                        + "<td>"+tur.getTurmaCod()+ "</td>" 
                        + "<td>"+tur.getTurmaHoras()+ "</td></tr><br/>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
