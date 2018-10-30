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
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servicios.DtContieneArray;
import servicios.DtFechaWeb;
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
public class ServletAltaPropuesta extends HttpServlet {

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
        //    try (PrintWriter out = response.getWriter()) {

        WSCPPort.propAutomaticas();
        /* TODO output your page here. You may use following sample code. */
        String titulo = request.getParameter("titulo");
        HttpSession session = request.getSession();
        if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Proponente")) {
            if (titulo == null) {
                DtContieneArray categoriasCol = WSCPPort.listaCategorias();
                Collection<String> categorias = (Collection) categoriasCol.getMyarreglo();
                request.setAttribute("categorias", categorias);
                request.getRequestDispatcher("PRESENTACIONES/altapropuesta.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("PRESENTACIONES/nocorresponde.jsp").forward(request, response);
        }

        //    }
    }

    private boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean isUtilizable(String fecha) {
        String[] fp = fecha.split("/");
        String dia = fp[0];
        String mes = fp[1];
        String anio = fp[2];
        if (dia.isEmpty() == false && mes.isEmpty() == false && anio.isEmpty() == false) {
            if (isNumeric(dia) && isNumeric(mes) && isNumeric(anio)) {
                if (dia.length() == 2 && mes.length() == 2 && anio.length() == 4) {
                    int d = Integer.parseInt(dia);
                    int m = Integer.parseInt(mes);
                    int a = Integer.parseInt(anio);
                    if (d > 0 && d < 32) {
                        if (m > 0 && m < 13) {
                            if (a > 2000 && a < 2090) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
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
        //    response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        String lugar = request.getParameter("lugar");
        String retorno = request.getParameter("retorno");
        String montorequerido = request.getParameter("montorequerido");
        String costoentrada = request.getParameter("costoentrada");
        String fecharealizacion = request.getParameter("fecharealizacion");
        String categoria = request.getParameter("categoria");
        boolean dardealta = true, seguircontrolando = true;
        DtContieneArray propColExis = (DtContieneArray) WSCPPort.listarTodasLasPropuestas("");
        Collection propuestasexistentes = (Collection) propColExis.getMyarreglo();
        HttpSession session = request.getSession();
        //out.println("<p>");

        if (titulo.isEmpty() && seguircontrolando == true) {
            out.println("El titulo no puede ser vacio");
            dardealta = false;
            seguircontrolando = false;
        }
        if (descripcion.isEmpty() && seguircontrolando == true) {
            out.println("La descricpion no puede ser vacia");
            dardealta = false;
            seguircontrolando = false;
        }
        if (lugar.isEmpty() && seguircontrolando == true) {
            out.println("El lugar no puede ser vacio");
            dardealta = false;
            seguircontrolando = false;
        }
        if (retorno.isEmpty() && seguircontrolando == true) {
            out.println("Debe seleccionar al menos un tipo de retorno");
            dardealta = false;
            seguircontrolando = false;
        }
        if (montorequerido.isEmpty() && seguircontrolando == true) {
            out.println("El monto requerido no puede ser vacio");
            dardealta = false;
            seguircontrolando = false;
        }
        if (costoentrada.isEmpty() && seguircontrolando == true) {
            out.println("EL costo de la entrada no puede ser vacio");
            dardealta = false;
            seguircontrolando = false;
        }
        if (fecharealizacion.isEmpty() && seguircontrolando == true) {
            out.println("La fecha de realizacion no puede ser vacia");
            dardealta = false;
            seguircontrolando = false;
        }
        if (fecharealizacion.isEmpty() == false && isUtilizable(fecharealizacion) == false && seguircontrolando == true) {
            out.println("Controle la fecha");
            dardealta = false;
            seguircontrolando = false;
        }
        if (montorequerido.isEmpty() == false && isNumeric(montorequerido) == false && seguircontrolando == true) {
            out.println("Controle el monto requerido");
            dardealta = false;
            seguircontrolando = false;
        }
        if (costoentrada.isEmpty() == false && isNumeric(costoentrada) == false && seguircontrolando == true) {
            out.println("Controle el precio de la entrada");
            dardealta = false;
            seguircontrolando = false;
        }
        if (titulo.isEmpty() == false && seguircontrolando == true) {
            if (propuestasexistentes.contains(titulo)) {
                out.println("Ya existe una propuesta con el titulo" + titulo);
                dardealta = false;
                seguircontrolando = false;
            }
        }

        if (dardealta == true) {
            String proponente = (String) session.getAttribute("nickusuario");
            DtFechaWeb dtFe = WSCPPort.getFecha();                                               //LA IMAGEN
            DtPropuestaWeb dtp = new DtPropuestaWeb();
            dtp.setTitulo(titulo);
            dtp.setDescripcion(descripcion);
            dtp.setLugar(lugar);
            dtp.setEstado("Ingresada");
            dtp.setCategoria(categoria);
            dtp.setProponente(proponente);
            dtp.setFechaRealizacion(fecharealizacion);
            dtp.setFechapublicada(dtFe.getFecha());
            dtp.setPrecioentrada(Integer.valueOf(costoentrada));
            dtp.setMontorequerido(Integer.valueOf(montorequerido));
            dtp.setRetorno(retorno);
            WSCPPort.altaPropuesta(dtp);

            out.println("Propuesta registrada con exito");
        }

        // out.println("</p>");
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
