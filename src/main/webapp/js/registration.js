if(getCookie('email') == null)
    window.location.href = "index.html";

$(document).ready(function () {

    var $message = $('#message');

    if(getCookie('message') != null)
    {
         $message.text(getCookie('message'));
    }

    $('#email').text(getCookie('email'));


});