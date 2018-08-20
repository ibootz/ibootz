package top.bootz.security.core.properties;

public enum LoginType {

	REDIRECT, // 跳转到指定路径，适用于传统的Java web应用

	JOIN // 返回json响应，适用于Restful风格或者前后端分离架构的应用

}
