<%-- 
    Document   : listarpropuestas
    Created on : Sep 14, 2018, 6:49:43 PM
    Author     : nicolasgutierrez
--%>


<%@page import="Logica.dtPropuesta"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="ESTILOS/consultas.css" rel="stylesheet"> 
        <link href="ESTILOS/index.css" rel="stylesheet"
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
        <div id="listar" class="main" style="min-height:670px; margin-top: 20px; margin-bottom: 20px; opacity: 0.93;">
    <% 
    Collection<dtPropuesta> propuestas= (Collection<dtPropuesta>) request.getAttribute("propuestas");
	for(dtPropuesta dtp: propuestas){
        %> 
            <div class="propuesta">
                <div class="derecha">
                    <a class="nombre" href="?titulo=<%=dtp.getTitulo()%>">
                        <%= dtp.getTitulo() %>  </a><a>      (Propuesta por: <%=dtp.getProponente()%>)
                    </a>
                </div>
            </div>
        
        <% } %>
        </div>
        
        <%@include file="../PRESENTACIONES/footergeneral.jsp"%>
    </body>
</html>