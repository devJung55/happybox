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
    return {memberDetail: memberDetail, productDetail: productDetail, recipeBoardDetail: recipeBoardDetail}
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

/*-- 레시피 게시물 상세보기 모달 --*/
const $recipeBoardUl = $(".recipeBoard__tr");

$recipeBoardUl.on("click", function() {
    let recipeBoardId = $($(this).children()[1]).text();
    adminService.recipeBoardDetail(recipeBoardId);
});

function showMemberDetail(member) {
    let text = "";
    const $modalAppend = $(".content-detail");

    text = `
        <h5 class="detail-title">회원 상세정보</h5>
        <div class="content-img-wrapper">
            <label>
                <div class="content-img one-img">
                    <img
                    src="/files/display?${member[0] + "/t_" + member[1] + "_" + member[2]}"
                    />
                </div>
                <input type="file" name="file" accept="image/*" style="display: none"/>
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
                <input type="text" value="${product[1] + '원'}" readonly/>
            </div>
        </li>
        <li class="content-list">
            <span>수량</span>
            <div class="content-input">
                <input type="text" value="${product[2] + '개'}" readonly/>
            </div>
        </li>
    `;
    $ulTag.empty();
    $ulTag.append(text);
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