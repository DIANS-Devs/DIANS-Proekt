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

function addToFavorites(button) {
    //TODO modify here later...
    var wineryId = button.getAttribute('data-winery-id');
    if (wineryId.trim() !== '') {
        var favorites = getFavoritesFromCookie();
        var index = favorites.indexOf(wineryId);
        if (index === -1) {
            favorites.push(wineryId);
            button.classList.add('red');
            alert('Winery added to Favorites!');
        } else {
            favorites.splice(index, 1);
            button.classList.remove('red');
            alert('Winery removed from Favorites!');
        }
        setFavoritesCookie(favorites);

        // Send the updated favorites to the server
        favoriteButton(favorites);
    }
}
function favoriteButton(favoritesList) {
    var xhr = new XMLHttpRequest();
    console.log('Creates new XMLHttpRequest');
    xhr.open("POST", "/favorites-list", true);
    console.log('Opens post connections');
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.send(JSON.stringify(favoritesList));
    console.log(JSON.stringify(favoritesList));
    console.log('Sends');
}
function refreshPage() {
    location.reload(true);
}

var favorites = getFavoritesFromCookie();
var heartButtons = document.querySelectorAll('.heart-button');
heartButtons.forEach(function(button) {
    var wineryId = button.getAttribute('data-winery-id');
    if (favorites.includes(wineryId)) {
        button.classList.add('red');
    }
});