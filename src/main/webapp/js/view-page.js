checkLoggingUser('Zaloguj się aby przeglądać swoje notatki.');
$(document).ready(function ()
{
    var $collapseContainer = $('#accordion'),
        $collapse,
        login = getCookie('login'),
        authToken = getCookie('auth_token'),
        idNote;


    $.getJSON('./rest/note/getAllUserBasicNotes/' + getCookie('login'), '',
        function(response)
        {
            makeRowsInCollapse(response);

        });

    function makeRowsInCollapse(listObjects)
    {
        listObjects.forEach(function(element, index)
        {
            $collapse =	$('<div class="panel panel-default collapse-panel">').appendTo($collapseContainer);
            createCollapseHeader(element, $collapse, index);
        });
    }

    function createCollapseHeader(note, $collapse,i)
    {
        var $collapseHeader,
            average = 0;


        $.getJSON('./rest/rate/getRatesByIdNote/'+note.idNote, '', function(response)
        {
            if (response.length <= 0)
                average = "Brak ocen.";
            else {
                response.forEach(function (elem) {
                    average += elem.value;
                });
                average /= response.length;
                average = 'Ocena:' +average;
            }


            if(note.type=="text")
            {
                var $typeIcon='glyphicon glyphicon-text-size';
                    disabledButton = '';
            }
            if(note.type=="photo")
            {
                var $typeIcon='glyphicon glyphicon-camera';
                    disabledButton = '';

            }
            if(note.type=="pdf")
            {
                var $typeIcon='glyphicon glyphicon-download',
                    disabledButton = 'disabled';
            }

            $collapseHeader =
                '<div class="panel-heading">'+
                '<h4 class="panel-title">'+
                '<input hidden="true" data-note="'+note.idNote+'" data-type = "' + note.type +'">'+
                '<a data-toggle="collapse" class="a-collapse '+ $typeIcon + '" data-note = "'+note.idNote + '" data-parent="#accordion" href="#collapse' + i+ '">'+ '  '+note.title+'</a>'+
                '<button type="button" class="btn-delete btn-xs"  style="float: right;" data-target="#delete-modal" data-toggle="modal">Usuń</button>'+
                '<button type="button" class="btn-update btn-xs" style="float: right;"' + disabledButton+'>Edytuj</button>'+
                '<p style="display: inline; float: right; margin-right: 5%;" class="rate">'+average+'</p>'+
                '</h4>'+
                '</div>';

            $collapse.append($collapseHeader);
            $collapse.append('<div id="collapse' + i +'" class="panel-collapse collapse"></div>');
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

                    if (response[2] != null)
                        content += '<div class="panel-body-file">' +
                            '<p align="center">' +
                            //'<img src="data:application/pdf;base64,' + response[2] + '" class="glyphicon glyphicon-eye-open">' +
                            '<a href="data:application/pdf;base64,' + response[2] + '" target="_blank">Pdf</a>'+
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




    $($collapseContainer).on('click', '.btn-delete', function ()
    {
        //$('#delete-modal').modal('hide') CHOWANIE MODALU
        checkLoggingUser();
        idNote = $(this).siblings('input').attr('data-note');
    });



    $('#confirm-delete').click(function()
    {
        var formData = new FormData();

        if($.isNumeric(idNote) && idNote != null && typeof idNote != 'undefined' )
        {
            formData.append("idNote", idNote);
            formData.append("login", login);
            formData.append("authToken", authToken);


            $.ajax({
            url: "./rest/note/deleteNote",
            type: "POST",
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,  // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType
            }).done(function (data)
            {
                console.log("Usunieto");
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




    $($collapseContainer).on('click', '.btn-update', function ()
    {

        checkLoggingUser('Zaloguj się, aby edytować swoje notatki.');

        localStorage.idNote =  $(this).siblings('input').attr('data-note');
        localStorage.type =  $(this).siblings('input').attr('data-type');
        window.location.href = '../updateNote.html';

    });




});
