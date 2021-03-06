/*
 * Copyright 2001-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nearor.framwork.secure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Operations to simplifiy common {@link MessageDigest} tasks.
 * This class is thread safe.
 *
 * @author Apache Software Foundation
 */
public class DigestUtil {
	public static final String MD5 = "md5";
	public static final String SHA1 = "SHA-1";
	public static final String HmacSHA1 = "HmacSHA1";

	/**
	 * Returns a MessageDigest for the given <code>algorithm</code>.
	 *
	 * @param algorithm
	 *            The MessageDigest algorithm name.
	 * @return An MD5 digest instance.
	 * @throws RuntimeException
	 *             when a {@link NoSuchAlgorithmException} is
	 *             caught,
	 */
	static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Returns an MD5 MessageDigest.
	 *
	 * @return An MD5 digest instance.
	 * @throws RuntimeException
	 *             when a {@link NoSuchAlgorithmException} is
	 *             caught,
	 */
	private static MessageDigest getMd5Digest() {
		return getDigest("MD5");
	}

	/**
	 * Returns an SHA digest.
	 *
	 * @return An SHA digest instance.
	 * @throws RuntimeException
	 *             when a {@link NoSuchAlgorithmException} is
	 *             caught,
	 */
	private static MessageDigest getShaDigest() {
		return getDigest("SHA");
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 16 element
	 * <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest
	 */
	public static byte[] md5(byte[] data) {
		return getMd5Digest().digest(data);
	}

	public static String md5File(File file) {
		String md5 = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] buffer = new byte[4096];
			int size = 0;
			MessageDigest md = getMd5Digest();
			while ((size = fileInputStream.read(buffer)) !=-1) {
				md.update(buffer, 0, size);
			}
			md5 = new String(Hex.encodeHex(md.digest()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		};
		return md5;
	}
	
	
	/**
	 * Calculates the MD5 digest and returns the value as a 16 element
	 * <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest
	 */
	public static byte[] md5(String data) {
		return md5(data.getBytes());
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex
	 * string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest as a hex string
	 */
	public static String md5Hex(byte[] data) {
		return new String(Hex.encodeHex(md5(data)));
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex
	 * string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest as a hex string
	 */
	public static String md5Hex(String data) {
		return new String(Hex.encodeHex(md5(data)));
	}

	/**
	 * Calculates the SHA digest and returns the value as a <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA digest
	 */
	public static byte[] sha(byte[] data) {
		return getShaDigest().digest(data);
	}

	/**
	 * Calculates the SHA digest and returns the value as a <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA digest
	 */
	public static byte[] sha(String data) {
		return sha(data.getBytes());
	}

	/**
	 * Calculates the SHA digest and returns the value as a hex string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA digest as a hex string
	 */
	public static String shaHex(byte[] data) {
		return new String(Hex.encodeHex(sha(data)));
	}

	/**
	 * Calculates the SHA digest and returns the value as a hex string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return SHA digest as a hex string
	 */
	public static String shaHex(String data) {
		return new String(Hex.encodeHex(sha(data)));
	}

	/**
	 * HmacSHA1 生成签名值
	 * 
	 * @param base
	 *            待签名原文
	 * @param aspPassword
	 * @param tempToken
	 *            tempToken
	 * @return
	 */
	public static String hmacSHA1(String base, String key) {
		try {

			Mac mac = Mac.getInstance(HmacSHA1);

			if (key != null) {
				SecretKeySpec spec = new SecretKeySpec(key.getBytes(), HmacSHA1);
				mac.init(spec);
			}

			byte[] bytes = mac.doFinal(base.getBytes());
			return new String(Hex.encodeHex(bytes));
		} catch (Exception e) {
		}
		return null;
	}
}
