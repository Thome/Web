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
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(name = "NewServlet1", urlPatterns = {"/NewServlet1"})
public class ListaAlunos extends HttpServlet {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        
    static final String DATABASE_URL = "jdbc:postgresql://localhost/postgres";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String saida = null;
        String media = null;
        Connection conn = null;
        Statement statement = null;
        Statement statement1 = null;
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ListaAlunos</title>");
        out.println("<style type=\"text/css\">" +
            "<!-- " +
            "body {background-color:beige; color:black; font-size:90%}"+
            "td   {font-size: 90%; background-color:white; color: black}" +
            "//--></style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Alunos aprovados:</h1><br/>");
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DATABASE_URL, "postgres", "thome");
            statement = conn.createStatement();
            statement1 = conn.createStatement();
            statement.executeUpdate("set search_path to public");
            ResultSet resultSet = statement.executeQuery(
                 "SELECT * FROM turma2 "
                         + "where nota_final >= 5");
            
            ResultSet resultSetMedia = statement1.executeQuery(
                    "select avg(nota_final) as media from turma2 where nota_final >= 5");
            out.println("<table border='1' >");
            out.println("<tr><th><b>Matricula</b></th>"
                    + "<th><b>Nome</b></th>"
                    + "<th><b>Curso</b></th>"
                    + "<th><b>Nota Final</b></th></tr>");
            while(resultSet.next()){
                out.println("<tr><td>"+resultSet.getString("matricula")+"</td>"
                        + "<td>"+resultSet.getString("nome")+"</td>"
                        + "<td>"+resultSet.getString("curso")+"</td>"
                        + "<td>"+resultSet.getString("nota_final")+"</td></tr>");
            }
            out.println("</table>");
            resultSetMedia.next();
            out.println("<br/><hr/><br/>Media dos alunos aprovados: "+resultSetMedia.getString("media"));
            
            
            conn.close();
            out.println("</body>");
            out.println("</html>");
            
        /*} catch (SQLException s) {
                out.println("SQL Error: " + s.toString() + " "
                    + s.getErrorCode() + " " + s.getSQLState());    
        */} catch (Exception e) {
                out.println("Error: " + e.toString()
                    + e.getMessage());
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
