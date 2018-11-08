package top.bootz.security.core.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import lombok.extern.slf4j.Slf4j;
import top.bootz.core.base.controller.BaseController;
import top.bootz.core.base.message.RestMessage;
import top.bootz.security.core.SecurityConstants;
import top.bootz.security.core.properties.SecurityProperties;
import top.bootz.security.core.properties.session.LoginResponseType;
import top.bootz.security.core.social.support.SocialUserInfo;

/**
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年11月6日 下午11:10:08
 */

@Slf4j
@RestController
public class CoreSecurityController extends BaseController {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 发起认证请求。
     * <p>
     * 当需要认证时跳转到该路径，通过这里判断请求是来自于浏览器，还是来自于客户端 ；
     * <p>
     * 如果请求的路径后缀是.html，那么采取转发的策略，否则返回json格式的响应体
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @datetime 2018年11月6日 下午11:11:34
     */
    @GetMapping(value = SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestMessage<String> requireAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            if (LoginResponseType.REDIRECT.equals(securityProperties.getSession().getLoginType())
                    || StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                // 跳转地址 留给安全组件的调用方来提供
                redirectStrategy.sendRedirect(request, response, securityProperties.getSession().getSignInUrl());
            }
        }
        return buildSimpleErrorResponse("你要访问的服务需要身份认证，请到登录页面进行认证");
    }

    /**
     * 获取用户使用第三方社交账号登录之后返回来的用户信息，此Api一旦绑定系统用户之后，即会被注销掉
     * 
     * @param request
     * @param response
     * @return
     * @datetime 2018年11月6日 下午11:11:34
     */
    @GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    public SocialUserInfo getSocailUserInfo(HttpServletRequest request) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        if (connection == null) {
            log.error("connection is null!");
            throw new RuntimeException("connection is null!");
        }
        SocialUserInfo userInfo = new SocialUserInfo();
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());
        return userInfo;
    }

}
