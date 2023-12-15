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
    <a href="/wineries/${id}"><button onclick="showDetails('${name}')">Details</button></a>
    `;
        return L.popup().setContent(popupContent);
    }

    let marker_helper, circle_helper;

    function drawLocations(wineriesList){
        //Winery list: Lat|Long|Name, first split
        wineriesList = wineriesList.substring(1, wineriesList.length - 1).split(", ");
        const accuracy= 200;

        wineriesList.forEach(winery => {
            let parts = winery.split("|");
            let id = parseInt(parts[0]);
            let lat = parseFloat(parts[1]);
            let lng = parseFloat(parts[2]);
            let name = parts[3];

            marker_helper = L.marker([lat, lng]).addTo(map);
            circle_helper = L.circle([lat, lng], { radius: accuracy }).addTo(map);

            marker_helper.bindPopup(createPopup(id, name)).openPopup();
            circle_helper.bindPopup(createPopup(id, name)).openPopup();
        })
    }

    const wineriesList = document.getElementById('map').getAttribute('wineriesList');
    // Now you can use 'myData' in your JavaScript logic
    drawLocations(wineriesList);
});