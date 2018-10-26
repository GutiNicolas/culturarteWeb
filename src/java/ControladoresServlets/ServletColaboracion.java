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
public class ServletColaboracion extends HttpServlet {
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
            String propuesta = request.getParameter("titulo");
            String cbe = request.getParameter("cb1");
            String cbp = request.getParameter("cb2");
            String especial = request.getParameter("especial");
            String monto = request.getParameter("monto");
            WSCPPort.propAutomaticas();
            HttpSession session = request.getSession();
            if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Colaborador")) {
                if (especial != null && especial.equals("si")) {
                    if ((cbe != null || cbp != null) && monto != null) {
                        if (isNumeric(monto)) {   
                            try {
                                DtPropuestaWeb dtp = (DtPropuestaWeb)WSCUPort.infoPropuesta(propuesta);
                                request.setAttribute("propuesta", dtp);
                                Collection<String> colaboradores = (Collection)dtp.getColaboradores();
                                request.setAttribute("colaboradores", colaboradores);
                                
                                if (colaboradores.contains((String) session.getAttribute("nickusuario")) == false) {
                                    if(dtp.getEstado().equals("Publicada") || dtp.getEstado().equals("En financiacion")){
                                        if(dtp.getEstado().equals("Publicada")){
                                            WSCPPort.agregarEstAPropW("En financiacion", dtp.getTitulo());
                                        }
                                    WSCCPort.registrarColaboracion(propuesta, (String) session.getAttribute("nickusuario"), Integer.parseInt(monto),(String) WSCCPort.armarRetorno(cbe, cbp));
                                    dtp = (DtPropuestaWeb) WSCUPort.infoPropuesta(propuesta);
                                    colaboradores = dtp.getColaboradores();
                                    request.setAttribute("propuesta", dtp);
                                    request.setAttribute("colaboradores", colaboradores);
                                    
                                    request.getRequestDispatcher("PRESENTACIONES/colaborar.jsp?error=no").
                                            forward(request, response);
                                    }
                                    else{
                                        request.getRequestDispatcher("PRESENTACIONES/colaborar.jsp?error=ne").
                                            forward(request, response);
                                    }
                                } else {
                                    request.getRequestDispatcher("PRESENTACIONES/colaborar.jsp?error=ya").
                                            forward(request, response);
                                }
                            } catch (Exception ex) {
                                Logger.getLogger(ConsultadePropuesta.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            try {
                                DtPropuestaWeb dtp = (DtPropuestaWeb) WSCUPort.infoPropuesta(propuesta);
                                request.setAttribute("propuesta", dtp);
                                Collection<String> colaboradores = dtp.getColaboradores();
                                request.setAttribute("colaboradores", colaboradores);
                            } catch (Exception ex) {
                                Logger.getLogger(ConsultadePropuesta.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            request.getRequestDispatcher("PRESENTACIONES/colaborar.jsp?error=nn").
                                    forward(request, response);
                        }
                        
                    } else {
                        try {
                            DtPropuestaWeb dtp = (DtPropuestaWeb) WSCUPort.infoPropuesta(propuesta);
                            request.setAttribute("propuesta", dtp);
                            Collection<String> colaboradores = dtp.getColaboradores();
                            request.setAttribute("colaboradores", colaboradores);
                        } catch (Exception ex) {
                            Logger.getLogger(ConsultadePropuesta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        request.getRequestDispatcher("PRESENTACIONES/colaborar.jsp?error=ns").
                                forward(request, response);
                    }
                } else {
                    if (propuesta == null) {
                        DtarregloDtPropWeb listarProp = WSCPPort.listarPropuestasWeb();
                        Collection<DtPropuestaWeb> props = (Collection)listarProp.getArregloPropuestas();
                        request.setAttribute("propuestas", props);
                        request.getRequestDispatcher("PRESENTACIONES/listarpropuestas.jsp").
                                forward(request, response);
                    } else {
                        try {
                            DtPropuestaWeb dtp = (DtPropuestaWeb) WSCUPort.infoPropuesta(propuesta);
                            request.setAttribute("propuesta", dtp);
                            Collection<String> colaboradores = dtp.getColaboradores();
                            request.setAttribute("colaboradores", colaboradores);
                        } catch (Exception ex) {
                            Logger.getLogger(ConsultadePropuesta.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        request.getRequestDispatcher("PRESENTACIONES/colaborar.jsp").
                                forward(request, response);
                        
                    }
                }
            } else {
                request.getRequestDispatcher("PRESENTACIONES/nocorresponde.jsp").forward(request, response);
            }
            
        }
    }

private boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
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
