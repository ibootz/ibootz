let Login = (function() {
    let changeCaptcha = function(){
        $("#captcha").click(function() {
            $(this).attr('src', '/verification/image?' + Math.floor(Math.random() * 100));
        })
    };
    return {
        init: function(){
            changeCaptcha();
        }
    }
})();

jQuery(document).ready(function() {
    Login.init();
});