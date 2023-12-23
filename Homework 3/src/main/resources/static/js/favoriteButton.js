function getFavorites() {
    const cookies = document.cookie.split(';');
    let favoritesCookie = '';
    for (let i = 0; i < cookies.length; i++) {
        let cookie = cookies[i].trim();
        if (cookie.indexOf('favorites=') === 0) {
            favoritesCookie = cookie.substring(('favorites=').length, cookie.length);
        }
    }

    return favoritesCookie;
}

function setFavorites(values) {
    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + 365);
    document.cookie = `favorites=${values}; path=/; expires=${expirationDate.toUTCString()}`;
}

function addOrRemoveFavorite(element) {
    const wineryId = element.getAttribute('data-winery-id');
    const child = element.querySelector('i');
    let favorites = getFavorites().split(',');
    if (favorites[0] === ''){
        favorites.pop();
        favorites.push(wineryId);
        child.classList.remove("far");
        child.classList.add("fas");
    }
    else {
        const index = favorites.indexOf(wineryId);
        if (index === -1) {
            favorites.push(wineryId);
            child.classList.remove("far");
            child.classList.add("fas");
        } else {
            favorites.splice(index, 1);
            child.classList.remove("fas");
            child.classList.add("far");
        }
    }
    let favString = favorites.join(',');
    setFavorites(favString);
}

function checkFavorite(element){
    let wineryId = element.getAttribute('data-winery-id');
    let child = element.querySelector('i');
    if (wineryId.trim() !== '') {
        let favorites = getFavorites();
        let index = favorites.indexOf(wineryId);
        if (index > -1) {
            child.classList.remove("far");
            child.classList.add("fas");
        }
    }
}

window.onload = function() {
    let element = document.querySelectorAll('#favourite_container');
    if (element.length > 0) {
        element.forEach(el => checkFavorite(el))
    }
};