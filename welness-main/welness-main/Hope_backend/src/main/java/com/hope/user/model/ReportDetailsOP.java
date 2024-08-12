package com.hope.user.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDetailsOP {
	private int errFlag;
	private String msg;
	private Integer reportId;
	private String nameOnReport;
	private String reportDate;
	private Integer labId;
	private String labName;
	private String labAddress;
	private String gender;
	private int age;
	private boolean containsPII;
	private List<UserTestsModel> userTestsList;
	

}
