package com.ihsan.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

public class CustomFileUtils {

	public static final String FILE_STORE_PATH = "C:\\FileStore";
	public static final String FILE_STORE_SUB_FOLDER_NAME = "ST";
	public static final int MIN_FOLDER_NUMBER = 0;
	public static final int MAX_FOLDER_NUMBER = 50;

	private static final Logger log = LoggerFactory.getLogger(CustomFileUtils.class);

	public static void main(String[] args) throws IOException {
		System.out.println(new CustomFileUtils().generateRandomStoreFolderPath());
	}

	public static String generateRandomFileName(String extension) {
		return UUID.randomUUID().toString() + "." + extension;
	}

	public static String getFileExtensionFromFileName(String fileName) {
		if (fileName.lastIndexOf(".") > 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		} else
			return null;
	}

	private static void createStoreFolders(String fileStorePath) {

		System.out.println("####### STARTED CREATING FOLDERS ##########");
		File level1Folder = null;
		File level2Folder = null;
		String level1Path = null;
		String level2Path = null;
		for (int i = MIN_FOLDER_NUMBER; i <= MAX_FOLDER_NUMBER; i++) {
			level1Path = fileStorePath + "\\" + FILE_STORE_SUB_FOLDER_NAME + i;
			level1Folder = new File(level1Path);
			level1Folder.mkdir();
			System.out.println("####### CREATED FOLDER: " + level1Folder.getPath());
			for (int j = MIN_FOLDER_NUMBER; j <= MAX_FOLDER_NUMBER; j++) {
				level2Path = fileStorePath + "\\" + FILE_STORE_SUB_FOLDER_NAME + i + "\\" + FILE_STORE_SUB_FOLDER_NAME
						+ j;
				level2Folder = new File(level2Path);
				level2Folder.mkdir();
			}
			System.out.println("####### CREATED SUB FOLDERS FOR: " + level1Folder.getPath());
		}
	}

	public byte[] getByteArrayFromEncoded64File(String base64File) {
		try {
			if (base64File == null)
				return null;
			byte[] bytes = new BASE64Decoder().decodeBuffer(base64File);
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String uploadFile(String storeFolderPath, String base64File) throws Exception {
		FileOutputStream fos = null;
		try {
			log.info("######## uploadFile,storeFolderPath: " + storeFolderPath);
			// String storeFolderPath = generateRandomStoreFolderPath();
			// String storeFolderPath =
			// "\\\\192.168.0.241\\CAPPS\\CAPPS_ATTACHMNET\\TM\\BOXES_CHECK_TRX_ATT\\";
			String fileName = generateRandomFileName("png");
			String filePath = storeFolderPath + fileName;

			log.info("######## uploadFile,filePath: " + filePath);

			byte[] decodedImg = getByteArrayFromEncoded64File(base64File);
			Path destinationFile = Paths.get(storeFolderPath, fileName);
			Files.write(destinationFile, decodedImg);

			return filePath;
		} catch (Exception e) {
			throw e;
		} finally {
			if (fos != null) {
				fos.flush();
				fos.close();
			}
		}
	}

	public String generateRandomStoreFolderPath() {
		int randomLevel1 = ThreadLocalRandom.current().nextInt(MIN_FOLDER_NUMBER, MAX_FOLDER_NUMBER + 1);
		int randomLevel2 = ThreadLocalRandom.current().nextInt(MIN_FOLDER_NUMBER, MAX_FOLDER_NUMBER + 1);
		return FILE_STORE_PATH + "\\" + FILE_STORE_SUB_FOLDER_NAME + randomLevel1 + "\\" + FILE_STORE_SUB_FOLDER_NAME
				+ randomLevel2 + "\\";
	}

}
