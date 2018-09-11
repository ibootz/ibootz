let SignUp = (function() {
    let getSocialUserInfo = function() {
        $.ajax({
            type : 'GET',
            url : "http://www.bootz.top/user/social/me",
            dataType: 'json',
            contentType : 'application/json; charset=UTF-8'
        }).done(function(data){
            console.log(data);
        });
    };
    return {
        init : function() {
            getSocialUserInfo();
        }
    }
})();

jQuery(document).ready(function() {
    SignUp.init();
});