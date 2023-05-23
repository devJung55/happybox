/* find-id.html */
// $('.confirm-id-layout').hide();
let step = 1;

const $radios = $("input[name='find-member-pw-radio']");
const $phoneInput = $('.phone-input');
const $phoneCodeInput = $('#certiNoPhoneArea');
const $emailInput = $('.email-input');
const $emailCodeInput = $('#certiNoEmailArea');
const $phoneBtn = $('#find-member-pw-certi-phone-btn');
// 아이디 찾기에서 이메일 버튼
const $IdEmailBtn = $('#find-member-id-certi-email-btn');

// 인증번호 입력창
const $confirmCode = $('#find-member-pw-certi-no-text');

// 비밀번호 찾기에서 이메일 받는 버튼
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
    $('.find-pw-layout').hide();
    $('.change-pw-layout').show();

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
    let confirmCode = $confirmCode.val();
    let phone = $phoneInput.val();
    if (confirmCode == code) {
        $.ajax({
            type: "POST",
            url: "/find-id-phone",
            data: { userPhone: phone },
            success: function(data) {
                identification = "[" + data + "]";
                if(data == ""){
                    $(".message").html(noId);
                }else{
                    $(".text-primary").html(identification);
                }
                goCheck();
            }
        });
    } else { alertModal(); }
});

/* 모달창 */
function alertModal() {
    $("div.modal").css("display", "flex").hide().fadeIn(500);
    setTimeout(function () {
        $("div.modal").fadeOut();
    }, 2000);
}


/* 해당 전화번호로 이메일 조회할 수 있도록 하기, 위에랑 겹처서 따로 뺌 */
$('.reset-password').on('click', function () {
    let phone = $phoneInput.val();
    $.ajax({
        url: "/find-email-phone",
        type: "post",
        data: { userPhone: phone },
        success: function(data) {
            $('input[name=userEmail]').val(data)
        }, error: function () {
            let text = `
                    <div class="result-cnt">
                        <p class="find-message">
                            회원님의 이메일이 존재하지 않습니다.
                            <br/>
                            <strong class="text-primary">[전화번호]</strong>
                            를 다시 한 번 확인해주세요
                        </p>
                    </div>
                `;
            $('.type2').html(text);
        }
    })
})

/* 이메일을 정규식에 맞게 검사하면 인증버튼 활성화 */
$emailInput.keyup(function(){
    if(emailRegex.test($emailInput.val())){
        $IdEmailBtn.addClass('btn-primary');
        $IdEmailBtn.removeClass('btn-dim');
        $IdEmailBtn.attr('disabled', false);
        $emailBtn.addClass('btn-primary');
        $emailBtn.removeClass('btn-dim');
        $emailBtn.attr('disabled', false);
        return;
    }
    $IdEmailBtn.addClass('btn-dim');
    $IdEmailBtn.removeClass('btn-primary');
    $IdEmailBtn.attr('disabled', true);
    $emailBtn.addClass('btn-dim');
    $emailBtn.removeClass('btn-primary');
    $emailBtn.attr('disabled', true);
});

// 이메일로 아이디 찾기
$IdEmailBtn.on('click', function () {
    let email = $emailInput.val();
    // 인증번호 보내기
    $.ajax({
        url: "/find-id-email",
        type: "post",
        data: { userEmail : email},
        success: function (data) {
            identification = "[" + data + "]";
            if(data == ""){
                $(".message").html(noId);
            }else{
                $(".text-primary").html(identification);
            }
        }
    })

})

/* (비밀번호 찾기에서) 버튼 누르면 이메일로 링크 보내기 */
$emailBtn.on('click', function(){
    $('#find-member-pw-email-btn').show();
    $.ajax({
        url: "/sendMail",
        type: "post",
        data: { "userEmail" : $emailInput.val()},
        success: function (answer) {
            let text = `
                    <div class="result-cnt">
                        <p class="find-message">
                            이메일이 발송되었습니다.
                            <br/>
                            <strong class="text-primary">[링크]</strong>
                            를 확인해 주시고 비밀번호를 변경해주세요
                        </p>
                    </div>
                `;
            $('.type2').html(text);
        }, error: function () {
            let text = `
                <div class="result-cnt">
                    <p class="message">
                        이메일 발송에 실패했습니다.
                        <br/>
                        <strong class="text-primary">[이메일]</strong>
                        을 다시 한 번 확인해주세요
                    </p>
                </div>
            `;
            $('.type2').html(text);
        }
    })
});

$('.email-code-input').keyup(function(){
    if($('.email-code-input').val().length == 6){
        $('#find-member-pw-email-btn').addClass('btn-primary');
        $('#find-member-pw-email-btn').removeClass('btn-dim');
        return;
    }
});

$('.go-to-login').on('click', function () {
    location.href = "/login";
})
