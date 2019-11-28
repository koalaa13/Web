<#-- @ftlvariable name="user" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if user??>
        <ul>
            <li>${user.id}</li>
            <li>${user.login}</li>
            <li>${user.creationTime}</li>
        </ul>
    <#else>
        No such user.
    </#if>
</@c.page>