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

    function recipeBoardDetail(recipeBoardId) {
        $.ajax({
            url: "/admin/recipeBoard-detail",
            data: {"recipeBoardId": recipeBoardId},
            success: function(recipeBoard) {
                console.log("success");
                showRecipeBoardDetail(recipeBoard);
            }
        })
    }

    function removeUser(userId) {
        $.ajax({
            url: "/admin/user-remove",
            data: {"userId": userId},
            success: function() {
                location.reload();
            }
        })
    }
    return {memberDetail: memberDetail, productDetail: productDetail, recipeBoardDetail: recipeBoardDetail, removeUser: removeUser}
})();

function showMemberDetail(member) {
    let text = "";
    let src = "";
    let filePath = member[0];
    let fileUuid = member[1];
    let fileOrgName = member[2];
    const $modalAppend = $(".content-detail");

    if(member[0] == null || member[0] == "") {
        src = "https://us.123rf.com/450wm/mathier/mathier1905/mathier190500002/134557216-%EC%8D%B8%EB%84%A4%EC%9D%BC-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%86%EC%9D%8C-%ED%8F%AC%EB%9F%BC-%EB%B8%94%EB%A1%9C%EA%B7%B8-%EB%B0%8F-%EC%9B%B9%EC%82%AC%EC%9D%B4%ED%8A%B8%EC%9A%A9-%EC%9E%90%EB%A6%AC-%ED%91%9C%EC%8B%9C%EC%9E%90.jpg?ver=6";
    } else {
        src = "/files/display?" + filePath + "/t_" + fileUuid + "_" + fileOrgName;
    }

    text = `
        <h5 class="detail-title">회원 상세정보</h5>
        <div class="content-img-wrapper">
            <label>
                <div class="content-img one-img">
                    <img
                    src= ${src}
                    />
                </div>
                <input type="file" name="file" accept="image/*" style="display: none" readonly/>
            </label>
        </div>
        <ul class="content-list-wrap">
            <li class="content-list">
                <span>이름</span>
                <div class="content-input">
                    <input type="text" value="${member[3]}" readonly/>
                </div>
            </li>
            <li class="content-list">
                <span>휴대전화</span>
                <div class="content-input">
                    <input type="text" value="${member[4]}" readonly/>
                </div>
            </li>
            <li class="content-list">
                <span>이메일</span>
                <div class="content-input">
                    <input type="text" value="${member[5]}" readonly/>
                </div>
            </li>
            <li class="content-list">
                <span>생년월일</span>
                <div class="content-input">
                    <input type="text" value="${member[6]}" readonly/>
                </div>
            </li>
            <li class="content-list">
                <span>성별</span>
                <div class="content-input">
                    <input type="text" value="${member[7]}" readonly/>
                </div>
            </li>
        </ul>
    `;
    $modalAppend.empty();
    $modalAppend.append(text);
}

function showProductDetail(product) {
    let text = "";
    let src = "";
    let filePath = product[0];
    let fileUuid = product[1];
    let fileOrgName = product[2];
    const $mainTag = $(".product__detail__modal");

    if(product[0] == null) {
        src = "https://us.123rf.com/450wm/mathier/mathier1905/mathier190500002/134557216-%EC%8D%B8%EB%84%A4%EC%9D%BC-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%86%EC%9D%8C-%ED%8F%AC%EB%9F%BC-%EB%B8%94%EB%A1%9C%EA%B7%B8-%EB%B0%8F-%EC%9B%B9%EC%82%AC%EC%9D%B4%ED%8A%B8%EC%9A%A9-%EC%9E%90%EB%A6%AC-%ED%91%9C%EC%8B%9C%EC%9E%90.jpg?ver=6";
    } else {
        src = "/files/display?" + filePath + "/t_" + fileUuid + "_" + fileOrgName;
    }

    text = `
        <div class="content-img-wrapper">
            <label>
                <div class="content-img one-img">
                    <img src= ${src}/>
                </div>
                <input type="file" name="file" style="display: none" />
            </label>
        </div>
        <ul class="content-list-wrap">
            <li class="content-list">
                <span>메뉴</span>
                <div class="content-input">
                    <input type="text" value="${product[3]}" readonly/>
                </div>
            </li>
            <li class="content-list">
                <span>가격</span>
                <div class="content-input">
                    <input type="text" value="${product[4] + '원'}" readonly/>
                </div>
            </li>
            <li class="content-list">
                <span>수량</span>
                <div class="content-input">
                    <input type="text" value="${product[5] + '개'}" readonly/>
                </div>
            </li>
        </ul>
    `;
    $mainTag.empty();
    $mainTag.append(text);
}

function showRecipeBoardDetail(recipeBoard) {
    let text = "";
    const $ulTag = $(".content-list-wrap");

    text = `
        <li class="content-list">
            <span>제목</span>
            <div class="content-input">
                <input type="text" value="${recipeBoard[0]}" readonly/>
            </div>
        </li>
        <li class="content-list">
            <span>이름</span>
            <div class="content-input">
                <input type="text" value="${recipeBoard[1]}" readonly/>
            </div>
        </li>
        <li class="content-list">
            <span>카테고리</span>
            <div class="content-input">
                <input type="text" value="레시피 게시판" readonly/>
            </div>
        </li>
        <li class="content-list">
            <span>작성날짜</span>
            <div class="content-input">
                <input type="text" value="${recipeBoard[2]}" readonly/>
            </div>
        </li>
        <li class="content-list txt-align">
            <span>내용</span>
            <div class="content-input">
                <textarea class="normal-textarea" cols="30" rows="10" name="" readonly>${recipeBoard[3]}</textarea>
            </div>
        </li>
    `;
    $ulTag.empty();
    $ulTag.append(text);
}

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

/*-- 레시피 게시물 상세보기 모달 --*/
const $recipeBoardUl = $(".recipeBoard__tr");

$recipeBoardUl.on("click", function() {
    let recipeBoardId = $($(this).children()[1]).text();
    adminService.recipeBoardDetail(recipeBoardId);
});

/*-- 회원 삭제 --*/
const $userCheckBox = $(".check__box");
const $confirm = $(".confirm-delete");

$confirm.on("click", function() {
    const $checkBoxes = $(".check__box");

    $checkBoxes.each((i, v) => {
        if(v.checked) {
            adminService.removeUser($($(v).parent().siblings()[0]).text());
        }
    });
});