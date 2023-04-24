/* Welfare List */
// 마우스 오버시 이미지 바뀌기

$(document).ready(function() {
  var imageArray = [
    "https://file.rankingdak.com/image/RANK/PRODUCT/RANKING/20230302/IMG1677MBE747353820.jpg",
    "https://file.rankingdak.com/image/RANK/PRODUCT/RANKING/20230222/IMG1677fxq053395593.jpg",
    "https://file.rankingdak.com/image/RANK/PRODUCT/RANKING/20230222/IMG1677Cym053561204.jpg"
  ];
  var intervalId;

  function changeImage(element) {
    var index = 0;
    intervalId = setInterval(function() {
      $(element).attr('src', imageArray[index]);
      index = (index + 1) % imageArray.length;
    }, 1000);
  }

  $('.welfare-img img').each(function() {
    var originalSrc = $(this).attr('src');
    $(this).hover(
      function() {
        clearInterval(intervalId);
        changeImage(this);
      },
      function() {
        clearInterval(intervalId);
        $(this).attr('src', originalSrc);
      }
    );
  });
});

  /* 화면에 출력 */
const $welfareList = $('.welfare-list-wrap');

  function showList() {
    let text = `
                <li class="swiper-slide swiper-slide-active">
                  <div class="brand-list-item">
                      <div class="welfare-img" style="cursor: pointer;">
                              <img class="original-img"
                                  src="https://file.rankingdak.com/image/RANK/PRODUCT/BRAND_PC/20220410/IMG1649Uey592990027.jpg"
                                  alt="맛있닭"
                              />
                      </div>
                      <div class="txt-area">
                          <strong class="welfare-name text-elps">맛있닭</strong>
                          <p class="welfare-info text-elps">Life is Delicious!</p>
                          <span class="welfare-location text-elps"
                              >서울 관악구</span
                          >
                      </div>
                  </div>
                </li>
                `
                
    $welfareList.append(text);
  }

//   6개 출력
  for (let i = 0; i < 6; i++) {
        showList();
  }