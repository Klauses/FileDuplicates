package com.klauses.kata.fileduplicates.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.klauses.kata.fileduplicates.api.CompareMode;
import com.klauses.kata.fileduplicates.api.IDuplicate;
import com.klauses.kata.fileduplicates.impl.DuplicateCheck;

public class CheckForDuplicatesTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void test_nameSizeCompare_equal() throws IOException {

		File folderroot = temporaryFolder.newFolder("root");
		temporaryFolder.newFolder("root", "sub1");
		temporaryFolder.newFolder("root", "sub2");
		temporaryFolder.newFolder("root", "sub3");
		temporaryFolder.newFolder("root", "sub4");

		File file1 = temporaryFolder.newFile("root/sub1/file");
		File file2 = temporaryFolder.newFile("root/sub2/file");
		File file3 = temporaryFolder.newFile("root/sub2/file2");
		File file4 = temporaryFolder.newFile("root/sub3/file");
		FileUtils.writeStringToFile(file4, "TestString", StandardCharsets.UTF_8);
		File file5 = temporaryFolder.newFile("root/sub4/file");
		FileUtils.writeStringToFile(file5, "Teststring", StandardCharsets.UTF_8);

		DuplicateCheck checkForDuplicates = new DuplicateCheck();
		Collection<IDuplicate> duplicates = checkForDuplicates.compileCandidates(folderroot.getParent());

		assertTrue(duplicates.size() == 2);

		for (IDuplicate duplicate : duplicates) {

			if (duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file1.getPath()))) {

				assertTrue(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file1.getPath())));
				assertTrue(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file2.getPath())));
				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file3.getPath())));
				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file4.getPath())));
				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file5.getPath())));

			} else if (duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file4.getPath()))) {

				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file1.getPath())));
				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file2.getPath())));
				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file3.getPath())));
				assertTrue(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file4.getPath())));
				assertTrue(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file5.getPath())));

			}
		}

		FileUtils.deleteDirectory(folderroot);
	}

	@Test
	public void test_nameSizeCompare_notEqual() throws IOException {

		File folderroot = temporaryFolder.newFolder("root");
		temporaryFolder.newFolder("root", "sub1");
		temporaryFolder.newFolder("root", "sub2");

		File file1 = temporaryFolder.newFile("root/sub1/file");
		FileUtils.writeStringToFile(file1, "TestString", StandardCharsets.UTF_8);
		temporaryFolder.newFile("root/sub2/file");

		DuplicateCheck checkForDuplicates = new DuplicateCheck();
		Collection<IDuplicate> duplicates = checkForDuplicates.compileCandidates(folderroot.getParent());

		assertTrue(duplicates.isEmpty());

		FileUtils.deleteDirectory(folderroot);
	}

	@Test
	public void test_sizeCompare_equal() throws IOException {

		File folderroot = temporaryFolder.newFolder("root");
		temporaryFolder.newFolder("root", "sub1");
		temporaryFolder.newFolder("root", "sub2");

		File file1 = temporaryFolder.newFile("root/sub1/file1");
		FileUtils.writeStringToFile(file1, "TestString", StandardCharsets.UTF_8);
		File file2 = temporaryFolder.newFile("root/sub2/file2");
		File file3 = temporaryFolder.newFile("root/sub2/file3");

		DuplicateCheck checkForDuplicates = new DuplicateCheck();
		Collection<IDuplicate> duplicates = checkForDuplicates.compileCandidates(folderroot.getParent(),
				CompareMode.Size);

		assertFalse(duplicates.isEmpty());

		for (IDuplicate duplicate : duplicates) {

			assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file1.getPath())));
			assertTrue(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file2.getPath())));
			assertTrue(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file3.getPath())));
		}

		FileUtils.deleteDirectory(folderroot);
	}

	@Test
	public void test_sizeCompare_notEqual() throws IOException {

		File folderroot = temporaryFolder.newFolder("root");
		temporaryFolder.newFolder("root", "sub1");
		temporaryFolder.newFolder("root", "sub2");

		temporaryFolder.newFile("root/sub1/file1");
		temporaryFolder.newFile("root/sub2/file2");

		DuplicateCheck checkForDuplicates = new DuplicateCheck();
		Collection<IDuplicate> duplicates = checkForDuplicates.compileCandidates(folderroot.getParent());

		assertTrue(duplicates.isEmpty());

		FileUtils.deleteDirectory(folderroot);
	}

	@Test
	public void test_hashCompare_equal() throws IOException {

		File folderroot = temporaryFolder.newFolder("root");
		temporaryFolder.newFolder("root", "sub1");
		temporaryFolder.newFolder("root", "sub2");
		temporaryFolder.newFolder("root", "sub3");
		temporaryFolder.newFolder("root", "sub4");

		File file1 = temporaryFolder.newFile("root/sub1/file");
		File file2 = temporaryFolder.newFile("root/sub1/file2");
		File file3 = temporaryFolder.newFile("root/sub2/file");
		File file4 = temporaryFolder.newFile("root/sub2/file2");
		File file5 = temporaryFolder.newFile("root/sub3/file");
		FileUtils.writeStringToFile(file5, "TestString", StandardCharsets.UTF_8);
		File file6 = temporaryFolder.newFile("root/sub4/file");
		FileUtils.writeStringToFile(file6, "Teststring", StandardCharsets.UTF_8);

		DuplicateCheck checkForDuplicates = new DuplicateCheck();
		Collection<IDuplicate> nameSizeDuplicates = checkForDuplicates.compileCandidates(folderroot.getParent());

		Collection<IDuplicate> hashDuplicates = checkForDuplicates.checkCandidates(nameSizeDuplicates);

		assertTrue(hashDuplicates.size() == 2);

		for (IDuplicate duplicate : hashDuplicates) {

			if (duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file1.getPath()))) {

				assertTrue(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file1.getPath())));
				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file2.getPath())));
				assertTrue(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file3.getPath())));
				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file4.getPath())));
				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file5.getPath())));
				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file6.getPath())));

			} else if (!duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file1.getPath()))) {

				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file1.getPath())));
				assertTrue(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file2.getPath())));
				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file3.getPath())));
				assertTrue(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file4.getPath())));
				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file5.getPath())));
				assertFalse(duplicate.getFilepaths().stream().anyMatch(dup -> dup.equals(file6.getPath())));

			}

		}

		FileUtils.deleteDirectory(folderroot);
	}

	@Test
	public void test_hashCompare_isEmpty() throws IOException {

		File folderroot = temporaryFolder.newFolder("root");
		temporaryFolder.newFolder("root", "sub1");
		temporaryFolder.newFolder("root", "sub2");

		File file1 = temporaryFolder.newFile("root/sub1/file1");
		FileUtils.writeStringToFile(file1, "TestString", StandardCharsets.UTF_8);

		temporaryFolder.newFile("root/sub2/file2");

		DuplicateCheck checkForDuplicates = new DuplicateCheck();
		Collection<IDuplicate> duplicates = checkForDuplicates.compileCandidates(folderroot.getParent());

		assertTrue(duplicates.isEmpty());

		FileUtils.deleteDirectory(folderroot);
	}

}
