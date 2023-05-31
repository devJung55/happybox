/* 구독 결제 JS */

/* ============================================================================= */


$(".btn_update").on("click", function () {
    console.log($("#sample6_postcode").val());
});

/* =============================================================================== */

function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}

/* ==================================================================================== */
$(document).ready(function () {
    var maxPoint = 2000; // 사용 가능한 최대 포인트
    var inputPoint = 0; // 입력된 포인트

    // 전액 사용 버튼 클릭 이벤트
    $('.btn-ex-grey').on('click', function () {
        inputPoint = maxPoint; // 입력된 포인트를 최대 포인트로 설정
        $('#point').val(inputPoint); // input 태그에 입력된 포인트 출력
        $('.text-num-bold').text(0); // 사용 가능한 기부 포인트 초기화
    });

    // 입력된 포인트가 변경될 때마다 발생하는 이벤트
    $('#point').on('change keyup', function () {
        var point = parseInt($(this).val().replace(/,/g, '')); // 입력된 포인트를 정수형으로 변환
        if (!isNaN(point)) { // 입력된 값이 숫자인 경우
            if (point > maxPoint) { // 입력된 포인트가 최대 포인트보다 큰 경우
                point = maxPoint; // 최대 포인트로 설정
                $(this).val(point); // input 태그에 최대 포인트 출력
            }
            $('.text-num-bold').text(maxPoint - point); // 사용 가능한 기부 포인트 계산하여 출력
            inputPoint = point; // 입력된 포인트 저장
        } else { // 입력된 값이 숫자가 아닌 경우
            $('.text-num-bold').text(maxPoint); // 사용 가능한 기부 포인트 초기화
            inputPoint = 0; // 입력된 포인트 초기화
        }
    });

    // 삭제 버튼 클릭 이벤트
    $('.btn-input-del').on('click', function () {
        $('#point').val(''); // input 태그 내용 삭제
        $('.text-num-bold').text(maxPoint); // 사용 가능한 기부 포인트 초기화
        inputPoint = 0; // 입력된 포인트 초기화
    });

});


/* ====================================== 주소 불러오기  ============================================== */

const $address = $(".checkbox");


$address.change(function () {
    if ($(this).is(':checked')) {
        $.ajax({
            url: '/orders/purchaser/' + userDetail.id,
            type: 'get',
            data: {id: userDetail.id},
            success: function (response) {
                console.log(response);
                $("#memberName").val(response.memberName);
                $("#postcode").val(response.address.zipcode);
                $("#address").val(response.address.firstAddress);
                $("#detailAddress").val(response.address.addressDetail);
                $("#userPhoneNumber").val(response.userPhoneNumber);
            }
        });
    } else {
        $('#payment input').val("");
    }
});

$.ajax({
    url: '/orders/member/' + userDetail.id,
    type: 'get',
    data: {id: userDetail.id},
    success: function (response) {
        $("#purchaserName").html(response.memberName);
        $("#purchaserPhone").html(response.userPhoneNumber);
        $("#purchaserEmail").html(response.userEmail);
        if (response.userRole == "MEMBER") {
            $("#purchaserRole").html("일반회원");
        } else if (response.userRole == "WELFARE") {
            $("#purchaserRole").html("복지관회원");
        } else if (response.userRole == "DISTRIBUTOR") {
            $("#purchaserRole").html("유통회원");
        }
    }
});


/* ====================================== 장바구니 정보 불러오기  ============================================== */
const $carts = carts;
const $cartArea = $('.productCart-list');

console.log(carts);

function showCarts() {
    let text = "";
    $carts.forEach((cart) => {
    let filePath = "";
    filePath = cart.productFileDTO.filePath + '/t_' + cart.productFileDTO.fileUuid + '_' + cart.productFileDTO.fileOrgName;
        text = `
                <li>
                    <div class="prd-info-area">
                        <div class="inner">
                            <div class="column img">
                                <a href="javascript:void(0);">
                                    <img
                                            src="/image/display?fileName=${filePath}"
                                    />
                                </a>
                            </div>
                            <div class="column tit">
                                <p class="tit" style="color: rgb(31 94 211); font-weight: bold">${cart.productDTO.productName}</p>
                                <p class="desc" onkeyup="chkNumber(this)">${cart.cartOrderAmount} 개</p>
                                <ul class="price-item">
                                    <li>
                                            상품가격 : 
                                            <span class="num" onkeyup="chkNumber(this)">${cart.productDTO.productPrice}</span>
                                            원
                                    </li>
                                </ul>
                            </div>
                            <div class="column price w70">
                                <span class="num" id="price-list" onkeyup="chkNumber(this)">${cart.productDTO.productPrice * cart.cartOrderAmount}</span>
                                원
                            </div>
                        </div>
                    </div>
                </li>
        
                `
        $cartArea.append(text);
    })

}

showCarts();

const $totalPrice = $('.total-price');
let totalPrice = 0;

$carts.forEach((cart) => {
    totalPrice += cart.productDTO.productPrice * cart.cartOrderAmount;
})
$totalPrice.val(totalPrice);


/* ===================================================================================*/

// 천단위 콤마 (소수점포함)
function numberWithCommas(num) {
    let parts = num.toString().split(".");
    return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") + (parts[1] ? "." + parts[1] : "");
}

// 숫자 체크(숫자 이외 값 모두 제거)
function chkNumber(obj) {
    let tmpValue = $(obj).val().replace(/[^0-9,]/g, '');
    tmpValue = tmpValue.replace(/[,]/g, '');
    // 천단위 콤마 처리 후 값 강제변경
    obj.value = numberWithCommas(tmpValue);
}
