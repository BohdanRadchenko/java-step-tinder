const DURATION_TIME = 5000;

const TYPE = {
    ERROR: "Error",
    SUCCESS: "Success",
    INFO: "Info",
    WARNING: "Warning",
}

const GRAVITY = {
    TOP: "top",
    BOTTOM: "bottom",
}

const POSITION = {
    LEFT: "left",
    CENTER: "center",
    RIGHT: "right",
}

const toastColor = {
    [TYPE.ERROR]: "#e74c3c",
    [TYPE.SUCCESS]: "#07bc0c",
    [TYPE.WARNING]: "#f1c40f",
    [TYPE.INFO]: "#3498db",
}

const css = (node, styles = {}) => {
    for (const prop in styles) {
        node.style[prop] = styles[prop];
    }
}

const createMessageNode = (type, msg) => {
    const wrapper = document.createElement("div");
    const title = document.createElement("span");
    const subtitle = document.createElement("span");

    css(wrapper, {
        display: "flex",
        flexDirection: "column",
        flexGrow: 1,
        alignItems: "start",
    });

    css(title, {
        color: toastColor[type],
        fontWeight: 600,
        fontSize: "16px",
    });

    css(subtitle, {
        color: "#666",
        fontWeight: 400,
        fontSize: "16px",
    });

    title.appendChild(document.createTextNode(type))
    subtitle.appendChild(document.createTextNode(msg))

    wrapper.appendChild(title);
    wrapper.appendChild(subtitle);
    return wrapper;
}

const toast = (type, msg) => Toastify({
    node: createMessageNode(type, msg),
    className: type,
    duration: DURATION_TIME,
    gravity: GRAVITY.TOP,
    position: POSITION.RIGHT,
    stopOnFocus: true,
    newWindow: true,
    close: true,
    style: {
        padding: "6px 6px 6px 16px",
        borderRadius: "8px",
        background: "#fff",
        display: "flex",
        alignItems: "flex-start",
        width: "auto",
        minWidth: "280px",
        maxWidth: "440px",
        minHeight: "56px",
        boxShadow: "0 5px 10px rgba(0,0,0,0.1)",
        borderLeft: `6px solid ${toastColor[type] || "#fff"}`,
    },
}).showToast();

window.toastError = message => toast(TYPE.ERROR, message)
window.toastSuccess = message => toast(TYPE.SUCCESS, message)