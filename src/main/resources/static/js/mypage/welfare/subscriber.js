myPageWelfareService.subscriberListAjax();

const $subscriberAppend = $(".subscriber__list");

function showSubscriberList(subscribers) {
    let text = "";

    subscribers.content.forEach(subscriber => {
        let img = "";
        let address = `${subscriber.address.firstAddress} ${subscriber.address.addressDetail}`;

        text += `
            <li id="delivery-product-2541529900N" class="delivery-product-2541529900NY">
                <div class="prd-info-area productCart-info-area">
                    <div class="inner">
                        <div class="user__img">
                `;

                if(subscriber.userFileDTO != null) {
                    img = `<img src="/image/display?fileName=${subscriber.userFileDTO.filePath}/${subscriber.userFileDTO.fileUuid}_${subscriber.userFileDTO.fileOrgName}">`;
                } else {
                    img = `<img src="/img/mypage/nomalProfile.png">`;
                }

        text += img;

        text += `
                            
                        </div>
                        <div class="user__info">
                            <div class="user__name">
                                <input type="hidden" value="${subscriber.id}">
                                <span>${subscriber.memberName}</span>
                            </div>
                            <div class="user__address">
                                <span>${address}</span>
                            </div>
                        </div>
                    </div>
                    <div class="column btn">
                        <button type="button" class="btn-x-sm deleteUserCart">
                            <i class="ico-x-black"></i>
                        </button>
                    </div>  
                </div>
            </li>
        `;
    });
    $subscriberAppend.empty();
    $subscriberAppend.append(text);
}


/* 전체 조회 */

$(".btn-black").on("click", function() {
    $(".input-text").val("");
    myPageWelfareService.subscriberListAjax();
});