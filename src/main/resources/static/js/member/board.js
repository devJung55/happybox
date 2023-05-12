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

function addList(){
    let text ="";
    text = `
    <article th:each="reviewDTO : ${list}" class="board-item-wrap">
                  <div class="profile-area">
                    <a href="javascript:void(0)"
                      ><div class="profile-wrap">
                        <div class="writer-image rw">
                          <img
                            src="/user-board/display?fileName=${review.files[0].reviewFilePath}/${review.files[0].reviewFileUuid}_${review.files[0].reviewFileOriginalName}"
                            alt=""
                          />
                        </div>
                        <div class="writer-info-wrap">
                          <div class="writer-info">
                            <div th:text="${reviewDTO.memberName}" class="writer-name"></div>
                            <span th:text="${reviewDTO.reviewBoardRegisterDate}" class="write-date">2023.04.21</span>
                          </div>
                          <div>
                            <div th:text="${reviewDTO.welfareName}" id="welfare-name">노원노인복지관</div>
                            <em class="rating-point">
                              <img
                                class="rating__point one"
                                src="/img/mypage/rating-pull.png"
                              />
                              <img
                                class="rating__point two"
                                src="/img/mypage/rating-pull.png"
                              />
                              <img
                                class="rating__point three"
                                src="/img/mypage/rating-pull.png"
                              />
                              <img
                                class="rating__point four"
                                src="/img/mypage/rating-pull.png"
                              />
                              <img
                                class="rating__point five"
                                src="/img/mypage/rating.png"
                              />
                            </em>
                          </div>
                        </div>
                      </div>
                    </a>
                  </div>
                  <a href="javascript:void(0)"
                    ><h3 th:text="${reviewDTO.reviewBoardTitle}" class="board-title">노원복지관 3개월 구독 후기!</h3>
                    <p th:text="${reviewDTO.reviewBoardContent}" class="board-content">
                      안녕하세요. 요리사정정짱입니다. 요즘 혼자 살다보니 아침 차려먹을 시간도 없고 그렇다고 매번 냉동식품만 먹자니 건강도 너무 안좋아지는게 느껴지더라구요 ㅠㅠ
                      저는 아침에 헬스장에 가기 전에 꼭 아침을 먹어야하기 때문에 아침을 정기적으로 배달시켜먹을 곳이 없나 찾아보다가 해피박스라는 서비슬 알게 되어서 한 번 도시락을 따로 시켜 먹어봤어요.
                      근데 정말 집밥 먹는 것처럼 너무 맛있고 든든하더라구요!! 그래서 여러 복지관에 도시락을 시켜 먹어보고 개인적으로 가장 맛있었던 노원복지관을 선택하게 되었어요!
                      벌써 3개월이나 지났는데 배달도 항상 제때 일찍 도착하고 너무 맛있고 영양분도 골고루 갖춰져 있어서 정말 최고입니다!!
                      아침에 준비하느라 시간 없는 직장인들에게 추천드립니다. 그리고 제가 레시피 게시판에 제육볶음 레시피 공유했으니까 좋아요와 댓글 부탁드릴게요 ^^
                    </p>
                    <picture>
                      <img
                        src="https://image.wanted.co.kr/optimize?src=https%3A%2F%2Fstatic.wanted.co.kr%2FImageTargetTypeEnum.COMMUNITY%2F2023%2F4%2Fcc499f657b5f0ace6f13f6a3e1d0926e0aac8fa0afe4168b85d11dfa251dd359&amp;w=310&amp;q=90"
                        class="board-image"
                    /></picture>
                    <div class="board-item-bottom">
                      <div class="recommend-btn">
                        <svg width="18" height="18" viewBox="0 0 18 18">
                          <path
                            fill="currentColor"
                            d="M13.353 2.214c.082.164.15.332.204.502.325 1.032.13 2.08-.396 3.092l-.105.191L16.253 6a.75.75 0 0 1 .743.648l.007.102v5.75a.75.75 0 0 1-.106.385l-.058.084-3.004 3.75a.75.75 0 0 1-.472.273L13.25 17H9.22a.75.75 0 0 1-.101-1.493l.102-.007h3.668l2.614-3.264V7.5h-3.91a.75.75 0 0 1-.604-1.195l.066-.077c.137-.14.36-.415.584-.778.5-.808.702-1.6.487-2.283a1.858 1.858 0 0 0-.113-.278c-.278-.551-1.075-.442-1.075-.056a3.17 3.17 0 0 1-.777 2.125c-.293.338-.59.555-.774.647l-.472.292c-.89.568-1.459 1.04-1.762 1.409l-.097.128-.058.095v.062l-.004.016-.006.093a.75.75 0 0 1-.641.641l-.102.007-.102-.007a.75.75 0 0 1-.648-.743V7.5H2.496v8h2.999l-.001-4.535.007-.102a.75.75 0 0 1 1.493.102v5.286l-.007.102a.75.75 0 0 1-.743.648H1.747l-.102-.007a.75.75 0 0 1-.648-.743v-9.5l.007-.102A.75.75 0 0 1 1.747 6h4.498l.066.005c.387-.38.92-.796 1.621-1.256l.472-.3.253-.154c.07-.035.217-.143.37-.32.226-.26.37-.576.403-.969l.008-.173c0-2.082 2.972-2.491 3.915-.619z"
                          ></path></svg
                        ><span th:text="${reviewDTO.reviewLikeCount}" class="recommend-count"></span>
                      </div>
                      <div class="reply-btn">
                        <svg width="18" height="18" viewBox="0 0 18 18">
                          <path
                            fill="currentColor"
                            transform="matrix(-1 0 0 1 18 0)"
                            d="M9 1c4.377 0 8 3.14 8 7s-3.623 7-8 7c-.317 0-.593-.026-.954-.088l-.395-.074-.205-.043-3.295 2.089a.75.75 0 0 1-.968-.143l-.067-.09a.75.75 0 0 1 .143-.968l.09-.067 3.55-2.25a.75.75 0 0 1 .551-.1l.652.132.301.052c.228.036.408.05.597.05 3.592 0 6.5-2.52 6.5-5.5S12.592 2.5 9 2.5C5.407 2.5 2.5 5.02 2.5 8c0 1.858 1.039 3.573 2.773 4.348a.75.75 0 1 1-.612 1.37C2.37 12.693 1 10.432 1 8c0-3.86 3.622-7 8-7z"
                          ></path></svg
                        ><span th:text="${reviewDTO.reviewBoardReplyCount}" class="reply-count"></span>
                      </div>
                    </div></a
                  >
                </article>
    `
}