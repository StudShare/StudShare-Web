
$(document).ready(function ()
{
    var $registerAlert = $("#register-alert"),
        $loginAlert = $("#login-alert");

    $("#register-me").click(function ()
    {
        $(".login-side").fadeToggle();
        $(".register-side").fadeToggle("fast");
    });

    $("#return-to-login").click(function ()
    {
        $(".register-side").fadeToggle();
        $(".login-side").fadeToggle("fast");
    });

    $("#btn-login").click(function ()
    {
        $loginAlert.removeClass('in');
        $loginAlert.text('');

        var login =  $("#login").val(),
            password = $("#password").val();

        doAjax("./rest/user/login", 'POST', '',
            {
                login: login,
                password: password,
                ssid: getCookie('auth_token')
            }, null).
        success(function(response)
        {
            //tu bedzie przekierowanie do moich notatek albo profilu
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


        doAjax("./rest/user/register", 'POST', '',
            {
                'login': login,
                'email' : email,
                'repeat_email': repeatEmail,
                'password': password,
                'repeat_password': repeatPassword
            }, null).
        success(function()
        {
            window.location.href = '../registration.html';
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

    $("#logout").click(function ()
    {

        doAjax("./rest/user/logout", 'POST', '',
            {
                'login': getCookie("login"),
                'auth_token': getCookie('auth_token')
            }, null).
        success(function()
        {
            console.log('logout');
        }).
        error(function(errorThrown)
        {

            if( errorThrown.status != 400 )
                console.log(errorThrown.responseText);
            else
            {
                $registerAlert.addClass("in");
                $registerAlert.text(errorThrown.responseText);
            }

        });

    });
});