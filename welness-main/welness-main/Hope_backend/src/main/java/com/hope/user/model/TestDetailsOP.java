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
public class TestDetailsOP {
	//to store timeline of tests
	private int errFlag;
	private String msg;
	private List<TestTimelineModel> userTestsList;
}
