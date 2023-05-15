/* ======================== Boot Pay =============================================*/

let storageTitle = [[${storageTitle}]];
let priceCount = "10";
let TotalPrice = "1000";
let storageId = [[${storageId}]];

const APIKEY = "64619d883049c8001d9686b3";

/*부트페이 결제 API*/
$('#goPayment').on('click', function () {
    BootPay.request({
        price: TotalPrice, //실제 결제되는 가격

        // 관리자로그인 -> 결제설치 -> 인증키 및 보안 -> WEB Application ID
        application_id: APIKEY,

        name: '(주)HappyBox', //결제창에서 보여질 이름
        pg: '',
        method: 'card', //결제수단, 입력하지 않으면 결제수단 선택부터 화면이 시작합니다.
        show_agree_window: 0, // 부트페이 정보 동의 창 보이기 여부
        items: [
            {
                item_name: storageTitle, //상품명
                qty: priceCount, //수량
                unique: '123', //해당 상품을 구분짓는 primary key
                price: TotalPrice, //상품 단가
            }
        ],
        order_id: '고유order_id_1234', //고유 주문번호로, 생성하신 값을 보내주셔야 합니다.
    }).error(function (data) {
        //결제 진행시 에러가 발생하면 수행됩니다.
        console.log(data);
    }).cancel(function (data) {
        //결제가 취소되면 수행됩니다.
        const container = document.querySelector(".modal");
        const close = document.querySelector(".pay-popup-check");

        //모달창 열기
        container.style.display = "block";

        //모달창 닫기
        close.addEventListener("click", function () {
            container.style.display = "none";
        });
        console.log(data);

    }).close(function (data) {
        // 결제창이 닫힐때 수행됩니다. (성공,실패,취소에 상관없이 모두 수행됨)
        console.log(data);
    }).done(function (data) {
        //결제가 정상적으로 완료되면 수행됩니다
        //비즈니스 로직을 수행하기 전에 결제 유효성 검증을 하시길 추천합니다.
        $.ajax({
            url: "/pay/payRegister", // 컨트롤러 주소
            type: "POST",
            data: {paymentAmount: paymentAmount, storageId: storageId}, // 결제정보 객체 paymetnVO
            success: function (result) {
                location.href = result;
            },
            error: function (error) {
                console.log(error);
            }
        });
        console.log(data);
    });
});


