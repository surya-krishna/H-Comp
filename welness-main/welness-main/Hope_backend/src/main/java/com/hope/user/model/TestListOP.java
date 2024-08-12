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
public class TestListOP {
	private int errFlag;
	private String msg;
	private List<TestBasicModel> testBasicList;
	
}
