let Login = (function() {
    let hideTab = function(tabId) {
        $("#" + tabId).hide();
    };
    let changeCaptcha = function() {
        $("#captcha").click(function() {
            $(this).attr('src', '/verification/image?width=60&length=3' + Math.floor(Math.random() * 100));
        })
    };
    let getSmsCode = function() {
        $("#smsCodeBtn").click(function() {
            let mobile = $("#mobile").val();
            if (!!mobile) {
                $.ajax({
                    url : "http://localhost:8060/verification/sms?mobile=" + mobile,
                    type : 'GET'
                });
            }
        })
    };
    return {
        hideTab : hideTab,
        init : function() {
            changeCaptcha();
            getSmsCode();
        }
    }
})();

jQuery(document).ready(function() {
    Login.hideTab("usernameTab");
    Login.init();
});