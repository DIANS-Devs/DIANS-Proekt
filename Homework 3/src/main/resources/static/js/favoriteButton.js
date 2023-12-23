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
    let element = document.getElementById('favourite_container');
    if (element.length > 0) {
        element.forEach(el => checkFavorite(el))
    }
};

document.addEventListener('DOMContentLoaded', function() {
    let favorite = document.getElementById('favourite_container');
    favorite.addEventListener('click', function() {
        document.getElementById('favoriteForm').submit();
    });
});