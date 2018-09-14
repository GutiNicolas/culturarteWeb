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
    <body>
        <div id="listar" class="main">
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
        
        <%@include file="../PRESENTACIONES/footergeneral.jsp"%>
    </body>
</html>