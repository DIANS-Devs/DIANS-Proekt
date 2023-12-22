document.addEventListener("DOMContentLoaded", function () {
// Initializes map
    const map = L.map("map");

// Sets initial coordinates and zoom level, Center of Macedonia
    map.setView([41.6086, 21.7453], 9);

// Sets map data source and associates with map
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap'
    }).addTo(map);

    function createPopup(id, name) {
        const popupContent = `
    <b>${name}</b></br>
    <a href="/wineries/${id}">
        <button>Details</button>
    </a>
   `;
        return L.popup().setContent(popupContent);
    }

    function createUserPopup(message){
        const popupContent = `
    <b>${message}</b>
    `;
        return L.popup().setContent(popupContent);
    }

    let marker_helper, circle_helper;

    function drawLocations(wineriesList){
        //Winery list: Lat|Long|Name, first split
        wineriesList = wineriesList.substring(1, wineriesList.length - 1).split(", ");
        const accuracy= 200;

        wineriesList.forEach(winery => {
            // id|lat|lng|name
            // 1|42.00|21.00|Bord winery
            let parts = winery.split("|");
            let id = parseInt(parts[0]);
            let lat = parseFloat(parts[1]);
            let lng = parseFloat(parts[2]);
            let name = parts[3];

            marker_helper = L.marker([lat, lng]).addTo(map);
            circle_helper = L.circle([lat, lng], { radius: accuracy }).addTo(map);

            marker_helper.bindPopup(createPopup(id, name));//.openPopup();
            circle_helper.bindPopup(createPopup(id, name));//.openPopup();
        })
    }

    function addMarkerForUserLocation() {
        console.log("ITS HERE")
        if ("geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition(
                function (position) {
                    const accuracy= 15000;
                    const { latitude, longitude } = position.coords;

                    // marker_helper = L.marker([latitude, longitude], {color: 'green'}).addTo(map);
                    let greenIcon = new L.Icon({
                        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
                        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                        iconSize: [25, 41],
                        iconAnchor: [12, 41],
                        popupAnchor: [1, -34],
                        shadowSize: [41, 41]
                    });

                    const userMarker = L.marker([latitude, longitude], {icon: greenIcon}).addTo(map);
                    const circle_helper = L.circle([latitude, longitude], { radius: accuracy, color: 'green'}).addTo(map);

                    userMarker.bindPopup(createUserPopup("Your location")).openPopup();
                    circle_helper.bindPopup(createUserPopup("Your radius"));//.openPopup();
                },
                function (error) {
                    console.error("Error getting user location:", error);
                },
                { timeout: 10000 } // Set a timeout of 10 seconds
            );
        }
    }

    function getDirections(destLat, destLng) {
        if ("geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition(
                function (position) {
                    const { latitude, longitude } = position.coords;
                    const apiKey = '1fa6f4e5-80ed-428e-8482-7541339a3855\t';
                    const apiUrl = `https://graphhopper.com/api/1/route?point=${latitude},${longitude}&point=${destLat},${destLng}&vehicle=car&locale=en-US&key=${apiKey}`;

                    // Make an HTTP request to the GraphHopper API
                    fetch(apiUrl)
                        .then(response => response.json())
                        .then(data => {
                            // Extract information from the API response
                            const points = data.paths[0].points.coordinates;

                            // Draw the route on the map
                            const route = L.polyline(points, { color: 'blue' }).addTo(map);
                            map.fitBounds(route.getBounds());

                            // You can also display additional information, such as duration and distance
                            const distance = data.paths[0].distance;
                            const duration = data.paths[0].time / 1000; // Convert milliseconds to seconds
                            console.log(`Distance: ${distance} meters`);
                            console.log(`Duration: ${duration} seconds`);
                        })
                        .catch(error => {
                            console.error("Error getting directions:", error);
                        });
                },
                function (error) {
                    console.error("Error getting user location:", error);
                },
                { timeout: 10000 } // Set a timeout of 10 seconds
            );
        }
    }

    const wineriesList = document.getElementById('map').getAttribute('wineriesList');
    // Now you can use 'myData' in your JavaScript logic
    drawLocations(wineriesList);
    addMarkerForUserLocation();
});

document.getElementById('buttonManageDrawRoute').addEventListener('click', async () => {
    GLOBALS.profiles.drawnRoute = GLOBALS.profiles.drawnRoute ?? undefined;
    await getDirections
})
