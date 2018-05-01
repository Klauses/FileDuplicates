package com.klauses.kata.fileduplicates.impl;

import java.io.File;

import com.klauses.kata.fileduplicates.api.Identifier;

public class SizeNameIdentifier implements Identifier {

	File file = null;

	public SizeNameIdentifier(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	@Override
	public String getUniqueKey() {
		return getFile().getName() + getFile().length();
	}

}
