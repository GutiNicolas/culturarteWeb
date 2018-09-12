/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function alertar(a){
    aler(a);
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

              //  alert("contrasenia no valida");
        return correct;
}
