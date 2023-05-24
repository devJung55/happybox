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

    function productDetail(btn) {
        let productId = $(btn).data("productid");
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
                showRecipeBoardDetail(recipeBoard);
            }
        })
    }

    function reviewBoardDetail(reviewBoardId) {
        $.ajax({
            url: "/admin/reviewBoard-detail",
            data: {"reviewBoardId": reviewBoardId},
            success: function(reviewBoard) {
                showReviewBoardDetail(reviewBoard);
            }
        })
    }

    function donationBoardDetail(donationBoardId) {
        $.ajax({
            url: "/admin/donationBoard-detail",
            data: {"donationBoardId": donationBoardId},
            success: function(donationBoard) {
                showDonationDetail(donationBoard);
            }
            }
        )
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

    function removeBoard(boardId) {
        $.ajax({
            url: "/admin/board-remove",
            data: {"boardId": boardId},
            success: function() {
                location.reload();
            }
        })
    }

    function removePayment(id) {
        $.ajax({
            url: "/admin/remove-payment",
            data: {"id": id},
            success: function() {
                location.reload();
            }
        })
    }
    return {memberDetail: memberDetail, productDetail: productDetail, recipeBoardDetail: recipeBoardDetail, reviewBoardDetail: reviewBoardDetail, removeUser: removeUser, removeBoard: removeBoard, removePayment: removePayment, donationBoardDetail: donationBoardDetail}
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
        src = "/image/display?fileName=" + filePath + "/" + fileUuid + "_" + fileOrgName;
    }

    text = `
        <h5 class="detail-title">회원 상세정보</h5>
        <div class="content-img-wrapper">
            <label>
                <div class="content-img one-img">
                    <img src= ${src}/>
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
        src = "/image/display?fileName=" + filePath + "/" + fileUuid + "_" + fileOrgName;
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

    $(".modal").show();
}

function showReviewBoardDetail(reviewBoard) {
    let text = "";
    let img = "";
    const $reviewDetailAppend = $(".content-detail");
    
    text += `
        <div class="content-img-wrapper">
            `;

    for (let i = 0; i < 3; i++) {
        if(i < reviewBoard.reviewBoardFiles.length) {
            img += `
                    <div class="content-img list-img">
                        <img src="/image/display?fileName=${reviewBoard.reviewBoardFiles[i].filePath}/${reviewBoard.reviewBoardFiles[i].fileUuid}_${reviewBoard.reviewBoardFiles[i].fileOrgName}">
                    </div>
                `;
        } else {
            img += `
                    <div class="content-img list-img">
                        <img src="https://us.123rf.com/450wm/mathier/mathier1905/mathier190500002/134557216-%EC%8D%B8%EB%84%A4%EC%9D%BC-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%86%EC%9D%8C-%ED%8F%AC%EB%9F%BC-%EB%B8%94%EB%A1%9C%EA%B7%B8-%EB%B0%8F-%EC%9B%B9%EC%82%AC%EC%9D%B4%ED%8A%B8%EC%9A%A9-%EC%9E%90%EB%A6%AC-%ED%91%9C%EC%8B%9C%EC%9E%90.jpg?ver=6"/>
                    </div>
                `;
        }
    }

        text += img;

        text += `
        </div>
            <ul class="content-list-wrap">
                <li class="content-list">
                    <span>제목</span>
                    <div class="content-input">
                        <input type="text" value="${reviewBoard.boardTitle}" readonly/>
                    </div>
                </li>
                <li class="content-list">
                    <span>닉네임</span>
                    <div class="content-input">
                        <input type="text" value="${reviewBoard.memberName}" readonly/>
                    </div>
                </li>
                <li class="content-list">
                    <span>카테고리</span>
                    <div class="content-input">
                        <input type="text" value="리뷰 게시판" readonly/>
                    </div>
                </li>
                <li class="content-list">
                    <span>작성날짜</span>
                    <div class="content-input">
                        <input type="text" value="${reviewBoard.boardRegisterDate}" readonly/>
                    </div>
                </li>
                <li class="content-list txt-align">
                    <span>내용</span>
                    <div class="content-input">
                        <textarea class="normal-textarea" cols="30" rows="10" name="" readonly>${reviewBoard.boardContent}</textarea>
                    </div>
                </li>
            </ul>
    `;
    $reviewDetailAppend.empty();
    $reviewDetailAppend.append(text);
}

function showRecipeBoardDetail(recipeBoard) {
    let text = "";
    let img = "";
    const $recipeDetailAppend = $(".content-detail");

    console.log(recipeBoard)
    text += `
        <div class="content-img-wrapper board-wrapper">
            <label style="display: flex">
            `;

        for (let i = 0; i < 7; i++) {
            if(i < recipeBoard.recipeBoardFiles.length) {
                img += `
                    <div class="content-img list-img img__width">
                        <img src="/image/display?fileName=${recipeBoard.recipeBoardFiles[i].filePath}/${recipeBoard.recipeBoardFiles[i].fileUuid}_${recipeBoard.recipeBoardFiles[i].fileOrgName}">
                    </div>
                `;
            } else {
                img += `
                    <div class="content-img list-img img__width">
                        <img src="https://us.123rf.com/450wm/mathier/mathier1905/mathier190500002/134557216-%EC%8D%B8%EB%84%A4%EC%9D%BC-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%86%EC%9D%8C-%ED%8F%AC%EB%9F%BC-%EB%B8%94%EB%A1%9C%EA%B7%B8-%EB%B0%8F-%EC%9B%B9%EC%82%AC%EC%9D%B4%ED%8A%B8%EC%9A%A9-%EC%9E%90%EB%A6%AC-%ED%91%9C%EC%8B%9C%EC%9E%90.jpg?ver=6">
                    </div>
                `;
            }
        }

    text += img;

    text += `
                </div>
            </label>
        </div>
        <ul class="content-list-wrap recipeBoard__detail__modal">
            <li class="content-list">
                <span>제목</span>
                <div class="content-input">
                    <input type="text" value="${recipeBoard.boardTitle}" readonly/>
                </div>
            </li>
            <li class="content-list">
                <span>이름</span>
                <div class="content-input">
                    <input type="text" value="${recipeBoard.memberDTO.memberName}" readonly/>
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
                    <input type="text" value="${recipeBoard.boardRegisterDate}" readonly/>
                </div>
            </li>
            <li class="content-list txt-align">
                <span>내용</span>
                <div class="content-input">
                    <textarea class="normal-textarea" cols="30" rows="10" name="" readonly>${recipeBoard.boardContent}</textarea>
                </div>
            </li>
        </ul>
    `;
    $recipeDetailAppend.empty();
    $recipeDetailAppend.append(text);
}

function showDonationDetail(board) {
    const $donationBoardAppend = $(".donationBoard__append");
    let text = "";
    let img = "";

    let createdDate = board.boardRegisterDate.split("T")[0]
    text = `
        <div class="content-img-wrapper">
            `;


        if(board.donationBoardFiles.length != 0) {
            img += `
                    <div class="content-img list-img">
                        <img src="/image/display?fileName=${board.donationBoardFiles[0].filePath}/${board.donationBoardFiles[0].fileUuid}_${board.donationBoardFiles[0].fileOrgName}">
                    </div>
                `;
        } else {
            img += `
                    <div class="content-img list-img">
                        <img src="https://us.123rf.com/450wm/mathier/mathier1905/mathier190500002/134557216-%EC%8D%B8%EB%84%A4%EC%9D%BC-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%86%EC%9D%8C-%ED%8F%AC%EB%9F%BC-%EB%B8%94%EB%A1%9C%EA%B7%B8-%EB%B0%8F-%EC%9B%B9%EC%82%AC%EC%9D%B4%ED%8A%B8%EC%9A%A9-%EC%9E%90%EB%A6%AC-%ED%91%9C%EC%8B%9C%EC%9E%90.jpg?ver=6">
                    </div>
                `;
        }

        text += img;

        text += `     
        <ul class="content-list-wrap">
            <li class="content-list">
                <span>제목</span>
                <div class="content-input">
                    <input type="text" value="${board.boardTitle}" readonly/>
                </div>
            </li>
            <li class="content-list">
                <span>복지관</span>
                <div class="content-input">
                    <input type="text" value="${board.welfareName}" readonly/>
                </div>
            </li>
            <li class="content-list">
                <span>작성날짜</span>
                <div class="content-input">
                    <input type="text" value="${createdDate}" readonly/>
                </div>
            </li>
            <li class="content-list">
                <span>무료급식소</span>
                <div class="content-input">
                    <input type="text" value="${board.donateLocation}" readonly/>
                </div>
            </li>
            <li class="content-list txt-align">
                <span>내용</span>
                <div class="content-input">
                    <textarea class="normal-textarea" cols="30" rows="10" name="" readonly>${board.boardContent}</textarea>
                </div>
            </li>
        </ul>
    `;
    $donationBoardAppend.empty();
    $donationBoardAppend.append(text);
}

/*-- 페이징 처리 --*/
function setPage(page) {
    const url = window.location.pathname;
    location.href = `${url}?page=${page}`;
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

/*-- 후기 게시물 상세보기 모달 --*/
const $reviewBoardUI = $(".reviewBoard__tr");

$reviewBoardUI.on("click", function() {
    let reviewBoardId = $($(this).children()[1]).text();
    adminService.reviewBoardDetail(reviewBoardId);
});

/*-- 기부 게시물 상세보기 모달 --*/
const $donationBoardUl = $(".donationBoard__Tr");

$donationBoardUl.on("click", function() {
    let donationBoardId = $($(this).children()[1]).text();
    adminService.donationBoardDetail(donationBoardId);
});

/*-- 삭제 --*/
const $confirm = $(".confirm-delete");

$confirm.on("click", function() {
    const $checkBoxes = $(".check__box");

    $checkBoxes.each((i, v) => {
        if(v.checked) {
            const url = window.location.pathname;

            if(url == "/admin/member-list") {
                adminService.removeUser($($(v).parent().siblings()[0]).text());
            } else if(url == "/admin/recipeBoard-list") {
                adminService.removeBoard($($(v).parent().parent().siblings()[0]).text());
            } else if(url == "/admin/reviewBoard-list") {
                console.log($($(v).parent().parent().siblings()[0]).text());
                adminService.removeBoard($($(v).parent().parent().siblings()[0]).text());
            }
        }
    });
});