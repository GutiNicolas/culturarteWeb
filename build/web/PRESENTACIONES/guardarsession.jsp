<%-- 
    Document   : guardarsession
    Created on : Sep 12, 2018, 11:33:36 AM
    Author     : nicolasgutierrez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Guardado de datos | Culturarte</title>
         
    </head>
    <body>
        <% String nombre=request.getParameter("nick");
        session.setAttribute("nickusuario", nombre);
        %>
        <META HTTP-EQUIV="REFRESH" CONTENT="1;URL=../index.jsp">
    </body>
</html>
