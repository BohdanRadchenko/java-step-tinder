<#import "common/links.ftl" as links>
<#import "common/script.ftl" as script>
<#import "common/template.ftl" as t>

<#--variables-->
<#--CURRENT_USER -> user from login token id-->

<@t.page>
    <@t.head title="Sign Up">
        <@links.fac />
        <@script.profile />
    </@t.head>
    <@t.body_auth>
        <#include "componnets/forms/profile_form.ftl">
    </@t.body_auth>
</@t.page>

