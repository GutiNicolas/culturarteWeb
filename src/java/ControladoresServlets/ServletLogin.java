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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servicios.DtContieneArray;
import servicios.DtUsuarioWeb;

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
public class ServletLogin extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();

            String usuario = request.getParameter("nick");
            String password = request.getParameter("pass");
            if (session.getAttribute("rol") == null) {
                if (usuario != null) {

                    DtUsuarioWeb dtu = (DtUsuarioWeb) WSCUPort.usuarioLoginSN(usuario);
                    if (dtu != null) {
                        if (dtu.getPass().equals(password)) {
                            session.setAttribute("nickusuario", dtu.getNickname()); //dtu.getNickname()
                            session.setAttribute("rol", dtu.getRol());  //dtu.getRol()
                            //    response.sendRedirect("index.jsp");
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                            // request.getRequestDispatcher("index.jsp").forward(request, response);
                            /*        if(dtu instanceof dtColaborador){ //pasarlo a tarea 1
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
                    }  */
                        } else {//contrasenia erronea  
                            //   response.sendRedirect("PRESENTACIONES/login.jsp?error=pm");
                            request.getRequestDispatcher("PRESENTACIONES/login.jsp?error=pm").forward(request, response);
                        }

                    } else {  //no existe el usuario
                        // response.sendRedirect("PRESENTACIONES/login.jsp?error=nu");
                        request.getRequestDispatcher("PRESENTACIONES/login.jsp?error=nu").forward(request, response);
                    }
                } else {
                    // response.sendRedirect("PRESENTACIONES/login.jsp");
                    request.getRequestDispatcher("PRESENTACIONES/login.jsp").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("PRESENTACIONES/nocorresponde.jsp").forward(request, response);
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
