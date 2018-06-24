package top.bootz.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import top.bootz.commons.constant.HttpConstants;
import top.bootz.commons.constant.SecurityConstants;
import top.bootz.commons.constant.SymbolConstants;
import top.bootz.core.base.dto.ErrorMessage;
import top.bootz.core.base.dto.RestMessage;
import top.bootz.core.dictionary.MessageStatusEnum;
import top.bootz.core.dictionary.SourceTypeEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * 提供所有Controller中的通用方法
 *
 * @author John
 */
@Slf4j
public class BaseController {

    /**
     * 构造返回给前端的统一消息体
     *
     * @param status     系统自定义状态码
     * @param httpStatus Http状态码
     * @param value      业务实体
     * @return
     * @author John
     * @time 2018年6月18日 上午12:43:14
     */
    protected ResponseEntity<RestMessage> buildRestMessage(HttpStatus httpStatus, MessageStatusEnum status,
                                                           Object value, ErrorMessage error) {
        RestMessage message = new RestMessage(status, value, error);
        return new ResponseEntity<>(message, httpStatus);
    }

    /**
     * 获取请求Base Url
     *
     * @param request
     * @return
     * @author John
     * @time 2018年6月17日 下午11:13:35
     */
    protected static String generateBaseUrl(HttpServletRequest request) {
        int port = request.getServerPort();
        String reqUri = request.getRequestURI();
        return request.getScheme() + HttpConstants.SCHEME_POSTFIX + request.getServerName()
                + (port == 80 ? SymbolConstants.BLANK : SymbolConstants.HALF_WIDTH_COLON + port)
                + (reqUri.endsWith(SymbolConstants.FORWARD_SLASH) ? reqUri : reqUri + SymbolConstants.FORWARD_SLASH);
    }

    /**
     * 新增操作之后，在response的header中添加新增资源的url地址。
     *
     * @param request
     * @param id
     */
    protected String buildLocation(HttpServletRequest request, Long id) {
        String requestUrl = generateBaseUrl(request);
        String location;
        if (requestUrl.endsWith("/")) {
            location = requestUrl + id;
        } else {
            location = requestUrl + "/" + id;
        }
        log.trace("location url [" + location + "]");
        return location;
    }

    /**
     * 从Source请求头中提取请求方的标识（Desktop, Android, IOS, H5...）
     *
     * @param request
     * @return
     */
    protected SourceTypeEnum getSourceType(HttpServletRequest request) {
        SourceTypeEnum sourceTypeEnum = SourceTypeEnum.UNKNOW;
        String sourceStr = request.getHeader(SecurityConstants.HEADER_SOURCE);
        if (StringUtils.isNotBlank(sourceStr) && StringUtils.isNumeric(sourceStr)) {
            sourceTypeEnum = SourceTypeEnum.getSourceTypeByCode(Integer.valueOf(sourceStr));
        }
        return sourceTypeEnum;
    }

}
