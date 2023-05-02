/* login.html */

/* 모달창 */

function alertModal(errorMsg) {
    $("div#content-wrap").html(errorMsg)
    $("div.modal").css("display", "flex").hide().fadeIn(500);
    setTimeout(function () {
      $("div.modal").fadeOut();
  }, 2000);
  }

function send() {
    /* 성공했을 시 id와 password를 DB와 비교해서 전송해줘야함 */
      alertModal('아이디 및 비밀번호를 확인하세요.');
  }

