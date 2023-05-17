/* find-id.html */
$('.confirm-id-layout').hide();
let step = 1;

const $radios = $("input[name='find-member-pw-radio']");
const $phoneInput = $('.phone-input');
const $phoneCodeInput = $('#certiNoPhoneArea');
const $emailInput = $('.email-input');
const $emailCodeInput = $('#certiNoEmailArea');
const $phoneBtn = $('#find-member-pw-certi-phone-btn');
const $emailBtn = $('#find-member-pw-certi-email-btn');
const phoneRegex = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
let code;
const emailRegex = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/;

$(document).ready(function(){

    $radios.change(function(){
        // 휴대폰 번호로 찾기 선택 시.
        if($("input[name='find-member-pw-radio']:checked").val() == 'phone'){
            $('#find-member-pw-input-group').addClass('active');
            $('#find-member-pw-email-input-group').removeClass('active');
        }
        // 이메일로 찾기 선택 시.
        else if($("input[name='find-member-pw-radio']:checked").val() == 'email'){
            $('#find-member-pw-email-input-group').addClass('active');
            $('#find-member-pw-input-group').removeClass('active');
        }
    });

});

function toolbox(){
    $('.tooltip-wrap').css('display', 'block');
}

function hide(){
    $('.tooltip-wrap').css('display', 'none');
};

/* 전화번호를 적고 정규식을 통과하면 버튼 disable 해제 */
$phoneInput.keyup(function(){
    if(phoneRegex.test($phoneInput.val())){
        $phoneBtn.addClass('btn-primary');
        $phoneBtn.removeClass('btn-dim');
        $phoneBtn.attr('disabled', false);
        return;
    }
    $phoneBtn.addClass('btn-dim');
    $phoneBtn.removeClass('btn-primary');
    $phoneBtn.attr('disabled', true);
});

/* 전화번호 입력 하면 자동으로 - 넣어주기 */
$phoneInput.on('blur', function () {
    let value = $(this).val();
    $(this).val(value.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`));
})

/* 인증번호 받기 버튼을 누르면 나오는 효과 */
$phoneBtn.on('click', function(){
    $phoneCodeInput.css('display', 'block');
    $phoneBtn.hide();
    $('#find-member-pw-phone-btn').show();

    /* 해당 전화번호로 인증 번호 전송 */
    let phone = $phoneInput.val().replaceAll("-", "");
    console.log(phone);
    $.ajax({
        type: "POST",
        url: "/member/sendCode",
        data: { memberPhone: phone },
        success: function(data) {
            console.log(data);
            code = data;
        }
    });

});

/* 6자리의 인증번호를 입력하면 확인 버튼 활성화 */
$('.phone-code-input').keyup(function(){
    if($('.phone-code-input').val().length == 6){
        $('#find-member-pw-phone-btn').addClass('btn-primary');
        $('#find-member-pw-phone-btn').removeClass('btn-dim');
        $('#find-member-pw-phone-btn').attr('disabled', false)
        return;
    }
    $('#find-member-pw-phone-btn').removeClass('btn-primary');
    $('#find-member-pw-phone-btn').addClass('btn-dim');
    $('#find-member-pw-phone-btn').attr('disabled', true)
});

/* 확인 버튼을 누르면 인증번호 보냄 */
function goCheck() {
    step = 2;

    $('.find-id-layout').hide();
    $('.confirm-id-layout').show();

    /* 전화번호로 확인한 결과가 나오는 창으로 스크롤바가 맨 위쪽으로 가는 애니메이션 */
    $([document.documentElement, document.body]).animate(
        {
            scrollTop: 0,
        },
        300
    );
}

/* 인증번호 입력 후 아이디 검사 */
let identification = "";
let noId = "고객님의 아이디가 존재하지 않습니다"

$("#find-member-pw-phone-btn").on("click", function() {
    let phone = $phoneInput.val();
    console.log(phone)
    $.ajax({
        type: "POST",
        url: "/member/find-id",
        data: { memberPhone: phone },
        success: function(data) {
            identification = "[" + data + "]";
            if(data == ""){
                $(".message").html(noId);
            }else{
                $(".text-primary").html(identification);
            }
        }
    });
});



$emailInput.keyup(function(){
    if(emailRegex.test($emailInput.val())){
        $emailBtn.addClass('btn-primary');
        $emailBtn.removeClass('btn-dim');
        return;
    }
    $emailBtn.addClass('btn-dim');
    $emailBtn.removeClass('btn-primary');
});

$emailBtn.on('click', function(){
    $emailCodeInput.css('display', 'block');
    $emailBtn.hide();
    $('#find-member-pw-email-btn').show();

});

$('.email-code-input').keyup(function(){
    if($('.email-code-input').val().length == 6){
        $('#find-member-pw-email-btn').addClass('btn-primary');
        $('#find-member-pw-email-btn').removeClass('btn-dim');
        return;
    }
});
