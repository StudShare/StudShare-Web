checkLoggingUser("Zaloguj się, aby edytować dane użytkownika");
$(document).ready(function()
{
    var formData;

    $('#login').append(getCookie('login'));

    $('#change-password').click(function()
    {
        $('#change-password-area').fadeIn('fast');
        $('#change-email-area').fadeOut('fast');
    });

    $('#change-mail').click(function()
    {
        $('#change-email-area').fadeIn('fast');
        $('#change-password-area').fadeOut('fast');
    });

    $('#btn-change-password').click(function()
    {

        var $changePasswordAlert = $('#change-password-alert'),
            password = $('#password').val(),
            newPassword = $('#new-password').val(),
            repeatNewPassword = $('#repeat-new-password').val();

        $changePasswordAlert.text('');
        $changePasswordAlert.removeClass('in');

        if(newPassword.length < 6 )
        {
            $changePasswordAlert.text('Nowe haslo musi mieć conajmniej 6 liter.');
            $changePasswordAlert.addClass('in');
            return;
        }
        else if(newPassword != repeatNewPassword)
        {
            $changePasswordAlert.text('Nowe haslo nie jest identyczne.');
            $changePasswordAlert.addClass('in');
            return;
        }


        formData = new FormData();
        formData.append("login", getCookie("login"));
        formData.append("authToken", getCookie("auth_token"));
        formData.append('password', password);
        formData.append('newPassword', newPassword);
        formData.append('repeatNewPassword', repeatNewPassword);

        $.ajax({
            url: "./rest/user/changePassword",
            type: "POST",
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,  // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType
        }).
        success(function()
        {
            $changePasswordAlert.text('Haslo zostalo zmienione!');
            $changePasswordAlert.removeClass('alert-danger');
            $changePasswordAlert.addClass('alert-success');
            $changePasswordAlert.addClass('in');
            setTimeout(function(){ location.href = "viewNote.html"}, 500);

        }).
        error(function(errorThrown)
        {
            if(errorThrown.status == 401)
            {
                $changePasswordAlert.text(errorThrown.responseText);
                $changePasswordAlert.addClass('in');
            }
            else
                console.log(errorThrown);
        });

    });

    $('#btn-change-email').click(function()
    {
        var $changeEmailAlert = $('#change-email-alert'),
            email = $('#email').val(),
            newEmail = $('#new-email').val(),
            repeatNewEmail = $('#repeat-new-email').val();

        $changeEmailAlert.text('');
        $changeEmailAlert.removeClass('in');

        if(newEmail != repeatNewEmail)
        {
            $changeEmailAlert.text('Email nie jest taki sam.');
            $changeEmailAlert.addClass('in');
            return;
        }

        formData = new FormData();
        formData.append("login", getCookie("login"));
        formData.append("authToken", getCookie("auth_token"));
        formData.append('email', email);
        formData.append('newEmail', newEmail);
        formData.append('repeatNewEmail', repeatNewEmail);

        $.ajax({
            url: "./rest/user/changeEmail",
            type: "POST",
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,  // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType
        }).
        success(function()
        {
            $changeEmailAlert.text('Email zostal zmieniony!');
            $changeEmailAlert.removeClass('alert-danger');
            $changeEmailAlert.addClass('alert-success');
            $changeEmailAlert.addClass('in');
            setTimeout(function(){ location.href = "viewNote.html"}, 500);

        }).
        error(function(errorThrown)
        {
            if(errorThrown.status == 401 || errorThrown.status == 400)
            {
                $changeEmailAlert.text(errorThrown.responseText);
                $changeEmailAlert.addClass('in');
            }
            else
                console.log(errorThrown);
        });
    });


    $("#logout").click(function ()
    {
        formData = new FormData();
        formData.append("login", getCookie("login"));
        formData.append("authToken", getCookie("auth_token"));

        $.ajax({
            url: "./rest/user/logout",
            type: "POST",
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,  // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType
        }).
        success(function()
        {
            window.location.href = 'index.html';
        });
    });
});