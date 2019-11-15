<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="loggedUser" type="ru.itmo.wp.model.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th class="admin">Admin</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <td class="user_admin"><input class="button" type="button"></td>
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
            var $admin = $(".users .admin");
            var $noData = $(".users .noData");
            $noData.find("td").text("Loading...");
            <#if (!loggedUser??) || !loggedUser.isAdmin()>
            $admin.hide();
            </#if>

            ajax({
                data: {
                    action: "findAll"
                },
                success: function (response) {
                    response["users"].forEach(function (user) {
                        $noData.hide();
                        var $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user_id").text(user["id"]);
                        $tr.find(".user_login").text(user["login"]);
                        <#if loggedUser?? && loggedUser.isAdmin()>
                        var value = (user["isAdmin"] === true ? "Disable" : "Enable");
                        $tr.find(".user_admin .button").attr("value", value);
                        $tr.find(".user_admin .button").attr("userToChange", user["id"]);
                        <#else>
                        $tr.find(".user_admin").hide();
                        </#if>
                        $tr.find("tr").attr("data-userId", user["id"]);
                        $(".users tbody").append($tr);
                    });
                    $(".users .user_admin .button").click(function () {
                        ajax({
                            data: {
                                action: "changeAdminProperty",
                                userToChange: $(this).attr("userToChange")
                            },
                            success: function (response) {
                                var isAdmin = response["currentAdminProp"];
                                $(this).attr("userToChange", isAdmin);
                            }
                        });
                        if ($(this).attr("value") === "Disable") {
                            $(this).attr("value", "Enable");
                        } else {
                            $(this).attr("value", "Disable");
                        }
                        return false;
                    });
                }
            });
        })
    </script>
</@c.page>
