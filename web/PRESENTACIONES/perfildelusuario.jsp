<%-- 
    Document   : perfildelusuario
    Created on : Sep 13, 2018, 11:56:31 AM
    Author     : nicolasgutierrez
--%>

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
    <body>
        <%  dtUsuario dtu= (dtUsuario) request.getAttribute("usuario"); 
            Collection<String> misseguidores=(Collection<String>) request.getAttribute("misseguidores");
            Collection<String> misseguidos=(Collection<String>) request.getAttribute("misseguidos");
            Collection<String> favoritas=(Collection<String>) request.getAttribute("favoritas");          
        %>
        
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
                <div class="row">
                    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <h4>Mis Seguidores</h4>
                    <%
                	    for(String seguidor: misseguidores){
                    %> 
                    <a href="/culturarteWeb/ConsultadePerfil?nickname=<%=seguidor%>">
                        <%= seguidor %>
                    </a>                      
                    <% } %>  
                    </div> 
                    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                        <h4>Mis Seguidos</h4>
                    <%
                	    for(String seguidos: misseguidos){
                    %> 
                    <a href="/culturarteWeb/ConsultadePerfil?nickname=<%=seguidos%>">
                        <%= seguidos %>
                    </a>                      
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
                        <%} }%>
                        <%
                        if(dtu instanceof dtProponente && session.getAttribute("nickusuario").equals(dtu.getNickname())){
                            Collection<String> ingresadas=(Collection<String>) request.getAttribute("ingresadass");                       
                            for(String propsi: ingresadas){                       
                        %>
                            <a href="/culturarteWeb/ConsultadePropuesta?titulo=<%=propsi%>">
                                <%= propsi %>
                            </a>
                        <%} }%>  
                        
                        <%
                        if(dtu instanceof dtColaborador){
                        %> <h4>Mis colaboraciones</h4> <%
                        
                            Collection<String> colabs=(Collection<String>) request.getAttribute("colaboradas");                       
                            for(String cols: colabs){                       
                        %>
                            <a href="/culturarteWeb/ConsultadePropuesta?titulo=<%=cols%>">
                                <%= cols %>
                            </a>
                        <%} }%> 
                        
                        <%
                        if(dtu instanceof dtColaborador && session.getAttribute("nickusuario").equals(dtu.getNickname())){
                            Collection<dtColProp> colabscompletas=(Collection<dtColProp>) request.getAttribute("colabscompletas");                       
                            for(dtColProp dtc: colabscompletas ){                       
                        %>
                            <label class="rotulo">Titulo:</label>
                            <a href="/culturarteWeb/ConsultadePropuesta?titulo=<%=dtc.getTitulo()%>">
                                <%=dtc.getTitulo() %>
                            </a>
                            <label class="rotulo">Monto:</label>
                            <a >
                                <%=dtc.getMontoColaborado() %>
                            </a>
                            <label class="rotulo">Fecha Registrada:</label>
                            <a >
                                <%=dtc.getFecha().getFecha() %>
                            </a><a> Hora: </a>
                            <a>
                                dtc.getHora().getHora()
                            </a>
                            
                            <br><br>
                        <%} }%>                         
                    </div>                     
                </div>    
	</div>
    </body>
</html>
