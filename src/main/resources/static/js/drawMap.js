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

    function createPopup(name) {
        const popupContent = `
    <b>${name}</b></br>
    <a href="/"><button onclick="showDetails('${name}')">Details</button></a>
    `;
        return L.popup().setContent(popupContent);
    }

    function showDetails(name) {
        // Logic to show details based on the name
        console.log(`Details for ${name}`);
    }

    let marker_helper, circle_helper;

    function drawLocations(wineriesList){
        //Winery list: Lat|Long|Name, first split
        wineriesList = wineriesList.substring(1, wineriesList.length - 1).split(", ");
        const accuracy= 200;

        wineriesList.forEach(winery => {
            let parts = winery.split("|");
            let lat = parseFloat(parts[0]);
            let lng = parseFloat(parts[1]);
            let name = parts[2];
            console.log(lat)
            console.log(lng)
            console.log(name)

            marker_helper = L.marker([lat, lng]).addTo(map);
            circle_helper = L.circle([lat, lng], { radius: accuracy }).addTo(map);

            marker_helper.bindPopup(createPopup(name)).openPopup();
            circle_helper.bindPopup(createPopup(name)).openPopup();
        })
    }

    const wineriesList = document.getElementById('map').getAttribute('wineriesList');
    // Now you can use 'myData' in your JavaScript logic
    drawLocations(wineriesList);
});