/**
 * 
 */
package top.bootz.security.session.strategy;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.helper.JsonHelper;
import top.bootz.core.base.message.RestMessage;
import top.bootz.core.dictionary.MessageStatusEnum;
import top.bootz.security.core.properties.SecurityProperties;

/**
 * 抽象的session失效处理器
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年10月17日 上午12:32:43
 */

@Slf4j
public class AbstractSessionStrategy {

    /**
     * 跳转的url
     */
    private String destinationUrl;

    /**
     * 系统配置信息
     */
    private SecurityProperties securityPropertie;

    /**
     * 重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 跳转前是否创建新的session
     */
    private boolean createNewSession = true;

    public AbstractSessionStrategy(SecurityProperties securityPropertie) {
        String invalidSessionUrl = securityPropertie.getSession().getSessionInvalidUrl();
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        Assert.isTrue(StringUtils.endsWithIgnoreCase(invalidSessionUrl, ".html"), "url must end with '.html'");
        this.destinationUrl = invalidSessionUrl;
        this.securityPropertie = securityPropertie;
    }

    protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("session失效");

        if (createNewSession) {
            request.getSession();
        }

        String sourceUrl = request.getRequestURI();
        String targetUrl;

        if (StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
            if (StringUtils.equals(sourceUrl, securityPropertie.getSession().getLoginPage())
                    || StringUtils.equals(sourceUrl, securityPropertie.getSession().getSignOutUrl())) {
                targetUrl = sourceUrl;
            } else {
                targetUrl = destinationUrl;
            }
            log.info("跳转到:" + targetUrl);
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            Object result = buildResponseContent();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonHelper.toJSON(result));
        }

    }

    /**
     * @param request
     * @return
     */
    protected RestMessage<String> buildResponseContent() {
        String errMsg = "session已失效";
        if (isConcurrency()) {
            errMsg = errMsg + "，有可能是并发登录导致的";
        }
        return new RestMessage<>(MessageStatusEnum.ERROR, errMsg, null);
    }

    /**
     * session失效是否是并发导致的
     * 
     * @return
     */
    protected boolean isConcurrency() {
        return false;
    }

    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }

}
