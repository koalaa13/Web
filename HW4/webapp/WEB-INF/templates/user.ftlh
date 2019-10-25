<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "common.ftl" as c/>

<@c.page>
    <#if user_id??>
        <#assign type="id">
        <#assign value=user_id>
    </#if>
    <#if handle??>
        <#assign type="handle">
        <#assign value=handle>
    </#if>
    <#if type?? && value??>
        <#if type=="id" && !value?is_number>
            <@c.noSuch object="user"/>
        <#else>
            <#assign viewedUser=c.findBy(users, type, value)!>
            <#if viewedUser.id??>
                <div class="user_profile">
                    <div class="username">
                        <@c.userlink user=viewedUser key="handle" nameOnly=false/>
                    </div>
                    <ul>
                        <li>id: ${viewedUser.id}</li>
                        <li>handle: ${viewedUser.handle}</li>
                        <li>name: ${viewedUser.name}</li>
                        <li>
                            <#assign count=c.postsCount(viewedUser.id)>
                            <a href="/posts?user_id=${viewedUser.id}">posts</a>: ${count}
                        </li>
                    </ul>
                    <img src="/img/user.jpg" alt="">
                </div>
                <div class="arrows">
                    <#assign prevUser=c.findPrev(users, type, value)!>
                    <#assign nextUser=c.findNext(users, type, value)!>
                    <#if prevUser=="">
                        <p>←</p>
                    <#else>
                        <a href="/user?handle=${prevUser.handle}">←</a>
                    </#if>
                    <#if nextUser=="">
                        <p>→</p>
                    <#else>
                        <a href="/user?handle=${nextUser.handle}">→</a>
                    </#if>
                </div>
            <#else>
                <@c.noSuch object="user"/>
            </#if>
        </#if>
    <#else>
        <@c.noSuch object="user"/>
    </#if>
</@c.page>
