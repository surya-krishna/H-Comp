package com.hope.root.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TestDataModel {
	private int testId;
	private String testName;
	private String testCode;
	private String testDescription;
	private String testResultType;
	private List<TestNamesModel> testNamesList;
	private List<TestUnitsModel> testUnits;
}
