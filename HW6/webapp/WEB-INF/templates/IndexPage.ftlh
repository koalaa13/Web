<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles">
        <template>
            <article>
                <div class="title"></div>
                <div class="information"></div>
                <div class="body"></div>
                <ul class="attachment">
                    <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
                    <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
                </ul>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+173</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                        <span class="creationTime"></span>
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                        <a href="#">68</a>
                    </div>
                </div>
            </article>
        </template>
        <article class="noData">
            There is 0 articles.
        </article>
    </div>

    <script>
        $(function () {
            var $noData = $(".articles .noData");
            $noData.text("Loading...");

            ajax({
                url: "/index",
                data: {
                    action: "findAllArticles"
                },
                success: function (response) {

                    response["articles"].forEach(function (article) {
                        $noData.hide();
                        var $article = $($(".articles template").prop("content")).clone();
                        $article.find(".title").text(article["title"]);
                        $article.find(".body").text(article["text"]);
                        ajax({
                            url: "/index",
                            async: false,
                            data: {
                                action: "userLoginById",
                                userId: article["userId"]
                            },
                            success: function (response) {
                                $article.find(".information").text("By " + response["creatorLogin"]);
                            }
                        });
                        $article.find(".creationTime").text(article["creationTime"]);
                        $(".articles").append($article);
                    });
                }
            });
        })
    </script>
<#--<script>-->
<#--    $(function () {-->
<#--        $("aside section").click(function () {-->
<#--            $(this).css("background-color", "red");-->
<#--        });-->
<#--    })-->
<#--</script>-->
</@c.page>
