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
            let url = Constants.domain + '/verification/sms?mobile=' + mobile;
            if (!!mobile) {
                $.ajax({
                    url : url,
                    type : 'GET',
                    contentType : Constants.media_type_json
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
    // Login.hideTab("usernameTab");
    Login.init();
});