/* subscribe.html */

/* 상세보기 클릭이벤트 */

const $welfareDetail = $(".welfare__detail");
const $detailButton = $(".detail__button");
const $arrow = $(".arrow");
const $thumbnail = $(".lozad");

$detailButton.on("click", function() {
    if($arrow.hasClass("arrow-0deg")) {
        $arrow.removeClass("arrow-0deg");
        $arrow.addClass("arrow-180deg");
        $welfareDetail.css("display", "block");
        $thumbnail.css("display", "none");
    } else {
        $arrow.removeClass("arrow-180deg");
        $arrow.addClass("arrow-0deg");
        $welfareDetail.css("display", "none");
        $thumbnail.css("display", "block");
    }
});

/* 구독 상태 클릭이벤트 */

const $subscribeButton = $(".badge-sm-primary");
const id = $(".orderSubscriptionId").val();

$subscribeButton.on("click", function() {
    if($subscribeButton.text() == '구독취소') {
        myPageService.cancelSubscribe(id);

        $subscribeButton.text("구독하기");
        $subscribeButton.css("border", "1px solid #666666");
        $subscribeButton.css("color", "#666666");
    } else {
        $subscribeButton.text("구독취소");
        $subscribeButton.css("border", "1px solid #FF5938");
        $subscribeButton.css("color", "#FF5938");
    }
});

/*---------------------------------------------------------*/

const $welfareThumnail = $(".lozad");
const $welfareImg = $(".welfare__img");

let userProfilePath = $userProfile.filePath;
let userProfileUuid = $userProfile.fileUuid;
let userProfileOrgName = $userProfile.fileOrgName;

$welfareThumnail.attr("src", `/image/display?fileName=${userProfilePath}/${userProfileUuid}_${userProfileOrgName}`);
$welfareImg.attr("src", `/image/display?fileName=${userProfilePath}/${userProfileUuid}_${userProfileOrgName}`);