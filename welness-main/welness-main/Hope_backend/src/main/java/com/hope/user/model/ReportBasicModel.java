package com.hope.user.model;

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
public class ReportBasicModel {
	private Integer reportId;
	private String nameOnReport;
	private String reportDate;
	private String labName;
	private List<String> testsList;
	
}
