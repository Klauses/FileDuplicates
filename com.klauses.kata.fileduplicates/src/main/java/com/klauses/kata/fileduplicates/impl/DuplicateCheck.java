package com.klauses.kata.fileduplicates.impl;

import java.io.File;
import java.util.Collection;

import com.klauses.kata.fileduplicates.api.CompareMode;
import com.klauses.kata.fileduplicates.api.IDuplicate;
import com.klauses.kata.fileduplicates.api.IDuplicateCheck;
import com.klauses.kata.fileduplicates.api.Identifier;

public class DuplicateCheck implements IDuplicateCheck {

	private CompareMode compareModes;

	private void findDuplicateFiles(DuplicateMap duplicatesMap, File directory) {
		for (File dirChild : directory.listFiles()) {
			// Iterate all file sub directories recursively
			if (dirChild.isDirectory()) {
				findDuplicateFiles(duplicatesMap, dirChild);
			} else {

				Identifier identifier = null;

				if (compareModes == CompareMode.Size_and_name) {
					identifier = new SizeNameIdentifier(dirChild);
				} else if (compareModes == CompareMode.Size) {
					identifier = new SizeIdentifier(dirChild);
				}
				
				duplicatesMap.put(identifier, dirChild.getAbsolutePath());
			}
		}
	}

	@Override
	public Collection<IDuplicate> compileCandidates(String folderpath) {

		return compileCandidates(folderpath, CompareMode.Size_and_name);
	}

	@Override
	public Collection<IDuplicate> compileCandidates(String folderpath, CompareMode mode) {
		compareModes = mode;

		DuplicateMap duplicatesMap = new DuplicateMap();

		File startPoint = new File(folderpath);

		findDuplicateFiles(duplicatesMap, startPoint);

		return duplicatesMap.getDuplicates();
	}

	@Override
	public Collection<IDuplicate> checkCandidates(Collection<IDuplicate> candidates) {
		DuplicateMap duplicatesMap = new DuplicateMap();

		// Iterate candidates
		for (IDuplicate duplicate : candidates) {

			// Iterate file-paths of candidates
			for (String filePath : duplicate.getFilepaths()) {

				Identifier identifier = null;

				if (compareModes == CompareMode.Size_and_name) {
					identifier = new HashNameIdentifier(filePath);
				} else if (compareModes == CompareMode.Size) {
					identifier = new HashIdentifier(filePath);
				}

				duplicatesMap.put(identifier, filePath);

			}
		}

		return duplicatesMap.getDuplicates();
	}

}
