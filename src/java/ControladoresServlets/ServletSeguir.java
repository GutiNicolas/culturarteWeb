/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladoresServlets;

import Logica.ContPropuesta;
import Logica.ContUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nicolasgutierrez
 */
public class ServletSeguir extends HttpServlet {

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
    //    try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();
            if(session.getAttribute("rol")!=null){  
            String nickaseguir= request.getParameter("nickaseguir");
            ContUsuario cu=ContUsuario.getInstance();
            if(nickaseguir==null){
                Collection usuarios=cu.listarusuarios("");
                request.setAttribute("usuarios", usuarios);
                request.getRequestDispatcher("PRESENTACIONES/seguirusuario.jsp").forward(request, response);
            }
            }else{
                request.getRequestDispatcher("PRESENTACIONES/nocorresponde.jsp").forward(request, response);
            }
      //  }
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

        PrintWriter out = response.getWriter();
        String nickaseguir = request.getParameter("nickaseguir");
        ContUsuario cu = ContUsuario.getInstance();
        HttpSession session = request.getSession();
        Collection usuariosseguidos = cu.cargarlosseguidospor((String) session.getAttribute("nickusuario"));
        out.println("<p>");

        if (nickaseguir.equals(session.getAttribute("nickusuario")) == false) {
            if (usuariosseguidos.contains(nickaseguir) == false) {
                try {
                    cu.seguir((String) session.getAttribute("nickusuario"), nickaseguir);
                    out.println("Usuario " + nickaseguir + " seguido con exito");
                } catch (Exception ex) {
                    Logger.getLogger(ServletSeguir.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                out.println("Ya sigues al usuario " + nickaseguir);
            }
        } else {
            out.println("No puedes seguirte tu mismo, " + nickaseguir);
        }

        out.println("</p>");
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
