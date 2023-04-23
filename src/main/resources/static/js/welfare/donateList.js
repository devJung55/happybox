/* ===============기부 리스트 =============================*/
/* 기부순, 최신순 버튼 이벤트 */
$(".sort-btn").on('click', function(e){
    e.preventDefault();

    // 현재 클릭한 요소의 하위 .tit-type 요소에만 active 클래스 추가
    $(this).find('.tit-type').addClass('active').end().siblings().find('.tit-type').removeClass('active');

    // 모든 .tit-type 요소에서 active 클래스 제거
    $(".tit-type").not($(this).find('.tit-type')).removeClass("active");
});


/* 랭킹 번호 작성해주는 이벤트 */
$("ol li").each(function(index) {
    var rankNum = $(this).find(".rank-num");
    rankNum.text(index + 1);
    
    if (index < 5) {
      rankNum.css("border-bottom", "1px solid #666");
    } else {
      rankNum.css("border-bottom", "none");
    }
  });


  /*  사진 마우스오버시 스케일 커지는 이벤트 */
  $(function() {
    $('.column.img img').on('mouseover', function() {
      $(this).css('transform', 'scale(1.2)');
    }).on('mouseout', function() {
      $(this).css('transform', 'scale(1)');
    });
  });