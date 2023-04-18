$("#allSelect").click(function() {
    if($("#allSelect").is(":checked")) $("input[name=check]").prop("checked", true);
    else $("input[name=check]").prop("checked", false);
});

$("input[name=check]").click(function() {
    var total = $("input[name=check]").length;
    var checked = $("input[name=check]:checked").length;

    if(total != checked) $("#allSelect").prop("checked", false);
    else $("#allSelect").prop("checked", true); 
});

const pageButton = $(".page-button");