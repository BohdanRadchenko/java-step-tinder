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
                <div class="col-8 offset-2">
                    <div class="panel panel-default user_panel">
                        <div class="panel-heading">
                            <h3 class="panel-title">User List</h3>
                        </div>
                        <div class="panel-body">
                            <div class="table-container">
                                <table class="table-users table" border="0">
                                    <#list users>
                                        <tbody>
                                        <#items as item>
                                            <tr>
                                                <td width="10">
                                                    <div class="avatar-img">
                                                        <img class="img-circle" src=${item.pathPhoto}>
                                                    </div>

                                                </td>
                                                <td class="align-middle">
                                                    ${item.login}
                                                </td>
                                                <td class="align-middle">
                                                    ${item.profession}
                                                </td>
                                                <td class="align-middle">
                                                    Last Login: ${item.lastLogin}<br><small
                                                            class="text-muted">${item.daysAgo}</small>
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