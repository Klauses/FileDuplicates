package com.klauses.kata.fileduplicates.impl;

import java.io.File;

import com.klauses.kata.fileduplicates.api.Identifier;

public class SizeIdentifier implements Identifier {

	File file = null;

	public SizeIdentifier(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	@Override
	public String getUniqueKey() {
		return String.valueOf(getFile().length());
	}

}
