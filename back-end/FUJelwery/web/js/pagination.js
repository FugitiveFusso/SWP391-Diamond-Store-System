document.addEventListener('DOMContentLoaded', function() {
    const rowsPerPage = 10;
    const table = document.getElementById('pagination').getElementsByTagName('tbody')[0];
    const rows = table.getElementsByTagName('tr');
    const rowsCount = rows.length;
    const pageCount = Math.ceil(rowsCount / rowsPerPage);
    const paginationControls = document.getElementById('paginationControls');
    const prevButton = document.getElementById('prevButton');
    const nextButton = document.getElementById('nextButton');
    const pageNumbers = document.getElementById('pageNumbers');
    let currentPage = 1;

    function showPage(pageNumber) {
        let start = (pageNumber - 1) * rowsPerPage;
        let end = start + rowsPerPage;
        for (let i = 0; i < rowsCount; i++) {
            rows[i].style.display = (i >= start && i < end) ? '' : 'none';
        }
        currentPage = pageNumber;
        updatePaginationControls();
        window.scrollTo(0, 0); // Scroll to the top of the page
    }

    function updatePaginationControls() {
        pageNumbers.innerHTML = '';
        for (let i = 1; i <= pageCount; i++) {
            let pageNumberElement = document.createElement('span');
            pageNumberElement.textContent = i;
            pageNumberElement.className = 'page-number';
            if (i === currentPage) {
                pageNumberElement.classList.add('active');
            }
            pageNumberElement.addEventListener('click', function() {
                showPage(i);
            });
            pageNumbers.appendChild(pageNumberElement);
        }
        prevButton.disabled = currentPage === 1;
        nextButton.disabled = currentPage === pageCount;
    }

    prevButton.addEventListener('click', function() {
        if (currentPage > 1) {
            showPage(currentPage - 1);
        }
    });

    nextButton.addEventListener('click', function() {
        if (currentPage < pageCount) {
            showPage(currentPage + 1);
        }
    });

    showPage(1);
});