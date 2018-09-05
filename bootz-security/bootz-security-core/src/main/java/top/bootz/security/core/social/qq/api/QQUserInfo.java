package top.bootz.security.core.social.qq.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * QQ返回的用户信息
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月2日 下午11:08:10
 */

@Data
public class QQUserInfo {

    /** 用户在qq中的openId */
    private String openId;

    /**
     * 返回码; 0: 正确返回, 其它: 失败;
     * 
     * @see http://wiki.connect.qq.com/%E5%85%AC%E5%85%B1%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E
     */
    private int ret;

    /** 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。 */
    private String msg;

    /** 用户在QQ空间的昵称。 */
    private String nickname;

    /** 不知道什么东西，文档上没写，但是实际api返回里有。 */
    @JsonProperty("is_lost")
    private String isLost;

    /** 省(直辖市) */
    private String province;

    /** 市(直辖市区) */
    private String city;

    /** 出生年月 */
    private String year;

    /** 大小为30×30像素的QQ空间头像URL。 */
    private String figureurl;

    /** 大小为50×50像素的QQ空间头像URL。 */
    @JsonProperty("figureurl_1")
    private String figureurl1;

    /** 大小为100×100像素的QQ空间头像URL。 */
    @JsonProperty("figureurl_2")
    private String figureurl2;

    /** 大小为40×40像素的QQ头像URL。 */
    @JsonProperty("figureurl_qq_1")
    private String figureurlQQ1;

    /** 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。 */
    @JsonProperty("figureurl_qq_2")
    private String figureurlQQ2;

    /** 性别。 如果获取不到则默认返回"男" */
    private String gender;

    /** 标识用户是否为黄钻用户（0：不是；1：是）。 */
    @JsonProperty("is_yellow_vip")
    private String isYellowVip;

    /** 标识用户是否为VIP用户（0：不是；1：是） */
    private String vip;

    /** 黄钻vip等级 */
    @JsonProperty("yellow_vip_level")
    private String yellowVipLevel;

    /** VIP等级 */
    private String level;

    /** 标识是否为年费黄钻用户（0：不是； 1：是） */
    @JsonProperty("is_yellow_year_vip")
    private String isYellowYearVip;

}
