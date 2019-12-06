<#import "commons.ftlh" as c>

<@c.page>
    <#if user.posts?size != 0>
        <#list user.posts as post>
            <@c.post post=post/>
        </#list>
    <#else>
        <h1>
            You don't have any posts yet.
        </h1>
    </#if>
</@c.page>
