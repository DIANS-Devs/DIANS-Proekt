document.addEventListener('DOMContentLoaded', function () {
    // Check if the 'favorites' cookie exists
    var favoritesCookie = getCookie('favorites');
    if (!favoritesCookie) {
        // If the cookie doesn't exist, show an alert and create a new cookie with an empty array
        var acceptCookies = confirm('This site uses cookies. Do you accept?');
        if (acceptCookies) {
            setCookie('favorites', '[]', 365, '/', true, 'None');
        }
    }
});

function getCookie(name){
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.indexOf(name + '=') === 0) {
            return cookie.substring((name + '=').length, cookie.length);
        }
    }
    return null;
}
function setCookie(name, value, days, path, secure, sameSite) {
    var expires = '';
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = '; expires=' + date.toUTCString();
    }
    var cookieString = name + '=' + value + expires + '; path=' + (path || '/');
    if (secure) {
        cookieString += '; Secure';
    }
    if (sameSite) {
        cookieString += '; SameSite=' + sameSite;
    }
    document.cookie = cookieString;
}
