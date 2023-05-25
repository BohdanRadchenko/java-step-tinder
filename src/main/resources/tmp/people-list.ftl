<#import "common/template.ftl" as t>
<#import "common/links.ftl" as links>
<#import "common/script.ftl" as script>
<@t.page>
    <@t.head title="Users">
        <@links.fac/>
    </@t.head>

    <@t.body_auth>
        <#include "componnets/forms/profile_form.ftl">
        <div class="container">
            <div class="row">
                <div>
                    <div>
                        <div class="panel-heading" style="text-align: center;">
                            <h3 class="panel-title">User List</h3>
                        </div>
                        <div class="panel-body">
                            <div class="table-container">
                                <table class="table-users table" border="0">
                                    <#list likedUsers>
                                        <tbody>
                                        <#items as item>
                                            <tr>
                                                <td width="10">
                                                    <a href="/new_chat?userID=${item.id()}">
                                                    <div class="avatar-img">
                                                        <#if item.avatar() ??>
                                                            <img class="img-circle" src="${item.avatar()}" alt="Avatar">
                                                        </#if>
                                                    </div>
                                                    </a>
                                                </td>
                                                <td class="align-middle">
                                                    <#if item.firstName() ??>
                                                        ${item.firstName()}
                                                    </#if>
                                                </td>
                                                <td class="align-middle">
                                                    <#if item.lastName() ??>
                                                        ${item.lastName()}
                                                    </#if>
                                                </td>
                                                <td class="align-middle">
                                                    <#if item.profession() ??>
                                                        ${item.profession()}
                                                    </#if>
                                                </td>
                                            </tr>
                                        </#items>
                                        </tbody>
                                    </#list>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </@t.body_auth>
</@t.page>