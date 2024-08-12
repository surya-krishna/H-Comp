package com.hope.root.model;

import org.springframework.security.core.GrantedAuthority;

public class UserRole implements GrantedAuthority {

	private long roleId;
	private String roleName;
	@Override
	public String getAuthority() {
		return roleName;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	

}
