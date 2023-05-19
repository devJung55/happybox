/* 날짜 변환 */

function formatDate(date) {
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}.${month}.${day}`;
}

/* 검색하기 */
function $searchAjax(type, url, data, callback) {
    $.ajax({
        type: type,
        url: url,
        data: data,
        dataType: "json",
        success: function(result) {
            if(callback) {
                callback(result)
            }
        }
    })
}