<#import "common.ftl" as c/>

<@c.page>
    <div class="users_table">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Handle</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>${u.id}</td>
                    <td><@c.userlink user=u key="handle" nameOnly=false/></td>
                    <td>${u.name}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
<#--    <ul>-->
<#--        <#list users as u>-->
<#--            <li><#if user?? && u.id == user.id>*</#if> <@c.userlink user=u/></li>-->
<#--        </#list>-->
<#--    </ul>-->
</@c.page>