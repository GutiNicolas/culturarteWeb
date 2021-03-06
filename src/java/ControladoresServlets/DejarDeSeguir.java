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
import javax.servlet.http.HttpSession;
import servicios.DtContieneArray;
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
public class DejarDeSeguir extends HttpServlet {

    private static Propiedades prop = Propiedades.getInstance();
    private String direccionWSU = prop.getWsU(), direccionWSP = prop.getWsP(), direccionWSC = prop.getWsC();
    WebServiceContUsusario WSCUPort;//"http://localhost:8580/ServicioU"
    WebServiceContPropuesta WSCPPort;//"http://localhost:8680/ServicioP"
    WebServiceContColaboracion WSCCPort;//"http://localhost:8780/ServicioC"

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
        // processRequest(request, response);
        HttpSession session = request.getSession();
        if (session.getAttribute("rol") != null) {
            String nickadejardeseguir = request.getParameter("nickadejardeseguir");

            if (nickadejardeseguir == null) {
                DtContieneArray usuSegCol = (DtContieneArray) WSCUPort.cargarLosSegPor((String) session.getAttribute("nickusuario"));
                Collection usuarios = (Collection) usuSegCol.getMyarreglo();
                request.setAttribute("usuarios", usuarios);
                request.getRequestDispatcher("PRESENTACIONES/dejardeseguirusuario.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("PRESENTACIONES/nocorresponde.jsp").forward(request, response);
        }
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
        String nickadejardeseguir = request.getParameter("nickadejardeseguir");

        HttpSession session = request.getSession();
        DtContieneArray usuColSeg = (DtContieneArray) WSCUPort.cargarLosSegPor((String) session.getAttribute("nickusuario"));
        Collection usuariosseguidos = (Collection) usuColSeg.getMyarreglo();
        out.println("<p>");

        if (usuariosseguidos.contains(nickadejardeseguir)) {
            try {
                WSCUPort.dejarDeSeguir((String) session.getAttribute("nickusuario"), nickadejardeseguir);
                out.println("Usuario " + nickadejardeseguir + " dejado de seguir con exito");
            } catch (Exception ex) {
                Logger.getLogger(ServletSeguir.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            out.println("No sigues al usuario " + nickadejardeseguir);
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
