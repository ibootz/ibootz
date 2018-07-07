package top.bootz.user.entity.elastic;

import java.io.Serializable;

import lombok.Data;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月30日 下午11:07:01
 */

@Data
public class PingItemTag implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String description; // 标签描述

	private String sku; // 产品SKU

}
