<#import "links.ftl" as links>

<#macro page>
    <!doctype html>
    <html lang="en">
    <#nested>
    </html>
</#macro>

<#macro head title>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>${title}</title>

        <@links.fav />
        <@links.css />
        <@links.fac />

        <#nested>
    </head>
</#macro>

<#macro body>
    <body>
    <div class="container">
        <#nested>
    </div>
    </body>
</#macro>

<#macro body_auth>
    <body>
    <div class="container auth">
        <#include "../componnets/header.ftl">
        <main class="main">
            <#nested>
        </main>
        <#include "../componnets/footer.ftl">
    </div>
    </body>
</#macro>
