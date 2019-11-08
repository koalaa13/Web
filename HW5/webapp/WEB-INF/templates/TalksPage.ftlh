<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="sendersLogins" type="java.lang.String[]" -->
<#-- @ftlvariable name="recipientsLogins" type="java.lang.String[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="talk-box">
        <div class="header">Send a message</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="sendMessage"/>
                <div class="text-input-label">
                    Enter your text message here:
                </div>
                <div class="text-input">
                    <textarea id="text" name="text"></textarea>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="recipient-list">Recipient</label>
                    </div>
                    <div class="value">
                        <div id="recipient-list" class="recipient-list">
                            <label>
                                <select name="recipient">
                                    <#list users as u>
                                        <option value=${u.id}>${u.login}</option>
                                    </#list>
                                </select>
                            </label>
                        </div>
                    </div>
                </div>
                <#if error??>
                    <div class="error">
                        ${error!}
                    </div>
                </#if>
                <div class="button-field">
                    <input type="submit" class="send-button" value="Send">
                </div>
            </form>
        </div>
    </div>
    <#if talks?size == 0>
        <div class="no-messages">
            You haven't got messages yet.
        </div>
    <#else>
        <div class="datatable">
        <div class="caption"> ${user.login}'s messages</div>
        <table>
            <thead>
            <tr>
                <th>Sender</th>
                <th>Recipient</th>
                <th>Text</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
            <#list 0..talks?size-1 as i>
                <tr>
                    <td>${sendersLogins[i]}</td>
                    <td>${recipientsLogins[i]}</td>
                    <td>${talks[i].text}</td>
                    <td>${talks[i].creationTime}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>
    </div>
</@c.page>