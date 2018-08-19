package top.bootz.core.base.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.constant.HttpConstants;
import top.bootz.commons.constant.SecurityConstants;
import top.bootz.commons.constant.SymbolConstants;
import top.bootz.core.base.message.ErrorMessage;
import top.bootz.core.base.message.RestMessage;
import top.bootz.core.dictionary.MessageStatusEnum;
import top.bootz.core.dictionary.SourceTypeEnum;

/**
 * 提供所有Controller中的通用方法
 *
 * @author John
 */
@Slf4j
public class BaseController {

	/**
	 * 构造查询成功的消息体
	 * 
	 * @param successObj
	 *            业务实体
	 * @return
	 * @author John
	 * @param <T>
	 * @time 2018年6月18日 上午12:43:14
	 */
	protected <T extends Serializable> RestMessage<T> buildSuccessResponse(T success) {
		return new RestMessage<>(MessageStatusEnum.SUCCESS, success, null);
	}

	/**
	 * 构造创建成功的消息体
	 * 
	 * @param successObj
	 *            业务实体
	 * @return
	 * @author John
	 * @time 2018年6月18日 上午12:43:14
	 */
	protected ResponseEntity<String> buildCreateSuccessResponse(String location) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HttpConstants.HEADER_NAME_LOCATION, location);
		return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
	}

	/**
	 * 构造调用失败的消息体
	 * 
	 * @param value
	 *            业务实体
	 * @return
	 * @author John
	 * @time 2018年6月18日 上午12:43:14
	 */
	protected <T extends Serializable> RestMessage<T> buildErrorResponse(ErrorMessage error) {
		return new RestMessage<>(MessageStatusEnum.ERROR, null, error);
	}

	/**
	 * 构造调用警告消息体（程序虽然执行了，但是过程中间可能有不符合预期的情况产生，需要将异常消息返回给前端）
	 * 
	 * @param value
	 *            业务实体
	 * @return
	 * @author John
	 * @time 2018年6月18日 上午12:43:14
	 */
	protected <T extends Serializable> RestMessage<T> buildWarningResponse(T warning, ErrorMessage error) {
		return new RestMessage<>(MessageStatusEnum.WARNING, warning, error);
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
