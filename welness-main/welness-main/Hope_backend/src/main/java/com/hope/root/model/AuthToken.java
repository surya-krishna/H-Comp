package com.hope.root.model;

public class AuthToken {

    private String token;
    private String role;
    private String userName;

    public AuthToken(){

    }

    public AuthToken(String token,String role,String userName){
        this.token = token;
        this.role=role;
        this.userName=userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
