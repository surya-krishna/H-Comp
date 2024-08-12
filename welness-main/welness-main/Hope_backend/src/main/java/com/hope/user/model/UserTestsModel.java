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
public class UserTestsModel {

	private Integer userTestId;
	private Integer testMasterId;
	private String testName;
	private String units;
	private String testValue;
	private BigDecimal testMinNormal;
	private BigDecimal testMaxNormal;
	private String testResultText;// to capture text based results like colors, blood groups etc
	private String testResultType;
	private String observation;
	private String suggestion;
	private List<MedicationModel> medication;
	private List<TestFilesModel> filesList;
	
}
