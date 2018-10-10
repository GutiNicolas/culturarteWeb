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
import javax.servlet.http.HttpSession;

/**
 *
 * @author nicolasgutierrez
 */
public class Comentar extends HttpServlet {

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
            String propuesta=request.getParameter("titulo");
            ContUsuario cu= ContUsuario.getInstance();
            HttpSession session = request.getSession();
            ContPropuesta cp=ContPropuesta.getInstance();
            cp.propAutomaticas();
            String comentario= request.getParameter("comentario");
            if(comentario==null){
            if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Colaborador")){
            if(propuesta==null){
                Collection<String> props= cu.listarpropuestascolaboradaspor((String) session.getAttribute("nickusuario"));
                request.setAttribute("propuestas", props);
                request.getRequestDispatcher("PRESENTACIONES/consultadepropuestacolaborar.jsp").
					forward(request, response);
            }
            else{
                try {
                    dtPropuesta dtp = cu.infoPropuesta(propuesta);
                    request.setAttribute("propuesta", dtp);
                    Collection<String> colaboradores=dtp.detColaboradores();
                    request.setAttribute("colaboradores", colaboradores);
                    session.setAttribute("titulo", dtp.getTitulo());
                } catch (Exception ex) {
                    Logger.getLogger(ConsultadePropuesta.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                
                request.getRequestDispatcher("PRESENTACIONES/comentar.jsp").
					forward(request, response);
            }
            }else{
                request.getRequestDispatcher("PRESENTACIONES/nocorresponde.jsp").forward(request, response);
            }
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
       // processRequest(request, response);
        PrintWriter out= response.getWriter();
        String comentario= request.getParameter("comentario");
        HttpSession session = request.getSession();
        String titulo= (String) session.getAttribute("titulo");
        ContUsuario cu= ContUsuario.getInstance();
       // (String) session.getAttribute("nickusuario")
        if(comentario.isEmpty()==false){
            cu.agregarcomentarioapropuesta((String) session.getAttribute("nickusuario"), titulo, comentario);
            
            out.println("Comentario agregado");
        }else{
            out.println("El comentario no puede ser vacio");
        }
        
        
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
