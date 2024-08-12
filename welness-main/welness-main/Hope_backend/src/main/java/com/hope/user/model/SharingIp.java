package com.hope.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharingIp {
	private int sharingId;
    private String doctorEmail;
    private int activeFlag;
}
