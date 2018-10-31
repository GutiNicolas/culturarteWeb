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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servicios.DtColaboracionCompWeb;
import servicios.DtContieneArray;
import servicios.DtSigoAWeb;
import servicios.DtUsuarioWeb;
import servicios.DtarregloDtColCompWeb;
import servicios.DtarregloDtSigoAWEB;
import servicios.DtarregloDtUsuWeb;
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
public class ConsultadePerfil extends HttpServlet {

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
            System.err.println(ex);
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
        // processRequest(request, response);
        String nick = request.getParameter("nickname");
        String propio = request.getParameter("usuario");
        HttpSession session = request.getSession();
        request.removeAttribute("usuarios");
        request.removeAttribute("colaboradas");
        request.removeAttribute("colabscompletas");
        request.removeAttribute("propuestas");
        request.removeAttribute("ingresadass");
        String txta = request.getParameter("txta");
        request.removeAttribute("usuario");
        request.removeAttribute("misseguidores");
        request.removeAttribute("misseguidos");
        request.removeAttribute("favoritas");

        if (txta == null) {
            if (propio != null && propio.equals("yes")) {
                nick = (String) session.getAttribute("nickusuario");
            }

            if (nick == null) {
                DtarregloDtUsuWeb listarUsuariosWeb = WSCUPort.listarUsuariosWeb("");
                Collection<DtUsuarioWeb> usuarios = (Collection) listarUsuariosWeb.getListaUsuarios();
                request.setAttribute("usuarios", usuarios);
                request.getRequestDispatcher("PRESENTACIONES/consultadeperfil.jsp").
                        forward(request, response);
            } else {

                DtUsuarioWeb dtu = (DtUsuarioWeb) WSCUPort.infoUsuarioGeneral(nick);
                DtarregloDtUsuWeb listarMisSeguidores = WSCUPort.listarMisSeguidores(nick);
                Collection<DtUsuarioWeb> misseguidores = (Collection) listarMisSeguidores.getListaUsuarios();
                DtarregloDtSigoAWEB listarMisSeguidos = WSCUPort.listarMisSeguidos(nick);
                Collection<DtSigoAWeb> misseguidos = (Collection) listarMisSeguidos.getArregloSigoAusu();
                DtContieneArray colPropFav = (DtContieneArray) WSCUPort.misPropFav(nick);
                Collection<String> favoritas = (Collection) colPropFav.getMyarreglo();
                if (dtu.getRol().equals("Colaborador")) {
                    DtContieneArray colPropCola = (DtContieneArray) WSCCPort.listarColaboraciones(nick);
                    Collection<String> colaboradas = (Collection) colPropCola.getMyarreglo();
                    request.setAttribute("colaboradas", colaboradas);
                    DtarregloDtColCompWeb listarMisColaboraciones = WSCCPort.listarMisColaboraciones(nick);
                    List<DtColaboracionCompWeb> colaboradascompletas = listarMisColaboraciones.getArregloColaBoraciion();
                    request.setAttribute("colabscompletas", colaboradascompletas);
                }
                if (dtu.getRol().equals("Proponente")) {
                    DtContieneArray colPropAcep = (DtContieneArray) WSCPPort.misPropAceptadas(nick);
                    Collection<String> propuestas = (Collection) colPropAcep.getMyarreglo();
                    request.setAttribute("propuestas", propuestas);
                    DtContieneArray ColProIng = (DtContieneArray) WSCPPort.misPropIngresadas(nick);
                    Collection<String> propuestasingresadas = (Collection) ColProIng.getMyarreglo();
                    request.setAttribute("ingresadass", propuestasingresadas);
                }
                request.setAttribute("usuario", dtu);
                request.setAttribute("misseguidores", misseguidores);
                request.setAttribute("misseguidos", misseguidos);
                request.setAttribute("favoritas", favoritas);
                request.getRequestDispatcher("PRESENTACIONES/perfildelusuario.jsp").
                        forward(request, response);

            }
        } else {
            DtarregloDtUsuWeb listarUsuariosWeb = WSCUPort.listarUsuariosWeb(txta);
            Collection<DtUsuarioWeb> usuarios = listarUsuariosWeb.getListaUsuarios();
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("PRESENTACIONES/consultadeperfil.jsp").
                    forward(request, response);
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
        String nickus = request.getParameter("txta");

        HttpSession session = request.getSession();
        DtarregloDtUsuWeb listarUsuariosWeb = WSCUPort.listarUsuariosWeb(nickus);
        List<DtUsuarioWeb> usuarios = listarUsuariosWeb.getListaUsuarios();
        Iterator it = usuarios.iterator();
        while (it.hasNext()) {
            DtUsuarioWeb dtu = (DtUsuarioWeb) it.next();
            out.println("<div class=\"propuesta\">");
            out.println("<div class=\"derecha\">");
            out.println("<a class=\"nombre\" href=\"?nickname=" + dtu.getNickname() + "\">");
            out.println(dtu.getNickname() + " (" + dtu.getNombre() + " " + dtu.getApellido() + ")");
            out.println("</a>");
            out.println("</div>");
            out.println("</div>");
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
