<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles datatable">
        <div class="caption">My articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hidden property options</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="article_id"></td>
                    <td class="article_title"></td>
                    <td class="hidden_prop"><input class="button" type="button"></td>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            var $noData = $(".articles .noData");
            $noData.find("td").text("Loading...");

            ajax({
                data: {
                    action: "findAllByUserId"
                },
                success: function (response) {
                    response["myArticles"].forEach(function (article) {
                        $noData.hide();
                        var $tr = $($(".articles template").prop("content")).clone();
                        $tr.find(".article_id").text(article["id"]);
                        var title = article["title"];
                        if (title.length > 50) {
                            title = title.substring(0, 50) + "articleId...";
                        }
                        $tr.find(".article_title").text(title);
                        var value = (article["hidden"] === true ? "Show" : "Hide");
                        $tr.find(".hidden_prop .button").attr("value", value);
                        $tr.find(".hidden_prop .button").attr("articleId", article["id"]);
                        $(".articles tbody").append($tr);
                    });
                    $(".articles .hidden_prop .button").click(function () {
                        ajax({
                            data: {
                                action: "changeHiddenProp",
                                articleId: $(this).attr("articleId")
                            },
                            success: function (response) {
                                var hide = response["currentHidProp"];
                                $(this).attr("articleId", hide);
                            }
                        });
                        if ($(this).attr("value") === "Show") {
                            $(this).attr("value", "Hide");
                        } else {
                            $(this).attr("value", "Show");
                        }
                        return false;
                    });
                }
            });
        })
    </script>
</@c.page>