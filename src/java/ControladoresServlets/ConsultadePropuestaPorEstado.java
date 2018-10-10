/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladoresServlets;

import Logica.ContPropuesta;
import Logica.ContUsuario;
import Logica.dtPropuesta;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nicolasgutierrez
 */
public class ConsultadePropuestaPorEstado extends HttpServlet {

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
            String cat = request.getParameter("selle");
            ContUsuario cu=ContUsuario.getInstance();
            ContPropuesta cp=ContPropuesta.getInstance();
            cp.propAutomaticas();
            if(cat==null){
                Collection<String> categorias= cp.listarCategorias("");
                request.setAttribute("categorias", categorias);
                request.getRequestDispatcher("PRESENTACIONES/consultadepropuestaporestado.jsp").forward(request, response);
            }
            else if(titulo==null && cat!=null){
                Collection<String> categorias= cp.listarCategorias("");
                request.setAttribute("categorias", categorias);
            try {
                Collection<dtPropuesta> propuestas= cu.listarpropuestasencategoria(cat);
               request.setAttribute("propuestas", propuestas);
            } catch (Exception ex) {
                Logger.getLogger(ConsultadePropuestaPorEstado.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
                request.getRequestDispatcher("PRESENTACIONES/consultadepropuestaporestado.jsp").forward(request, response);               
            }
            else if(titulo!=null){
                try {
                    dtPropuesta dtp = cu.infoPropuesta(titulo);
                    request.setAttribute("propuesta", dtp);
                    Collection<String> colaboradores=dtp.detColaboradores();
                    request.setAttribute("colaboradores", colaboradores); 
                } catch (Exception ex) {
                    Logger.getLogger(ConsultadePropuesta.class.getName()).log(Level.SEVERE, null, ex);
                }
             
                request.getRequestDispatcher("PRESENTACIONES/informacionpropuesta.jsp").
					forward(request, response);                
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
