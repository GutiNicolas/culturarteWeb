/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



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