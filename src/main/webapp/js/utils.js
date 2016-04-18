function getCookie(cname)
{
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return null;
}

function checkLoggingUser(message)
{
    if(getCookie('login') == null ||  getCookie('auth_token') == null)
    {
        localStorage.message = message;
        window.location.href = "index.html";
    }
}

function createGuid()
{
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random()*16|0, v = c === 'x' ? r : (r&0x3|0x8);
        return v.toString(16);
    });
}
function  showAlert($alert, lowerValue, maxValue, textIfError, func)
{
    if(lowerValue > maxValue)
    {
        $alert.addClass('in');
        $alert.text(textIfError);
        if( func != undefined)
            func();
    }
}
function initializeTags($input, freeInput, maxTags)
{
    return $.getJSON('./rest/tag/getAllTags').success(function(response)
    {
        var tags = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.obj.whitespace("name"),
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            local: $.map(response, function (tag) {
                return {
                    name: tag
                };
            })
        });
        tags.initialize();

        $input.tagsinput({
            trimValue : true,
            maxChars: 10,
            maxTags: maxTags,
            typeaheadjs: [{
                minLength: 1,
                highlight: true,
            },{
                minlength: 1,
                name: 'tags',
                displayKey: 'name',
                valueKey: 'name',
                source: tags.ttAdapter()
            }],
            freeInput: freeInput
        });

    });

}