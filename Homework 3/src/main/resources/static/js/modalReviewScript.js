function openReviewForm() {
    var modal = new bootstrap.Modal(document.getElementById('reviewModal'));
    modal.show();
}

document.addEventListener('DOMContentLoaded', () => {
    // Set up event listener when the document is ready
    let closeButton = document.getElementById('closeReviewModalBtn');

    // Check if the close button is available
    if (closeButton) {
        closeButton.addEventListener('click', () => $('#reviewModal').modal('hide'));
    }
});
