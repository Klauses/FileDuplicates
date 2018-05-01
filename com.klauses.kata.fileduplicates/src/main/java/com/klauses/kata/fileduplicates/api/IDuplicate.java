package com.klauses.kata.fileduplicates.api;

import java.util.Collection;

public interface IDuplicate {

	/**
	 * To fulfill the open-close-principle, method should be named neutral, like getValues
	 * 
	 * @return
	 */
	Collection<String> getFilepaths();	
}
