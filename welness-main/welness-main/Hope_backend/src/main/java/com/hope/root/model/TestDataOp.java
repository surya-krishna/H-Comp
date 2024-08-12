package com.hope.root.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class TestDataOp {
	List<TestDataModel> testDataList;
}
