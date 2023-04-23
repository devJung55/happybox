/* find-id.html */

const $radios = $("input[name='find-member-pw-radio']");
const $phoneInput = $('.phone-input');
const $phoneCodeInput = $('#certiNoPhoneArea');
const $emailInput = $('.email-input');
const $emailCodeInput = $('#certiNoEmailArea');
const $phoneBtn = $('#find-member-pw-certi-phone-btn');
const $emailBtn = $('#find-member-pw-certi-email-btn');
const phoneRegex = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
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

$phoneBtn.on('click', function(){
    $phoneCodeInput.css('display', 'block');
    $phoneBtn.hide();
    $('#find-member-pw-phone-btn').show();

});

$('.phone-code-input').keyup(function(){
    if($('.phone-code-input').val().length == 6){
        $('#find-member-pw-phone-btn').addClass('btn-primary');
        $('#find-member-pw-phone-btn').removeClass('btn-dim');
        return;
    }
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
