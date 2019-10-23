<#-- @ftlvariable name="user_id" type="java.lang.Long" -->

<#import "common.ftl" as c/>

<@c.page>
    <#if user_id??>
        <#assign count=c.postsCount(user_id)>
        <#if count==0>
            <div class="no_posts">This user hasn't posted anything yet.</div>
        <#else>
            <div class="users_posts_list">
                <#list posts as post>
                    <#if post.user_id==user_id>
                        <@c.fullPostView post=post small=false/>
                    </#if>
                </#list>
            </div>
        </#if>
    <#else>
        <@c.noSuch object="posts created by this user"/>
    </#if>
</@c.page>