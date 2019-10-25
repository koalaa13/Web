<#assign MaxTextLength=250>

<#macro underlineMenuItem link lable>
    <#if menu_item?? && menu_item==link>
        <li><a style="border-bottom: 2px solid var(--caption-color)" href=${link}>${lable}</a></li>
    <#else>
        <li><a href=${link}>${lable}</a></li>
    </#if>
</#macro>

<#function postsCount userId>
    <#assign count=0>
    <#list posts as post>
        <#if post.user_id==userId>
            <#assign count++>
        </#if>
    </#list>
    <#return count>
</#function>

<#macro postTextView text small>
    <#if text?length gt MaxTextLength && small==true>
        ${text[0..MaxTextLength]}...
    <#else>
        ${text}
    </#if>
</#macro>

<#macro fullPostView post small>
    <div class="post">
        <article>
            <div class="title"><@postlink post=post key="title"/></div>
            <div class="information">
                <#assign post_owner=findBy(users, "id", post.user_id)>
                By <@userlink user=post_owner key="handle" nameOnly=false/>
            </div>
            <div class="body">
                <p>
                    <@postTextView text=post.text small=small/>
                </p>
            </div>
            <ul class="attachment">
                <li>Attachment 1</li>
                <li>Attachment 2</li>
            </ul>
            <div class="footer">
                <div class="left">
                    <img src="/img/voteup.png" title="Vote Up" alt="Vote Up"/>
                    <span class="positive-score">+173</span>
                    <img src="/img/votedown.png" title="Vote Down" alt="Vote Down"/>
                </div>
                <div class="right">
                    <img src="/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                    2 days ago
                    <img src="/img/comments_16x16.png" title="Comments" alt="Comments"/>
                    <a href="#">68</a>
                </div>
            </div>
        </article>
    </div>
</#macro>

<#macro header>
    <header>
        <a href="/index"><img src="/img/logo.png" alt="Codeforces" title="Codeforces"/></a>
        <div class="languages">
            <a href="#"><img src="/img/gb.png" alt="In English" title="In English"/></a>
            <a href="#"><img src="/img/ru.png" alt="In Russian" title="In Russian"/></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user=user key="handle" nameOnly=true/>
                |
                <a href="#">Logout</a>
            <#else>
                <a href="#">Enter</a>
                |
                <a href="#">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <#assign items={"/index":"Index", "/misc/help":"Help", "/users":"Users"}>
                <#list items?keys as item>
                    <@underlineMenuItem link=item lable=items[item]/>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts as post>
            <section>
                <div class="header">
                    Post #${post.id}
                </div>
                <div class="body">
                    <@postTextView text=post.text small=true/>
                </div>
                <div class="footer">
                    <a href="/post?post_id=${post.id}">View all</a>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        <a href="#">Codeforces</a> &copy; 2010-2019 by Mike Mirzayanov
    </footer>
</#macro>

<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" type="text/css" href="/css/normalize.css">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <link rel="stylesheet" type="text/css" href="/css/no_such_page.css">
        <link rel="stylesheet" type="text/css" href="/css/post.css">
        <link rel="stylesheet" type="text/css" href="/css/user.css">
        <link rel="stylesheet" type="text/css" href="/css/users.css">
        <link rel="icon" href="/favicon.ico" type="image/x-icon"/>
        <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro userlink user key nameOnly>
    <#if nameOnly==false>
        <a style="text-decoration: none; color:${user.color}; font-weight: bold; "
           href="/user?handle=${user.handle}">${user[key]}</a>
    <#else>
        <a style="text-decoration: none; font-weight: bold; " href="/user?handle=${user.handle}">${user[key]}</a>
    </#if>
</#macro>

<#macro postlink post key>
    <a style="text-decoration: none" href="/post?post_id=${post.id}">${post[key]}</a>
</#macro>

<#function findBy items key id>
    <#list items as item>
        <#if item[key]==id>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findPrev items key id>
    <#assign last="">
    <#list items as item>
        <#if item[key]==id>
            <#return last>
        </#if>
        <#assign last=item>
    </#list>
    <#return last>
</#function>

<#function findNext items key id>
    <#return findPrev(items?reverse key id)>
</#function>

<#macro noSuch object>
    <h1>No such ${object?html}</h1>
</#macro>
