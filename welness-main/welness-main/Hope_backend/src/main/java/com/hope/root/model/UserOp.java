package com.hope.root.model;

public class UserOp {
	
	private String msg;
	private int errFlag;
	public UserOp() {}
	public UserOp(String msg,int errFlag){
		this.msg=msg;
		this.errFlag=errFlag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getErrFlag() {
		return errFlag;
	}
	public void setErrFlag(int errFlag) {
		this.errFlag = errFlag;
	}
	

}
