package com.cyg.framework.module.dict.entity;

import java.io.Serializable;


/**
 * 字典项实体
 *
 */ 
public class DictItem  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//字典项名称 
	private String name;
	//字典项值 
	private String value;
	//所属字典 
	private Dict dict;
	private Long sort;
	//是否默认值
	private boolean defaultValue=false;
	
	
	public Long getSort() {
		return sort;
	}
	public void setSort(Long sort) {
		this.sort = sort;
	}
	public boolean isDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Dict getDict() {
		return dict;
	}
	public void setDict(Dict dict) {
		this.dict = dict;
	}
}
 