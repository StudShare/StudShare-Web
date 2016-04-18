checkLoggingUser('Zaloguj się, aby edytować notatkę.');
if(localStorage.idNote == null || typeof localStorage.idNote == 'undefined' || localStorage.type == null || typeof localStorage.type == 'undefined' )
    window.location.href = '../viewNote.html';
$(document).ready(function ()
{


    var $minipictureContainer = $('#minipicture'),
        $minipicture,
        $updateAlert = $('#update-alert'),
        $tagAlert = $ ('#tag-alert'),
        checkBox;

     var login = getCookie('login'),
         authToken = getCookie('auth_token'),
         $idNote = $('#idNote'),
         $title = $("#title"),
         $picturecontent = $("#picturecontent"),
         $textcontent =  $("#textcontent"),
         $tags = $('#note-tags');

     var idNoteVal = localStorage.idNote,
         noteType = localStorage.type,
         titleVal,
         picturecontentVal,
         textcontentVal,
         tagsVal = new Array();



    if (noteType == 'text')
    {
        $(".other-side").fadeOut();
        $(".text-side").fadeIn();
    }
    if (noteType == 'photo'){
        $(".text-side").fadeOut();
        $(".other-side").fadeIn();
    }
    if(noteType == 'file')
    {
    }



    $tags.on('beforeItemAdd', function(event)
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

    initializeTags($tags, true, 10).done(function()
    {
        $.getJSON('./rest/note/getUserNoteByID/'+ idNoteVal).success(function(response)
        {

            if(typeof response == 'undefined')
                window.location.href = '../viewNote.html';

            $idNote.val(response.idNote);
            titleVal = response.title;
            $title.val(titleVal);
            textcontentVal = response.textcontent;
            $textcontent.val(textcontentVal);
            picturecontentVal = response.picturecontent;
            //$picturecontent.val(picturecontentVal);

            $minipicture = $('<p align="center">'+
                             '<img src="data:image/jpg;base64,' + picturecontentVal +  '" class="img-responsive" alt="Brak zdjecia" width="304" height="236">'+
                             '</p>').appendTo($minipictureContainer);

            response.tags.forEach(function(tag, i)
            {
                //i==0? $tags.tagsinput('refresh'): '';
                $tags.tagsinput('add', tag.value);
                tagsVal[i] = tag.value;

            });
        })
    });


    $('input[type="checkbox"]').click(function(){
        if($(this).prop("checked") == true){
            checkBox = true;

        }
        else if($(this).prop("checked") == false){
            checkBox = false;
        }
    });



    $('#btn-edit').click(function()
    {
        if(typeof idNoteVal == null && typeof idNoteVal == 'undefined' )
            return;

             var newTitle = $("#title").val(),
                 newPicturecontent = $("#picturecontent")[0].files[0],
                 newTextcontent =  $("#textcontent").val(),
                 newTags = $('#note-tags').tagsinput('items'),
                 formData = new FormData();


        if( newTitle === titleVal &&
            newTextcontent === textcontentVal &&
            JSON.stringify(newTags) === JSON.stringify(tagsVal))
        {
            $updateAlert.text('Edytuj notatkę, dane są takie same!');
            $updateAlert.addClass('in');
        }
        else
        {

            formData.append("idNote", idNoteVal);
            formData.append("login", login);
            formData.append("authToken", authToken);


            console.log(checkBox);
            if (checkBox = (true) && checkBox != undefined && checkBox != null)
            {
                formData.append("picturecontent", null);
                checkBox = false;
                console.log("co?");
                console.log(checkBox);
            }
            if (newPicturecontent != undefined && (checkBox == (false) || checkBox == undefined || checkBox == null)) {
                console.log(checkBox);
                console.log("weszlo?");
                formData.append("picturecontent", newPicturecontent);
            }

/*
            if (newPicturecontent != undefined)
                formData.append("picturecontent", newPicturecontent);
*/


            formData.append("textcontent", newTextcontent);
            formData.append("title", newTitle);
            formData.append('tagsValues', newTags);

            $.ajax({

                url: "./rest/note/updateNote",
                type: "POST",
                data: formData,
                enctype: 'multipart/form-data',
                processData: false,  // tell jQuery not to process the data
                contentType: false   // tell jQuery not to set contentType
            }).done(function (data) {
                console.log("Edytowano");
                location.reload(true);
                console.log(data);
            }).error(function(errorThrown)
            {
                if(errorThrown.status == 401)
                {
                    localStorage.message = errorThrown.responseText;
                    window.location.href = "index.html";
                }
            });
        }
    });

});
