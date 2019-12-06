<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
        <@c.post post=post/>
        <div class="form">
            <div class="header">Add Comment</div>
            <div class="body">
                <form method="post" action="">
                    <input type="hidden" name="action" value="writeComment"/>
                    <div class="field">
                        <div class="name">
                            <label for="text">Text</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text">${comment.text!}</textarea>
                        </div>
                        <@c.error "comment.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Add comment">
                    </div>
                </form>
            </div>
        </div>
        <div class="comments">
            <#list post.comments as comment>
                <div class="comment">
                    <#if comment.text?length gt 100>
                        ${comment.text[0..100]}...
                    <#else>
                        ${comment.text}
                    </#if>
                    <div class="information">
                        By ${comment.user.login}
                    </div>
                </div>
            </#list>
        </div>
    <#else>
        <h1>
            No such post.
        </h1>
    </#if>
</@c.page>