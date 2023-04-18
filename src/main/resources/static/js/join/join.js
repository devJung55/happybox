/* join.html */

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
  const $arrows = $(".arrow-0deg");
  const $terms = $('.ui-slide-content');
  $arrows.each((i, e) =>{
    let $term = $terms.eq(i);
      $(e).click(function(){
        console.log($term);
        if($(e).hasClass('arrow-0deg')){
            $term.slideDown();
            $(e).removeClass("arrow-0deg");
            $(e).addClass("arrow-180deg");
        } else {
            $term.slideUp();
            $(e).removeClass("arrow-180deg");
            $(e).addClass("arrow-0deg");
        }
      })
  });


  

  /* 이메일 selectBox */

  const selectBox = document.querySelector('.ui-select.select-box');
const selectList = document.querySelector('.select-list');
const selectListItems = selectList.querySelectorAll('li');

// selectBox 클릭 시 on 클래스 추가
selectBox.addEventListener('click', function() {
  if (this.classList.contains('on')) {
    this.classList.remove('on');
  } else {
    this.classList.add('on');
  }
});

// selectListItems 클릭 시 on 클래스 제거 및 텍스트 변경
selectListItems.forEach(function(item) {
  item.addEventListener('click', function() {
    const spanText = this.querySelector('span').textContent;
    selectBox.querySelector('.select-value span').textContent = spanText;
    selectBox.dataset.value = this.dataset.name || '';
  });
});

// selectList 닫기 버튼 클릭 시 on 클래스 제거
selectList.addEventListener('mouseleave', function() {
  selectBox.classList.remove('on');
});


  function termsBtn() {
    if (!$('#member-join-general-agree-terms-check').is(':checked')) {
      //$('#member-join-general-agree-terms-check-error').html('<p class="valid error">회원가입 약관에 동의해주세요.</p>');
    } else {
      //$('#member-join-general-agree-terms-check-error').html('');
    }

    if (!$('#member-join-general-agree-user-info-check').is(':checked')) {
      //$('#member-join-general-agree-user-info-check-error').html('<p class="valid error">개인정보처리방침에 동의해주세요.</p>');
    } else {
      //$('#member-join-general-agree-user-info-check-error').html('');
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
const $phoeBtn = $('.btn-basic-md');


$inputPhone.on('click', function(){
    $modal.css('display', 'block');
})


  // 휴대폰 유효성 검사
  $(document).ready(function () {
    $('#popup-member-join-certi-sms-phone-no-error').html('');
});

function nextModal(){
    $modal.css('display', 'none');
    $modal2.css('display', 'block');
}

function closeMemberJoinCertiSmsPop() {
    $modal.css('display', 'none');
}

function closeMemberJoinInputCertiNoPop() {
    $modal2.css('display', 'none');
}

function onClickLoginCertiPopSendSms() {
  const phonePrefix = $('#popup-member-join-certi-sms-pre-phone-no').data('value');
  const phoneMid = $('#popup-member-join-certi-sms-mid-phone-no').val();
  const phonePostfix = $('#popup-member-join-certi-sms-post-phone-no').val();
    if (phoneMid.search(/^\d{3,4}$/) === -1) {
        $('#popup-member-join-certi-sms-phone-no-error').html('<p class="valid error">중간 자리는 3자 또는 4자의 숫자입니다.</p>');
        return;
    }

    if (phonePostfix.search(/^\d{4}$/) === -1) {
        $('#popup-member-join-certi-sms-phone-no-error').html('<p class="valid error">끝 자리는 4자의 숫자입니다.</p>');
        return;
    }

    $('#popup-member-join-certi-sms-phone-no-error').html('');

    const phoneNo = phonePrefix + '-' + phoneMid + '-' + phonePostfix;
   
    nextModal();
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
      $('#popup-member-join-input-certi-no-pop-error').html('<p class="valid error">6자리 숫자만 사용 가능합니다.</p>');
      setDimMemberJoinCertiNoBtn(true);
      return;
  } else {
      setDimMemberJoinCertiNoBtn(false);
  }

  $('#popup-member-join-input-certi-no-pop-error').html('');
}

function onClickCertiNoComfirmBtn() {
  const certiNo = $('#member-join-input-certi-no-pop-text').val();
  // 임시 인증번호
  const code = 123456;

  if (certiNo.length > 6) {
      $('#member-join-input-certi-no-pop-text').val(certiNo.substring(0, 6));
      return;
  }

  if (!certiNo.match(/^\d{6}$/)) {
      $('#popup-member-join-input-certi-no-pop-error').html('<p class="valid error">6자리 숫자만 사용 가능합니다.</p>');
      return;
  }

  if(certiNo != code){
    $('#popup-member-join-input-certi-no-pop-error').html('<p class="valid error">인증번호를 확인하세요.</p>');
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
  

  alert('인증이 완료되었습니다.');
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