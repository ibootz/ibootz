let Index = (function() {
    let checkAuth = function() {
        $.ajax({
            type : 'GET',
            url : "http://www.bootz.top/user/me",
            dataType : 'json',
            contentType : 'application/json; charset=UTF-8'
        }).done(function(result) {
            console.log(result);
            $('#unLogin').hide();
            $('#username').append(result.data.username);
            $('#logined').show();
        });
    };
    return {
        init : function() {
            checkAuth();
        }
    }
})();

jQuery(document).ready(function() {
    Index.init();
});