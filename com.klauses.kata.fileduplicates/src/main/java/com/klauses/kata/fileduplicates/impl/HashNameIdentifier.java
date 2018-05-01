package com.klauses.kata.fileduplicates.impl;

import java.io.File;

import com.klauses.kata.fileduplicates.api.Identifier;
import com.klauses.kata.fileduplicates.utils.HashUtils;

public class HashNameIdentifier implements Identifier {

	File file = null;

	public HashNameIdentifier(String pathname) {
		super();
		this.file = new File(pathname);
	}

	public File getFile() {
		return file;
	}

	@Override
	public String getUniqueKey() {
		return getFile().getName() + HashUtils.getHash(getFile());
	}

}
