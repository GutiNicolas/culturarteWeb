<%-- 
    Document   : registrarse
    Created on : Sep 13, 2018, 11:56:15 AM
    Author     : nicolasgutierrez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CultuRarte | Registrarse</title> 
    




    <%
        if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Colaborador")) {
    %> <%@include file="../PRESENTACIONES/menucolaboradorgeneral.jsp"%> <%
    } else if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Proponente")) {
    %> <%@include file="../PRESENTACIONES/menuproponentegeneral.jsp"%> <%
    } else {
    %> <%@include file="../PRESENTACIONES/headergeneral.jsp"%> <%
        }
    %>

    <script src="./SCRIPTS/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="./SCRIPTS/usuariosRegistrar.js"></script>
    <script type="text/javascript" >
            function registro() {
                console.log("inicio...");
                var r=validaDatos();
                if(r===true){altaPerfil();}else{console.log("Faltan datos");}

                console.log("fin...");
                

            }

       
    </script>

</head>
<link rel="stylesheet" type="text/css" href="../ESTILOS/registroUsuario.css" media="screen" >
<body style="background: url(../IMAGENESDISENIO/fondo.jpg) repeat center center fixed;     background-size: cover;">
<link rel="stylesheet" type="text/css" href="../ESTILOS/registroUsuario.css" media="screen" >


    <div class="container-fluid">
        <form class="form-group">
            <div class="form-group" >  

                <label class="control-label col-sm-2" for="nickname">Nickname:</label>
                <div class="col-sm-10"> 
                    <input type="text" class="form-group" id="nickname"placeholder="Ingrese su Nickname" required>
                </div>


                <label class="control-label col-sm-2" for="nombre">Nombre:</label>
                <div class="col-sm-10"> 
                    <input type="text" class="form-group" id="nombre"placeholder="Ingrese su Nombre" required>
                </div>



                <label class="control-label col-sm-2" for="apellido">Apellido:</label>
                <div class="col-sm-10"> 
                    <input type="text" class="form-group" id="apellido"placeholder="Ingrese su Apellido" min="1910-01-01" max="2000-01-01" required>

                </div>


                <label class="control-label col-sm-2" for="fechaNac">Fecha de nacimiento:</label>
                <div class="col-sm-10"> 
                    <input type="date" class="form-group" id="fechaNac" max="2000-01-01" min="1930-01-01" placeholder="Ingrese su Fecha de nacimiento"  required>
                </div>


                <label class="control-label col-sm-2" for="email">Email:</label>
                <div class="col-sm-10"> 
                    <input type="email" class="form-group" id="email"placeholder="Ingrese su Email"required >
                </div>


                <label class="control-label col-sm-2" for="passw">Contraseña:</label>
                <div class="col-sm-10"> 
                    <input type="password" class="form-group" id="passw"placeholder="Ingrese su Contraseña" required>
                </div>


                <label class="control-label col-sm-2" for="passwC">Contraseña confirmar:</label>
                <div class="col-sm-10"> 
                    <input type="password" class="form-group" id="passwC"placeholder="Reingrese su Contraseña"required>
                </div>
            </div>

            <label class="control-label col-sm-2" >Tipo usuario</label>
            <div class="radio" >
                <label class="radio"><input type="radio"  id="opCola" name="usu" onclick="mostrarOcultar()">Colaborador</label>

                <label class="radio"><input type="radio"  id="opProp" name="usu" onclick="mostrarOcultar()">Proponente</label>
            </div>


            <div id="propo" style="display:none;">
                <div class="form-group">  
                    <label class="control-label col-sm-2" for="direccion">Direccion:</label>
                    <div class="col-sm-10"> 
                        <input type="text" class="form-group" id="direccion"placeholder="Ingrese su Direccion">
                    </div>
                </div>
                <div class="form-group">  
                    <label class="control-label col-sm-2" for="pagWeb">Pagina web:</label>
                    <div class="col-sm-10"> 
                        <input type="text" class="form-group" id="pagWeb"placeholder="Ingrese su Pagina Web">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="bioG">Biografia:</label>
                    <textarea type="textarea" class="form-group" rows="5" id="bioG"></textarea>
                </div>
            </div>  

            <br>
            <br>
            <div>
                <button type="button" class="btn-inline btn-default" id="acept" onclick="registro()">Aceptar</button>
                <button type="button" class="btn-inline btn-default" onclick="cancelar()" >Cancelar</button>
 
            </div>
        </form>
    </div>
    <div id="letajaxdoit"></div>

    <!-- <div id="letajaxdoit"></div>-->
    <%@include file="../PRESENTACIONES/footergeneral.jsp"%>
</body>
</html>
