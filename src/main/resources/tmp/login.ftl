<#import "common/links.ftl" as links>
<#import "common/script.ftl" as script>
<#import "common/template.ftl" as t>

<#--variables-->
<#--EMAIL-->
<#--PASSWORD-->
<#--REGISTER_LINK-->

<@t.page>
    <@t.head title="Sign In">
        <@script.login />
    </@t.head>
    <@t.body>
        <#include "componnets/forms/login_form.ftl">
        <#include "componnets/copyright.ftl">
    </@t.body>
</@t.page>

