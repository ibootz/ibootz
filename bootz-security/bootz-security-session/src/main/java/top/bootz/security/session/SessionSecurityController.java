package top.bootz.security.session;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import top.bootz.security.session.support.SimpleResponse;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年8月19日 下午3:27:37
 */

@Slf4j
@RestController
public class SessionSecurityController {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

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
     * @Author : Zhangq <momogoing@163.com>
     * @CreationDate : 2018年8月19日 下午3:28:56
     */
    @GetMapping("/authentication/require")
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.debug("引发跳转的请求是[" + targetUrl + "]");
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                // 跳转地址 留给安全组件的调用方来提供
                redirectStrategy.sendRedirect(request, response, "");
            }
        }
        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }

}
