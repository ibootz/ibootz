package top.bootz.security.core.social;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.UserIdSource;
import org.springframework.social.security.SocialUserDetails;

/**
 * 用来提取spring social中userid属性的类，由于我们数据库中UserConnection中userid保存的是User表的主键，所以这里必须要自己实现一个UserIdSource
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月24日 上午12:10:27
 */
public class IdentityUserIdSource implements UserIdSource {

    public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
        }

        if (!(authentication.getPrincipal() instanceof SocialUserDetails)) {
            throw new IllegalStateException(
                    "Unable to get a ConnectionRepository: principal cannot be cast to SocialUserDetails");
        }
        SocialUserDetails userDetails = (SocialUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }

}
