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
public class ServletCancelarPropuesta extends HttpServlet {

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
        String titulo= request.getParameter("titulo");
         HttpSession session = request.getSession();
            if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Proponente")){              
            ContUsuario cu=ContUsuario.getInstance();
            
            if(titulo==null){
                
                Collection propuestas=cu.listarpropuestasparacancelar((String) session.getAttribute("nickusuario"));
                request.setAttribute("propuestas", propuestas);
                request.getRequestDispatcher("PRESENTACIONES/cancelarpropuesta.jsp").forward(request, response);
            }
    }else{
request.getRequestDispatcher("PRESENTACIONES/nocorresponde.jsp").forward(request, response);
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
        
        PrintWriter out= response.getWriter();
        String titulo= request.getParameter("titulo");
        ContUsuario cu=ContUsuario.getInstance();
        ContPropuesta cp= ContPropuesta.getInstance();
        cp.propAutomaticas();
        HttpSession session=request.getSession();
        Collection propuestas=cu.listarpropuestasparacancelar((String) session.getAttribute("nickusuario"));
        out.println("<p>");
        
        if(propuestas.contains(titulo)){
            try {
                cp.agregarestadoapropWEB("Cancelada", titulo);
                out.println("Propuesta "+titulo+" Cancelada con exito");
            } catch (Exception ex) {
                Logger.getLogger(ServletSeguir.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
            out.println("Imposible Cancelar la propuesta "+titulo);
        
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
