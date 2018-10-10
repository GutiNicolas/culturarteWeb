<%-- 
    Document   : colaborar
    Created on : Sep 13, 2018, 12:00:03 PM
    Author     : nicolasgutierrez
--%>

<%@page import="java.util.Collection"%>
<%@page import="Logica.dtPropuesta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CultuRarte |Colaborar</title>
        <link href="ESTILOS/consultas.css" rel="stylesheet"> 
        <link href="ESTILOS/index.css" rel="stylesheet"
              <link href="ESTILOS/login.css" rel="stylesheet"      
              <%
                  if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Colaborador")) {
              %> <%@include file="../PRESENTACIONES/menucolaboradorgeneral.jsp"%> <%
                    } else if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Proponente")) {
              %> <%@include file="../PRESENTACIONES/menuproponentegeneral.jsp"%> <%
                    } else {
              %> <%@include file="../PRESENTACIONES/headergeneral.jsp"%> <%
                        }
              %>
    </head>
    <%  String error;
        if (request.getParameter("error") != null) {
            error = request.getParameter("error");
        } else {
            error = "";
        }

        if (error.equals("nn"))
            out.print("<body onload=\"alertar3('MONTO NO NUMERICO')\" style='background: url(IMAGENESDISENIO/fondo.jpg) repeat center center fixed;     background-size: cover;'>");
        else if (error.equals("ns"))
            out.print("<body onload=\"alertar3('COMPLETE LOS CAMPOS')\" style='background: url(IMAGENESDISENIO/fondo.jpg) repeat center center fixed;     background-size: cover;'>");
        else if (error.equals("no")) {
            out.print("<body onload=\"alertar3('COLABORACION REGISTRADA CON EXITO')\" style='background: url(IMAGENESDISENIO/fondo.jpg) repeat center center fixed;     background-size: cover;'>");
            
        } else if (error.equals("ya"))
            out.print("<body onload=\"alertar3('YA HAS COLABORADO CON ESTA PROPUESTA')\" style='background: url(IMAGENESDISENIO/fondo.jpg) repeat center center fixed;     background-size: cover;'>");
        else if (error.equals("ne")) {
            out.print("<body onload=\"alertar3('LA PROPUESTA NO ACEPTA COLABORACIONES')\" style='background: url(IMAGENESDISENIO/fondo.jpg) repeat center center fixed;     background-size: cover;'>");
        }else {
            out.print("<body style='background: url(IMAGENESDISENIO/fondo.jpg) repeat center center fixed;     background-size: cover;'>");
    %>



    <% }
    %>

    <%  dtPropuesta dtp = (dtPropuesta) request.getAttribute("propuesta");
        Collection<String> colaboradores = (Collection<String>) request.getAttribute("colaboradores");
    %>


    <div id="perfil" class ="main" style="min-height:670px; margin-top: 20px; margin-bottom: 20px; opacity: 0.93;">
        <div id="perfil_izquierda">
            <img src="media/images/defecto.gif" alt="imagen"/>
        </div>

        <div id="perfil_derecha">
            <div class="contenedor">
                <h2>Información de la Propuesta</h2>
                <label class="rotulo">Titulo:</label>
                <label class="valor"><%= dtp.getTitulo()%></label>
                <br/>
                <label class="rotulo">Descripcion:</label>
                <label class="valor"><%= dtp.getDescripcion()%></label>
                <br/>
                <label class="rotulo">Lugar:</label>
                <label class="valor"><%= dtp.getLugar()%></label>
                <br/>
                <label class="rotulo">Estado:</label>
                <label class="valor"><%= dtp.getEstado()%></label>
                <br/>                             
                <label class="rotulo">Fecha de realizacion:</label>
                <label class="valor">
                    <%= dtp.getFechaRealizacion().getFecha()%>
                </label>
            </div>

            <div class="contenedor">
                <h2>Aun mas</h2>
                <label class="rotulo">Precio de entrada:</label>
                <label class="valor">
                    <%= dtp.getPrecioentrada()%>
                </label>
                <br/>
                <label class="rotulo">Categoria:</label>
                <label class="valor"><%= dtp.getCategoria()%></label>
                <br/>
                <label class="rotulo">Proponente:</label>
                <label class="valor"><%= dtp.getProponente()%></label>
                <br/>
                <label class="rotulo">Monto requerido:</label>
                <label class="valor"><%= dtp.getMontorequerido()%></label>
                <br/>
                <label class="rotulo">Monto actual:</label>
                <label class="valor"><%= dtp.getMontoTotal()%></label>
                <br/>
            </div>
            <div class="contenedor">
                <h4>Colaboradores:</h4> 
                <%
                    for (String cols : colaboradores) {
                %>
                <a href="/culturarteWeb/ConsultadePerfil?nickname=<%=cols%>">
                    <%= cols%>
                </a>
                <br>
                <%}%>
            </div>
        </div>
        <div class="row">                  
            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <h2>Colaborar:</h2>
                <form id="login_form" action="/culturarteWeb/ServletColaboracion?especial=si&titulo=<%=dtp.getTitulo()%>" method="post" onsubmit="return verificarcolaboracion()">

                    <label for="name">Monto:</label>
                    <input type="text" id="monto" name="monto"/>
                    <span id="error_monto" class="error" style="	color: red;
                          display: none;
                          font-size: 12px;
                          margin-left: 6px;">Debes ingresar un monto</span>                    
                    <br/>
                    <label for="pass">Retorno:</label>
                    <%
                        if (dtp.getRetorno().contentEquals("Entrada")) {
                    %> <input type="checkbox" id="cb1" name="cb1" value="Entrada"/> <a>Entrada</a> <br> <%
                            }
                            if (dtp.getRetorno().contentEquals("Porcentaje")) {
                    %>  <input type="checkbox" id="cb2" name="cb2" value="Porcentaje"/> <a>Porcentaje</a> <br><%
                    }
                    if (dtp.getRetorno().contentEquals("Entrada/Porcentaje")) {
                    %> <input type="checkbox" id="cb1" name="cb1" value="Entrada"/> <a>Entrada</a> <br>

                    <input type="checkbox" id="cb2" name="cb2" value="Porcentaje"/> <a>Porcentaje</a> <br> <%
                        }


                    %>


                    <span id="error_retorno" class="error" style="	color: red;
                          display: none;
                          font-size: 12px;
                          margin-left: 6px;">Debes seleccionar un retorno</span>                  
                    <br/>

                    <br/>
                    <input type="submit" value="Enviar"/>  
                    <br>

                </form>



            </div>        
        </div>      
    </div>

    <script>
        function verificarcolaboracion() {
            var correct = true;

            var ch1 = ('#cb1').checked;
            var ch2 = ('#cb2').checked;
            var mon = ('#monto').val();

            if (ch1 == false && ch2 == false) {
                $('#error_retorno').show();
                correct = false;

            } else {
                $('#error_retorno').hide();
            }
            if (mon == '') {
                $('#error_monto').show();
                correct = false;
            } else {
                $('#error_monto').hide();
            }

            return correct;
        }
    </script>
    <script>
        function alertar3(a) {
            alert(a);
        }
    </script>


    <%@include file="../PRESENTACIONES/footergeneral.jsp"%>

    
    </body>

</html>