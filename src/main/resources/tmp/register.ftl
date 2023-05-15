<#import "common/links.ftl" as links>
<#import "common/script.ftl" as script>
<#import "common/template.ftl" as t>

<#--variables-->
<#--LOGIN-->
<#--EMAIL-->
<#--PASSWORD-->
<#--PASSWORD_CONFIRM-->
<#--LOGIN_LINK-->

<@t.page>
    <@t.head title="Sign Up">
        <@links.fac />
        <@script.register />
    </@t.head>
    <@t.body>
        <#include "componnets/forms/register_form.ftl">
        <#include "componnets/copyright.ftl">
    </@t.body>
</@t.page>

