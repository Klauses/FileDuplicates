package com.klauses.kata.fileduplicates.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

	/**
	 * SRP - Single Responsibility Principle A class should have one and only one
	 * reason to change
	 * 
	 * -> What if I want to change hash function (MD5 to SHA-512) 
	 * 
	 */
	private static MessageDigest messageDigest;
	static {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("cannot initialize MD5 hash function", e);
		}
	}

	public static String getHash(File file) {
		String result = null;
		try {
			// Read file as bytes
			FileInputStream fileInput = new FileInputStream(file);
			byte fileData[] = new byte[(int) file.length()];
			fileInput.read(fileData);
			fileInput.close();
			// Create unique hash for current file
			result = new BigInteger(1, messageDigest.digest(fileData)).toString(16);

		} catch (IOException e) {
			throw new RuntimeException("cannot read file " + file.getAbsolutePath(), e);
		}

		return result;
	}

	public static String getHash(String pathname) {
		File file = new File(pathname);

		return getHash(file);
	}
}
