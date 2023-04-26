const $navCtrl = $(".nav-control");

$navCtrl.on("click", function () {
    let navClass = "nav-show";
    let $nav = $(this).next();
    let check = $nav.hasClass(navClass);

    if (check) $nav.removeClass(navClass);
    else $nav.addClass(navClass);
});
