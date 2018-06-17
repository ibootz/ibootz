package top.bootz.usercenter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.constant.SecurityConstants;
import top.bootz.core.dictionary.SourceTypeEnum;

/**
 * 提供所有Controller中的通用方法
 * 
 * @author John
 *
 */
@Slf4j
public class BaseController {

	/**
	 * 新增操作之后，在response的header中添加新增资源的url地址。
	 * 
	 * @param response
	 * @param id
	 */
	public void buildRedirectUrl(HttpServletRequest request, HttpServletResponse response, String id) {
		String requestUrl = request.getRequestURL().toString();
		String location = "";
		if (requestUrl.endsWith("/")) {
			location = requestUrl + id;
		} else {
			location = requestUrl + "/" + id;
		}
		log.trace("location url [" + location + "]");
		response.addHeader("Location", location);
	}

	/**
	 * 从Source请求头中提取请求方的标识（Desktop, Android, IOS, H5...）
	 * 
	 * @param request
	 * @return
	 */
	public SourceTypeEnum getSourceType(HttpServletRequest request) {
		SourceTypeEnum sourceTypeEnum = SourceTypeEnum.UNKNOW;
		String sourceStr = request.getHeader(SecurityConstants.HEADER_SOURCE);
		if (StringUtils.isNotBlank(sourceStr) && StringUtils.isNumeric(sourceStr)) {
			sourceTypeEnum = SourceTypeEnum.getSourceTypeByCode(Integer.valueOf(sourceStr));
		}
		return sourceTypeEnum;
	}

}
