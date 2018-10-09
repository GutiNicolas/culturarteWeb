/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var tipo;
function mostrarOcultar() {
    if ($('#opCola').is(':checked')) {
        $('#propo').css("display", "none");
        tipo = "Colaborador";
        document.getElementById("bioG").value = null;
        document.getElementById("direccion").value = null;
        document.getElementById("pagWeb").value = null;
        ;
    }
    if ($('#opProp').is(":checked")) {
        $('#propo').css("display", "block");
        tipo = "Proponente";
    }
}
function armaFecha(bruto) {
    var b = bruto.toString();
    b.split("-");
    return b.split("-")[2] + "/" + b.split("-")[1] + "/" + b.split("-")[0];

}
function altaPerfil() {




    var nombre = $('#nombre').val();
    var apellido = $('#apellido').val();
    var nickname = $('#nickname').val();
    var correo = document.getElementById("email");
    var email = correo.value;
    var dt = $('#fechaNac').val();
    var fecha = armaFecha(dt);
    var pass1 = $('#passw').val();
    var pass2 = $('#passwC').val();
    var direccion = null;
    var bio = null;
    var pagWeb = null;
    if (tipo.valueOf() === "Proponente") {
        direccion = $('#direccion').val();
        bio = $('#bioG').val();
        pagWeb = $('#pagWeb').val();
    }

    if (pass1 !== pass2) {
        $('#pass1').focus().select();
        alert("Contraseñas no coinciden");
    }
    console.log("PTM");
    $.ajax({
        url: 'servletRegistrarse',
        data: {
            nombre: nombre,
            apellido: apellido,
            nickname: nickname,
            correo: email,
            nacimiento: fecha,
            password: pass1,
            password2: pass2,
            direccion: direccion,
            bio: bio,
            pagWeb: pagWeb, tipo: tipo},
        type: 'POST',
        success: function (data) {

            if (data.toString() === "correoE") {
                console.log("correoE");
                alert("Error: ya existe un usuario registrado con ese correo!");
            }
            if (data.toString() === "usuarioE") {
                console.log("usuarioE");
                alert("Error: ya existe un usuario registrado con el mismo nickname");
            } else {
                alert(data.toString());
                inicio();
            }


        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("error" + errorThrown.toString());
        }
    });


}
function inicio() {
    var nickname = $('#nickname').val();
    var pass1 = $('#passw').val();
    $.ajax({url: 'ServletLogin',
        data: {nick: nickname,
            pass: pass1}, type: 'POST',
        success: function (data) {
            if (data) {
                window.location.replace("index.jsp");
            }
        }


    });
}

function validaDatos() {
    var correo = document.getElementById("email").value;
    var nombre = $('#nombre').val();
    var apellido = $('#apellido').val();
    var nick = $('#nickname').val();
    var fechan = $('#fechaNac').val();
    var pass = $('#passw').val();
    var pass2 = $('#passwC').val();

    if (nick === null || nick.length === 0 || /^\s+$/.test(nick)) {
        alert("Debe ingresar un nickname");
        $('#nickname').focus();
        console.log("Enickname");
        return false;
    }
    if (nombre === null || nombre.length == 0 || /^\s+$/.test(nombre)) {
        alert("Debe ingresar un nombre");
        $('#nombre').focus();
        console.log("Enombre");
        return false;
    }
    console.log("llega");
    if (apellido === null || apellido.length === 0 || /^\s+$/.test(apellido)) {
        alert("Debe ingresar un apellido");
        $('#apellido').focus();
        console.log("Eapellido");
        return false;
    }

    if (correo === null || correo.length === 0 || /^\s+$/.test(correo)) {
        alert("Debe ingresar un correo");
        document.getElementById("email").focus();
        console.log("Ecorreo");
        return false;
    }
    
    if (fechan === null || fechan.length === 0 || /^\s+$/.test(fechan)) {
        alert("Debe ingresar una fecha de nacimiento");
        $('#fechaNac').focus();
        console.log("EfechaNac");
        return false;
    }
    
    if (pass === null || pass.length === 0 || /^\s+$/.test(pass)) {
        alert("Debe ingresar una password");
        $('#passw').focus();
        console.log("Epassw");
        return false;
    }
    
    if (pass2 === null || pass2.length === 0 || /^\s+$/.test(pass2)) {
        alert("Debe ingresar una password");
        $('#passwC').focus();
        console.log("EpasswC");
        return false;
    }
    if ((!$('#opCola').is(':checked')) && (!$('#opProp').is(':checked'))) {
        alert("Debe completar los datos faltantes");
        return false;
    }
    return true;
}
function cancelar() {

    $.get('ServletLogin', function (data) {
        if (data) {
            window.location.replace("index.jsp");
        }
    });

}
