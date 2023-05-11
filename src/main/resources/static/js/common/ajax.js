function $doAjax(type, url, data) {
    $.ajax({
        type: type,
        url: url,
        data: data,
        dataType: "json",
        success: function (response) {
            console.log(response);
        }
    });
}