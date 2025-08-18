document.addEventListener("DOMContentLoaded", function () {

    const form = document.getElementById("formRegis");
    const fields = {
        username: { el: document.getElementById("username"), regex: /^([a-z0-9\.]{8,16})$/, msg: "Format huruf kecil, numeric, titik, 8-16 karakter" },
        password: { el: document.getElementById("password"), regex: /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@_#\-$])[A-Za-z\d@_#\-$]{8,15}$/, msg: "Minimal 1 angka, 1 huruf kecil, 1 huruf besar, 1 spesial karakter, 8-15 karakter" },
        namaLengkap: { el: document.getElementById("namaLengkap"), regex: /^[a-zA-Z\s]{4,70}$/, msg: "Hanya alfabet dan spasi, 4-70 karakter" },
        email: { el: document.getElementById("email"), regex: /^[^\s@]+@[^\s@]+\.[^\s@]+$/, msg: "Format email tidak valid" },
        noHp: { el: document.getElementById("noHp"), regex: /^\d{9,15}$/, msg: "Nomor HP hanya angka, 9-15 digit" },
        alamat: { el: document.getElementById("alamat"), regex: /^.{5,255}$/, msg: "Alamat minimal 5 karakter" },
        tanggalLahir: { el: document.getElementById("tanggalLahir"), regex: /^\d{4}-\d{2}-\d{2}$/, msg: "Tanggal lahir tidak valid" }
    };

    function showError(field) {
        const errorEl = document.getElementById(field + "Message");
        errorEl.textContent = fields[field].msg;
    }

    function clearError(field) {
        const errorEl = document.getElementById(field + "Message");
        errorEl.textContent = "";
    }

    function validateField(field) {
        const value = fields[field].el.value.trim();
        if (!fields[field].regex.test(value)) {
            showError(field);
            return false;
        }
        clearError(field);
        return true;
    }

    Object.keys(fields).forEach(field => {
        fields[field].el.addEventListener("blur", () => validateField(field));
    });

    form.addEventListener("submit", function (e) {
        let isValid = true;
        Object.keys(fields).forEach(field => {
            if (!validateField(field)) isValid = false;
        });
        if (!isValid) e.preventDefault();
    });
});
