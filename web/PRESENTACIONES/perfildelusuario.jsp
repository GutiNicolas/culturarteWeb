<%-- 
    Document   : perfildelusuario
    Created on : Sep 13, 2018, 11:56:31 AM
    Author     : nicolasgutierrez
--%>

<%@page import="Logica.dtSigoA"%>
<%@page import="Logica.dtColProp"%>
<%@page import="Logica.dtColaborador"%>
<%@page import="java.util.Collection"%>
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
    <body style="background: url(IMAGENESDISENIO/fondo.jpg) repeat center center fixed;     background-size: cover;">
        <%  dtUsuario dtu= (dtUsuario) request.getAttribute("usuario"); 
            Collection<dtUsuario> misseguidores=(Collection<dtUsuario>) request.getAttribute("misseguidores");
            Collection<dtSigoA> misseguidos=(Collection<dtSigoA>) request.getAttribute("misseguidos");
            Collection<String> favoritas=(Collection<String>) request.getAttribute("favoritas");          
        %>
        
        <div id="perfil" class ="main" style="min-height:670px; margin-top: 20px; margin-bottom: 20px; opacity: 0.93;">
		<div id="perfil_izquierda">
			<img src="media/images/defecto.gif" alt="imagen"/>
		</div>

		<div id="perfil_derecha">
			<div class="contenedor">
				<h2>Información de Perfil</h2>
                                <br><br>
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
                                <% if(dtp.getBiografia().isEmpty()==false && dtp.getBiografia().equals(" ")==false){%>
				<label class="rotulo">Biografia</label>
				<label class="valor"><%= dtp.getBiografia() %></label>
                                <br>
                                <%}%>
                                <% if(dtp.getSitioWeb().isEmpty()==false && dtp.getSitioWeb().equals(" ")==false){%>
                                <label class="rotulo">Web:</label>
                                <label class="valor"><a href="<%=dtp.getSitioWeb()%>"><%= dtp.getSitioWeb() %></a></label>
				<br>
                                <%}%>
                                <% if(dtp.getDireccion().isEmpty()==false && dtp.getDireccion().equals(" ")==false){%>
                                <label class="rotulo">Direccion:</label>
				<label class="valor"><%= dtp.getDireccion() %></label>
				<br>
                                <%}%>
                            </div>    
                                <% } %>
			
		</div>
                <div class="row">
                    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <h4>Mis Seguidores</h4>
                    <%
                	    for(dtUsuario seguidor: misseguidores){
                    %> 
                    <a href="/culturarteWeb/ConsultadePerfil?nickname=<%=seguidor.getNickname()%>">
                        <%= seguidor.getNombre()%> <%=seguidor.getApellido()%> (@<%=seguidor.getNickname()%>)
                    </a> 
                    <br>
                    <% } %>  
                    </div> 
                    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <h4>Mis Seguidos</h4>
                    <%
                	    for(dtSigoA seguidos: misseguidos){
                    %> 
                    <a href="/culturarteWeb/ConsultadePerfil?nickname=<%=seguidos.getNickusuario()%>">
                        <%=seguidos.getNombrecompleto()%> (@<%= seguidos.getNickusuario() %> : <%= seguidos.getRol() %>)
                    </a> 
                    <br>
                    <% } %>     
                    </div> 
                    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <h4>Propuestas favoritas</h4> 
                    <%
                	    for(String favs: favoritas){
                    %> 
                    <a href="/culturarteWeb/ConsultadePropuesta?titulo=<%=favs%>">
                        <%= favs %>
                    </a> 
                    <br>
                    <% } %>                          
                    </div> 
                    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <%
                        if(dtu instanceof dtProponente){
                        %> <h4>Mis propuestas</h4> <%
                        
                            Collection<String> propuestas=(Collection<String>) request.getAttribute("propuestas");                       
                            for(String props: propuestas){                       
                        %>
                            <a href="/culturarteWeb/ConsultadePropuesta?titulo=<%=props%>">
                                <%= props %>
                            </a>
                            <br>
                        <%} }%>
                        <%
                        if(dtu instanceof dtProponente && session.getAttribute("nickusuario") != null && session.getAttribute("nickusuario").equals(dtu.getNickname())){
                            Collection<String> ingresadas=(Collection<String>) request.getAttribute("ingresadass");                       
                            for(String propsi: ingresadas){                       
                        %>
                            <a href="/culturarteWeb/ConsultadePropuesta?titulo=<%=propsi%>">
                                <%= propsi %>
                            </a>
                            <br>
                        <%} }%>  
                        
                        <%
                        if(dtu instanceof dtColaborador && request.getParameter("usuario")==null ){
                        %> <h4>Mis colaboraciones</h4> <%
                        
                            Collection<String> colabs=(Collection<String>) request.getAttribute("colaboradas");                       
                            for(String cols: colabs){                       
                        %>
                            <a href="/culturarteWeb/ConsultadePropuesta?titulo=<%=cols%>">
                                <%= cols %>
                            </a>
                            <br>
                        <%} }%> 
                        
                        <%
                        if(dtu instanceof dtColaborador && session.getAttribute("nickusuario") != null && session.getAttribute("nickusuario").equals(dtu.getNickname())){
                            Collection<dtColProp> colabscompletas=(Collection<dtColProp>) request.getAttribute("colabscompletas");                       
                            %> <h4>Mis colaboraciones</h4> <%
                            
                            for(dtColProp dtc: colabscompletas ){                       
                        %>
                            <label class="rotulo">Titulo:</label>
                            <a href="/culturarteWeb/ConsultadePropuesta?titulo=<%=dtc.getTitulo()%>">
                                <%=dtc.getTitulo() %>
                            </a>
                            <br>
                            <label class="rotulo">Monto:</label>
                            <a >
                                <%=dtc.getMontoColaborado() %>
                            </a>
                            <br>
                            <label class="rotulo">Fecha Registrada:</label>
                            <a >
                                <%=dtc.getFecha().getFecha() %>
                            </a><a> Hora: </a>
                            <a>
                                <%=dtc.getHora().getHora()%>
                            </a>
                            
                            <br><br>
                            
                        <%} }%>                         
                    </div>                     
                </div>    
	</div>
                    
            <%@include file="../PRESENTACIONES/footergeneral.jsp"%>
    </body>
</html>
