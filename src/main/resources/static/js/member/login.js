/* login.html */

/* 모달창 */

const $alertModal = $('.alert-modal-display');
function alertModal(errorMsg) {
  $(".alert").text(errorMsg);
  $alertModal.fadeIn();
  setTimeout(function () {
      $alertModal.fadeOut();
  }, 2000);
}

function send() {
    /* 성공했을 시 id와 password를 DB와 비교해서 전송해줘야함 */
      alertModal('아이디 및 비밀번호를 확인하세요.');
  }

