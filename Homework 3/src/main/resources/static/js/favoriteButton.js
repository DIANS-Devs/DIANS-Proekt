function getCookie(name) {
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.indexOf(name + '=') === 0) {
            return cookie.substring((name + '=').length, cookie.length);
        }
    }
    return null;
}
function setCookie(name, value, days) {
    var expires = '';
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = '; expires=' + date.toUTCString();
    }
    document.cookie = name + '=' + value + expires + '; path=/';
}
function getFavoritesFromCookie() {
    var favoritesCookie = getCookie('favorites');
    return favoritesCookie ? JSON.parse(favoritesCookie) : [];
}
function setFavoritesCookie(favorites) {
    setCookie('favorites', JSON.stringify(favorites), 365);
}

function addToFavorites(element) {
    //TODO modify here later...
    let wineryId = element.getAttribute('data-winery-id');
    let child = element.querySelector('i');
    if (wineryId.trim() !== '') {
        var favorites = getFavoritesFromCookie();
        var index = favorites.indexOf(wineryId);
        if (index === -1) {
            favorites.push(wineryId);
            child.classList.remove("far");
            child.classList.add("fas");
        } else {
            favorites.splice(index, 1);
            child.classList.remove("fas");
            child.classList.add("far");
        }
        setFavoritesCookie(favorites);

        favoriteButton(favorites);
    }
}
function checkFavorite(element){
    var wineryId = element.getAttribute('data-winery-id');
    let child = element.querySelector('i');
    if (wineryId.trim() !== '') {
        let favorites = getFavoritesFromCookie();
        let index = favorites.indexOf(wineryId);
        if (index > -1) {
            child.classList.remove("far");
            child.classList.add("fas");
        }
    }
}

function favoriteButton(favoritesList) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/favorites-list", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify(favoritesList));
}
function refreshPage() {
    location.reload(true);
}

window.onload = function() {
    let element = document.querySelectorAll('#favourite_container');
    if (element.length > 0) {
        element.forEach(el => checkFavorite(el))
    }
};