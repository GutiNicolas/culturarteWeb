<%-- 
    Document   : altapropuesta
    Created on : Sep 13, 2018, 12:00:45 PM
    Author     : nicolasgutierrez
--%>

<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CultuRarte | Alta Propuesta</title>
        <link href="ESTILOS/altapropuesta.css" rel="stylesheet"> 
        <link href="ESTILOS/mrd.css" rel="stylesheet"> 
           
              <%
                  if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Colaborador")) {
              %> <%@include file="../PRESENTACIONES/menucolaboradorgeneral.jsp"%> <%
                    } else if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Proponente")) {
              %> <%@include file="../PRESENTACIONES/menuproponentegeneral.jsp"%> <%
                    } else {
              %> <%@include file="../PRESENTACIONES/headergeneral.jsp"%> <%
                        }
              %>
              
         
        <script type="text/javascript">
            $(document).ready(function(){
                $('#bttacept').click(function(){
                    var titulo= $('#titulo').val();
                    var descripcion= $('#descripcion').val();
                    var lugar= $('#lugar').val();
                    var cben= document.getElementById("cben");
                    var cbpo= document.getElementById("cbpo");
                    var categoria=$('#sell').val();
                   
                    if(cben.checked==true && cbpo.checked==false){
                        var retorno="Entrada";
                    }
                    else if(cben.checked==true && cbpo.checked==true){
                        var retorno="Entrada/Porcentaje";
                    }
                    else if(cben.checked==false && cbpo.checked==true){
                        var retorno="Porcentaje";
                    }
                    else{
                        var retorno="";
                    }
                    
                    var montorequerido= $('#montorequerido').val();
                    var costoentrada= $('#costoentrada').val();
                    var fecharealizacion= $('#fecharealizacion').val();
                    $.ajax({
                        type: 'POST',
                        url: 'AltaPropuesta',
                        data: {titulo: titulo,
                        descripcion: descripcion,
                        lugar: lugar,
                        retorno: retorno,
                        montorequerido: montorequerido,
                        costoentrada: costoentrada,
                        fecharealizacion: fecharealizacion,
                        categoria: categoria},
                        success: function(result3){
                            $('#letajaxdoit').html(result3);
                            
                            document.getElementById("descripcion").value = null;
                            document.getElementById("titulo").value = null;
                            document.getElementById("lugar").value = null;
                            document.getElementById("cbpo").value = null;
                            document.getElementById("cben").checked=false;
                            document.getElementById("montorequerido").value = null;
                            document.getElementById("costoentrada").value = null;
                            document.getElementById("fecharealizacion").value = null;}
                        
                    });  
                });
                
            });
        </script>
    </head>
        
    <body style='background: url(IMAGENESDISENIO/fondo.jpg) repeat center center fixed;     background-size: cover;'>
    
        <%
            Collection<String> categorias= (Collection<String>) request.getAttribute("categorias");
        %>
          <div id="contenedor3">  
         <div id="main3">   
        <div class="form-group">
            <label for="sell">Seleccione una Categoria</label>
            <select class="form-control" id="sell">
                <% 
                    for(String cats: categorias){
                %>
                <option><%=cats%></option>
                <%}%>
            </select>
        </div>    
    
    <div class="container">
        <form class="formalta">
        <div class="row">
            <div class="col-sm-6 text-right">
                Titulo <input type="text" id="titulo"> <br> 
                <label for="comment">Descripcion</label>
                <textarea class="form-control" rows="4" id="descripcion" cols="2"></textarea> <br> 
                Lugar <input type="text" id="lugar"> <br>
                
            </div>
            <div class="col-sm-6 text-left">
                <div class="retos">
                    <label for="retorns">Retornos:</label> <br>
                    <div class="checkbox">
                        <label><input type="checkbox" value="Entrada" id="cben">Entrada</label>
                    </div>
                    <div class="checkbox">
                        <label><input type="checkbox" value="Porcentaje" id="cbpo">Porcentaje</label>
                    </div>
                </div>
                Monto Requerido <input type="text" id="montorequerido"> <br> <br>
                Costo de la Entrada <input type="text" id="costoentrada"> <br> <br>
                Fecha Realizacion <input type="text" id="fecharealizacion" placeholder="DD/MM/AAAA"> <br> <br>
                <input type="button" value="Aceptar" id="bttacept">
                <br>
                <div id="letajaxdoit"></div>
            </div>
        </div>
        </form>

     
    </div>
         </div>
          </div>
    
        <%@include file="../PRESENTACIONES/footergeneral.jsp"%>    
            
        
    </body>
        
    
        
        
    
</html>
