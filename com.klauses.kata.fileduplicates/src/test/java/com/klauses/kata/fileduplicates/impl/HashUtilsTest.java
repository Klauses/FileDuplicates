package com.klauses.kata.fileduplicates.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.klauses.kata.fileduplicates.utils.HashUtils;

public class HashUtilsTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void test_hashCompare_equal() throws IOException {

		File folderroot = temporaryFolder.newFolder("root");
		temporaryFolder.newFolder("root", "sub1");
		temporaryFolder.newFolder("root", "sub2");

		File file1 = temporaryFolder.newFile("root/sub1/file");
		File file2 = temporaryFolder.newFile("root/sub2/file");
		File file3 = temporaryFolder.newFile("root/sub2/file1");
		File file4 = temporaryFolder.newFile("root/sub2/file2");
		FileUtils.writeStringToFile(file4, "TestString", StandardCharsets.UTF_8);

		assertTrue(StringUtils.equals(HashUtils.getHash(file1.getAbsolutePath()),
				HashUtils.getHash(file2.getAbsolutePath())));
		assertTrue(StringUtils.equals(HashUtils.getHash(file2.getAbsolutePath()),
				HashUtils.getHash(file3.getAbsolutePath())));
		assertFalse(StringUtils.equals(HashUtils.getHash(file3.getAbsolutePath()),
				HashUtils.getHash(file4.getAbsolutePath())));

		FileUtils.deleteDirectory(folderroot);
	}

}
