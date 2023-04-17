/* Welfare List */
// 마우스 오버시 이미지 바뀌기
  function changeImg(selector, newSrc) {
    var $imageElements = $(selector);
    
    $imageElements.each(function() {
      var $imageElement = $(this);
      var originalSrc = $imageElement.attr('src');
      
      $imageElement.mouseover(function() {
        $imageElement.attr('src', newSrc);
      }).mouseout(function() {
        $imageElement.attr('src', originalSrc);
      });
    });
  }

  changeImg('.welfare-img img','https://file.rankingdak.com/image/RANK/PRODUCT/PRD001/20220831/IMG1661yAg937657633_330_330.jpg');


  /* 화면에 출력 */
const $welfareList = $('.welfare-list-wrap');

  function showList() {
    let text = `
                        <li class="swiper-slide swiper-slide-active">
                            <div class="brand-list-item">
                                <div class="welfare-img">
                                    <a href="">
                                        <img
                                            src="https://file.rankingdak.com/image/RANK/PRODUCT/BRAND_PC/20220410/IMG1649Uey592990027.jpg"
                                            alt="맛있닭"
                                        />
                                    </a>
                                </div>
                                <div class="txt-area">
                                    <strong class="welfare-name text-elps">맛있닭</strong>
                                    <p class="welfare-info text-elps">Life is Delicious!</p>
                                    <span class="welfare-location text-elps">서울 관악구</span>
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