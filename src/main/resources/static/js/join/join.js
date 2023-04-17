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