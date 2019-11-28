<#-- @ftlvariable name="loggedUser" type="ru.itmo.wp.domain.User" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.domain.User[]" -->
<#-- @ftlvariable name="userId" type="java.lang.String" -->
<#-- @ftlvariable name="newValue" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                    <tr>
                        <td class="id">${user.id}</td>
                        <td class="login">${user.login}</td>
                        <td class="creationTime">${user.creationTime}</td>
                        <#if loggedUser??>
                            <td class="disabled">
                                <form method="post" action="">
                                    <input type="hidden" name="action" value="users/all">
                                    <div class="button-field">
                                        <input type="hidden" id="userId" name="userId" value="${user.id}">
                                        <#if user.disabled>
                                            <input type="hidden" id="newValue" name="newValue" value="false">
                                            <input type="submit" value="Enable">
                                        <#else>
                                            <input type="hidden" id="newValue" name="newValue" value="true">
                                            <input type="submit" value="Disable">
                                        </#if>
                                    </div>
                                </form>
                            </td>
                        </#if>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
