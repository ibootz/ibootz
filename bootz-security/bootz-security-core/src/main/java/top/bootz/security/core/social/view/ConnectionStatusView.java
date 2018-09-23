package top.bootz.security.core.social.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import top.bootz.commons.helper.JsonHelper;

/**
 * 社交账号绑定状态视图
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月23日 下午9:57:14
 */

@Component("connect/status")
public class ConnectionStatusView extends AbstractView {

    @Override
    @SuppressWarnings("unchecked")
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");

        Map<String, Boolean> result = new HashMap<>();
        for (Map.Entry<String, List<Connection<?>>> entry : connections.entrySet()) {
            result.put(entry.getKey(), CollectionUtils.isNotEmpty(entry.getValue()));
        }

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JsonHelper.toJSON(result));
    }

}
