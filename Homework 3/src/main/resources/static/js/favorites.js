document.addEventListener('DOMContentLoaded', function() {
    let favorites = document.getElementsByClassName('favorite_container');
    Array.from(favorites).forEach(function(favorite, index) {
        favorite.addEventListener('click', function() {
            let form = document.getElementById("favoriteForm" + index);
            form.submit();
        });
    });
});