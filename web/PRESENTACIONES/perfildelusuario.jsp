<%-- 
    Document   : perfildelusuario
    Created on : Sep 13, 2018, 11:56:31 AM
    Author     : nicolasgutierrez
--%>


<%@page import="servicios.DtColaboracionCompWeb"%>
<%@page import="servicios.DtSigoAWeb"%>
<%@page import="servicios.DtUsuarioWeb"%>
<%@page import="java.util.Collection"%>

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
        <%  DtUsuarioWeb dtu= (DtUsuarioWeb) request.getAttribute("usuario"); 
            Collection<DtUsuarioWeb> misseguidores=(Collection<DtUsuarioWeb>) request.getAttribute("misseguidores");
            Collection<DtSigoAWeb> misseguidos=(Collection<DtSigoAWeb>) request.getAttribute("misseguidos");
            Collection<String> favoritas=(Collection<String>) request.getAttribute("favoritas");  
            
        %>
        
        <div id="perfil" class ="main" style="min-height:670px; margin-top: 20px; margin-bottom: 20px; opacity: 0.93;">
		<div id="perfil_izquierda">
			<img src="media/images/defecto.gif" alt="imagen"/>
		</div>

		<div id="perfil_derecha">
			<div class="contenedor">
                            <h2>Información de Perfil</h2> <% if(session.getAttribute("rol")!= null){%>  <a class="btn btn-outline-primary btn-sm" href="/culturarteWeb/ServletSeguir">Seguir</a> <%}%>
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
					<%= dtu.getFechaNac() %>
				</label>
			</div>
                                        <% if(dtu.getRol().equals("Proponente")){
                              
                                
                         %>
			<div class="contenedor">
				<h2>Informacion adicional de Proponente</h2>
                                <% if(dtu.getBio().isEmpty()==false && dtu.getBio().equals(" ")==false){%>
				<label class="rotulo">Biografia</label>
                                <label class="valor"><%= dtu.getBio() %></label>
                                <br>
                                <%}%>
                                <% if(dtu.getPagWeb().isEmpty()==false && dtu.getPagWeb().equals(" ")==false){%>
                                <label class="rotulo">Web:</label>
                                <label class="valor"><a href="<%=dtu.getPagWeb()%>"><%= dtu.getPagWeb() %></a></label>
				<br>
                                <%}%>
                                <% if(dtu.getDireccion().isEmpty()==false && dtu.getDireccion().equals(" ")==false){%>
                                <label class="rotulo">Direccion:</label>
				<label class="valor"><%= dtu.getDireccion() %></label>
				<br>
                                <%}%>
                            </div>    
                                <% } %>
			
		</div>
                <div class="row">
                    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <h4>Mis Seguidores</h4>
                    <%
                	    for(DtUsuarioWeb seguidor: misseguidores){
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
                	    for(DtSigoAWeb seguidos: misseguidos){
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
                        if(dtu.getRol().equals("Proponente")){
                        %> <h4>Mis propuestas</h4> <%
                        
                            Collection<String> propuestas=(Collection<String>) request.getAttribute("propuestas");                       
                            for(String props: propuestas){                       
                        %>
                            <a href="/culturarteWeb/ConsultadePropuesta?titulo=<%=props%>">
                               props
                            </a>
                            <br>
                        <%} }%>
                        <%
                        if(dtu.getRol().equals("Proponente") && session.getAttribute("nickusuario") != null && session.getAttribute("nickusuario").equals(dtu.getNickname())){
                            Collection<String> ingresadas=(Collection<String>) request.getAttribute("ingresadass");                       
                            for(String propsi: ingresadas){                       
                        %>
                            <a href="/culturarteWeb/ConsultadePropuesta?titulo=<%=propsi%>">
                                propsi
                            </a>
                            <br>
                        <%} }%>  
                        
                        <%
                        if(dtu.getRol().equals("Colaborador") && request.getParameter("usuario")==null ){
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
                        if(dtu.getRol().equals("Colaborador") && session.getAttribute("nickusuario") != null && session.getAttribute("nickusuario").equals(dtu.getNickname())){
                            Collection<DtColaboracionCompWeb> colabscompletas=(Collection<DtColaboracionCompWeb>) request.getAttribute("colabscompletas");                       
                            %> <h4>Mis colaboraciones</h4> <%
                            
                            for(DtColaboracionCompWeb dtc: colabscompletas ){                       
                        %>
                            <label class="rotulo">Titulo:</label>
                            <a href="/culturarteWeb/ConsultadePropuesta?titulo=<%=dtc.getTituloP()%>">
                                <%=dtc.getTituloP()%>
                            </a>
                            <br>
                            <label class="rotulo">Monto:</label>
                            <a >
                                <%=dtc.getMontoC()%>
                            </a>
                            <br>
                            <label class="rotulo">Fecha Registrada:</label>
                            <a >
                                <%=dtc.getFechaC()%>
                            </a><a> Hora: </a>
                            <a>
                                <%=dtc.getHoraC()%>
                            </a>
                            
                            <br><br>
                            
                        <%} }%>                         
                    </div>                     
                </div>    
	</div>
                    
            <%@include file="../PRESENTACIONES/footergeneral.jsp"%>
    </body>
</html>
