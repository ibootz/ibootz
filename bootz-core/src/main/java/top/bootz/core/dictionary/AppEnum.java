package top.bootz.core.dictionary;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 系统内各微应用的名称和编号
 * 
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月24日 下午8:18:13
 */
public enum AppEnum {

    UNKNOW(0, "Unknow", "未知"),

    USER(5, "User", "用户中心"),

    SECURITY(10, "Security", "安全中心"),

    ORDER(15, "Order", "商城");

    private int code;

    private String name;

    private String desc;

    AppEnum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public static List<String> getAllAppName() {
        List<String> names = new ArrayList<>();
        for (AppEnum appEnum : AppEnum.values()) {
            names.add(appEnum.getName());
        }
        return names;
    }

    public static AppEnum getAppByCode(Integer code) {
        for (AppEnum appEnum : AppEnum.values()) {
            if (appEnum.getCode() == code) {
                return appEnum;
            }
        }
        return AppEnum.UNKNOW;
    }

    public static AppEnum getAppByName(String name) {
        for (AppEnum appEnum : AppEnum.values()) {
            if (appEnum.getName().equalsIgnoreCase(name)) {
                return appEnum;
            }
        }
        return AppEnum.UNKNOW;
    }

    public static AppEnum getAppByDesc(String desc) {
        for (AppEnum appEnum : AppEnum.values()) {
            if (appEnum.getDesc().equalsIgnoreCase(desc)) {
                return appEnum;
            }
        }
        return AppEnum.UNKNOW;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }

}
