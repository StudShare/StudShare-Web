if(localStorage.url == null || typeof localStorage.url == 'undefined')
    window.location.href = '../index.html';
$(document).ready(function()
{
    var $collapseContainer = $('#accordion'),
        $collapse,
        $successAddAlert = $('#success-add-alert'),
        $noteAlert = $('#note-alert');

    var noteRate,
        login = getCookie('login'),
        authToken = getCookie('auth_token');


    $.getJSON(localStorage.url, '',
        function(response)
        {
            //localStorage.removeItem('url');
            makeRowsInCollapse(response);
        });

    function makeRowsInCollapse(listObjects)
    {

        listObjects.forEach(function(note, index)
        {
            $collapse =	$('<div class="panel panel-default collapse-panel">').appendTo($collapseContainer);
            createCollapseHeader(note, $collapse, index);

        });
    }

    function createCollapseHeader(note, $collapse,i)
    {
        var $collapseHeader,
            average =0;

        $.getJSON('./rest/rate/getRatesByIdNote/'+note.idNote, '', function(response)
        {

            if (response.length <= 0)
                average = "Brak ocen. Bądź pierwszy!";
            else {
                response.forEach(function (elem) {
                    average += elem.value;
                });
                average /= response.length;
                average = 'Ocena:' +average;
            }

            $collapseHeader =
                '<div class="panel-heading">' +
                '<h4 class="panel-title">' +
                '<input hidden="true" data-note="' + note.idNote + '">' +
                '<a data-toggle="collapse" class="a-collapse" data-note = "' + note.idNote + '" data-parent="#accordion" href="#collapse' + i + '">' + note.title + ' ' + note.type + '</a>' +
                '<button style="display: inline; float: right; margin-left: 5%" type="button" class="btn-add btn-xs">Dodaj ocenę</button>'+
                '<p style="display: inline; float: right;" class="rate">'+average+'</p>'+
                '</h4>' +
                '</div>';

            $collapse.append($collapseHeader);
            $collapse.append('<div id="collapse' + i + '" class="panel-collapse collapse"></div>');
        });
    }

    $collapseContainer.on('click','.a-collapse', function()
    {

        var div =  $($(this).attr('href')),
            idNote = $(this).attr('data-note');

        if(div.children().length == 0)
            $.getJSON('./rest/note/getAllUserPictureNotes/'+idNote, '', function(response)
            {
                if (div.children().length == 0)
                {
                    var content = '';

                    if (response[0] != null)
                        content += '<div class="panel-body-text">' +
                            '<p align="justify">' + response[0] + '</p>' +
                            '</div>';
                    if (response[1] != null)
                        content += '<div class="panel-body-picture">' +
                            '<p align="center">' +
                            '<img src="data:image/jpg;base64,' + response[1] + '" class="img-responsive" alt="Brak zdjecia">' +
                            '</p>' +
                            '</div>';

                    div.append(content);
                    $.getJSON("/rest/tag/getTagsByIdNote/"+idNote).success(function(response)
                    {
                        var d = $('<div class="tags-container" >Tagi:</div>').appendTo(div);
                        response.forEach(function(element, i)
                        {
                            d.append('<span class="tag label label-info">' + element + '</span></div>' );
                        })
                    });
                }


            });

    });




    $(':radio').change(  function(){

        noteRate=this.value;
    } );


    $($collapseContainer).on('click', '.btn-add', function ()
    {
        var idNote = $(this).siblings('input').attr('data-note'),
            formdata = new FormData();

        formdata.append("value", noteRate);
        formdata.append("idNote", idNote);
        formdata.append("login", login);
        formdata.append("authToken", authToken);
        console.log(formdata);


        $.ajax({
            url: "./rest/rate/addRate",
            type: "POST",
            data: formdata,
            enctype: 'multipart/form-data',
            processData: false,  // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType
        }).done(function (data)
        {
            $successAddAlert.addClass('in');
            console.log("przeslano");
            setTimeout(function (){location.reload(true);}, 3000);

        }).error(function (errorThrown)
        {
            console.log(" nie przeslano");
            if(errorThrown.status == 401)
            {
                localStorage.message = errorThrown.responseText;
                window.location.href = "index.html";
            }

            $noteAlert.addClass("in");
            //$noteAlert.text(errorThrown.responseText);
            $noteAlert.text("Nie wybrano oceny. Wybierz ocenę - ilość gwiazdek - w menu powyżej");
        });

/*
        console.log(idNote);
        console.log(noteRate);
        console.log(login);
        console.log(authToken);*/


    });





});
