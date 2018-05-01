package com.klauses.kata.fileduplicates.impl;

import com.klauses.kata.fileduplicates.api.Identifier;
import com.klauses.kata.fileduplicates.utils.HashUtils;

public class HashIdentifier implements Identifier {

	String pathname = null;

	public HashIdentifier(String pathname) {
		super();
		this.pathname = pathname;
	}

	public String getPathname() {
		return pathname;
	}

	@Override
	public String getUniqueKey() {
		return HashUtils.getHash(getPathname());
	}

}
