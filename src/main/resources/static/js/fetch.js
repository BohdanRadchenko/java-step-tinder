window.originFetch = (url, opt) =>
    new Promise((resolve, reject) => {
        return fetch(url, {
            credentials: "include",
            redirect: "follow",
            ...opt,
        })
            .then(async res => {
                if (res.redirected) {
                    document.location.href = res.url;
                }
                if (!res.ok) {
                    const json = await res.json();
                    reject(new Error(json));
                }
            })
            .catch(err => reject(err));
    })

window.post = (url, data, options = {headers: {}}) => originFetch(url, {
    method: "post",
    body: data,
    ...options,
    headers: {
        "Content-Type": "application/x-www-form-urlencoded",
        ...options.headers,
    }
})