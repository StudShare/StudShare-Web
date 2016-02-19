function doAjax(url, type, dataType, headers, data)
{
    return $.ajax
    ({
        url: url,
        type: type,
        dataType: dataType,
        processData: false,
        headers: headers,
        data: data
    });
}
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

function checkLoggingUser()
{
    if(getCookie('login') == null ||  getCookie('auth_token') == null)
    {
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