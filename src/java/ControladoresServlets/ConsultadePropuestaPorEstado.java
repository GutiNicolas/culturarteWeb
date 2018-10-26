/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladoresServlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicios.DtContieneArray;
import servicios.DtPropuestaWeb;
import servicios.DtarregloDtPropWeb;
import servicios.ServicioContColabiracion;
import servicios.ServicioContPropuesta;
import servicios.ServicioContusuario;
import servicios.WebServiceContColaboracion;
import servicios.WebServiceContPropuesta;
import servicios.WebServiceContUsusario;

/**
 *
 * @author nicolasgutierrez
 */
public class ConsultadePropuestaPorEstado extends HttpServlet {
 private String direccionWSU = "http://localhost:8580/ServicioU", direccionWSP = "http://localhost:8680/ServicioP", direccionWSC = "http://localhost:8780/ServicioC";
    WebServiceContUsusario WSCUPort;
    WebServiceContPropuesta WSCPPort;
    WebServiceContColaboracion WSCCPort;

    /**
     * funcion inicial que se llama al crear el servlet
     *
     * @param conf
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig conf)
            throws ServletException {
        inicio();
        super.init(conf);
    }

    private void inicio() {
        try {
            ServicioContusuario WSCU = new ServicioContusuario(new URL(direccionWSU));
            WSCUPort = WSCU.getWebServiceContUsusarioPort();
            ServicioContPropuesta WSCP = new ServicioContPropuesta(new URL(direccionWSP));
            WSCPPort = WSCP.getWebServiceContPropuestaPort();
            ServicioContColabiracion WSCC = new ServicioContColabiracion(new URL(direccionWSC));
            WSCCPort = WSCC.getWebServiceContColaboracionPort();
        } catch (MalformedURLException ex) {
            Logger.getLogger(servletRegistrarse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
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
         
            WSCPPort.propAutomaticas();
            if(cat==null){
                DtContieneArray colCat = (DtContieneArray)WSCPPort.listaCategorias();
                Collection<String> categorias= (Collection)colCat.getMyarreglo();
                request.setAttribute("categorias", categorias);
                request.getRequestDispatcher("PRESENTACIONES/consultadepropuestaporestado.jsp").forward(request, response);
            }
            else if(titulo==null && cat!=null){
                DtContieneArray colCat= (DtContieneArray)WSCPPort.listaCategorias();
                Collection<String> categorias= (Collection)colCat.getMyarreglo();
                request.setAttribute("categorias", categorias);
            try {
                    DtarregloDtPropWeb listarPropEnCategoria = WSCPPort.listarPropEnCategoria(cat);
                Collection<DtPropuestaWeb> propuestas= (Collection)listarPropEnCategoria.getArregloPropuestas();
               request.setAttribute("propuestas", propuestas);
            } catch (Exception ex) {
                Logger.getLogger(ConsultadePropuestaPorEstado.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
                request.getRequestDispatcher("PRESENTACIONES/consultadepropuestaporestado.jsp").forward(request, response);               
            }
            else if(titulo!=null){
                try {
                    DtPropuestaWeb dtp =(DtPropuestaWeb) WSCUPort.infoPropuesta(titulo);
                    request.setAttribute("propuesta", dtp);
                    Collection<String> colaboradores=(Collection)dtp.getColaboradores();
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
