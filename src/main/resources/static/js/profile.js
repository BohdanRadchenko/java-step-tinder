const inputFile = document.querySelector(".form-profile input[type='file']");
const inputLabel = document.querySelector(".form-profile input[type='file'] + label");

inputFile.onchange = e => {
    const file = e.target.files[0];
    inputLabel.innerText = file.name;
}