/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladoresServlets;

import Logica.ContPropuesta;
import Logica.ContUsuario;
import Logica.dtFecha;
import Logica.dtPropuesta;
import Logica.utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nicolasgutierrez
 */
public class ServletAltaPropuesta extends HttpServlet {

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
            ContPropuesta cp= ContPropuesta.getInstance();
            cp.propAutomaticas();
            /* TODO output your page here. You may use following sample code. */
            String titulo= request.getParameter("titulo");
            HttpSession session = request.getSession();
            if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Proponente")){     
            if(titulo==null){         
                Collection<String> categorias= cp.listarCategorias("");
                request.setAttribute("categorias", categorias);
                request.getRequestDispatcher("PRESENTACIONES/altapropuesta.jsp").forward(request, response);
            }
            }else{
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

private boolean isUtilizable(String fecha){
    String[] fp=fecha.split("/");
    String dia=fp[0];
    String mes=fp[1];
    String anio=fp[2];
    if(dia.isEmpty()==false && mes.isEmpty()==false && anio.isEmpty()==false){
        if(isNumeric(dia) && isNumeric(mes) && isNumeric(anio)){
            if(dia.length()==2 && mes.length()==2 && anio.length()==4){
                int d=Integer.parseInt(dia);
                int m=Integer.parseInt(mes);
                int a=Integer.parseInt(anio);
                if(d>0 && d<32){
                    if(m>0 && m<13){
                        if(a>2000 && a<2090){
                            return true;
                        }
                        else
                            return false;
                    }
                    else
                        return false;
                }
                else
                    return false;
            }
            else
                return false;
        }
        else
            return false;
    }
    else{
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
        PrintWriter out= response.getWriter();
        String titulo= request.getParameter("titulo");
        String descripcion= request.getParameter("descripcion");
        String lugar= request.getParameter("lugar");
        String retorno= request.getParameter("retorno");
        String montorequerido= request.getParameter("montorequerido");
        String costoentrada= request.getParameter("costoentrada");
        String fecharealizacion= request.getParameter("fecharealizacion");
        String categoria= request.getParameter("categoria");
        utilidades utils= utilidades.getInstance();
        boolean dardealta=true,seguircontrolando=true;
        ContUsuario cu=ContUsuario.getInstance();
        ContPropuesta cp=ContPropuesta.getInstance();
        Collection propuestasexistentes=cu.listartodaslaspropuestas("");
        HttpSession session=request.getSession();
        //out.println("<p>");
        
        if(titulo.isEmpty() && seguircontrolando==true){
            out.println("El titulo no puede ser vacio");
            dardealta=false;
            seguircontrolando=false;
        }
        if(descripcion.isEmpty() && seguircontrolando==true){
            out.println("La descricpion no puede ser vacia");
            dardealta=false;
            seguircontrolando=false;
        }
        if(lugar.isEmpty() && seguircontrolando==true){
            out.println("El lugar no puede ser vacio");
            dardealta=false;
            seguircontrolando=false;
        }
        if(retorno.isEmpty() && seguircontrolando==true){
            out.println("Debe seleccionar al menos un tipo de retorno");
            dardealta=false;
            seguircontrolando=false;
        }        
        if(montorequerido.isEmpty() && seguircontrolando==true){
            out.println("El monto requerido no puede ser vacio");
            dardealta=false;
            seguircontrolando=false;
        }
        if(costoentrada.isEmpty() && seguircontrolando==true){
            out.println("EL costo de la entrada no puede ser vacio");
            dardealta=false;
            seguircontrolando=false;
        }  
        if(fecharealizacion.isEmpty() && seguircontrolando==true){
            out.println("La fecha de realizacion no puede ser vacia");
            dardealta=false;
            seguircontrolando=false;
        }
        if(fecharealizacion.isEmpty()==false && isUtilizable(fecharealizacion)==false && seguircontrolando==true){
            out.println("Controle la fecha");
            dardealta=false;
            seguircontrolando=false;
        }
        if(montorequerido.isEmpty()==false && isNumeric(montorequerido)==false && seguircontrolando==true){
            out.println("Controle el monto requerido");
            dardealta=false;
            seguircontrolando=false;
        }
        if(costoentrada.isEmpty()==false && isNumeric(costoentrada)==false && seguircontrolando==true){
            out.println("Controle el precio de la entrada");
            dardealta=false;
            seguircontrolando=false;
        }
        if(titulo.isEmpty()==false && seguircontrolando==true){
            if(propuestasexistentes.contains(titulo)){
                out.println("Ya existe una propuesta con el titulo" + titulo);
                dardealta=false;
                seguircontrolando=false;
            }
        }
        
        if(dardealta==true){
 //           Calendar cal=Calendar.getInstance();
            
 //           Date da=cal.getTime();
 //           da.setYear(2018);
 //           dtFecha dtfpublicada=new dtFecha(Integer.toString(da.getDay()),Integer.toString(da.getMonth()),Integer.toString(da.getYear()));
 //           String[] fr=fecharealizacion.split("/");
 //           String dia=fr[0];
 //           String mes=fr[1];
 //           String anio=fr[2];
 //           dtFecha dtfrealizar=new dtFecha(dia,mes,anio);
            
                                                            //LA IMAGEN
            dtPropuesta dtp=new dtPropuesta(titulo,descripcion,"",lugar,"Ingresada",categoria,(String)session.getAttribute("nickusuario"),utils.construirFecha(fecharealizacion),utils.getFecha(),Integer.parseInt(costoentrada),Integer.parseInt(montorequerido),0,retorno);
            cp.datosPropuesta(dtp);
            
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
