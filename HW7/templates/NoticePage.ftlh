<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.form.NoticeCredentials" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="add-notice form-box">
        <div class="header">Create a notice</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="notice"/>
                <div class="field">
                    <div class="field">
                        <div class="name">
                            <label for="content">Text</label>
                        </div>
                        <div class="value">
                            <textarea id="content" name="content"></textarea>
                        </div>
                    </div>
                    <@c.error "noticeForm.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Create notice">
                </div>
            </form>
        </div>
    </div>
</@c.page>