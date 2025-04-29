// Theme Toggle
document.querySelectorAll('[data-bs-theme-value]').forEach(button => {
    button.addEventListener('click', () => {
        const theme = button.getAttribute('data-bs-theme-value');
        document.documentElement.setAttribute('data-bs-theme', theme);
        localStorage.setItem('theme', theme);

        document.querySelectorAll('[data-bs-theme-value]').forEach(btn => {
            btn.parentElement.classList.remove('active');
        });
        button.parentElement.classList.add('active');
    });
});

// Load saved theme
window.addEventListener('DOMContentLoaded', () => {
    const savedTheme = localStorage.getItem('theme') || 'dark'; // Default to dark if not set
    document.documentElement.setAttribute('data-bs-theme', savedTheme);

    const activeBtn = document.querySelector(`[data-bs-theme-value="${savedTheme}"]`);
    if (activeBtn) activeBtn.parentElement.classList.add('active');

    // Initialize animations
    document.querySelectorAll('.fadeIn').forEach(el => {
        el.style.opacity = '0';
        setTimeout(() => {
            el.style.opacity = '1';
        }, 100);
    });
});

// Search and Filter
document.addEventListener('DOMContentLoaded', function() {
    const nsearchInput = document.getElementById('nsearchInput');
    const ndropdown = document.getElementById('nfilter-dropdown');

    if (nsearchInput && ndropdown) {
        nsearchInput.addEventListener('focus', () => {
            ndropdown.style.display = 'block';
        });

        // Close dropdown when clicking outside
        document.addEventListener('click', (event) => {
            if (!nsearchInput.contains(event.target) && !ndropdown.contains(event.target)) {
                ndropdown.style.display = 'none';
            }
        });
    }

    // Set up filter option selection
    document.querySelectorAll('.nfilter-option').forEach(option => {
        option.addEventListener('click', function(event) {
            event.stopPropagation(); // Prevent event from bubbling up
            this.classList.toggle('selected');
            // Here you can add logic to handle the selected filter
            console.log('Selected filter:', this.textContent);
        });
    });
});

function ntoggleFilterSection(sectionId) {
    const options = document.getElementById('n' + sectionId + 'Options');
    const icon = event.currentTarget.querySelector('i');

    if (options.classList.contains('show')) {
        options.classList.remove('show');
        icon.classList.remove('bi-chevron-up');
        icon.classList.add('bi-chevron-down');
    } else {
        options.classList.add('show');
        icon.classList.remove('bi-chevron-down');
        icon.classList.add('bi-chevron-up');
    }
    event.stopPropagation(); // Prevent event from bubbling up
}