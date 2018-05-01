package com.klauses.kata.fileduplicates.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.klauses.kata.fileduplicates.api.IDuplicate;

public class Duplicate implements IDuplicate {

	private Collection<String> filepath = null;

	public Collection<String> getFilepaths() {
		if (filepath == null) {
			filepath = new ArrayList<>();
		}
		return filepath;
	}

}
