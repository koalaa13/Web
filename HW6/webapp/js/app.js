window.notify = function(message) {
    $.notify(message, {position: "bottom right"})
};

window.ajax = function(data, $error) {
    var settings = {
        type: "POST",
        url: "",
        dataType: "json",
        success: function (response) {
            if (response["error"]) {
                $error.text(response["error"]);
            } else {
                location.href = response["redirect"];
            }
        }
    };

    for (var key in data) {
        settings[key] = data[key];
    }

    $.ajax(settings)
};
