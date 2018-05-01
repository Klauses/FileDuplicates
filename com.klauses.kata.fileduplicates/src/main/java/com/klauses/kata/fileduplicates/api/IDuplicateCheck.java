package com.klauses.kata.fileduplicates.api;

import java.util.Collection;

public interface IDuplicateCheck {

	Collection<IDuplicate> compileCandidates(String folderpath);

	Collection<IDuplicate> compileCandidates(String folderpath, CompareMode mode);

	Collection<IDuplicate> checkCandidates(Collection<IDuplicate> candidates);
}

