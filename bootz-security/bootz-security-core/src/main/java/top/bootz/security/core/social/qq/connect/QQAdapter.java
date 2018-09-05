package top.bootz.security.core.social.qq.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import top.bootz.security.core.social.qq.api.QQ;
import top.bootz.security.core.social.qq.api.QQUserInfo;

/**
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月3日 下午9:49:19
 */
public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * 测试QQ开放平台是否可连接
     * 
     * @param api
     * @return
     * @author Zhangq<momogoing@163.com>
     * @datetime 2018年9月3日 下午9:49:52
     */
    @Override
    public boolean test(QQ api) {
        return true; // 默认永远可连接
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurlQQ1());
        values.setProfileUrl(null); // 个人主页，qq没有，但是微博开放平台可以把微博的个人主页放上来
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    /**
     * 类似于个人主页的东西，因为QQ没有这些东西，所以这里什么都不做
     * 
     * @param api
     * @param message
     * @author Zhangq<momogoing@163.com>
     * @datetime 2018年9月3日 下午10:02:07
     */
    @Override
    public void updateStatus(QQ api, String message) {
        // do nothing
    }

}
