package top.bootz.security.session.strategy;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import top.bootz.security.core.properties.SecurityProperties;

/**
 * session失效事件记录器
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年10月16日 下午11:51:11
 */

@Component("sessionInformationExpiredStrategy")
public class CustomExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public CustomExpiredSessionStrategy(SecurityProperties securityPropertie) {
        super(securityPropertie);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }

}
