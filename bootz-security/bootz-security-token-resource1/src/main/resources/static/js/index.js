let Index = (function() {
    let checkAuth = function() {
        let url = Constants.domain + '/user/me';
        $.ajax({
            type : 'GET',
            url : url,
            dataType : 'json',
            contentType : Constants.media_type_json
        }).done(function(result) {
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