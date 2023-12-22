// document.addEventListener("DOMContentLoaded", function () {
//     navigator.geolocation.getCurrentPosition(
//         function(position) {
//             let lat1 = position.coords.latitude;
//             let lon1 = position.coords.longitude;
//             let value = lat1 + "," + lon1;
//
//             let expires = new Date();
//             expires.setTime(expires.getTime() + (60 * 60 * 1000)); // 1 hour in milliseconds
//
//             // Convert to UTC string format
//             expires = expires.toUTCString();
//
//             document.cookie = "userPosition=" + value + "; expires=" + expires + "; path=/";
//
//         },
//         function(error) {
//             alert("Location not available");
//         },
//         { timeout: 10000 } // Set a timeout of 10 seconds
//     );
// })
document.addEventListener("DOMContentLoaded", function () {
    navigator.geolocation.getCurrentPosition(
        function(position) {
            let lat1 = position.coords.latitude;
            let lon1 = position.coords.longitude;
            let value = lat1 + "," + lon1;

            // Send geolocation data to the server using fetch
            fetch('/test', {
                method: 'POST',
                headers: {
                    'Content-Type': 'text/plain',
                },
                body: value,
                // body: JSON.stringify({ userPosition: value }),
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    // Do something with the successful response if needed
                })
                .catch(error => {
                    console.error('There was a problem with the fetch operation:', error);
                });

        },
        function(error) {
            alert("Location not available");
        },
        { timeout: 10000 } // Set a timeout of 10 seconds
    );
});
