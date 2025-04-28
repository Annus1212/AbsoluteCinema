// Theme Toggle
document.querySelectorAll('[data-bs-theme-value]').forEach(button => {
    button.addEventListener('click', () => {
        const theme = button.getAttribute('data-bs-theme-value');
        document.documentElement.setAttribute('data-bs-theme', theme);
        localStorage.setItem('theme', theme);

        // === ADD THIS: ===
        if (theme === 'light') {
            document.body.classList.add('light-theme');
        } else {
            document.body.classList.remove('light-theme');
        }
        // ==================

        document.querySelectorAll('[data-bs-theme-value]').forEach(btn => {
            btn.parentElement.classList.remove('active');
        });
        button.parentElement.classList.add('active');
    });
});

// Load saved theme
window.addEventListener('DOMContentLoaded', () => {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme) {
        document.documentElement.setAttribute('data-bs-theme', savedTheme);

        // === ADD THIS:FOR ADMIN DASHBOARD LIGHT THEME ===
        if (savedTheme === 'light') {
            document.body.classList.add('light-theme');
        } else {
            document.body.classList.remove('light-theme');
        }
        // ==================

        const activeBtn = document.querySelector(`[data-bs-theme-value="${savedTheme}"]`);
        if (activeBtn) activeBtn.parentElement.classList.add('active');
    }
});
