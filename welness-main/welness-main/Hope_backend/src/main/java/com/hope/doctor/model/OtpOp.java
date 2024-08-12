package com.hope.doctor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpOp {
	private int consultationId;
	private int errFlag;
	private String msg;
}
