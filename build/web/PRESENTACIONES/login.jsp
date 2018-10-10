<%-- 
    Document   : login
    Created on : Sep 11, 2018, 5:32:48 PM
    Author     : nicolasgutierrez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log In | Culturarte</title>
        <script src="../SCRIPTS/jquery-3.3.1.min.js" ></script>
        
            <%
        if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Colaborador")) {
    %> <%@include file="../PRESENTACIONES/menucolaboradorgeneral.jsp"%> <%
    } else if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Proponente")) {
    %> <%@include file="../PRESENTACIONES/menuproponentegeneral.jsp"%> <%
    } else {
    %> <%@include file="../PRESENTACIONES/headergeneral.jsp"%> <%
        }
    %>
        
        <% 
            String error;
            if(request.getParameter("error") != null){
               error= request.getParameter("error"); 
            }
            else
                error="";
        %>       
        <link rel="stylesheet" type="text/css" href="ESTILOS/login.css" />
    </head>
    
    <%
        if(error.equals("nu"))
            out.print("<body onload=\"alertar('USUARIO NO ENCONTRADO')\" >");
        else if(error.equals("pm"))
            out.print("<body onload=\"alertar('CONTRASENIA ERRONEA')\" >");
        else if(session.getAttribute("nickusuario") != null){
            out.print("<body onload=\"alertar('BIEVENIDO "+ session.getAttribute("nickusuario")+"')\" >");
            response.sendRedirect("../index.jsp");
        }
        else
            out.print("<body>");
    %>
    
        
       <div id="home">
           <div class="container">
               <div class="landing-text">
                   <br><br><br><br><br>
                   <h3>Bienvenido de nuevo</h3>
               </div>
               <form class="formx" id="login_form" action="/culturarteWeb/ServletLogin" method="post" onsubmit="return verificar()">
                   
                    <label for="name" >Nickname:</label>
                    <input type="text" id="nick" name="nick"/>
                    <span id="error_nick" class="error">Debes ingresar un nick</span>                    
                    <br/>
                    <label for="pass">Clave:</label>
                    <input type="password" id="pass" name="pass"/>
                    <span id="error_pass" class="error">Debes ingresar la clave</span>                  
                    <br/>
                    <input type="checkbox" name="rememberMe" value="rememberMe"/>Recordarme
                    <br/>
                    <input type="submit" value="Enviar"/>  
                    <br>
                    <p>No tienes una cuenta? <a href="/culturarteWeb/servletRegistrarse" style="color: #1b1c1b ">Registrate</a></p>                   
               </form>
               <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
           </div>
       </div> 
         
        <script type="text/javascript" src="../SCRIPTS/verificarlogin.js"></script>
        <script type="text/javascript" src="../SCRIPTS/ver.js"></script>
    </body>
</html>
