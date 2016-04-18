$(document).ready(function ()
{
    var $searchInput = $('#search-input');

    initializeTags($searchInput, false , 3).done(function()
    {
        $('.bootstrap-tagsinput').addClass('form-control');

        $('.tt-input').keydown( function(e)
        {
            if($searchInput.tagsinput('items').length == 3)
                e.preventDefault();
        });

        $('#search').click(function()
        {

            if($searchInput.tagsinput('items').length > 0)
            {
                var url = './rest/note/getNotesByTags?';
                $searchInput.tagsinput('items').forEach(function(item)
                {
                    url += 'tags=' +item+"&";
                });

                localStorage.url = url;
                window.location.href = '../searchNote.html';

            }
        });
    });





});
