/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
@WebServlet(name = "NewServlet", urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        
    static final String DATABASE_URL = "jdbc:postgresql://localhost/postgres";
    static final String usuario = "postgres";
    static final String senha = "postgres";
    
    protected String dataConversion(String dataCod){
        if (dataCod.length() < 4 || dataCod==null) {
            return "";
        }
        String data = "";
        char dia = dataCod.charAt(0);
        char turno = dataCod.charAt(1);
        dataCod = dataCod.substring(2);
        if (dia=='2') data = data + "Seg ";
        if (dia=='3') data = data + "Ter ";
        if (dia=='4') data = data + "Qua ";
        if (dia=='5') data = data + "Qui ";
        if (dia=='6') data = data + "Sex ";
        
        if (turno == 'M'){
                data = data + Integer.toString(6 + dataCod.charAt(0)-'0') + "-"
                        + Integer.toString(6 + dataCod.charAt(0)-'0' + dataCod.length());
        } else if (turno == 'T'){
                data = data + Integer.toString(12 + dataCod.charAt(0)-'0') + "-"
                        + Integer.toString(12 + dataCod.charAt(0)-'0' + dataCod.length());
        } else if (turno == 'N'){
            if (dataCod.charAt(0) == '1' && dataCod.length() == 2)
                data = data + "18:50-20:30";
            else if (dataCod.charAt(0) == '1' && dataCod.length() == 4)
                data = data + "18:50-22:20";
            else if (dataCod.charAt(0) == '3' && dataCod.length() == 2)
                data = data + "20:40-22:20";
        }
        return data;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        ArrayList<Turma> lista = new ArrayList<>();
        ArrayList<String> aluno = new ArrayList<>();
        String matricula = request.getParameter("matricula");
        Connection conn;
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ServletMySql33</title>");
        out.println( "<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>");
        out.print("<link rel=\"stylesheet\" href=\"styleTable.css\" type=\"text/css\">");
        out.println("<style type=\"text/css\">" +
            "<!-- " +
            "body {background-image:url(http://eppora.com/wp-content/uploads/2015/05/plain-light-color-for-guest-background.jpg);"
                + "background-repeat: no-repeat; background-size: cover;"
                + " color:black; font-size:90%}"+
            
            "//--></style>");
        out.println("</head>");
        out.println("<body>");
        
        out.println("<center><h2>Disciplinas Ofertadas</h1></center><br/>");
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DATABASE_URL, usuario, senha);
            Statement st = conn.createStatement();
            st.executeUpdate("set search_path to matriculas");
            
            ResultSet recAluno = st.executeQuery(
                    "select * from alunos "
                    + "where (matricula='"+matricula+"')");
            while(recAluno.next()){
                aluno.add(recAluno.getString(1));
                aluno.add(recAluno.getString(2));
                aluno.add(recAluno.getString(3));
            }
            ResultSet rec = st.executeQuery(
                    "select * from disciplinas md join turmas mt "
                            + "on md.coddisc = mt.disciplina"
                            + " order by coddisc, codturma");
                       
            out.println("<table align=\"center\" class = \"table table-bordered\"><tr>");
            out.println("<th><b>Código da disciplina</b></th>"
                    +"<th><b>Nome da disciplina</b></th>"
                    +"<th><b>Carga horária semanal</b></th>"
                    +"<th><b>Código da turma</b></th>"
                    +"<th><b>Horários da turma</b></th>"
                    +"<th><b>Selecionar disciplina</b></th></tr>");
            out.println("<form action=\"SolicitacaoMatricula\"");
            int i = 0;
            while(rec.next()) {
                Turma tur = new Turma();
                tur.setDiscCod(rec.getString(1));
                tur.setDiscNome(rec.getString(2));
                tur.setDiscCarga(rec.getString(3));
                tur.setTurmaCod(rec.getString(5));
                String data = rec.getString(6) + " "
                        + rec.getString(7) + " "
                        + rec.getString(8);
                tur.setTurmaHoras(data);
                out.println("<tr><td>"+ tur.getDiscCod() + "</td>"
                        + "<td>"+tur.getDiscNome()+ "</td>"
                        + "<td>"+tur.getDiscCarga()+ "</td>" 
                        + "<td>"+tur.getTurmaCod()+ "</td>" 
                        /*+ "<td>"+rec.getString(5)+ "</td>"
                        + "<td>"+rec.getString(6)+ "</td>"        
                        + "<td>"+rec.getString(7)+ "</td>"*/
                         + "<td>"+tur.getTurmaHoras()+ "</td>"      
                        + "<td><input type=\"checkbox\" name=\"checkbox["
                        + i + "]\" value=" + i + "</td></tr>");
                lista.add(tur);
                i++;
            }
            out.println("</table>");
            out.println("<center><input type='submit' value='Matricular' name='enviar' class='btn btn-success'/></center>");
            st.close();            
        } catch (SQLException s) {
                out.println("SQL Error: " + s.toString() + " "
                    + s.getErrorCode() + " " + s.getSQLState());    
        } catch (Exception e) {
                out.println("Error: " + e.toString()
                    + e.getMessage());
        }
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        session.setAttribute("list", lista);
        session.setAttribute("aluno", aluno);
        out.close();
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
