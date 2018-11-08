let SignUp = (function() {
    let getSocialUserInfo = function() {
        $.ajax({
            type : 'GET',
            url : Constants.domain + "/user/social/me",
            dataType : 'json',
            contentType : Constants.media_type_json
        }).done(function(data) {
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