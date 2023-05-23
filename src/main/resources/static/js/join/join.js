/* join.html */

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
const $joinInputs = $('.join-input');
const nameRegex = /^[가-힣|a-z|A-Z|]+$/;
const nicknameRegex = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$/;
const specialCharacterRegex = /[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gim;
const birthRegex = /^(19[0-9][0-9]|20\d{2}).?(0[0-9]|1[0-2]).?(0[1-9]|[1-2][0-9]|3[0-1])$/;
const phoneRegex = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
const idRegex = /^(?!(?:[0-9]+)$)([a-zA-Z]|[0-9a-zA-Z]){4,}$/;
const passwordNumberRegex = /[0-9]/g;
const passwordEnglishRegex = /[a-z]/gi;
const passwordSpecialCharacterRegex = /[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi;
const emailRegex = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/;
const $phoneInput = $('#member-general-join-phone-text');
const $addressInput = $('.address-input');
const $addressDetailInput = $('.address-detail-input');

let joinBlurMessages = [
    '아이디를 입력하세요.',
    '비밀번호를 입력하세요.',
    '비밀번호 확인을 위해 한번 더 입력하세요.',
    '이름을 입력하세요.',
    '이메일을 입력하세요.',
    '생년월일을 입력하세요.',
];
let joinRegexMessages = [
    '영문 혹은 영문과 숫자를 조합하여 4자~16자로 입력하세요.',
    '공백 제외 영어 및 숫자, 특수문자 모두 포함하여 6~16자로 입력하세요.',
    '위 비밀번호와 일치하지 않습니다. 다시 입력하세요.',
    '영문 혹은 한글로 2자이상 입력하세요.',
    '이메일 주소를 확인하세요.',
    '생년월일을 확인하세요.',
];
const $joinHelp = $('p.help');

let joinCheck;
let joinCheckAll = [false, false, false, false, false, false];

// $('.modal').hide();

$('#member-join-general-birthday-text').on('focus', function () {
    $(this).val($(this).val().replaceAll('.', ''));
});

$joinInputs.on('blur', function () {
    let i = $joinInputs.index($(this));
    let value = $(this).val();
    console.log(value + " 인덱스번호: " + i);

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
            joinCheck = emailRegex.test(value);
            break;
        case 5:
            joinCheck = birthRegex.test(value);
            if (joinCheck) {
                $(this).val(value.replace(/^(\d{4})(\d{2})(\d{2})$/, `$1.$2.$3`));
            }
            break;
    }

    joinCheckAll[i] = joinCheck;

    if (!joinCheck) {
        $joinHelp.eq(i).show();
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
                        $joinHelp.eq(i).text('중복된 아이디입니다.');
                    } else {
                        joinCheckAll[i] = true;
                        $joinHelp.eq(i).text('사용가능한 아이디입니다.');
                    }
                },
            });
        });
    } else if (i == 4) {
        console.log("이메일 중복체크에 들어옴?" + i);
        $.ajax({
            type: "get",
            url: "/checkUserEmail",
            data: {userEmail: value},
            success: function (result) {
                let message;
                if (result) {
                    message = "중복된 이메일입니다.";
                    $joinHelp.eq(i).show();
                    $joinHelp.eq(i).text(message);
                    joinCheckAll[i] = false;
                } else {
                    message = "사용가능한 이메일입니다.";
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
    } else if ($phoneInput.val() == '') {
        alertModal('휴대폰 번호를 입력하세요.');
    } else if ($addressInput.val() == '') {
        alertModal('주소를 입력하세요.');
    } else if ($addressDetailInput.val() == '') {
        alertModal('상세주소를 입력하세요.')
    }else {
        document.join.submit();
    }
}

function onClickMemberJoinCheckAll() {
    const checked = $('#member-join-general-agree-all-check').is(':checked');
    if (checked) {
        $('#member-join-general-agree-terms-check').prop('checked', true);
        $('#member-join-general-agree-user-info-check').prop('checked', true);
        $('#member-join-general-agree2-user-info-check').prop('checked', true);
        $('#member-join-general-agree-sms-check').prop('checked', true);
        $('#member-join-general-agree-email-check').prop('checked', true);
    } else {
        $('#member-join-general-agree-terms-check').prop('checked', false);
        $('#member-join-general-agree-user-info-check').prop('checked', false);
        $('#member-join-general-agree2-user-info-check').prop('checked', false);
        $('#member-join-general-agree-sms-check').prop('checked', false);
        $('#member-join-general-agree-email-check').prop('checked', false);
    }
}

function onClickMemberJoinCheckItem() {
    const termsChecked = $('#member-join-general-agree-terms-check').is(':checked');
    const userInfoChecked = $('#member-join-general-agree-user-info-check').is(':checked');
    const userInfoChecked2 = $('#member-join-general-agree2-user-info-check').is(':checked');
    const smsChecked = $('#member-join-general-agree-sms-check').is(':checked');
    const emailChecked = $('#member-join-general-agree-email-check').is(':checked');

    if (termsChecked && userInfoChecked && userInfoChecked2 && smsChecked && emailChecked) {
        $('#member-join-general-agree-all-check').prop('checked', true);
    } else {
        $('#member-join-general-agree-all-check').prop('checked', false);
    }
}

function onClickMemberJoinVerifyLoginIdBtn() {
    memberJoinStatus.isIdChecked = false;

    if (verifyMemberJoinLoginIdAjax()) {
        memberJoinStatus.isIdChecked = true;
    }
}

const $arrows = $('.arrow-0deg');
const $terms = $('.ui-slide-content');
$arrows.each((i, e) => {
    let $term = $terms.eq(i);
    $(e).click(function () {
        console.log($term);
        if ($(e).hasClass('arrow-0deg')) {
            $term.slideDown();
            $(e).removeClass('arrow-0deg');
            $(e).addClass('arrow-180deg');
        } else {
            $term.slideUp();
            $(e).removeClass('arrow-180deg');
            $(e).addClass('arrow-0deg');
        }
    });
});

function termsBtn() {
    if (!$('#member-join-general-agree-terms-check').is(':checked')) {
        $('#member-join-general-agree-terms-check-error').show().html('<p class="valid error">회원가입 약관에 동의해주세요.</p>');
    } else {
        $('#member-join-general-agree-terms-check-error').html('');
    }

    if (!$('#member-join-general-agree-user-info-check').is(':checked')) {
        $('#member-join-general-agree-user-info-check-error').show().html('<p class="valid error">개인정보처리방침에 동의해주세요.</p>');
    } else {
        $('#member-join-general-agree-user-info-check-error').html('');
    }

    if (
        $('#member-join-general-agree-terms-check').is(':checked') &&
        $('#member-join-general-agree-user-info-check').is(':checked')
    ) {
        $('.terms-btn-wrap button').removeClass('btn-dim');
        $('.terms-btn-wrap button').addClass('btn-primary');
    } else {
        $('.terms-btn-wrap button').addClass('btn-dim');
        $('.terms-btn-wrap button').removeClass('btn-primary');
    }
}

function termsBtnClick() {
    if ($('.terms-btn-wrap button').hasClass('btn-primary')) {
        // 이메일, SMS 수신 미동의 시 처리
        if (
            !$('#member-join-general-agree-email-check').is(':checked') ||
            !$('#member-join-general-agree-sms-check').is(':checked')
        ) {
            if (
                confirm(
                    '이메일, SMS 수신에 동의하지 않으시는 경우, 할인쿠폰의 발급 또는 이벤트 참여가 제한될 수 있습니다. 이메일, SMS 수신에 동의하시겠습니까?'
                )
            ) {
                $('#member-join-general-agree-sms-check').prop('checked', true);
                $('#member-join-general-agree-email-check').prop('checked', true);
            }
        }
        if (!$('#member-join-general-agree2-user-info-check').is(':checked')) {
            if (
                confirm(
                    '(선택) 개인정보 수집 및 이용 동의하지 않으시는 경우, 추천아이디를 제한될 수 있습니다. (선택) 개인정보 수집 및 이용 동의하시겠습니까?'
                )
            ) {
                $('#member-join-general-agree2-user-info-check').prop('checked', true);
            }
        }
        $('.terms-section').css('display', 'none');
        $('.join-container2').css('display', 'block');
        if (!$('#member-join-general-agree2-user-info-check').is(':checked')) {
            $('.reco-check').css('display', 'none');
        }
    }
}

// 휴대폰 인증 모달창
const $inputPhone = $('#member-general-join-phone-text');
const $modal = $('.layer-wrap');
const $modal2 = $('.layer-wrap2');
const $phoneBtn = $('.btn-basic-md');

$inputPhone.on('click', function () {
    $modal.css('display', 'block');
});

// 휴대폰 유효성 검사
$(document).ready(function () {
    $('#popup-member-join-certi-sms-phone-no-error').html('');
});

function nextModal() {
    $modal.css('display', 'none');
    $modal2.css('display', 'block');
}

function closeMemberJoinCertiSmsPop() {
    $modal.css('display', 'none');
}

function closeMemberJoinInputCertiNoPop() {
    $modal2.css('display', 'none');
}


/* 휴대폰 중복 검사, 인증번호 전송 및 확인 */
let phoneNo = '';

function onClickLoginCertiPopSendSms() {
    const phoneMid = $('#popup-member-join-certi-sms-mid-phone-no').val();
    const phonePostfix = $('#popup-member-join-certi-sms-post-phone-no').val();
    if (phoneMid.search(/^\d{3,4}$/) === -1) {
        $('#popup-member-join-certi-sms-phone-no-error').html(
            '<p class="valid error">중간 자리는 3자 또는 4자의 숫자입니다.</p>'
        );
        return;
    }

    if (phonePostfix.search(/^\d{4}$/) === -1) {
        $('#popup-member-join-certi-sms-phone-no-error').html(
            '<p class="valid error">끝 자리는 4자의 숫자입니다.</p>'
        );
        return;
    }
    phoneNo = '010' + '-' + phoneMid + '-' + phonePostfix;

    /* 입력한 휴대폰 번호로 가입된 정보가 있는지 확인 */
    $.ajax({
        type: "get",
        url: "/checkUserPhoneNumber",
        data: {userPhoneNumber: phoneNo},
        success: function (result) {
            console.log(result);
            if (result) {
                alertModal('중복된 번호입니다.');
                $modal.css('display', 'none');
                $('#popup-member-join-certi-sms-mid-phone-no').val("");
                $('#popup-member-join-certi-sms-post-phone-no').val("");
            }else{
                /* 중복된 휴대폰 번호가 없으면 인증번호 전송 */
                $.ajax({
                    type: "POST",
                    url: "/member/sendCode",
                    data: { memberPhone: phoneNo },
                    success: function(data) {
                        console.log(data);
                        code = data;
                    }
                });
                /* 다음 모달창으로 넘어감 */
                $("#phone-num").text(phoneNo);
                $('#popup-member-join-certi-sms-phone-no-error').html();
                nextModal();
            }
        }
    });
}

// 인증번호 체크

function setDimMemberJoinCertiNoBtn(isDim) {
    if (isDim) {
        if (!$('#member-join-input-certi-no-pop-input').hasClass('type-dim')) {
            $('#member-join-input-certi-no-pop-input').addClass('type-dim').prop('disabled', true);
        }
    } else {
        if ($('#member-join-input-certi-no-pop-input').hasClass('type-dim')) {
            $('#member-join-input-certi-no-pop-input').removeClass('type-dim').prop('disabled', false);
            $('#member-general-join-phone-text').attr('value', phoneNo);
        }
    }
}

function onKeyUpMemberJoinCertiNoText() {
    const certiNo = $('#member-join-input-certi-no-pop-text').val();
    let isDim = true;

    if (certiNo.length > 6) {
        $('#member-join-input-certi-no-pop-text').val(certiNo.substring(0, 6));
        setDimMemberJoinCertiNoBtn(false);
        return;
    }

    if (certiNo.search(/^\d{6}$/) === -1) {
        $('#popup-member-join-input-certi-no-pop-error').html(
            '<p class="valid error">6자리 숫자만 사용 가능합니다.</p>'
        );
        setDimMemberJoinCertiNoBtn(true);
        return;
    } else {
        setDimMemberJoinCertiNoBtn(false);
    }

    $('#popup-member-join-input-certi-no-pop-error').html('');
}

function onClickCertiNoComfirmBtn() {
    const certiNo = $('#member-join-input-certi-no-pop-text').val();

    if (certiNo.length > 6) {
        $('#member-join-input-certi-no-pop-text').val(certiNo.substring(0, 6));
        return;
    }

    if (!certiNo.match(/^\d{6}$/)) {
        $('#popup-member-join-input-certi-no-pop-error').html(
            '<p class="valid error">6자리 숫자만 사용 가능합니다.</p>'
        );
        return;
    }

    if (certiNo != code) {
        $('#popup-member-join-input-certi-no-pop-error').html(
            '<p class="valid error">인증번호를 확인하세요.</p>'
        );
        return;
    }

    //     getCertificationNoCallbackFunc = 'getCertificationNo';

    //     setSmsTalkCertifiedCallbackFunc = 'setSmsTalkCertified';

    // if (getCertificationNoCallbackFunc === '' || setSmsTalkCertifiedCallbackFunc === '') {
    //     alert("에러: callback is null");
    //     return;
    // }

    // if(fnCheckCertiNoAjax(certiNo) == false){
    //     return;
    // }

    $('#popup-member-join-input-certi-no-pop-error').html('');

    // setSmsTalkCertified(true);

    alertModal('인증이 완료되었습니다.');
    closeMemberJoinInputCertiNoPop();
}

// 휴대폰 인증하는 ajax인데 뭔가 쓸만 할 거 같아서 일단 가져옴
/* function fnCheckCertiNoAjax(certiNo){
  let isValid = false;

  cmAjax({
      url : '/member/join/checkCertiNoAjax'
      , type: "post"
      , data: {
          phoneNo: '010-1234-1234',
          certiNo: certiNo
      }
      , dataType: "json"
      , async : false
      , success : function(data) {
          if(data.status == 'succ'){
              isValid = true;
          }else{
              isValid = false;
              const alertMsg = (isEmpty(data.message) ? '잘못된 인증번호입니다.\n인증번호를 확인한 다음에 다시 입력해 주세요.' : data.message);
              alert(alertMsg.replace(/<br\/>/ig, '\n'));
              return;
          }
      }
      , error : function() {
          alert("시스템 오류입니다. 잠시후 다시 시도해주세요.");
      }
  });

  return isValid;
} */


/* 모달창 */

function alertModal(errorMsg) {
    $("div#content-wrap").html(errorMsg)
    $("div.modal").css("display", "flex").hide().fadeIn(500);
    setTimeout(function () {
        $("div.modal").fadeOut();
    }, 2000);
}


/* ======================================= 휴대폰 중복검사 및 인증 ==================================================   */
// else if (i == 3) {
// $(".join-phone-btn").click(function () {
//     $.ajax({
//         type: "get",
//         url: "/checkUserPhoneNumber",
//         data: {userPhoneNumber: phoneNo},
//         success: function (result) {
//             let message ="";
//             if (!result) {
//                 message = "중복된 휴대폰 번호입니다.";
//                 $joinHelp.eq(i).show();
//                 $joinHelp.eq(i).css('color', 'red')
//                 $joinInputs.eq(i).css('border', '1px solid rgb(255, 64, 62)');
//                 $joinHelp.eq(i).text(message);
//                 phoneNumberCheck = false;
//                 joinCheckAll[i] = false;
//             } else {
//                 message = "사용가능한 휴대폰 번호입니다.";
//                 $joinHelp.eq(i).show();
//                 $joinHelp.eq(i).css('color', 'blue');
//                 $joinInputs.eq(i).css('border', '1px solid rgb(255, 64, 62)');
//                 phoneNumberCheck = true;
//                 joinCheckAll[i] = true;
//                 let modalMessage = "인증번호가 전송되었습니다.";
//                 showWarnModal(modalMessage);
//                 $joinHelp.eq(i).hide();
//                 console.log(i);
//                 $joinInputs.eq(i).css('border', '1px solid #05AE68');
//                 phoneNumberCheck = true;
//                 joinCheckAll[i] = true;
//                 let phone = $(".join-phone").val().replaceAll("-", "");
//                 console.log(phone);
//                 $.ajax({
//                     type: "POST",
//                     url: "/member/sendCode",
//                     data: {memberPhone: phoneNo},
//                     success: function (data) {
//                         console.log(data);
//                         code = data;
//                     }
//                 });
//             }
//         }
//     });
// });
// }
