package com.wzj.DTO;

public class SysRoleDTO {
	private String id;

    private String name;
    
    private String oldrolename;

    private String available;
    
    private String[] permissions;
    
    private int[] pmsid;
    
    private String belonged;
    
    public String getOldrolename() {
		return oldrolename;
	}

	public void setOldrolename(String oldrolename) {
		this.oldrolename = oldrolename;
	}

	public String getBelonged() {
		return belonged;
	}

	public void setBelonged(String belonged) {
		this.belonged = belonged;
	}

	public int[] getPmsid() {
		return pmsid;
	}

	public void setPmsid(int[] pmsid) {
		this.pmsid = pmsid;
	}

	public SysRoleDTO() {
		super();
	}

	public SysRoleDTO(String id, String name, String available, String[] permissions) {
		super();
		this.id = id;
		this.name = name;
		this.available = available;
		this.permissions = permissions;
	}
	
	public String[] getPermissions() {
		return permissions;
	}

	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available == null ? null : available.trim();
    }
}