<#-- @ftlvariable name="post_id" type="java.lang.Long" -->

<#import "common.ftl" as c/>

<@c.page>
    <#if post_id??>
        <#assign post=c.findBy(posts "id" post_id)!>
        <#if post.id??>
            <@c.fullPostView post=post small=false/>
        <#else>
            <@c.noSuch object="post"/>
        </#if>
    <#else>
        <@c.noSuch object="post"/>
    </#if>
</@c.page>