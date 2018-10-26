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
import servicios.DtPropuestaWeb;
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
public class Comentar extends HttpServlet {
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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
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
        //processRequest(request, response);
            String propuesta=request.getParameter("titulo");

            HttpSession session = request.getSession();
            
            WSCPPort.propAutomaticas();
            String comentario= request.getParameter("comentario");
            if(comentario==null){
            if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Colaborador")){
            if(propuesta==null){
                DtContieneArray colPropCola= (DtContieneArray)WSCPPort.listarPropColaboradas((String) session.getAttribute("nickusuario"));
                Collection<String> props=(Collection) colPropCola.getMyarreglo();
                request.setAttribute("propuestas", props);
                request.getRequestDispatcher("PRESENTACIONES/consultadepropuestacolaborar.jsp").
					forward(request, response);
            }
            else{
                try {
                    DtPropuestaWeb dtp = (DtPropuestaWeb)WSCUPort.infoPropuesta(propuesta);
                    request.setAttribute("propuesta", dtp);
                    Collection<String> colaboradores=dtp.getColaboradores();
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
        
       // (String) session.getAttribute("nickusuario")
        if(comentario.isEmpty()==false){
            WSCPPort.comentar((String) session.getAttribute("nickusuario"), titulo, comentario);
            
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
