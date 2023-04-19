/* 복지관 상세페이지 */

/* 하트 클릭 이벤트 */
$('.like-btn').on('click', function () {
    var $image = $(this).find('img');
    var src = $image.attr('src');
    var activeSrc = '../../static/img/mypage/heart-pull.png';
    var defaultSrc = '../../static/img/mypage/heart.png';

    if (src === defaultSrc) {
        $image.attr('src', activeSrc);
    } else {
        $image.attr('src', defaultSrc);
    }
});

/* Radio 버튼활성화 이벤트 */
/* const $radioLabels = $('.custom-radio label');

$radioLabels.on('click', function() {
  const $clickedRadio = $(this).prev('input[type="radio"]');
  $radioLabels.siblings('input[type="radio"]').prop('checked', false);
  $clickedRadio.prop('checked', true);
});

$radioLabels.filter(':has(input[type="radio"]:checked)').trigger('click'); */


/* Radio 버튼 활성화 및, 선택된 가격으로 총 상품 가격으로 선택 */
const $radioLabels = $('.custom-radio label');
const $orderNormalTotalPrice = $('.orderNormalTotalPrice');

$radioLabels.on('click', function() {
  const $clickedRadio = $(this).prev('input[type="radio"]');
  const $totalPrice = $('.total-price');
  const $specialTotalPrice = $('.special-total-price');
  let selectedPrice = '';

  $radioLabels.siblings('input[type="radio"]').prop('checked', false);
  $clickedRadio.prop('checked', true);

  if ($clickedRadio.parent().text().trim() === '일반회원가') {
    $totalPrice.show();
    selectedPrice = $totalPrice.text();
  } else if ($clickedRadio.parent().text().trim() === 'Happy') {
    $specialTotalPrice.show();
    selectedPrice = $specialTotalPrice.text();
  }

  $orderNormalTotalPrice.html(selectedPrice);
});

$radioLabels.filter(':has(input[type="radio"]:checked)').trigger('click');
