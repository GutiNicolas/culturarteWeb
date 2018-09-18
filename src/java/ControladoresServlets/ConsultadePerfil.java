/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladoresServlets;

import Logica.ContUsuario;
import Logica.dtColProp;
import Logica.dtColaborador;
import Logica.dtProponente;
import Logica.dtSigoA;
import Logica.dtUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author nicolasgutierrez
 */
public class ConsultadePerfil extends HttpServlet {

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
            String nick=request.getParameter("nickname");
            String propio=request.getParameter("usuario");
            HttpSession session= request.getSession();
            request.removeAttribute("usuarios");
            request.removeAttribute("colaboradas");
            request.removeAttribute("colabscompletas");
            request.removeAttribute("propuestas");
            request.removeAttribute("ingresadass");
            request.removeAttribute("usuario");
            request.removeAttribute("misseguidores");
            request.removeAttribute("misseguidos");
            request.removeAttribute("favoritas");
            
            if(propio!=null && propio.equals("yes")){
                nick=(String) session.getAttribute("nickusuario");
            } 
            ContUsuario cu= ContUsuario.getInstance(); 
            
            if(nick==null){
                Collection<String> usus= cu.listarusuarios("");
                request.setAttribute("usuarios", usus);
                request.getRequestDispatcher("PRESENTACIONES/consultadeperfil.jsp").
					forward(request, response);
            }         
            else{  
                
                    dtUsuario dtu = cu.infoUsuarioGeneral(nick);
                    Collection<String> misseguidores= cu.listarmisseguidores(nick);
                    Collection<dtSigoA> misseguidos= cu.listarmisseguidos(nick);
                    Collection<String> favoritas=cu.mispropuestasfavoritas(nick);
                    if(dtu instanceof dtColaborador){
                        Collection<String> colaboradas=cu.listarColaboraciones(nick);
                        request.setAttribute("colaboradas", colaboradas);
                        Collection<dtColProp> colaboradascompletas= cu.listarmiscolaboraciones(nick);
                        request.setAttribute("colabscompletas", colaboradascompletas);
                    }
                    if(dtu instanceof dtProponente){
                        Collection<String> propuestas=cu.mispropuestasaceptadas(nick);
                        request.setAttribute("propuestas", propuestas);
                        Collection<String> propuestasingresadas=cu.mispropuestasaingresadas(nick);
                        request.setAttribute("ingresadass", propuestasingresadas);
                    }
                    request.setAttribute("usuario", dtu);
                    request.setAttribute("misseguidores", misseguidores);
                    request.setAttribute("misseguidos", misseguidos);
                    request.setAttribute("favoritas",favoritas);
                    request.getRequestDispatcher("PRESENTACIONES/perfildelusuario.jsp").
					forward(request, response);
                    
                                            
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
