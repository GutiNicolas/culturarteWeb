<%-- 
    Document   : comentar
    Created on : Sep 13, 2018, 12:00:14 PM
    Author     : nicolasgutierrez
--%>

<%@page import="java.util.Collection"%>
<%@page import="Logica.dtPropuesta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comentar | CultuRarte</title>
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
         <script type="text/javascript">
            $(document).ready(function(){
                $('#bttacept').click(function(){
                    var comentario= $('#comentario').val();
                    var titulo= $('#tituloprope').val();
                    $.ajax({
                        type: 'POST',
                        url: 'Comentar',
                        data: {comentario:comentario,
                        titulo:titulo},
                        success: function(result3){
                           
                            if(result3){$('#letajaxdoit').html(result3);
                            
                            document.getElementById("comentario").value = null;
                        }}
                        
                    });  
                });
                
            });
        </script>
        
        
    </head>
    <body style="background: url(IMAGENESDISENIO/fondo.jpg) repeat center center fixed;     background-size: cover;">
        <%  dtPropuesta dtp= (dtPropuesta) request.getAttribute("propuesta");
            Collection<String> colaboradores=(Collection<String>) request.getAttribute("colaboradores");
        %>
        
        <div id="perfil" class ="main" style="min-height:670px; margin-top: 20px; margin-bottom: 20px; opacity: 0.93;">
		<div id="perfil_izquierda">
			<img src="media/images/defecto.gif" alt="imagen"/>
		</div>

		<div id="perfil_derecha">
			<div class="contenedor">
				<h2>Información de la Propuesta</h2>
                                <br><br>
				<label class="rotulo">Titulo:</label>
				<label class="valor" id="tituloprope"><%= dtp.getTitulo() %></label>
				<br/>
                                 <label class="rotulo">Descripcion:</label>
				<label class="valor"><%= dtp.getDescripcion() %></label>
				<br/>
                                 <label class="rotulo">Lugar:</label>
				<label class="valor"><%= dtp.getLugar() %></label>
				<br/>
                                <label class="rotulo">Estado:</label>
				<label class="valor"><%= dtp.getEstado() %></label>
				<br/>                             
				<label class="rotulo">Fecha de realizacion:</label>
				<label class="valor">
					<%= dtp.getFechaRealizacion().getFecha() %>
				</label>
			</div>

			<div class="contenedor">
				<h2>Aun mas</h2>
				<label class="rotulo">Precio de entrada:</label>
				<label class="valor">
						<%= dtp.getPrecioentrada() %>
				</label>
                                <br/>
                                <label class="rotulo">Categoria:</label>
				<label class="valor"><%= dtp.getCategoria() %></label>
				<br/>
                                <label class="rotulo">Proponente:</label>
				<label class="valor"><%= dtp.getProponente() %></label>
				<br/>
                                <label class="rotulo">Monto requerido:</label>
				<label class="valor"><%= dtp.getMontorequerido() %></label>
				<br/>
                                <label class="rotulo">Monto actual:</label>
				<label class="valor"><%= dtp.getMontoTotal() %></label>
				<br/>
			</div>
                        <div class="contenedor">
                             <h4>Colaboradores:</h4> 
                        <%                                               
                            for(String cols: colaboradores){                       
                        %>
                            <a href="/culturarteWeb/ConsultadePerfil?nickname=<%=cols%>">
                                <%= cols %>
                            </a>
                            <br>
                        <%} %>
                        </div>
                </div>
                <div class="comentar"> 
                    <form class="formalta">
                        <label for="comment">Comentario</label><br>
                        <textarea class="form-control" rows="4" id="comentario" cols="2"></textarea>
                        <input type="button" value="Aceptar" id="bttacept">
                        <br>
                        <div id="letajaxdoit"></div>
                        <br>
                    </form>
                </div>      
	</div>
                                
            <%@include file="../PRESENTACIONES/footergeneral.jsp"%>
    </body>
</html>

