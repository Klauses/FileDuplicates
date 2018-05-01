package com.klauses.kata.fileduplicates.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.klauses.kata.fileduplicates.api.IDuplicate;
import com.klauses.kata.fileduplicates.api.Identifier;

public class DuplicateMap {

	private Map<String, IDuplicate> map;

	private Map<String, IDuplicate> getMap() {
		if (map == null) {
			map = new HashMap<>();
		}
		return map;
	}

	public void put(Identifier key, String value) {

		IDuplicate duplicates = getMap().get(key.getUniqueKey());
		if (duplicates == null) {
			duplicates = new Duplicate();
		}

		duplicates.getFilepaths().add(value);

		getMap().put(key.getUniqueKey(), duplicates);
	}

	public Collection<IDuplicate> getDuplicates() {
		Collection<IDuplicate> result = new ArrayList<>();

		for (IDuplicate duplicates : getMap().values()) {
			if (duplicates.getFilepaths().size() > 1) {

				result.add(duplicates);
			}
		}

		return result;
	}

}
