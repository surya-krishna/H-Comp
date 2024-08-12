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
public class TestBasicModel {
	private int testMasterId;
	private String testName;
	private long count;
	private String meanValue;
	private String meanNormalMin;
	private String meanNormalMax;
	private String testResultType;//helps in determining whether to show values or not
	
	
}
