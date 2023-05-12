/*-- ajax 모듈화 --*/

let adminService = (function() {
    function memberDetail(memberId) {
        $.ajax({
            url: "/admin/member-detail",
            data: {"memberId": memberId},
            success: function(member) {
                showMemberDetail(member);
            }
        })
    }

    function productDetail(productId) {
        $.ajax({
            url: "/admin/product-detail",
            data: {"productId": productId},
            success: function(product) {
                showProductDetail(product);
            }
        })
    }
    return {memberDetail: memberDetail, productDetail: productDetail}
})();

/*-- 회원 상세보기 모달 --*/
const $memberTr = $(".tr__tag");

$memberTr.on("click", function() {
    let memberId = $($(this).children()[1]).text();
    adminService.memberDetail(memberId);
});

/*-- 상품 상세보기 모달 --*/
const $productTr = $(".product__tr");

$productTr.on("click", function() {
    let productId = $($(this).children()[1]).text();
    adminService.productDetail(productId);
});

/*-- 회원 정보 모달 --*/

function showMemberDetail(member) {
    let text = "";
    const $ulTag = $(".content-list-wrap");

    text = `
        <li class="content-list">
            <span>이름</span>
            <div class="content-input">
                <input type="text" value="${member[0]}" readonly/>
            </div>
        </li>
        <li class="content-list">
            <span>휴대전화</span>
            <div class="content-input">
                <input type="text" value="${member[1]}" readonly/>
            </div>
        </li>
        <li class="content-list">
            <span>이메일</span>
            <div class="content-input">
                <input type="text" value="${member[2]}" readonly/>
            </div>
        </li>
        <li class="content-list">
            <span>생년월일</span>
            <div class="content-input">
                <input type="text" value="${member[3]}" readonly/>
            </div>
        </li>
        <li class="content-list">
            <span>성별</span>
            <div class="content-input">
                <input type="text" value="${member[4]}" readonly/>
            </div>
        </li>
    `;
    $ulTag.empty();
    $ulTag.append(text);
}

function showProductDetail(product) {
    let text = "";
    const $ulTag = $(".product__detail__modal");

    text = `
        <li class="content-list">
            <span>메뉴</span>
            <div class="content-input">
                <input type="text" value="${product[0]}" readonly/>
            </div>
        </li>
        <li class="content-list">
            <span>가격</span>
            <div class="content-input">
                <input type="text" value="${product[1]}" readonly/>
            </div>
        </li>
        <li class="content-list">
            <span>수량</span>
            <div class="content-input">
                <input type="text" value="${product[2]}" readonly/>
            </div>
        </li>
    `;
    $ulTag.empty();
    $ulTag.append(text);
}