let lastOption = null; // menyimpan pilihan sebelumnya

function sortTargets(option) {
    const container = document.getElementById("targetContainer");
    const cards = Array.from(container.getElementsByClassName("card"));

    // Toggle ASC/DESC jika opsi sama diklik lagi
    if (lastOption === option) {
        if (option.endsWith('-asc')) {
            option = option.replace('-asc', '-desc');
        } else if (option.endsWith('-desc')) {
            option = option.replace('-desc', '-asc');
        }

        // update dropdown supaya tampil sesuai toggle
        document.getElementById("sortSelect").value = option;
    }

    lastOption = option;

    // Sort cards
    cards.sort((a, b) => {
        switch(option) {
            case 'name-asc':
                return a.dataset.name.localeCompare(b.dataset.name);
            case 'name-desc':
                return b.dataset.name.localeCompare(a.dataset.name);
            case 'progress-asc':
                return parseFloat(a.dataset.progress) - parseFloat(b.dataset.progress);
            case 'progress-desc':
                return parseFloat(b.dataset.progress) - parseFloat(a.dataset.progress);
            default:
                return 0;
        }
    });

    // Append kembali ke container
    cards.forEach(card => container.appendChild(card));
}

// Default sort saat halaman load
window.onload = () => {
    const defaultOption = document.getElementById("sortSelect").value;
    lastOption = defaultOption; // simpan default
    sortTargets(defaultOption);
};
