<%-- 
    Document   : dejardeseguirusuario
    Created on : Sep 13, 2018, 11:58:46 AM
    Author     : nicolasgutierrez
--%>

<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="ESTILOS/altapropuesta.css" rel="stylesheet"> 
           
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
                    var nickadejardeseguir=$('#sell').val();
                    $.ajax({
                        type: 'POST',
                        url: 'DejarDeSeguir',
                        data: { nickadejardeseguir: nickadejardeseguir},
                        success: function(result3){
                            $('#letajaxdoit').html(result3);
                        }
                    });  
                });
                
            });
        </script>
    </head>
    <body style='background: url(IMAGENESDISENIO/fondo.jpg) repeat center center fixed;     background-size: cover;'>
        
        <%
            Collection<String> usuarios= (Collection<String>) request.getAttribute("usuarios");
        %>
        
        <div class="form-group">
            <label for="sell">Seleccione un Usuario</label>
            <select class="form-control" id="sell">
                <% 
                    for(String usus: usuarios){
                %>
                <option><%=usus%></option>
                <%}%>
            </select>
            <br>
            <input type="button" value="Dejar de Seguir" id="bttacept">
                <br>
                <div id="letajaxdoit"></div>
        </div> 
            <br><br><br><br><br><br><br><br><br><br><br><br>
            
              <%@include file="../PRESENTACIONES/footergeneral.jsp"%> 
    </body>
</html>
