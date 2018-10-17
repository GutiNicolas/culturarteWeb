<%-- 
    Document   : ranking
    Created on : Oct 17, 2018, 4:31:55 PM
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
            <h3>Ranking de Usuarios</h3>
    <% 
    Collection<dtUsuario> usuarios= (Collection<dtUsuario>) request.getAttribute("ranking");
	for(dtUsuario dtu: usuarios){
        %> 
        <div class="propuesta">
            <div class="derecha">
                <a class="nombre" href="/culturarteWeb/ConsultadePerfil?nickname=<%=dtu.getNickname()%>">
                [<%=dtu.getPuntaje()%> Puntos]  <%=dtu.getNombre()%> <%=dtu.getApellido()%> (@<%=dtu.getNickname()%>)
                </a>
            </div>
        </div>
        
        <% } %>
        <br>            
        </div>
            
         </div>
             
         </div>
    </body>
</html>
