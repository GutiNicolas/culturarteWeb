<%-- 
    Document   : consultadecategoria
    Created on : Sep 13, 2018, 11:59:33 AM
    Author     : nicolasgutierrez
--%>

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
    <% 
    Collection<String> propuestas= (Collection<String>) request.getAttribute("propuestas");
	for(String prop: propuestas){
        %> 
            <div class="propuesta">
                <div class="derecha">
                    <a class="nombre" href="?titulo=<%=prop%>">
                        <%= prop %>
                    </a>
                </div>
            </div>
        
        <% } %>
        </div>
         </div>
        </div>
        
        <%@include file="../PRESENTACIONES/footergeneral.jsp"%>
    </body>
</html>
