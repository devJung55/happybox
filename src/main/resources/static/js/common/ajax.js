function $doAjax(type, url, data, callback) {
    $.ajax({
        type: type,
        url: url,
        data: data,
        dataType: "json",
        success: function (response) {
            if (callback) {
                callback(response);
            }
        }
    });
}

function $doAjaxPost(type, url, data, callback) {
    $.ajax({
        type: type,
        url: url,
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            if (callback) {
                callback(response);
            }
        }
    });
}