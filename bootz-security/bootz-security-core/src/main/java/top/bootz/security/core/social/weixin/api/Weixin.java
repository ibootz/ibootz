package top.bootz.security.core.social.weixin.api;

/**
 * 微信API调用接口
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月23日 下午7:16:04
 */
public interface Weixin {

	WeixinUserInfo getUserInfo(String openId);
	
}
