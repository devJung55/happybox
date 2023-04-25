/* recipe-board.html */
/* review-board.html */

$(".sort-btn").on('click', function(e){
    e.preventDefault();

    // 현재 클릭한 요소의 하위 .tit-type 요소에만 active 클래스 추가
    $(this).find('.tit-type').addClass('active').end().siblings().find('.tit-type').removeClass('active');

    // 모든 .tit-type 요소에서 active 클래스 제거
    $(".tit-type").not($(this).find('.tit-type')).removeClass("active");
});


$(document).ready(function() {
    // 최신순 클릭 이벤트
    $('.last-pop-btn a:first-child').click(function() {
      // 최신순에 'on' 클래스 추가, 인기순에서 'on' 클래스 제거
      $(this).addClass('on');
      $(this).siblings().removeClass('on');
    });
  
    // 인기순 클릭 이벤트
    $('.last-pop-btn a:last-child').click(function() {
      // 인기순에 'on' 클래스 추가, 최신순에서 'on' 클래스 제거
      $(this).addClass('on');
      $(this).siblings().removeClass('on');
    });
  });