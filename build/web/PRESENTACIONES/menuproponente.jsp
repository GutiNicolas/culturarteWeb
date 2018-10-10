<%-- 
    Document   : menuproponente
    Created on : Sep 12, 2018, 11:40:19 AM
    Author     : nicolasgutierrez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    </head>
    <body>
<nav class="navbar navbar-expand-lg navbar-dark " style="background-color:#1b1c1b;">
            <a class="navbar-brand" href="index.jsp" style="color: #f2d5a9;">              
                CULTURARTE
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto"> 
                     <li class="nav-item dropdown ">
                        <a class="nav-link dropdown-toggle" style="color: #f2d5a9;" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Usuarios
                        </a>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenu">
                            <a class="dropdown-item" href="/culturarteWeb/ConsultadePerfil">Consulta de Perfil</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="/culturarteWeb/ServletSeguir">Seguir usuario</a>  
                            <a class="dropdown-item" href="/culturarteWeb/DejarDeSeguir">Dejar de seguir usuario</a>  
                        </div>
                    </li>
                    <li class="nav-item dropdown ">
                        <a class="nav-link dropdown-toggle" style="color: #f2d5a9;" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Propuestas
                        </a>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenu">
                            <a class="dropdown-item" href="/culturarteWeb/ConsultadePropuesta">Consulta de Propuesta</a>
                            <a class="dropdown-item" href="/culturarteWeb/ConsultaPorEstado">Consulta de Propuesta por Categoria</a>  
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="/culturarteWeb/AgregarFavorita">Propuesta como favorita</a> 
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="/culturarteWeb/AltaPropuesta">Alta de Propuesta</a>
                            <a class="dropdown-item" href="#">Extender financiacion</a> 
                            <a class="dropdown-item" href="/culturarteWeb/CancelarPropuesta">Cancelar Propuesta</a> 
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" style="color: #f2d5a9;" href="/culturarteWeb/AltaPropuesta">Tengo una Propuesta</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" style="color: #f2d5a9;" href="/culturarteWeb/ConsultadePropuesta">Quiero ver Propuestas</a>
                    </li>
                </ul>
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" style="color: #f2d5a9;" href="/culturarteWeb/DatosdePrueba">Datos de Prueba</a>
                    </li>
                    <li class="nav-item dropdown ">
                        <a class="nav-link dropdown-toggle" style="color: #f2d5a9;" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                           <%= session.getAttribute("nickusuario") %>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenu">
                            <a class="dropdown-item" href="/culturarteWeb/ConsultadePerfil?usuario=yes">Perfil</a>                           
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="/culturarteWeb/logout">Log out</a>
                        </div>
                    </li>                   
                </ul>
                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="Titulo de propuesta" aria-label="Search">
                    <button class="btn btn-outline-light my-2 my-sm-0 mr-sm-5" style="color: #f2d5a9; border-color: #f2d5a9;" type="submit">Buscar</button>
                </form>
            </div>
        </nav>    

                         <script src="./SCRIPTS/jquery-3.3.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
     
    </body>
</html>

