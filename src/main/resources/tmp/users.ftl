<#import "common/template.ftl" as t>
<#import "common/links.ftl" as links>
<#import "common/script.ftl" as script>
<@t.page>
    <@t.head title="Users">
        <@links.fac/>
        <style>
            .newRect {
                width: 300px;
                height: 450px;
                background-color: #e0e0e0;
                display: flex;
                align-items: center;
                justify-content: center;
                overflow: hidden;
            }
            .newRect img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }
        </style>
    </@t.head>

    <@t.body_auth>
        <#include "componnets/forms/profile_form.ftl">
        <div class="container">
            <div class="newRect">
                <#if avatar??>
                    <img src="${avatar}" alt="Avatar">
                </#if>

            </div>
            <form method="post" action="/users">
                <input type="hidden" name="userFrom" value="${userFrom}">
                <input type="hidden" name="userTo" value="${userTo}">
                <div style="text-align: center;">
                    <#if name??>
                        <h1>${name}</h1>
                    </#if>
                    <#if prof??>
                        <h1>${prof}</h1>
                    </#if>
                </div>
                <div class="button-container">
                    <button type="submit" name="response" value="Yes" class="button"><span class="fa fa-heart"></span> Like</button>
                    <button type="submit" name="response" value="No" class="button"><span class="fa fa-times"></span> Dislike</button>
                </div>
            </form>
        </div>
    </@t.body_auth>
</@t.page>