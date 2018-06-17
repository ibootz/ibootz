package top.bootz.user.commons.dictionary;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 操作类型枚举
 * 
 * @author John
 *
 */
public enum OperationEnum {

	NONE(0, "None"), // 无任何权限
	VIEW(1, "View"), // 查看
	CREATE(2, "Add"), // 创建添加
	MODIFY(3, "Modify"), // 修改
	DELETE(4, "Delete"), // 删除
	EXECUTE(5, "Execute"), // 执行
	UPLOAD(6, "Upload"), // 上传
	DOWNLOAD(7, "Download"); // 下载

	private Integer code;

	private String desc;

	private OperationEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@JsonValue
	public Integer getCode() {
		return this.code;
	}

	public String getDesc() {
		return this.desc;
	}

	public static OperationEnum getSourceTypeByCode(Integer code) {
		for (OperationEnum sourceType : OperationEnum.values()) {
			if (sourceType.getCode().equals(code)) {
				return sourceType;
			}
		}
		return OperationEnum.NONE;
	}

}
