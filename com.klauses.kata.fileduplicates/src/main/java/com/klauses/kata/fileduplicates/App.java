package com.klauses.kata.fileduplicates;

import java.util.Collection;
import java.util.Iterator;

import com.klauses.kata.fileduplicates.api.IDuplicate;
import com.klauses.kata.fileduplicates.impl.DuplicateCheck;

public class App {
	public static void main(String[] args) {

		DuplicateCheck checkForDuplicates = new DuplicateCheck();
		Collection<IDuplicate> duplicates = checkForDuplicates.compileCandidates("D:/zzz/Root");

		for (Iterator<IDuplicate> iterator = duplicates.iterator(); iterator.hasNext();) {
			IDuplicate duplicate = iterator.next();

			for (Iterator<String> iteratorInner = duplicate.getFilepaths().iterator(); iteratorInner.hasNext();) {
				String path = iteratorInner.next();
				System.out.println(path);
			}
			System.out.println("\n");
		}
	}
}
