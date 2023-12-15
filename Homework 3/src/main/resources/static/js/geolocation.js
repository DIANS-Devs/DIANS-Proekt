function updateDistance(element, lat2, lon2) {
    if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(
            function(position) {
                var lat1 = position.coords.latitude;
                var lon1 = position.coords.longitude;

                var R = 6371; // Radius of the Earth in kilometers
                var dLat = deg2rad(lat2 - lat1);
                var dLon = deg2rad(lon2 - lon1);
                var a =
                    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2);
                var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                var distance = R * c; // Distance in kilometers

                element.innerText = distance.toFixed(2) + " km";
            },
            function(error) {
                console.error("Error getting geolocation:", error);
                element.innerText = "Location not available";
            },
            { timeout: 10000 } // Set a timeout of 10 seconds
        );
    } else {
        element.innerText = "Location not available";
    }
}

function deg2rad(deg) {
    return deg * (Math.PI / 180);
}

function getCurrentLocation(){
    navigator.geolocation.getCurrentPosition(locationAllowed, locationNotAllowed);
}
function locationAllowed(){
    let el = document.querySelector("#distance");
    el.readOnly = false;
}
function locationNotAllowed(err){
    let el = document.querySelector("#distance");
    el.readOnly = true;
    if (err.code === 1) {
        alert("Ве молиме дозволете користење на локација, за користење на филтерот: Оддалеченост");
    } else {
        alert("Не може да се пристапи локацијата");
    }
}
document.addEventListener("DOMContentLoaded", function () {
    let elements= document.querySelectorAll(".distance_winery");
    elements.forEach(el => {
        let winery = el.getAttribute('data-winery');
        let latitude = parseFloat(winery.match(/latitude=(-?\d+\.\d+)/)[1]);
        let longitude = parseFloat(winery.match(  /longitude=(-?\d+\.\d+)/)[1]);
        updateDistance(el, latitude, longitude);
    })
});