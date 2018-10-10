/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladoresServlets;

import Logica.ContUsuario;
import Logica.dtColaborador;
import Logica.dtFecha;
import Logica.dtProponente;
import Logica.dtUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author juan
 */
@WebServlet(name = "servletRegistrarse", urlPatterns = {"/servletRegistrarse"})
public class servletRegistrarse extends HttpServlet {

    ContUsuario contU = ContUsuario.getInstance();

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
        //    response.setContentType("text/html;charset=UTF-8");
        String url = "/PRESENTACIONES/registrarse.jsp";
        ServletContext sr = getServletContext();
        RequestDispatcher rd = sr.getRequestDispatcher(url);
        rd.forward(request, response);
        // request.getRequestDispatcher(url).forward(request, response);  //    request.getRequestDispatcher("PRESENTACIONES/altapropuesta.jsp").forward(request, response);
    }

    /**
     *
     * @param conf
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig conf)
            throws ServletException {
        super.init(conf);
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
        try {
            PrintWriter out = response.getWriter();

            altaUsuario(request, response);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    private void altaUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String nick = null, correo = null, pass1, pass2, nombre, apellido, direccion, web, biografia, fechaNac;

        try {
            nick = (String) request.getParameter("nickname");
            correo = (String) request.getParameter("correo");
            pass1 = (String) request.getParameter("password");
            pass2 = (String) request.getParameter("password2");
            nombre = (String) request.getParameter("nombre");
            apellido = (String) request.getParameter("apellido");
            direccion = (String) request.getParameter("direccion");
            web = (String) request.getParameter("pagWeb");
            biografia = (String) request.getParameter("bio");
            fechaNac = (String) request.getParameter("nacimiento");//yyyy-mm-dd
            System.out.println(nick);
            System.out.println(correo);
            System.out.println(pass1);
            System.out.println(pass2);
            System.out.println(nombre);
            System.out.println(apellido);
            System.out.println(direccion);
            System.out.println(web);
            System.out.println(biografia);
            System.out.println(fechaNac);
            HttpSession session = request.getSession();
            if (verificaExistencia(nick, correo) == 0) {
                if (pass1.equals(pass2)) {
                    String tipo = (String) request.getParameter("tipo");
                    dtFecha dtf = contU.creadtFecha(fechaNac);
                    if (tipo.equals("Proponente")) {

                        dtProponente nuevoProp = new dtProponente(nombre, apellido, nick, "notiene", correo, dtf, direccion, biografia, web, pass1);
                        contU.agregarUsu(nuevoProp);
                        out.print("Usuario: " + nick + " agregado con Exito!!");
                        dtUsuario dtu = contU.usuarioLogin(nick);
                        session.setAttribute("nickusuario", dtu.getNickname());
                        session.setAttribute("rol", dtu.getRol());

                    }
                    if (tipo.equals("Colaborador")) {
                        dtColaborador nuevoColaborador = new dtColaborador(nombre, apellido, nick, "no tiene", correo, dtf, pass1);
                        contU.agregarUsu(nuevoColaborador);
                        out.print("Usuario: " + nick + " agregado con Exito!!");
                        dtUsuario dtu = contU.usuarioLogin(nick);
                        session.setAttribute("nickusuario", dtu.getNickname());
                        session.setAttribute("rol", dtu.getRol());
                    }
                }else{ out.print("noOkPass");}
               
                   
               
            } else {
                out.print(queExiste(nick, correo));
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * funcion que comprueba existencia de nickname u correo retorna 1 si existe
     * el nickname retorna -1 si existe el correo retorna 0 si no existe ninguno
     */
    private int verificaExistencia(String nick, String correo) {
        if (contU.existeUsuario(nick)) {
            return 1;
        }
        if (contU.existeMail(correo)) {
            return -1;
        }
        return 0;
    }

    private String queExiste(String nick, String correo) {
        String existe = null;
        if (verificaExistencia(nick, correo) == 1) {
            existe = "usuarioE";//"Ya existe un usuario registrado con el mismo Nickname";
        }
        if (verificaExistencia(nick, correo) == -1) {
            existe = "correoE";//"Ya existe un usuario registrado con el mismo correo";
        }
        return existe;
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
