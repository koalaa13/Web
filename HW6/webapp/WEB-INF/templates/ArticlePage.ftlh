<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="add-article form-box">
        <div class="header">Create an article</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="addArticle"/>
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
                        <input id="title" type="text" name="title">
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text"></textarea>
                    </div>
                </div>
                <div class="button-field">
                    <input type="submit" class="send-button" value="Create article">
                </div>
                <div class="error">
                    ${error!}
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".add-article form").submit(function () {
                var title = $(this).find("input[name='title']").val();
                var text = $(this).find("textarea[name='text']").val();
                var $error = $(this).find(".error");

                ajax({
                        data: {
                            action: "addArticle",
                            title: title,
                            text: text
                        }
                    },
                    $error);
                return false;
            });
        })
    </script>
</@c.page>