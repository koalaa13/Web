<#import "common.ftl" as c/>

<@c.page>
    <#list posts as post>
        <@c.fullPostView post=post small=true/>
    </#list>
</@c.page>
