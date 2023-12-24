document.addEventListener('DOMContentLoaded', function() {
    let favorites = document.getElementsByClassName('favourite_container');
    Array.from(favorites).forEach(function(favorite, index) {
        favorite.addEventListener('click', function() {
            let form = document.getElementById("favoriteForm" + index);
            form.submit();
        });
    });
});