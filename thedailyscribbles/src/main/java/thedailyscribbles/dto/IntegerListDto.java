package thedailyscribbles.dto;

/**
* A data transfer object class that is used to store a list of Integers (For example : list of blogger id's)
 * 
 * @author Puthin Kumar
 */

import java.util.List;

public class IntegerListDto {
	
	private List<Integer> integerList;

	public List<Integer> getIntegerList() {
		return integerList;
	}

	public void setIntegerList(List<Integer> integerList) {
		this.integerList = integerList;
	}
	
	
}
