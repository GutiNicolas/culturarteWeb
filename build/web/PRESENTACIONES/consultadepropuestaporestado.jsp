<%-- 
    Document   : consultadecategoriaporestado
    Created on : Sep 13, 2018, 11:59:45 AM
    Author     : nicolasgutierrez
--%>

<%@page import="Logica.dtPropuesta"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="ESTILOS/altapropuesta.css" rel="stylesheet"> 
                <link href="ESTILOS/mrd.css" rel="stylesheet"> 
                <link href="ESTILOS/listarestilos.css" rel="stylesheet">
           
              <%
                  if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Colaborador")) {
              %> <%@include file="../PRESENTACIONES/menucolaboradorgeneral.jsp"%> <%
                    } else if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Proponente")) {
              %> <%@include file="../PRESENTACIONES/menuproponentegeneral.jsp"%> <%
                    } else {
              %> <%@include file="../PRESENTACIONES/headergeneral.jsp"%> <%
                        }
              %>
 	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.2.5/jquery.fancybox.min.css"/>
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
	<link rel="stylesheet" href="css/style.css">             
    </head>
    <body style='background: url(IMAGENESDISENIO/fondo.jpg) repeat center center fixed;     background-size: cover;'>
        
          <%
            Collection<String> categorias= (Collection<String>) request.getAttribute("categorias");
        %>
          <div id="contenedor3">  
         <div id="main3">   
        <div class="form-group">
            <form action="ConsultaPorEstado" method="post">
            <label for="sell">Seleccione una Categoria</label>
            <select class="form-control" name="selle" id="selle">
                <% 
                    for(String cats: categorias){
                %>
                <option><%=cats%></option>
                <%}%>
            </select>
            <br>                
                <input type="submit" value="Listar"/>  
            </form>
           
        </div> 
        
        
        
        
       <%
           if(request.getAttribute("propuestas")!=null){
            Collection<dtPropuesta> propuestas= (Collection<dtPropuesta>) request.getAttribute("propuestas");
           
        %>
        
        
        <div class="container-fluid" style="max-width: 900px">
		<div class="row">
            
                <%
                    for(dtPropuesta prop: propuestas){
                %>

                <div class="col-md-3 col-sm-4 col-xs-12 single_portfolio_text" style="min-width: 450px">
							<img src="https://cronicaglobal.elespanol.com/uploads/s1/22/50/78/5/coa-1.jpeg" alt="" />
							<div class="portfolio_images_overlay text-center">
								<h6 class="clrd-font"><%= prop.getTitulo() %></h6>
								<p class="clrd-font product_price"> <i class="fa fa-usd clrd-font" aria-hidden="true"></i>  <%=prop.getPrecioentrada()%></p>
                                                                <a href="?titulo=<%=prop.getTitulo()%>&selle=<%=prop.getCategoria()%>" class="btn btn-primary">INFORMACION</a>
							</div>
							
					</div>               
                
               <%}%>
                </div>
        </div>
           
            <%}%>
            
            
            
         </div>
          </div>
            
            
            
            
              <%@include file="../PRESENTACIONES/footergeneral.jsp"%> 
              
              <script src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.2.5/jquery.fancybox.min.js"></script>
                <script src="SCRIPTS/listar.js"></script>
    </body>
</html>
