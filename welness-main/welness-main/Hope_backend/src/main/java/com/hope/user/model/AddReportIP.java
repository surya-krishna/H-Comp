package com.hope.user.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddReportIP {
	private Integer reportId;
	private String nameOnReport;
	private int age;
	private String gender;
	private String reportDate;
	private Integer labId;
	private String labName;
	private String labAddress;
	private String labPhone;
	private String reportPdf;
	private String reportMimeType;
	private List<UserTestsModel> userTestsList;
	private Boolean containsPII;
	
}
