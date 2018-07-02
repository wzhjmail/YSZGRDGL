package com.wzj.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.db.HasRevision;
import org.activiti.engine.impl.db.PersistentObject;

public class SysRole implements Group, Serializable, PersistentObject, HasRevision {

	private static final long serialVersionUID = 1L;

	  protected String id;
	  protected int revision;
	  protected String name;
	  protected String type;
	  protected String available;
	  protected String belonged;//本角色是否是中心人员的专属角色？
	  
	  public String getBelonged() {
		return belonged;
	}

	public void setBelonged(String belonged) {
		this.belonged = belonged;
	}

	public SysRole() {
	  }
	  
	  public SysRole(String name) {
	    this.name = name;
	  }
	  
	  public Object getPersistentState() {
	    Map<String, Object> persistentState = new HashMap<String, Object>();
	    persistentState.put("name", name);
	    persistentState.put("type", type);
	    return persistentState;
	  }
	  
	  public int getRevisionNext() {
	    return revision+1;
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
	  public String getType() {
	    return type;
	  }
	  public void setType(String type) {
	    this.type = type;
	  }
	  public int getRevision() {
	    return revision;
	  }
	  public void setRevision(int revision) {
	    this.revision = revision;
	  }
	  public String getAvailable() {
        return available;
	  }
      public void setAvailable(String available) {
        this.available = available == null ? null : available.trim();
      }
}