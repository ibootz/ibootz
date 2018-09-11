package top.bootz.security.core.social.qq.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.helper.JsonHelper;

/**
 * 该类为非单例模式，所以不能声明为Spring的Component
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月2日 下午11:09:14
 */

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /** 此处的access_token由父类挂载，此处就不需要在参数中特别添加了 */
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        this.openId = getOpenId(accessToken);
    }

    /**
     * 获取用户openId
     * 
     * @param accessToken
     * @return
     * @author Zhangq<momogoing@163.com>
     * @datetime 2018年9月3日 下午9:02:36
     */
    private String getOpenId(String accessToken) {
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        if (result.contains("openid")) {
            return StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
        }
        throw new RuntimeException("没有获取到openId, accessToken [" + accessToken + "]");
    }

    /**
     * 获取用户信息
     * 
     * @return
     * @author Zhangq<momogoing@163.com>
     * @datetime 2018年9月3日 下午9:29:54
     */
    @Override
    public QQUserInfo getUserInfo() {
        try {
            String url = String.format(URL_GET_USERINFO, this.appId, this.openId);
            String result = getRestTemplate().getForObject(url, String.class);
            log.debug("getUserInfo result [" + result + "]");
            QQUserInfo userInfo = JsonHelper.fromJSON(result, QQUserInfo.class);
            userInfo.setOpenId(this.openId);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("从QQ开放平台获取用户信息出错!  errMsg [" + e.getMessage() + "]", e);
        }
    }

}
