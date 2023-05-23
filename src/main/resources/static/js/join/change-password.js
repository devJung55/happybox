/* change-password.js */

// 비밀번호 정규식
const passwordNumberRegex = /[0-9]/g;
const passwordEnglishRegex = /[a-z]/gi;
const passwordSpecialCharacterRegex = /[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi;
const $password = $('.pw-input');
const $passwordConfirm = $('.pw-confirm');

$password.keyup(function () {
    let value = $password.val();

    let numberCheck = value.search(passwordNumberRegex);
    let englishCheck = value.search(passwordEnglishRegex);
    let specialCharacterCheck = value.search(passwordSpecialCharacterRegex);

    var condition1 =
        numberCheck >= 0 &&
        englishCheck >= 0 &&
        englishCheck >= 0 &&
        specialCharacterCheck >= 0 &&
        specialCharacterCheck >= 0 &&
        numberCheck >= 0;
    var condition2 = value.length > 5 && value.length < 15;
    var condition3 = value.search(/\s/) < 0;

    if (condition1 && condition2 && condition3) {
        $('.valid').css('display', 'none');
        return;
    }
    $('.valid').css('display', 'block');
});

$('input[name=userPassword]').on('blur', function () {
    console.log($(this).val())
})

$passwordConfirm.keyup(function(){
    if($passwordConfirm.val() == $password.val()){
        $('#reset-pw-submit-btn').addClass('btn-primary');
        $('#reset-pw-submit-btn').removeClass('btn-dim');
        $('#reset-pw-submit-btn').attr('disabled', false)
        return;
    }
    $('#reset-pw-submit-btn').addClass('btn-dim');
    $('#reset-pw-submit-btn').removeClass('btn-primary');
    $('#reset-pw-submit-btn').attr('disabled', true)
});

$('#reset-pw-submit-btn').on('click', function () {
    document.changePw.submit();
})


