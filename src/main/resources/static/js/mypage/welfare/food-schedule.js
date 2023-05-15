const $more = $('.food-write-btn');
const $delete = $('.delete-btn');

$delete.on('click', function() {
    $(this).closest('tr').next('.attach-file-line').remove(); // 첨부파일 영역 삭제
    $(this).closest('tr').remove(); // 해당 행 삭제
    foodCount--; // 음식 개수 1 감소
    if (foodCount < 9) { // 음식 개수가 9보다 작으면 버튼 다시 표시
      $more.css('display', 'block');
    }
  });
  
  $(document).on('click', '.delete-btn', function() {
    $(this).closest('tr').next('.attach-file-line').remove(); // 첨부파일 영역 삭제
    $(this).closest('tr').remove(); // 해당 행 삭제
    foodCount--; // 음식 개수 1 감소
    if (foodCount < 9) { // 음식 개수가 9보다 작으면 버튼 다시 표시
      $more.css('display', 'block');
    }
  });



const text = 
                `
                <tr>
                    <th class="text-left" scope="row">
                        <div class="in-tb">
                            음식 이름 <em class="es"><span class="blind"></span></em>
                        </div>
                        <div class="delete-btn">
                            <img src="../../../static/img/welfare/delete-btn.png" alt="">
                        </div>
                    </th>
                    <td class="text-left">
                        <div class="input-group w-full">
                            <input type="text" name="" id="vQuestionTitle" title="" class="food-name" placeholder="음식 이름을 적어주세요" value=""/>
                        </div>
                    </td>
                </tr>

                <!-- 첨부파일 영역 -->
                <tr class="attach-file-line">
                    <th class="text-left" scope="row"></th>
                    <td class="text-left">
                        <div class="file-add-boxes" style="display: inline-block;">

                            <div class="div-attach-thumb" id="US_RV_IMG1_attachThumb">
                                <button type="button" class="btn-attach-thumb">
                                    <i class="ico-plus-xlg"></i><span class="blind">파일첨부</span>
                                </button>
                                <input class="input_file" name="imgFile" type="file" accept="image/*" style="display: none"/>
                                <div class="attach-img" style="display: none;">
                                    <img src="" style="width: 68px; height: 68px"/>
                                    <button type="button" class="btn-x-xs2 btn_del">
                                        <i class="ico-x-white"></i><span class="blind">삭제</span>
                                    </button>
                                </div>
                            </div>
                            <p class="text-guide-md">
                                - 최대 15MB 이하의 JPG, PNG, GIF, BMP 파일 첨부 가능합니다.
                            </p>
                        </div>
                    </td>
                </tr>
                `;

var foodCount = 0; // 음식 개수 초기값 0으로 설정
$more.on('click', function() {
    if (foodCount < 9) { // 음식 개수가 9보다 작으면 append 실행
    $('.more-food').append(text);
    foodCount++; // 음식 개수 1 증가
    }
    if(foodCount == 8){
        $more.css('display','none');
    }
});

$delete.on('click', function() {
    $(this).closest('tr').next('.attach-file-line').remove(); // 첨부파일 영역 삭제
    $(this).closest('tr').remove(); // 해당 행 삭제
    foodCount--; // 음식 개수 1 감소
    if (foodCount < 9) { // 음식 개수가 9보다 작으면 버튼 다시 표시
      $more.css('display', 'block');
    }
  });

