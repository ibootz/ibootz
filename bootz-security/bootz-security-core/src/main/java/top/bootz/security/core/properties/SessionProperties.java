package top.bootz.security.core.properties;

import lombok.Data;

@Data
public class SessionProperties {

	/** 登录页面路径，如果使用我们安全组件的应用没有配置，则使用此处配置的默认值 */
	private String loginPage = "/html/default-login.html";

	/** 登录认证成功或者失败之后，是采用跳转的方式还是采用返回json格式响应的方式来应对，如果应用未配置，默认返回json响应 */
	private LoginType loginType = LoginType.JOIN;

}
