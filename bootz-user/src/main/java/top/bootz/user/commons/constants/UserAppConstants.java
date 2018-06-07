package top.bootz.user.commons.constants;

/**
 * Polaris系列项目相关的常量(后续可将部分常量转移到配置文件中)
 * 
 * @author John
 *
 */
public final class UserAppConstants {

	private UserAppConstants() {
	}

	/**
	 * spring mvc dispatcher servlet name
	 */
	public static final String DEFAULT_SERVLET_NAME = "Bootz";

	/**
	 * jsp试图解析器前缀
	 */
	public static final String VIEW_JSP_PREFIX = "/WEB-INF/jsp/";

	/**
	 * jsp试图解析器后缀
	 */
	public static final String VIEW_JSP_SUFFIX = ".jsp";

	/**
	 * text/html; charset=UTF-8
	 */
	public static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	/**
	 * 静态资源处理路径
	 */
	public static final String RESOURCE_HANDLER = "/static/**";

	/**
	 * 静态资源路径
	 */
	public static final String RESOURCE_LOCATION = "/static/";

	/**
	 * Freemark视图后缀
	 */
	public static final String VIEW_FREEMARKER_SUFFIX = ".ftl";

	/**
	 * Freemark视图前缀
	 */
	public static final String VIEW_FREEMARKER_TEMPLATE_LOADER_PATH = "/WEB-INF/freemarker/";

	/**
	 * 消息国际化默认使用的模板名称
	 */
	public static final String MESSAGE_SOURCE = "i18n.message,i18n.operate";

	/**
	 * TODO Audit - 系统做出的创建和更新，数据库中的审计字段（creater, updater）统一填写System
	 */
	public static final String AUDIT_CREATOR_SYSTEM = "System";

	public static final String AUDIT_UPDATER_SYSTEM = "System";

}
