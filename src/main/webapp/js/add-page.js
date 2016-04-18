checkLoggingUser('Zaloguj się, aby dodawać notatki.');

var _validFileExtensionsPhoto = [".jpg", ".jpeg", ".bmp", ".gif", ".png"];
var _validFileExtensionsPDF = [".pdf"];
function ValidateSingleInputPhoto(oInput) {
    if (oInput.type == "file") {
        var sFileName = oInput.value;
        if (sFileName.length > 0) {
            var blnValid = false;
            for (var j = 0; j < _validFileExtensionsPhoto.length; j++) {
                var sCurExtension = _validFileExtensionsPhoto[j];
                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                    blnValid = true;
                    break;
                }
            }

            if (!blnValid) {
                alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + _validFileExtensionsPhoto.join(", "));
                oInput.value = "";
                return false;
            }
        }
    }
    return true;
}

function ValidateSingleInputPDF(oInput) {
    if (oInput.type == "file") {
        var sFileName = oInput.value;
        if (sFileName.length > 0) {
            var blnValid = false;
            for (var j = 0; j < _validFileExtensionsPDF.length; j++) {
                var sCurExtension = _validFileExtensionsPDF[j];
                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                    blnValid = true;
                    break;
                }
            }

            if (!blnValid) {
                alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + _validFileExtensionsPDF.join(", "));
                oInput.value = "";
                return false;
            }
        }
    }
    return true;
}



$(document).ready(function ()
{

    var login = getCookie('login'),
        authToken = getCookie('auth_token'),
        $noteAlert = $('#note-alert'),
        $tagAlert = $ ('#tag-alert'),
        $noteTags = $('#note-tags'),
        $successAddAlert = $('#success-add-alert'),
        addOnce = true,
        noteType;

    $(".picture-side").fadeOut();
    $(".pdf-side").fadeOut();
    $(".text-side").fadeOut();


    $("#text-rb").click(function ()
    {
        $(".picture-side").fadeOut();
        $(".pdf-side").fadeOut();
        $(".text-side").fadeIn();
        noteType = "text";
    });


    $("#graphic-rb").click(function ()
    {
        $(".text-side").fadeOut();
        $(".pdf-side").fadeOut();
        $(".picture-side").fadeIn();
        noteType = "photo";
    });



    $("#file-rb").click(function ()
    {
        $(".text-side").fadeOut();
        $(".picture-side").fadeOut();
        $(".pdf-side").fadeIn();
        noteType = "file";
    });




    initializeTags($noteTags, true, 10);

    $noteTags.on('beforeItemAdd', function(event)
    {
        $tagAlert.removeClass("in");
        $tagAlert.text('');

        showAlert($tagAlert, 3,event.item.length, 'Tag musi mieć conajmniej 3 litery', function () {
            event.cancel = true;
        });
        showAlert($tagAlert, event.item.length, 10, 'Tag moze mieć maksymalnie 10 liter', function () {
            event.cancel = true;
        });
    });


    $("#btn-add").click(function ()
    {

        //SPRAWDZAMY CZY UZYTKOWNIK JEST ZALOGOWANY, A W RESCIE SPRAWDZISZ CZY TEN AUTH_TOKEN NALEZY DO TEGO UZYTKOWNIKA I CZY W OGOLE ISTNIEJE
        checkLoggingUser();

        $noteAlert.removeClass("in");
        $noteAlert.text('');

        var picturecontent = $("#picturecontent")[0].files[0],
            textcontent =  $("#textcontent").val(),
            filecontent = $("#filecontent")[0].files[0],
            title = $("#title").val(),
            tags = $("#note-tags").tagsinput('items'),
            formdata = new FormData();

        console.log(filecontent);

        if(title == "")
        {
            $noteAlert.text('Notatka musi zawierac tytul!');
            $noteAlert.addClass('in');
            return;
        }
        if(textcontent == "" && picturecontent == undefined && filecontent == undefined)
        {
            $noteAlert.text('Notatka musi zawierać treść albo(i) zdjęcie!');
            $noteAlert.addClass('in');
            return;
        }
        if(tags.length < 1)
        {
            $noteAlert.text('Notatka musi zawierać minimum 1 tag!');
            $noteAlert.addClass('in')
            return;
        }

        if(addOnce)
        {
            formdata.append("login", login);
            formdata.append("authToken", authToken);
            formdata.append("textcontent", textcontent);
            if (picturecontent != undefined)
                formdata.append("picturecontent", picturecontent);
            if (filecontent != undefined) {
                 formdata.append("filecontent", filecontent);
                 console.log("dodalo filecontent do form");
             }
            formdata.append("title", title);
            formdata.append("tagsValues", tags);

            addOnce = false;

            $.ajax({
                url: "./rest/note/addNote",
                type: "POST",
                data: formdata,
                enctype: 'multipart/form-data',
                processData: false,  // tell jQuery not to process the data
                contentType: false   // tell jQuery not to set contentType
            }).done(function (data)
            {
                $successAddAlert.addClass('in');
                setTimeout(function (){location.reload(true);}, 3000);

            }).error(function (errorThrown)
            {
                if(errorThrown.status == 401)
                {
                    localStorage.message = errorThrown.responseText;
                    window.location.href = "index.html";
                }

                $noteAlert.addClass("in");
              //  $noteAlert.text(errorThrown.responseText);
                $noteAlert.text("Twoje konto nie jest potwierdzone");
                addOnce = true;
            });
        }

    });



});