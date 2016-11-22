package com.cyg.web.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * @author admin
 *
 */
public class User implements Serializable {
	
    private static final long serialVersionUID = 1L;

    /**ID*/
    private String id;
    /**用户名*/
    private String name;
    /**密码*/
    private String pwd;
    /**地址*/
    private String address;
    /**邮箱*/
    private String email;
    /**注册来源*/
    private Integer sourceType;
    /**创建时间*/
    private Date createDate;
    /**最后一次修改时间*/
    private Date lastUpdateDate;
    
    
    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getId() {
         return id;
    }

    public void setId(String id) {
         this.id = id;
    }

    public String getName() {
         return name;
    }

    public void setName(String name) {
         this.name = name;
    }

    public String getPwd() {
         return pwd;
    }

    public void setPwd(String pwd) {
         this.pwd = pwd;
    }


}