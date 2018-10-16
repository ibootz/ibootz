package top.bootz.security.session.strategy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import top.bootz.security.core.properties.SecurityProperties;

/**
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年10月17日 上午12:20:24
 */

@Component
public class CustomInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

    public CustomInvalidSessionStrategy(SecurityProperties securityPropertie) {
        super(securityPropertie);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        onSessionInvalid(request, response);
    }

}
