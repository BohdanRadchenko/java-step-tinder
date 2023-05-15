<#macro fetch>
    <script src="static/js/fetch.js" defer></script>
</#macro>

<#macro toastify>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
    <script src="static/js/toastify.js" defer></script>
</#macro>

<#macro login>
    <@fetch/>
    <@toastify/>
    <script src="static/js/login.js" defer></script>
</#macro>

<#macro register>
    <@fetch/>
    <@toastify/>
    <script src="static/js/register.js" defer></script>
</#macro>