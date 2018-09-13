/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladoresServlets;

import Logica.ContUsuario;
import Logica.dtColaborador;
import Logica.dtProponente;
import Logica.dtUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nicolasgutierrez
 */
@WebServlet(name = "ServletLogin", urlPatterns = {"/ServletLogin"})
public class ServletLogin extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session= request.getSession();
            ContUsuario cU= ContUsuario.getInstance();            
            String usuario= request.getParameter("nick");
            String password= request.getParameter("pass");
            dtUsuario dtu=cU.usuarioLogin(usuario);
            if(dtu!=null){
                if(dtu.getPass().equals(password)){
                    if(dtu instanceof dtColaborador){ //pasarlo a tarea 1
                        session.setAttribute("nickusuario", dtu.getNickname());
                        String col="Colaborador";
                        session.setAttribute("rol", col);
                        response.sendRedirect("index.jsp");
                    }
                    if(dtu instanceof dtProponente){
                        session.setAttribute("nickusuario", dtu.getNickname());
                        String prop="Proponente";
                        session.setAttribute("rol", prop);
                        response.sendRedirect("index.jsp");                    
                    }
                }
                else{//contrasenia erronea  
                    response.sendRedirect("PRESENTACIONES/login.jsp?error=pm");
                }
                  
            }
            else{  //no existe el usuario
                response.sendRedirect("PRESENTACIONES/login.jsp?error=nu");
            }
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
