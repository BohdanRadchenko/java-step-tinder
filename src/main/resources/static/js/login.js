const form = document.querySelector(".form-signin");

const formListener = async (e) => {
    e.preventDefault();
    e.stopPropagation();
    const data = new URLSearchParams(new FormData(form));
    await post("/login", data)
        .then(() => {
            form.removeEventListener("submit", formListener);
        })
        .catch(err => toastError(err.message));
}
form.addEventListener("submit", formListener);