<%-- 
    Document   : consultadeperfil
    Created on : Sep 13, 2018, 11:56:42 AM
    Author     : nicolasgutierrez
--%>

<%@page import="Logica.dtUsuario"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="ESTILOS/consultas.css" rel="stylesheet"> 
        <link href="ESTILOS/index.css" rel="stylesheet">
        <link href="ESTILOS/mrd.css" rel="stylesheet"> 
        <%
            if(session.getAttribute("rol")!= null && session.getAttribute("rol").equals("Colaborador")){
              %> <%@include file="../PRESENTACIONES/menucolaboradorgeneral.jsp"%> <%
            }     
            else if(session.getAttribute("rol")!= null && session.getAttribute("rol").equals("Proponente")){
              %> <%@include file="../PRESENTACIONES/menuproponentegeneral.jsp"%> <%
            }
            else{
              %> <%@include file="../PRESENTACIONES/headergeneral.jsp"%> <%  
            }   
        %>
        

    </head>
    <body style="background: url(IMAGENESDISENIO/fondo.jpg) repeat center center fixed;     background-size: cover;">
          <div id="contenedor3">  
         <div id="main3"> 
        <div id="listar" class="main" style="min-height:670px; margin-top: 20px; margin-bottom: 20px; opacity: 0.93;">
                <form class="form-inline my-2 my-lg-0" action="ConsultadePerfil">
                    <input class="form-control mr-sm-2" id="txta" name="txta" type="search" placeholder="Nickname" aria-label="Search">
                    <button class="btn btn-outline-light my-2 my-sm-0 mr-sm-5" style="color: #f2d5a9; border-color: #f2d5a9;" type="submit" >Buscar</button>
                </form>
               <br>
            
    <% 
    Collection<dtUsuario> usuarios= (Collection<dtUsuario>) request.getAttribute("usuarios");
	for(dtUsuario dtu: usuarios){
        %> 
        <div class="propuesta">
            <div class="derecha">
                <a class="nombre" href="?nickname=<%=dtu.getNickname()%>">
                    <%=dtu.getNickname()%>  (<%=dtu.getNombre()%> <%=dtu.getApellido()%>)
                </a>
            </div>
        </div>
        
        <% } %>
        <br>
        
        
        </div>
         </div>
         </div>
        

        
        <%@include file="../PRESENTACIONES/footergeneral.jsp"%>
    </body>
</html>
