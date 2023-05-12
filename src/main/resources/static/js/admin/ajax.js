/*-- ajax 모듈화 --*/

let adminService = (function() {
    function memberDetail(memberId) {
        $.ajax({
            url: "/admin/member-detail",
            data: {"memberId" : memberId},
            success: function(member) {
                showList(member);
            },
            error: function(a, b, c) {
                console.log(a);
                console.log(b);
                console.log(c);
            }
        })
    }
    return {memberDetail: memberDetail}
})();

/*-- 회원 상세보기 모달 --*/

const $tr = $(".tr__tag");

$tr.on("click", function() {
    let memberId = $($(this).children()[1]).text();
    adminService.memberDetail(memberId);
});

/*-- 회원 정보 모달 --*/

function showList(member) {
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
    `
    $ulTag.empty();
    $ulTag.append(text);
}