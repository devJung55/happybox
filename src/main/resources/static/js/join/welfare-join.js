/* welfare-join.html */
/* company-join.html */

window.onload = function () {
    document.getElementById("address-kakao").addEventListener("click", function () { //주소입력칸을 클릭하면
        //카카오 지도 발생
        new daum.Postcode({
            oncomplete: function (data) { //선택시 입력값 세팅
                document.getElementById("address-kakao").value = data.address; // 주소 넣기
                document.getElementById("zone-code").value = data.zonecode;
            }
        }).open();
    });
}


// 정규식 검사
const $joinInputs = $('.join-input'); // 6개
const nameRegex = /^[가-힣|a-z|A-Z|]+$/;
const nicknameRegex = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$/;
const specialCharacterRegex = /[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gim;
const birthRegex = /^(19[0-9][0-9]|20\d{2}).?(0[0-9]|1[0-2]).?(0[1-9]|[1-2][0-9]|3[0-1])$/;
const phoneRegex = /^\d{2,3}-?\d{3,4}-?\d{4}$/;
const idRegex = /^(?!(?:[0-9]+)$)([a-zA-Z]|[0-9a-zA-Z]){4,}$/;
const passwordNumberRegex = /[0-9]/g;
const passwordEnglishRegex = /[a-z]/gi;
const passwordSpecialCharacterRegex = /[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi;
const emailRegex = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/;
const $addressInput = $('.address-input');
const $addressDetailInput = $('.address-detail-input');
const $submit = $('#join-general-form');

let joinBlurMessages = [
    '아이디를 입력하세요.',
    '비밀번호를 입력하세요.',
    '비밀번호 확인을 위해 한번 더 입력하세요.',
    '마켓이름을 입력하세요.',
    '전화번호를 입력하세요.',
    '이메일 주소를 입력하세요.',
];
let joinRegexMessages = [
    '영문 혹은 영문과 숫자를 조합하여 4자~16자로 입력하세요.',
    '공백 제외 영어 및 숫자, 특수문자 모두 포함하여 6~16자로 입력하세요.',
    '위 비밀번호와 일치하지 않습니다. 다시 입력하세요.',
    '마켓이름을 확인하세요.',
    '전화번호를 확인하세요.',
    '상세주소를 확인하세요',
    '이메일 주소를 확인하세요.',
];
const $joinHelp = $('p.help');

let joinCheck;
let joinCheckAll = [false, false, false, false, false, false, false];


$joinInputs.on('blur', function () {
    let i = $joinInputs.index($(this));
    let value = $(this).val();
    console.log(value +" 인덱스번호: "+ i);

    if (!value) {
        $joinHelp.eq(i).text(joinBlurMessages[i]);
        joinCheck = false;
        joinCheckAll[i] = joinCheck;
        return;
    }

    switch (i) {
        case 0:
            joinCheck =
                value.length > 3 &&
                value.length < 21 &&
                idRegex.test(value) &&
                !specialCharacterRegex.test(value);
            break;
        case 1:
            let numberCheck = value.search(passwordNumberRegex);
            let englishCheck = value.search(passwordEnglishRegex);
            let specialCharacterCheck = value.search(passwordSpecialCharacterRegex);

            var condition1 =
                numberCheck >= 0 &&
                englishCheck >= 0 &&
                englishCheck >= 0 &&
                specialCharacterCheck >= 0 &&
                specialCharacterCheck >= 0 &&
                numberCheck >= 0;
            var condition2 = value.length > 5 && value.length < 15;
            var condition3 = value.search(/\s/) < 0;

            joinCheck = condition1 && condition2 && condition3;
            break;
        case 2:
            joinCheck = $joinInputs.eq(i - 1).val() == value;
            break;
        case 3:
            joinCheck =
                value.length > 1 &&
                value.length < 21 &&
                nameRegex.test(value) &&
                !specialCharacterRegex.test(value);
            break;
        case 4:
            joinCheck = phoneRegex.test(value);
            if (joinCheck) {
                $(this).val(value.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`));
            }
            break;
        case 6:
            joinCheck = emailRegex.test(value);
            break;
    }

    joinCheckAll[i] = joinCheck;

    if (!joinCheck) {
        $joinHelp.eq(i).show();
        $joinHelp.eq(i).css('color','red');
        $joinHelp.eq(i).text(joinRegexMessages[i]);
        return;
    }
    console.log("i의 숫자는?:" + i);
    // 아이디 중복 검사
    if (i == 0) {
        $('#btn-id-check').click(function () {
            $.ajax({
                type: 'get',
                url: '/checkUserId',
                data: {userId: value},
                success: function (result) {
                    $joinHelp.eq(i).show();
                    if (result) {
                        joinCheckAll[i] = false;
                        $joinHelp.eq(i).css('color','red');
                        $joinHelp.eq(i).text('중복된 아이디입니다.');
                    } else {
                        joinCheckAll[i] = true;
                        $joinHelp.eq(i).css('color','blue');
                        $joinHelp.eq(i).text('사용가능한 아이디입니다.');
                    }
                },
            });
        });
    } else if (i == 3) {
        $.ajax({
            type: "get",
            url: "/checkDistributorName",
            data: {distributorName: value},
            success: function (result) {
                let message;
                if (result) {
                    message = "중복된 유통업체입니다.";
                    $joinHelp.eq(i).css('color','red');
                    $joinHelp.eq(i).show();
                    $joinHelp.eq(i).text(message);
                    joinCheckAll[i] = false;
                } else {
                    message = "사용가능한 이름입니다."
                    $joinHelp.eq(i).css('color','blue');
                    $joinHelp.eq(i).show();
                    $joinHelp.eq(i).text(message);
                    joinCheckAll[i] = true;
                }
            }
        });
    } else if (i == 4) {
        $.ajax({
            type: "get",
            url: "/checkUserPhoneNumber",
            data: {userPhoneNumber: value},
            success: function (result) {
                let message;
                if (result) {
                    message = "중복된 번호입니다.";
                    $joinHelp.eq(i).css('color','red');
                    $joinHelp.eq(i).show();
                    $joinHelp.eq(i).text(message);
                    joinCheckAll[i] = false;
                } else {
                    message = "사용가능한 번호입니다."
                    $joinHelp.eq(i).css('color','blue');
                    $joinHelp.eq(i).show();
                    $joinHelp.eq(i).text(message);
                    joinCheckAll[i] = true;
                }
            }
        });
    } else if (i == 6) {
        $.ajax({
            type: "get",
            url: "/checkUserEmail",
            data: {userEmail: value},
            success: function (result) {
                let message;
                if (result) {
                    message = "중복된 이메일입니다.";
                    $joinHelp.eq(i).css('color','red');
                    $joinHelp.eq(i).show();
                    $joinHelp.eq(i).text(message);
                    joinCheckAll[i] = false;
                } else {
                    message = "사용가능한 이메일입니다."
                    $joinHelp.eq(i).css('color','blue');
                    $joinHelp.eq(i).show();
                    $joinHelp.eq(i).text(message);
                    joinCheckAll[i] = true;
                }
            }
        });
    }

    $joinHelp.eq(i).hide();
});

function send() {

    if (joinCheckAll.filter((check) => check).length != $joinInputs.length) {
        alertModal('가입 정보를 확인하세요.');
    } else if ($addressInput.val() == '') {
        alertModal('주소를 입력하세요.');
    } else if ($addressDetailInput.val() == '') {
        alertModal('상세주소를 입력하세요.')
    }else {
        $submit.submit();
    }
}

function alertModal(errorMsg) {
    $("div#content-wrap").html(errorMsg)
    $("div.modal").css("display", "flex").hide().fadeIn(500);
    setTimeout(function () {
        $("div.modal").fadeOut();
    }, 1000);
}