const $paymentConfirm = $(".confirm-delete");

$paymentConfirm.on("click", function() {
    const $checkBoxes = $(".check__box");

    $checkBoxes.each((i, v) => {
        if(v.checked) {
            adminService.removePayment($($(v).parent().parent().siblings()[0]).text());
        }
    });
});