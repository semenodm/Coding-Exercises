package org.sdo.algorythms.sort;


public class BasicCucumberSortTestImpl {
	

	public BasicCucumberSortTestImpl() {
		super();
	}

	Integer[] convertStringToArray(String input) {
		String[] split = input.split(",");
		Integer[] out = new Integer[split.length];
		for (int i = 0; i < out.length; i++) {
			out[i] = Integer.valueOf(split[i].trim());
		}
		return out;
	}
}