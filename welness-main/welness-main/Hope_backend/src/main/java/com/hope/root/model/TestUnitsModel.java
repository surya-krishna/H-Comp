package com.hope.root.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TestUnitsModel {
	private int testUnitid;
	private String testUnit;
	private int isPrimary;
	private BigDecimal conversionToPrimary;
	
}
