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