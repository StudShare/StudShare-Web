if(getCookie('login') != null &&  getCookie('auth_token') != null)
    window.location.href = 'viewNote.html';
$(document).ready(function ()
{
    var $registerAlert = $("#register-alert"),
        $loginAlert = $("#login-alert"),
        formData;


    $loginAlert.removeClass('in');
    $loginAlert.text('');
    if(localStorage.getItem('message') !== null)
    {
        $loginAlert.addClass('in');
        $loginAlert.text(localStorage.message);
        localStorage.removeItem('message');
    }

    $("#register-me").click(function ()
    {
        $(".login-side").fadeToggle(500);
        setTimeout(function(){$(".register-side").fadeToggle("fast")}, 500);
    });

    $("#return-to-login").click(function ()
    {
        $(".register-side").fadeToggle(500);
        setTimeout(function(){$(".login-side").fadeToggle("fast");}, 500);
    });

    $("#btn-login").click(function ()
    {
        $loginAlert.removeClass('in');
        $loginAlert.text('');

        var login =  $("#login").val(),
            password = $("#password").val();

        formData = new FormData();
        formData.append('login', login);
        formData.append('password', password);
        formData.append('authToken', getCookie('auth_token'));

        $.ajax({
            url: "./rest/user/login",
            type: "POST",
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,  // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType
        }).
        success(function(response)
        {
            window.location.href = 'viewNote.html';
        }).
        error(function(errorThrown)
        {
            if( errorThrown.status == 401 || errorThrown.status == 400 )
            {
                $loginAlert.addClass("in");
                $loginAlert.text(errorThrown.responseText);
            }
            else
            {
                console.log(errorThrown.responseText);
            }

        });
    });

    $("#btn-register").click(function ()
    {
        $registerAlert.removeClass('in');
        $registerAlert.text('');

        var login =  $('#new-login').val(),
            email = $('#new-email').val(),
            repeatEmail = $('#new-repeat-email').val(),
            password = $('#new-password').val(),
            repeatPassword = $('#new-repeat-password').val();

        formData = new FormData();
        formData.append('login', login);
        formData.append('email', email);
        formData.append('repeatEmail', repeatEmail);
        formData.append('password', password);
        formData.append('repeatPassword', repeatPassword);


        $.ajax({
            url: "./rest/user/register",
            type: "POST",
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,  // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType
        }).
        success(function()
        {
            window.location.href = 'registration.html';
        }).
        error(function(errorThrown)
        {

            if( errorThrown.status != 400  )
                console.log(errorThrown.responseText);
            else
            {
                $registerAlert.addClass("in");
                $registerAlert.text(errorThrown.responseText);
            }

        });

    });

});