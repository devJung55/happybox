/* welfare-join.html */
/* company-join.html */

window.onload = function(){
    document.getElementById("address-kakao").addEventListener("click", function(){ //주소입력칸을 클릭하면
        //카카오 지도 발생
        new daum.Postcode({
            oncomplete: function(data) { //선택시 입력값 세팅
                document.getElementById("address-kakao").value = data.address; // 주소 넣기
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
const phoneRegex = /^\d{2,3}-?\d{3,4}-?\d{4}$/;
const idRegex = /^(?!(?:[0-9]+)$)([a-zA-Z]|[0-9a-zA-Z]){4,}$/;
const passwordNumberRegex = /[0-9]/g;
const passwordEnglishRegex = /[a-z]/gi;
const passwordSpecialCharacterRegex = /[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi;
const emailRegex = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/;
const $addressInput = $('.address-input');
const $addressDetailInput = $('.address-detail-input');

let joinBlurMessages = [
  '아이디를 입력하세요.',
  '비밀번호를 입력하세요.',
  '비밀번호 확인을 위해 한번 더 입력하세요.',
  '복지관명을 입력하세요.',
  '전화번호를 입력하세요.',
  '상세주소를 입력하세요.',
  '이메일 주소를 입력하세요.',
];
let joinRegexMessages = [
  '영문 혹은 영문과 숫자를 조합하여 4자~16자로 입력하세요.',
  '공백 제외 영어 및 숫자, 특수문자 모두 포함하여 6~16자로 입력하세요.',
  '위 비밀번호와 일치하지 않습니다. 다시 입력하세요.',
  '복지관명을 확인하세요.',
  '전화번호를 확인하세요.',
  '이메일 주소를 확인하세요.',
];
const $joinHelp = $('p.help');

let joinCheck;
let joinCheckAll = [false, false, false, false, false, false, false];

// $('.modal').hide();


// /* 인증번호 입력 버튼 */
// $(".join-check-btn").on("click", function(){
//   if($(".join-check").val() == code){
//       let modalMessage = "인증이 완료되었습니다.";
//       showWarnModal(modalMessage);
//       console.log(joinCheck);
//       joinCheck = true;
//       return;
//   }
//   joinCheck = false;
// });

$joinInputs.on('blur', function () {
  let i = $joinInputs.index($(this));
  let value = $(this).val();
  console.log(value);

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
      if(joinCheck){
        $(this).val(value.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`));
      }
      break;
    case 5:
      joinCheck = emailRegex.test(value);
  }

  joinCheckAll[i] = joinCheck;

  if (!joinCheck) {
    $joinHelp.eq(i).show();
    $joinHelp.eq(i).text(joinRegexMessages[i]);
    return;
  }

  // 아이디 중복 검사
  // if (i == 0) {
  //   '#btn-id-check'.click(function () {
  //     $.ajax({
  //       type: 'POST',
  //       url: '/member/checkId',
  //       data: { memberIdentification: value },
  //       success: function (result) {
  //         $joinHelp.eq(i).show();
  //         if (result != 'success') {
  //           joinCheckAll[i] = false;
  //           $joinHelp.eq(i).text('중복된 아이디입니다.');
  //         } else {
  //           joinCheckAll[i] = true;
  //           $joinHelp.eq(i).text('사용가능한 아이디입니다.');
  //         }
  //       },
  //     });
  //   });
  // } else if (i == 4) {
  //     $(".join-phone-btn").click(function () {
  //         $.ajax({
  //             type: "POST",
  //             url: "/member/checkPhone",
  //             data: {memberPhone: value.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`)},
  //             success: function (result) {
  //                 let message;
  //                 if (result != "success") {
  //                     message = "중복된 휴대폰 번호입니다.";
  //                     $joinHelp.eq(i).show();
  //                     $joinHelp.eq(i).css('color', 'red')
  //                     $joinInputs.eq(i).css('border', '1px solid rgb(255, 64, 62)');
  //                     phoneNumberCheck = false;
  //                     joinCheckAll[i] = false;
  //                 } else {
  //                     let modalMessage = "인증번호가 전송되었습니다.";
  //                     showWarnModal(modalMessage);
  //                     $joinHelp.eq(i).hide();
  //                     console.log(i);
  //                     $joinInputs.eq(i).css('border', '1px solid #05AE68');
  //                     phoneNumberCheck = true;
  //                     joinCheckAll[i] = true;
  //                     let phone = $(".join-phone").val().replaceAll("-", "");
  //                     console.log(phone);
  //                     $.ajax({
  //                         type: "POST",
  //                         url: "/member/checkSms",
  //                         data: {memberPhone: phone},
  //                         success: function(data) {
  //                             console.log(data);
  //                             code = data;
  //                         }
  //                     });
  //                 }
  //                 $joinHelp.eq(i).text(message);
  //             }
  //         });
  //     });
  // } else if (i == 6) {
  //     $.ajax({
  //         type: "POST",
  //         url: "/member/checkNickname",
  //         data: {memberNickname: value},
  //         success: function (result) {
  //             let message;
  //             if (result != "success") {
  //                 message = "중복된 닉네임입니다.";
  //                 $joinHelp.eq(i).show();
  //                 $joinHelp.eq(i).css('color', 'red')
  //                 $joinInputs.eq(i).css('border', '1px solid rgb(255, 64, 62)');
  //                 joinCheckAll[i] = false;
  //             } else {
  //                 joinCheckAll[i] = true;
  //             }
  //             $joinHelp.eq(i).text(message);
  //         }
  //     });
  // }   else if (i == 7) {
  //     $.ajax({
  //         type: "POST",
  //         url: "/member/checkEmail",
  //         data: {memberEmail: value},
  //         success: function (result) {
  //             let message;
  //             if (result != "success") {
  //                 message = "중복된 이메일입니다.";
  //                 $joinHelp.eq(i).show();
  //                 $joinHelp.eq(i).css('color', 'red')
  //                 $joinInputs.eq(i).css('border', '1px solid rgb(255, 64, 62)');
  //                 joinCheckAll[i] = false;
  //             } else {
  //                 joinCheckAll[i] = true;
  //             }
  //             $joinHelp.eq(i).text(message);
  //         }
  //     });
  // }

  $joinHelp.eq(i).hide();
});

function send() {
    if (joinCheckAll.filter((check) => check).length != $joinInputs.length) {
      alertModal('가입 정보를 확인하세요.');
    } else if ($addressInput.val() == '') {
      alertModal('주소를 입력하세요.');
    } else if($addressDetailInput.val() == ''){
      alertModal('상세주소를 입력하세요.')
    } 
  }

  const $alertModal = $('.alert-modal-display');
function alertModal(errorMsg) {
  $(".alert").text(errorMsg);
  $alertModal.fadeIn();
  setTimeout(function () {
      $alertModal.fadeOut();
  }, 2000);
}