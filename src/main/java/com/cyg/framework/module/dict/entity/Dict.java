package com.cyg.framework.module.dict.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cyg.framework.annotation.TableName;

/**
 * 字典实体
 *
 */ 
@TableName(tableName="p_dict")
public class Dict implements Serializable{

	private static final long serialVersionUID = 1L; 
	 
	private String name;
	 
	private String code;
	
	private String description; 
	
	private Long sort;
	 
	private List<DictItem> dictItems=new ArrayList<DictItem>();

	
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<DictItem> getDictItems() {
		return dictItems;
	}

	public void setDictItems(List<DictItem> dictItems) {
		this.dictItems = dictItems;
	}

}
