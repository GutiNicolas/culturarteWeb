/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function alertar(a){
    alert(a);
}

function verificar()
{
        var correct = true;

        var name = $('#nick').val();
        if(name == ''){ 
                $('#error_nick').show();
                correct = false;
        } else 
                $('#error_name').hide();

        var pass = $('#pass').val();
        if(pass == '') {
                $('#error_pass').show();
                correct = false;
        } else
                $('#error_pass').hide();

              // alert("contrasenia no valida");
        return correct;
}

function verificarcolaboracion(){
    var correct=true;
    
    var ch1 = ('#cb1').checked;
    var ch2 = ('#cb2').checked;
    var mon= ('#monto').val();
    
    if(ch1==false && ch2==false){
        $('#error_retorno').show();
        correct = false;
        
    }
    else{
        $('#error_retorno').hide();
    }
    if(mon==''){
        $('#error_monto').show();
        correct = false;
    }
    else{
        $('#error_monto').hide();
    }
    
    return correct;
}


$("#btnenviarr").click(function(){
    if(verificar()===false){
        return false;
    }
    else{
    $.ajax({
       type: "POST",
       url: "/ServletLogin",
       data: {
           "nickname": $("nick").val().toString(),
           "password": $("pass").val().toString()
       } ,
       success: function (data) {
           if(data.toString()=== "errornick"){
               alert("Error: nickname invalido");
           }
           else if(data.toString()=== "errorpass"){
               alert("Error: contrasenia erronea");
           }          
           else if(data.toString()==="success"){
               window.location.replace("../index.jsp");
           }
       }
    });
    }
    
});

function ver(){
    if(verificar()===false){
        return false;
    }
    else{
    $.ajax({
       type: "POST",
       url: "/ServletLogin",
       data: {
           "nickname": $("nick").val().toString(),
           "password": $("pass").val().toString(),
       } ,
       success: function (data) {
           if(data.toString()=== "errornick"){
               alert("Error: nickname invalido");
           }
           else if(data.toString()=== "errorpass"){
               alert("Error: contrasenia erronea");
           }          
           else if(data.toString()==="success"){
               window.location.replace("../index.jsp");
           }
       }
    });
    }
    
}

//action="../ServletLogin" method="post" onsubmit="return verificar()"