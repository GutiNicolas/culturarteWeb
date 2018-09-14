<%-- 
    Document   : perfildelusuario
    Created on : Sep 13, 2018, 11:56:31 AM
    Author     : nicolasgutierrez
--%>

<%@page import="Logica.dtProponente"%>
<%@page import="Logica.dtUsuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Informacion de Perfil</title>
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
        <%  dtUsuario dtu= (dtUsuario) request.getAttribute("usuario");  %>
        
        <div id="perfil" class ="main">
		<div id="perfil_izquierda">
			<img src="media/images/defecto.gif" alt="imagen"/>
		</div>

		<div id="perfil_derecha">
			<div class="contenedor">
				<h2>Información de Perfil</h2>
				<label class="rotulo">Nickname:</label>
				<label class="valor"><%= dtu.getNickname() %></label>
				<br/>
                                 <label class="rotulo">Nombre:</label>
				<label class="valor"><%= dtu.getNombre() %> <%= dtu.getApellido() %> </label>
				<br/>
                                 <label class="rotulo">Email:</label>
				<label class="valor"><%= dtu.getEmail() %></label>
				<br/>                                                     
				<label class="rotulo">Fecha de Nacimiento:</label>
				<label class="valor">
					<%= dtu.getFechaNac().getFecha() %>
				</label>
			</div>
                         <% if(dtu instanceof dtProponente){
                                dtProponente dtp=(dtProponente)dtu;
                         %>
			<div class="contenedor">
				<h2>Informacion adicional de Proponente</h2>
                                <% if(dtp.getBiografia().equals("null")==false){%>
				<label class="rotulo">Biografia</label>
				<label class="valor">
						<%= dtp.getBiografia() %>
				</label>
                                <br/>
                                <%}%>
                                <% if(dtp.getSitioWeb().equals("null")==false){%>
                                <label class="rotulo">Web:</label>
                                <label class="valor"><a href="<%=dtp.getSitioWeb()%>"><%= dtp.getSitioWeb() %></a></label>
				<br/>
                                <%}%>
                                <% if(dtp.getDireccion().equals("null")==false){%>
                                <label class="rotulo">Direccion:</label>
				<label class="valor"><%= dtp.getDireccion() %></label>
				<br/>
                                <%}%>
                                <% } %>
			</div>
		</div>
	</div>
    </body>
</html>
