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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 *
 * @author monteiro
 */
@WebServlet(name = "newservlet1", urlPatterns = {"/newservlet1"})
public class newservlet1 extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        
    static final String DATABASE_URL = "jdbc:postgresql://localhost/postgres";
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
        PrintWriter out = response.getWriter();
        String saida = null;
        Connection connection = null; // gerencia a conexÃ£o
        Statement statement = null;  
        Statement statement1 = null;
        try {
         Class.forName( "org.postgresql.Driver" ); // carrega classe de driver do banco de dados
 
         // estabelece conexao com o banco de dados
         connection =                                                     
            DriverManager.getConnection( DATABASE_URL, "postgres", "thome" );
         // cria Statement para consultar banco de dados
         statement = connection.createStatement();
         statement1 = connection.createStatement();
         statement1.executeUpdate("set search_path to public");
         // consulta o banco de dados 
         ResultSet resultSet = statement.executeQuery(
                 "SELECT * FROM turma2 WHERE nota_final >= 5");       
         // processa resultados da consulta
         ResultSetMetaData metaData = resultSet.getMetaData();
         int numberOfColumns = metaData.getColumnCount();     
         saida = "<h1>Relacao dos alunos:</h1><br /><hr /><br />";
         saida = saida + "<tr> <th>Matricula</th>  <th>Nome</th> <th>Curso</th> <th>Nota_Final</th> </tr>";
         while(resultSet.next()){
            
            saida = saida 
                +"<tr>"
                +"<td>" +resultSet.getString("matricula")+"</td>"
                +"<td>" +resultSet.getString("nome")+"</td>"
                +"<td>" +resultSet.getString("curso")+"</td>"
                +"<td>" +resultSet.getString("nota_final")+"</td>"
                +"</tr>";
                
         }
         // consulta o banco de dados 
         ResultSet resultSet2 = statement.executeQuery(
                 "SELECT avg(nota_final) FROM turma2 WHERE nota_final >=5 ");       
         // processa resultados da consulta
         ResultSetMetaData metaData2 = resultSet2.getMetaData();            
         saida = "<h1>Media dos alunos:</h1><br /><hr /><br />";
         saida = saida + "<tr> <th>Media</th> </tr>";
         while(resultSet2.next()){
            
            saida = saida 
                +"<tr>"
                +"<td>" +resultSet2.getString(" Media")+"</td>"             
                +"</tr>";
                
         }
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Atividade 8</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Atividade 8</h1>");
        out.println("<table border='1' >" + saida + "</table>");
        out.println("</body>");
        out.println("</html>");
   
        out.close();
      }  // fim do try
      catch (SQLException | ClassNotFoundException sqlException)                                
      {                                                                  
         sqlException.printStackTrace();
         return;                                               
      }
        // fim do catch
         // fim do catch
      finally // assegura que a instruÃ§Ã£o e conexÃ£o sÃ£o fechadas adequadamente
      {                                                             
         try                                                       
         {                                                          
            statement.close();                                      
            connection.close();                                     
         } // fim do try
         catch ( Exception exception )                              
         {                                                          
            exception.printStackTrace();                            
            return;                                       
         } // fim do catch
      } // fim do finally                                             
   
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
